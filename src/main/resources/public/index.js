$(document).ready(function (){
    $('#nav').load("common/_nav.html");

    getMuralCards("/murals");
    //TODO: http://127.0.0.1:8080/murals?page=1&size=10  FILTER?
    	function getMuralCards(url){
    		$('#mural-card').empty();

            $.ajax({
                url: url
            }).then(function(data) {
            	setCards(data._embedded.murals);
            	setPagination(data);
            	setImages(data._embedded.murals);
            });
        }

        function setCards(murals){
        	for(let mural of murals){
        		var html = "<div class='card col-sm-12 col-lg-12'>\n"
        		+ "<img id='"+ mural.mural_registration_id +"' class='card-img-top' src='common/images/no-image.png' alt='no image'>\n"
        		+ "<div class='card-body'>\n"
        		+ "<span class='card-title'>"+ mural.artwork_title +"</span>\n"
        		+ "<p>"+ mural.street_address +"</p>\n"
        		+ "</div>\n"
        		+ "<div class='card-footer'>\n"
        		+ "<a href='/mural/mural-details.html?muralRegistrationId="+ mural.mural_registration_id + "'>More Details</a>\n"
        		+ "</div>\n"
        		+ "</div>"
        		$('#mural-card').append(html);

        	}
        }

        function setPagination(pagination){
        	pageNumber = pagination.page.number;
        	pageCount = pagination.page.totalPages;
        	$('#pagination').empty();

        	for(var i=1; i <= pageCount; i++){
        		//set left chevron
        		if(i === 1){
        			$('#pagination').append('<li id="left-chevron" class="page-item"><a class="page-link" href="#" tabindex="-1">Previous</a></li>');
        		}
        		//set normal page
        		$('#pagination').append('<li class="page-item"><a  class="page-link" href="#!">'+ i +'</a></li>');
        		//set right chevron
        		if(i === pageCount){
        			$('#pagination').append('<li id="right-chevron" class="page-item"><a class="page-link" href="#!">Next</a></li>');
        		}
        	}

            setPaginationClassAttributes(pageNumber, pageCount);
        }

        function setPaginationClassAttributes(pageNumber, pageCount){
        	// highlight active page
        	$('#pagination').find('li').find('a').eq(pageNumber + 1).parent("li").addClass("active");
        	// disable left chevron
        	if(pageNumber === 0){
        		$('#left-chevron').addClass("disabled");
        	}
        	//disable right chenvron
        	if(pageNumber === (pageCount -1)){
        		$('#right-chevron').addClass("disabled");
        	}

        }

        //function to handle pagination on click event
        $('#pagination').click(function(event) {
        	event.preventDefault();

        	target = event.target.innerText;
        	chevronPageAction(target);

        	pageNumber = event.target.text - 1;

        	if(target != "Previous" && target != "Next"){
        		url = "murals/?page=" + pageNumber;
        		getMuralCards(url);
        	}

        });

        function chevronPageAction(target){
        	currentPage = $('#pagination li.active').text();
        	// do nothing
        	if((target != "Previous") && (target != "Next")){
        		return;
        	}else if((target === "Previous" && currentPage === "1") || (target === "Next" && currentPage === "18")){ //TODO: 18 is the number of pages- refactor
        		return;
        	}

        	if(target === "Previous"){
        		goToPageNumber = currentPage -2
        		url = "murals?page=" + goToPageNumber;
            	getMuralCards(url);
        	}

        	if(target === "Next"){
        		goToPageNumber = currentPage;
        		url = "murals?page=" + goToPageNumber;
            	getMuralCards(url);
        	}
        }

        function setImages(murals){
            // for each mural get image url
            for(let mural of murals){
                // find image url
                let url = "/mural/" + mural.mural_registration_id + "/aws-url";
                $.ajax({
                    url: url
                }).then(function(data) {
                    if(data.length > 1){
                        $('#'+mural.mural_registration_id).attr("src", data);
                    }
                });

            }
        }
})
