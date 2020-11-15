$(document).ready(function (){
    $('#nav').load("../common/_nav.html");
    vm = this;
    vm.mural = {};

    let searchParams = new URLSearchParams(window.location.search);

    if(searchParams.has('muralRegistrationId')){
        var muralRegistrationId = searchParams.get('muralRegistrationId');

        $.ajax({
            url: "/mural/details/" + muralRegistrationId
        }).then(function(data) {
            setDescriptionDetails(data);
            setImages(data);
            vm.mural = data;
        });
    }

    function setDescriptionDetails(mural){
        title = mural.artwork_title;
        artist = mural.artist_credit;
        commissioned = mural.affiliated_or_commissioning;
        description = mural.description_of_artwork;
        address = mural.street_address;
        // find and set text
        $('#title').append(" " + title);
        $('#artist').append(" " + artist);
        $('#commissioned').append(" " + commissioned);
        $('#description').append(" " + description);
        $('#address').append(" " + address);
    }

    function setImages(mural){
        let images = mural.muralImageUploads;

        for(var i=0;i < images.length; i++){
            if(i < 1){
                $('#image').attr("src", images[i].awsUrl);
            }else{
                $('.other-images').append("<div class='card'>" +
                    "<img class='side-image' alt='image loading' src=" + images[i].awsUrl + ">" +
                    "</div>"
                );
            }
        }
    }

    $('#image-upload-form').on('submit', function(event){
        event.preventDefault();

        $.ajax({
            url: "/mural/ "+ vm.mural.id +"/aws-image-upload",
            type: "POST",
            data: new FormData(this),
            processData: false,
            contentType: false,
            cache: false
        }).then(function(data){
            swal({
                title: "Success!",
                text: "You Uploaded an Image",
                icon: "success",
                timer: 2000
            }).then(function(){
                location.reload();
            });
        }).fail(function(error){
            console.log(error);
            swal({
                title: "Error!",
                text: "Could not upload image \n" + error.responseJSON.message,
                icon: "error"
            });
        });
    });


})