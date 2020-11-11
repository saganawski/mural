package com.chicago.mural.mural.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import com.chicago.mural.mural.Mural;
import com.chicago.mural.mural.dao.JpaMuralRepo;
import com.chicago.mural.securtiy.UserPrincipal;
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

    @Override
    public Mural getMural(int muralRegistrationId) {
        final Mural mural = jpaMuralRepo.findByMuralRegistrationId(muralRegistrationId);
        return mural;
    }

    @Override
    public ResponseEntity<String> awsImageUpload(int muralId, UserPrincipal userPrincipal, MultipartFile file) {
        //TODO: remove hard codes
        Regions clientRegion = Regions.US_EAST_2;
        String bucketName = "aws-mural-images";
        String fileName = file.getOriginalFilename();

        try{
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(clientRegion)
                    .build();

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getInputStream().available());
            metadata.setContentType(file.getContentType());

            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, file.getInputStream(),metadata);
            s3Client.putObject(putObjectRequest);

        } catch (AmazonS3Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>("s3 exception \n" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (SdkClientException e){
            e.printStackTrace();
            return new ResponseEntity<String>("SdkClientException \n" + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<String>("Io Exception \n" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>("UpLoad successful!", HttpStatus.OK);
    }

    @Override
    public List<String> getMuralImages(int muralId) {
        //find mural
        // get images for murals

        final List<String> base64Strings = new ArrayList<>();
        //TODO: remove hard codes
        Regions clientRegion = Regions.US_EAST_2;
        String bucketName = "aws-mural-images";
        String key = "profile.jpg";

        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(clientRegion)
                .build();
        S3Object s3Object = s3Client.getObject(bucketName,key);
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
        
        return base64Strings;
    }
}
