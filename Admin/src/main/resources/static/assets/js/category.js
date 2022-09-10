
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

//chart
        $(document).ready(
                function() {
                    $.ajax({
                        url : "/chart",
                        success : function(result) {
                            var month = JSON.parse(result).month;
                            var total = JSON.parse(result).total;
                            
                            drawChart(month, total);
                        }
                    });
                });
        function drawChart(month, total) {
            Highcharts.chart('container-bar', {
                chart : {
                    type : 'column',
                    styledMode : true
                },
                tooltip: {
				    formatter: function() {
				        return '<b>' + this.x + '</b> : <b>' + this.y + '</b>';
				    }
				},
                title : {
                    text : 'Biểu đồ doanh thu theo tháng'
                },
                xAxis : [ {
                    title : {
                        text : 'Tháng'
                    },
                    categories : month
                } ],
                yAxis : [ {
                    className : 'highcharts-color-0',
                    title : {
                        text : 'Doanh thu'
                    }
                } ],
                series : [ {
                    data : total
                } ]
            });
        }


