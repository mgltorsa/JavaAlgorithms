$(document).ready(changeHeader);

function changeHeader(){
	$(window).scroll(changeClassHeader);
}

function changeClassHeader(){
	if($(this).scrollTop()>0){
		addClassHeader("header2");
	}else{
		removeClassHeader("header2");
	}
}

function addClassHeader(className)
{
	$("header").addClass(className);
}

function removeClassHeader(className){
	$("header").removeClass(className);
}