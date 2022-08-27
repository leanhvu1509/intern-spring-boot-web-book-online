$(function () {
	$("#rateYo").rateYo({
		rating:0,
		numStars:5,
		maxValue:5,
		halfStar:true,
		onChange: function (number, rateYoInstance) {
			$(this).next().text(number);
			$('#number').val(number);
		
	}
});
})

function viewOrder(id) {
    $.ajax({
        url: "/account/order/" + id,
        success: function(response) {
            $('#detailHolder').html(response);
            $('#detailModal').modal();
        }
    });
}										