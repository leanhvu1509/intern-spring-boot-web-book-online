//Order
$(function() {
    $('#order-table').DataTable({
        pageLength: 10,
		columnDefs: [
	 	   {
		      "targets": 0,
		      "className": "text-center",
		   },
		   {
		      "targets": 2,
		      "className": "text-right",
		   },
		   {
		      "targets": 3,
		      "className": "text-center",
		   },
		   {
		      "targets": 4,
		      "className": "text-right",
		   },
	    ],
		language: {
        	url: '//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Vietnamese.json'
    	}
    });
})

$(function() {
    $('#example-table').DataTable({
        pageLength: 10,
	
		language: {
        	url: '//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Vietnamese.json'
    	}
    });
})