var app = {};

app.model = {


getProjectWiseTimeSheet: function() {
	var projectId = $('.projectId').val();
	var table = $('#exampleForAjaxDataTable').DataTable({
		"sAjaxSource": "/api/timesheet/"+projectId+"/project",
		"sAjaxDataProp": "",
		"aoColumns": [
			{
				'data': 'id',
				 "render": function (data, type,full, row) {
			            var val=row.row+1;
			            return val;
			        }
			},
			{
				'data': 'ticket_id',
				'render': function (data, type, row, meta) {
					data = '<img src="/dist/img/Edit2.png" height="25px;" data-toggle="modal"	data-target="#TimeLogEdit-modal" >'
					return data;
				}
			},
			{"data": "ticket_name"},
			{"data": "project_name"},
			{
				"data": "log_date",
				'render': function (data, type, row, meta) {
					let utcDate = moment(data);
					data = '<span  style="white-space: nowrap">' + utcDate.format("DD-MM-YYYY") + '</span>';
					return data;
				}
			},
			{"data": "fromtime"},
			{"data": "totime"},
			{"data": "TimeDiff"},
			{"data": "employee_name"},
			{"data": "remarks"}
		],
        dom: "<'row'<'col-sm-4'B><'col-sm-6'><'col-sm-2'f>>" +
        "<'row'<'col-sm-12'tr>>" +
        "<'row'<'col-sm-4'i><'col-sm-6'><'col-sm-2'p>>",      
        buttons: [
            'copy', 'csv', 'excel', 'pdf', 'print'
        ]
	})
},
getProjectWiseAttachments: function() {
	var projectId = $('.projectId').val();
	var table = $('#AttachmentsInfoProjectWise').DataTable({
		"sAjaxSource": "/api/attachments/"+projectId+"/project",
		"sAjaxDataProp": "",
		"aoColumns": [
			{
				'data': 'id',
				 "render": function (data, type,full, row) {
			            var val=row.row+1;
			            return val;
			        }
			},
			{
				'data': 'ticket_id',
				
			},
			{"data": "ticket_name"},
			{"data": "project_name"},
			{
				"data": "file_name",
				"data2": "filePath",
				'render': function (data, type, row, meta) {
					data = '<a th:href="" target="_blank" height="35px;"><span>'+data+'</span></a>';
					return data;
				}
			}
		],
        dom: "<'row'<'col-sm-4'B><'col-sm-6'><'col-sm-2'f>>" +
        "<'row'<'col-sm-12'tr>>" +
        "<'row'<'col-sm-4'i><'col-sm-6'><'col-sm-2'p>>",      
        buttons: [
            'copy', 'csv', 'excel', 'pdf', 'print'
        ]
	})
},
getProjectWiseTimeSheetTest : function(ele){
	var projectId = $('.projectId').val();
	$.ajax({
			method: 'GET',
			url: "/api/timesheet/"+projectId+"/project",
			success: function(data) {
				processTimeSheetData(data);
			}
		});
		let processTimeSheetData = (data) => {
			let Info = ' ';
			var i = 1;
			data.forEach((data) => {
							
				var mydate  = `${data.log_date}`;
				//var CreatedOndate  = `${data.createdOn}`;
				// Create a UTC date object. The moment constructor will recognize the date as UTC since it includes the 'Z' timezone specifier.
				let utcDate =moment(mydate);
				//let CreatedutcDate =moment(CreatedOndate);
			
			Info += `<tr><td>${i}</td>
			<td hidden>${data.id}</td>
			<td style="cursor: pointer;"><img src="/dist/img/Edit2.png" height="25px;" data-toggle="modal"	data-target="#TimeLogEdit-modal" title=${data.fromtime} '-' ${data.fromtime} ></td>
			<td>${data.ticket_name}</td>
			<td>${data.project_name}</td>
			<td  class="text-center">${utcDate.format("DD-MM-YYYY")}</td>
			<td hidden>${data.logDate}</td>
			<td class="text-center">${data.fromtime}</td>
			<td  class="text-center">${data.totime}</td>
			<td  class="text-center">${data.TimeDiff}</td>
			<td>${data.employee_name}</td>
			<td>${data.remarks}</td></tr>`;
	i++;
					
			});
			//$('#example1>tbody').html(Info);
			if(Info.length > 2){
				$('.visable-TimeSheetData').show();
				$('#ValidateTimeSheetData').hide();
				$('.ProjectWiseLogHoursInfo').empty().append(Info);
			}
			else{
				$('#ValidateTimeSheetData').show();
				$('.visable-TimeSheetData').hide();
			}
			
		};
}

}