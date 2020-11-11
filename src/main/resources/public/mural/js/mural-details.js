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
            vm.mural = data;
            console.log(data);
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
        //TODO: confirmation and add image / reload
            console.log(data);
        }).fail(function(error){
        //TODO: sweetalert
            console.log(error);
        });
    });

    getMuralImages(vm.mural.id);
    function getMuralImages(muralId){
        $.ajax({
            url: "/mural/ "+ muralRegistrationId +"/aws-image-download",
            type: "GET"
        }).then(function(murals){
            for(var i=0;i < murals.length; i++){
                if(i < 1){
                    $('#image').attr("src", "data:image/jpeg;base64," + murals[i]);
                }else{
                    $('#image').append("<img class='center-align' alt='' src='data:image/jpeg;base64,'"+murals[i] );
                }
            }

        }).fail(function(error){
            console.log(error);
        });

    }

})