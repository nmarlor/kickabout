$(document).ready(function(){
	
	$.ajaxSetup({ cache: false });
    
    $("#select-file").click(function(){
    	$("#file").trigger("click");
    });
    $("#file").change(function(){
		document.getElementById('file-name').value = this.value.split('\\').pop().split('/').pop();
	});
    $("#file-name").click(function(){
    	$("#file").trigger("click");
    });
    $(".upload-button").click(function(){
		$(this).css("display", "none");
		$(".loader").css("display", "inline-block");
	});
    
});