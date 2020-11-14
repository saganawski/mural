package com.chicago.mural.mural.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import com.chicago.mural.mural.Mural;
import com.chicago.mural.mural.MuralImageUpload;
import com.chicago.mural.mural.dao.JpaMuralImageUploadRepo;
import com.chicago.mural.mural.dao.JpaMuralRepo;
import com.chicago.mural.mural.dto.MuralDTO;
import com.chicago.mural.securtiy.UserPrincipal;
import com.chicago.mural.user.User;
import com.chicago.mural.user.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
@Transactional
public class MuralServiceImpl implements MuralService {
    @Autowired
    private JpaMuralRepo jpaMuralRepo;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JpaMuralImageUploadRepo jpaMuralImageUploadRepo;

    @Override
    public MuralDTO getMural(int muralRegistrationId) {
        final Mural mural = jpaMuralRepo.findByMuralRegistrationId(muralRegistrationId);
        final MuralDTO muralDTO = MuralDTO.builder()
                .id(mural.getId())
                .mural_registration_id(mural.getMural_registration_id())
                .artwork_title(mural.getArtwork_title())
                .artist_credit(mural.getArtist_credit())
                .affiliated_or_commissioning(mural.getAffiliated_or_commissioning())
                .description_of_artwork(mural.getDescription_of_artwork())
                .street_address(mural.getStreet_address())
                .ward(mural.getWard())
                .build();
        return muralDTO;
    }

    @Override
    public ResponseEntity<String> awsImageUpload(int muralId, UserPrincipal userPrincipal, MultipartFile file) {
        final Mural mural = jpaMuralRepo.getOne(muralId);
        final Integer userId = userPrincipal.getUserId();
        final User user = userRepository.getOne(userId);

        //TODO: remove hard codes
        Regions clientRegion = Regions.US_EAST_2;
        String bucketName = "aws-mural-images";
        String fileName = user.getUsername() + "-" + file.getOriginalFilename();

        try{
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(clientRegion)
                    .build();

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getInputStream().available());
            metadata.setContentType(file.getContentType());

            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, file.getInputStream(),metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead);

            s3Client.putObject(putObjectRequest);

            final String awsUrl = s3Client.getUrl(bucketName, fileName).toExternalForm();

            final MuralImageUpload muralImageUpload = MuralImageUpload.builder()
                    .mural(mural)
                    .user(user)
                    .awsKey(putObjectRequest.getKey())
                    .awsBucketName(putObjectRequest.getBucketName())
                    .awsUrl(awsUrl)
                    .likes(0)
                    .updatedBy(userId)
                    .createdBy(userId)
                    .build();

            jpaMuralImageUploadRepo.save(muralImageUpload);
            mural.getMuralImageUploads().add(muralImageUpload);
            jpaMuralRepo.save(mural);

        } catch (AmazonS3Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>("s3 exception \n" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (SdkClientException e){
            e.printStackTrace();
            return new ResponseEntity<String>("SdkClientException \n" + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<String>("Io Exception \n" + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>(" Exception \n" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>("UpLoad successful!", HttpStatus.OK);
    }

    @Override
    public List<String> getMuralImages(int muralRegistrationId) {
        final Mural mural = jpaMuralRepo.findByMuralRegistrationId(muralRegistrationId);
        final List<MuralImageUpload> muralImageUploads = mural.getMuralImageUploads();

        final List<String> base64Strings = new ArrayList<>();

        muralImageUploads.forEach(mi ->{

            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(Regions.US_EAST_2)
                    .build();
            S3Object s3Object = s3Client.getObject(mi.getAwsBucketName(),mi.getAwsKey());
            S3ObjectInputStream s3ObjectInputStream = s3Object.getObjectContent();
            // TODO: encoding takes alot of time maybe switch to just adding the url to image src?
            byte[] bytes = null;
            try {
                bytes = IOUtils.toByteArray(s3ObjectInputStream);

            } catch (IOException e) {
                e.printStackTrace();
            }

            final String encodedString = Base64.getEncoder().encodeToString(bytes);
            base64Strings.add(encodedString);

        });

        return base64Strings;
    }
}
