$(document).ready(function (){
    $('#nav').load("common/_nav.html");

    getMuralCards("/murals");

    	function getMuralCards(url){
    		$('#mural-card').empty();

            $.ajax({
                url: url
            }).then(function(data) {
            	setCards(data._embedded.murals);
//            	setPagination(data);
            });
        }

        function setCards(murals){
        	for(let mural of murals){
        		var html = "<div class='card col-sm-12 col-lg-12'>\n"
        		+ "<img class='card-img-top' src='common/images/no-image.png' alt='no image'>\n"
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
        			$('#pagination').append('<li id="left-chevron" class="waves-effect"><a href="#!"><i class="material-icons">chevron_left</i></a></li>');
        		}
        		//set normal page
        		$('#pagination').append('<li class="waves-effect"><a href="#!">'+ i +'</a></li>');
        		//set right chevron
        		if(i === pageCount){
        			$('#pagination').append('<li id="right-chevron" class="waves-effect"><a href="#!"><i class="material-icons">chevron_right</i></a></li>');
        		}

        		setPaginationClassAttributes(pageNumber, pageCount);
        	}
        }

        function setPaginationClassAttributes(pageNumber, pageCount){
        	// highlight active page
        	$('#pagination').find('li').find('a').eq(pageNumber + 1).parent("li").removeClass("waves-effect").addClass("active");

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
        	cheveronPageAction(target);

        	pageNumber = event.target.text - 1;

        	if(target != "chevron_left" && target != "chevron_right"){
        		url = "murals/?page=" + pageNumber;
        		getMuralCards(url);
        	}

        });

        function cheveronPageAction(target){
        	currentPage = $('#pagination li.active').text();
        	// do nothing
        	if((target != "chevron_left") && (target != "chevron_right")){
        		return;
        	}else if((target === "chevron_left" && currentPage === "1") || (target === "chevron_right" && currentPage === "15")){
        		return;
        	}

        	if(target === "chevron_left"){
        		goToPageNumber = currentPage -2
        		url = "murals?page=" + goToPageNumber;
            	getMuralCards(url);
        	}

        	if(target === "chevron_right"){
        		goToPageNumber = currentPage;
        		url = "murals?page=" + goToPageNumber;
            	getMuralCards(url);
        	}
        }
})
