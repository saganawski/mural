$(document).ready(function (){
    $('#nav').load("../common/_nav.html");

    let searchParams = new URLSearchParams(window.location.search);

    if(searchParams.has('muralRegistrationId')){
        var muralRegistrationId = searchParams.get('muralRegistrationId');

        $.ajax({
            url: "/mural/details/" + muralRegistrationId
        }).then(function(data) {
            setDescriptionDetails(data);
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
})