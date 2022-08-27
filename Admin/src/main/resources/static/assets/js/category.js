
function viewOrder(id) {
    $.ajax({
        url: "/order/" + id,
        success: function(response) {
            $('#detailHolder').html(response);
            $('#detailModal').modal();
        }
    });
}

function viewCategory(id) {
    $.ajax({
        url: "/category/" + id,
        success: function(response) {
            $('#detailHolder').html(response);
            $('#detailModal').modal();
        }
    });
}

function chooseFile(fileInput) {
	if (fileInput.files && fileInput.files[0]) {
		var reader = new FileReader();
		reader.onload = function() {
			var output = document.getElementById('image');
			output.src = reader.result;
		}
		reader.readAsDataURL(fileInput.files[0]);
	}
}

