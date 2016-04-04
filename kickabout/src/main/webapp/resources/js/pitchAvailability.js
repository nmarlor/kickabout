$(document).ready(function(){
	
	$('.datepicker').datepicker();
	
	$('.switch').removeClass("switch");
	$('#availabilityDateCal').click(function(){
		$("#availability-picker-date").trigger("select");
	});
	
	$('#availability-picker-date').on("changeDate", function(){
		$('.search-date-btn').trigger("click");
	});
	
	var date = $('#datepicker').datepicker({ dateFormat: 'dd-mm-yy' }).val();
});