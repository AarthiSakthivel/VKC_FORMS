var app = {};
var Toast = Swal.mixin({
	toast: true,
	position: 'top-end',
	showConfirmButton: false,
	timer: 3000
});
app.model = {
	displayProjectAdd: function(ele) {
		$(ele).attr("hidden", true);
		$('.projectAddCard').attr("hidden", false);
	},
	copyToClipboard : function(ele){
		
		 var copyText = $(ele).closest('tr')[0].dataset.taskid;

  // Select the text field
  //copyText.select();
 // copyText.setSelectionRange(0, 99999); // For mobile devices

   // Copy the text inside the text field
  navigator.clipboard.readText(copyText);

  // Alert the copied text
//  alert("Copied the text: " + copyText);
	},
	getClientData: function(ele) {
		$.ajax({
			method: 'GET',
			url: "/api/client/list",
			success: function(data) {
				processClientData(data);
			}
		});
		let processClientData = (data) => {
			let Info = ' ';
			var i = 1;
			let clientIdAndName = '<option selected disabled value="">-- Select Client --</option>';
			data.forEach((data) => {
				const mydate  = `${data.createdOn}`;
				// Create a UTC date object. The moment constructor will recognize the date as UTC since it includes the 'Z' timezone specifier.
				let utcDate = moment(mydate);

				Info += `<tr><td>${i}</td>
									<td class="text-center" data-toggle="modal"	data-target="#ClientPreview"><img src="/dist/img/preview4.png" height="25px;"></td>
										<td  style="white-space: nowrap">${data.clientName}</td>
										<td  style="white-space: nowrap">${data.email}</td>
										<td  style="white-space: nowrap">${data.contactPerson}</td>
										<td data-toggle="tooltip" title=${data.address}>
											<span class="leftDesc">${data.address}</span>
										</td>
										<td  style="white-space: nowrap">${data.contactNo}</td>
										<td  style="white-space: nowrap">${data.state}</td>
										<td  style="white-space: nowrap"><a href="${data.website}" target="_blank">${data.website}</a></td>`;
										/*<td  style="white-space: nowrap">${utcDate.format("DD-MM-YYYY HH:mm:ss")}</td>`*/
										if(`${data.status}` == 'true'){
											Info += `<td><span class="badge badge-success">Active</span></td>`;
										}else{
											Info += `<td><span class="badge badge-danger">InActive</span></td>`;
										}
										
									Info +=`</tr>`;
				clientIdAndName += `<option value=${data.id}>${data.clientName}</option>`;			
				i++;		
			});
			//$('#example1>tbody').html(Info);
			$('#clientInfo').empty().append(Info);
			$('.clientIdNameInfoInSelectAttr').html(clientIdAndName);
		};

	},
	
	clientRegister: function() {
		var companyName = $('.companyName').val();
		var companyEmail = $('.companyEmail').val();
		var companyMobileNo = $('.companyMobileNo').val();
		var companyContactPerson = $('.companyContactPerson').val();
		var companyWebsiteUrl = $('.companyWebsiteUrl').val();
		var companyState = $('.companyState').val();
		var companyAddress = $('.companyAddress').val();
		var companyRemarks = $('.companyRemarks').val();
		var companyCountry = $('.companyCountry').val();

		if (companyName.trim().length == 0) {
			Swal.fire("Invalid Company Name", "Please Enter Valid Company Name", "error");
			return;
		}
		if (companyEmail.trim().length == 0) {
			Swal.fire("Invalid Company Email", "Please Enter Valid Company Email", "error");
			return;
		}
		console.log(ValidateEmail(companyEmail));
		
		if (!ValidateEmail(companyEmail)){
			Swal.fire("Invalid Company Email", "Please Enter Valid Company Email", "error");
			return;
		}
		
		/*if (companyMobileNo.trim().length == 0) {
			Swal.fire("Invalid Company MobileNumner", "Please Enter Valid Company MobileNumner", "error");
			return;
		}*/
		if (companyContactPerson.trim().length == 0) {
			Swal.fire("Invalid Company Contact Person", "Please Enter Valid Company ContactPerson Details", "error");
			return;
		}
		if (companyState.trim().length == 0) {
			Swal.fire("Invalid State !!", "Please Enter the State Information", "error");
			return;
		}
		if (companyCountry.trim().length == 0) {
			Swal.fire("Invalid Country !!", "Please Enter the Country Information", "error");
			return;
		}
		if (companyAddress.trim().length == 0) {
			Swal.fire("Invalid Address Inforamtion !!", "Please Enter the Company Address!!", "error");
			return;
		}
		
		
		function ValidateEmail(mail) {
			  var re = /\S+@\S+\.\S+/;
  return re.test(mail);
		}

		var dataToSend = {
			"clientName": companyName,
			"email": companyEmail,
			"mobileNo": companyMobileNo,
			"contactPerson": companyContactPerson,
			"websiteUrl": companyWebsiteUrl,
			"state": companyState,
			"country": companyCountry,
			"address": companyAddress,
			"remarks": companyRemarks
		}
				var myhtml = document.createElement("div");
		myhtml.innerHTML = "<span style=color:blue><b> Your Request is Processing !! </b></span></br></br>" +
			"<span style=color:black> Please Wait... </span>";
		swal.fire({
			html: myhtml,
			showCloseButton: true,
			showConfirmButton: false,			
		});

		$.ajax({
			method: 'POST',
			url: "/admin/client/add",
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(dataToSend),

			success: function(data) {
				Swal.fire({
					icon: 'success',
					title: data.body.message
				}).then((result) => { window.location.reload(); })
			},
			error: function(data) {
				Swal.fire({
					icon: 'error',
					title: 'Unable to Add the Client !!'
				})
			}

		});
	},
	clientUpdate: function() {
		var clientId = $('.clientId').val();
		var clientName = $('.companyName').val();
		var companyEmail = $('.companyEmail').val();
		var companyMobileNo = $('.companyMobileNo').val();
		var companyContactPerson = $('.companyContactPerson').val();
		var companyWebsiteUrl = $('.companyWebsiteUrl').val();
		var companyState = $('.companyState').val();
		var companyAddress = $('.companyAddress').val();
		var companyRemarks = $('.companyRemarks').val();
		var companyCountry = $('.companyCountry').val();
		var status = $('.status').val();

		if (clientName.trim().length == 0) {
			Swal.fire("Invalid Company Name", "Please Enter Valid Company Name", "error");
			return;
		}
		if (companyEmail.trim().length == 0) {
			Swal.fire("Invalid Company Email", "Please Enter Valid Company Email", "error");
			return;
		}
		
		if (companyContactPerson.trim().length == 0) {
			Swal.fire("Invalid Company Contact Person", "Please Enter Valid Company ContactPerson Details", "error");
			return;
		}
		if (companyState == null) {
			Swal.fire("Invalid State !!", "Please Enter the State Information", "error");
			return;
		}
		if (companyCountry == null) {
			Swal.fire("Invalid Country !!", "Please Enter the Country Information", "error");
			return;
		}
		if (companyAddress.trim().length == 0) {
			Swal.fire("Invalid Address Inforamtion !!", "Please Enter the Company Address!!", "error");
			return;
		}

		var dataToSend = {
			"clientId" : clientId,
			"clientName": clientName,
			"email": companyEmail,
			"mobileNo": companyMobileNo,
			"contactPerson": companyContactPerson,
			"websiteUrl": companyWebsiteUrl,
			"state": companyState,
			"country": companyCountry,
			"address": companyAddress,
			"remarks": companyRemarks,
			"status":status
		}
				var myhtml = document.createElement("div");
		myhtml.innerHTML = "<span style=color:blue><b> Your Request is Processing !! </b></span></br></br>" +
			"<span style=color:black> Please Wait... </span>";
		swal.fire({
			html: myhtml,
			showCloseButton: true,
			showConfirmButton: false,			
		});

		$.ajax({
			method: 'POST',
			url: "/admin/client/update",
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(dataToSend),

			success: function(data) {
				Swal.fire({
					icon: 'success',
					title: data.body.message
				}).then((result) => { window.location = "/admin/client/list"; })
			},
			error: function(data) {
				Swal.fire({
					icon: 'error',
					title: ' Not Successfully Added Company !!'
				})
			}

		});
	},

	getEmployeeData: function(ele) {
		$.ajax({
			method: 'GET',
			url: "/api/employees/list",
			success: function(data) {
				processEmployeeData(data);
			}
		});
		let processEmployeeData = (data) => {
			let Info = ' ';
			data.forEach((data) => {
				const mydate  = `${data.createdOn}`;
				// Create a UTC date object. The moment constructor will recognize the date as UTC since it includes the 'Z' timezone specifier.
				let utcDate = moment(mydate); //${utcDate.format("DD-MM-YYYY HH:mm:ss")}

				Info += `<tr><td>1</td>
									<td class="text-center" data-toggle="modal"	data-target="#EmployeePreview"><img src="/dist/img/preview4.png" height="25px;"></td>
										<td  style="white-space: nowrap">${data.employeeName}</td>
										<td  style="white-space: nowrap">${data.email}</td>
										<td  style="white-space: nowrap">${data.contactNo}</td>`;
										if (`${data.groupId}` == `null`){
											Info += `<td  style="white-space: nowrap">-</td>`;
										}
										else{
											Info += `<td  style="white-space: nowrap">${data.groupId.groupName}</td>`;
										}
										if (`${data.reportingTo}` == `null` ){
											Info += `<td  style="white-space: nowrap">-</td>`;
										}
										else{
											Info += `<td  style="white-space: nowrap">${data.reportingTo.employeeName}</td>`;
										}
								Info += `<td  style="white-space: nowrap">${data.clientId.clientName}</td>
										<td  style="white-space: nowrap">${utcDate.format("DD-MM-YYYY HH:mm:ss")}</td>`
										if(`${data.status}` == 'true'){
											Info += `<td><span class="badge badge-success">Active</span></td>`;
										}else{
											Info += `<td><span class="badge badge-danger">InActive</span></td>`;
										}
									Info +=`</tr>`;
			});
			$('#employeesInfo').html(Info);
		};

	},
	employeeRegister: function() {
		var employeeClientId = $('.employeeClientId').val();
		var employeeGroup = $('.employeeGroup').val();
		var employeeName = $('.employeeName').val();
		var employeeEmail = $('.employeeEmail').val();
		var employeeMobileNo = $('.employeeMobileNo').val();
		var employeeRoleId = $('.employeeRoleId').val();
		var employeeReportingTo = $('.employeeReportingTo').val();
		var employeeRemarks = $('.employeeRemarks').val();
		var employeeType = $('.employeeType').val();

		if (employeeClientId == null) {
			Swal.fire("Invalid Client Selection", "Please Select the Valid Client Name", "error");
			return;
		}
		if (employeeName.trim().length == 0) {
			Swal.fire("Invalid Employee Name", "Please Enter Valid Employee Name", "error");
			return;
		}
		if (employeeEmail.trim().length == 0) {
			Swal.fire("Invalid Employee Mail", "Please Enter Valid Employee Mail", "error");
			return;
		}
		if (employeeRoleId == null) {
			Swal.fire("Invalid Role Selection", "Please Assign the Valid Role !!", "error");
			return;
		}
		/*if (employeeReportingTo == null) {
			Swal.fire("Invalid ReportingTo selection !!", "Please Select Valid ReportingTo !!", "error");
			return;
		}*/

		var dataToSend = {
			"clientId": employeeClientId,
			"employeeName": employeeName,
			"employeeEmail": employeeEmail,
			"employeeMobileNo": employeeMobileNo,
			"employeeRoleId": employeeRoleId,
			"employeeReportingTo": employeeReportingTo,
			"employeeGroup": employeeGroup,
			"remarks": employeeRemarks,
			"employeeType" : employeeType
		}
				var myhtml = document.createElement("div");
		myhtml.innerHTML = "<span style=color:blue><b> Your Request is Processing !! </b></span></br></br>" +
			"<span style=color:black> Please Wait... </span>";
		swal.fire({
			html: myhtml,
			showCloseButton: true,
			showConfirmButton: false,			
		});

		$.ajax({
			method: 'POST',
			url: "/user/employees/add",
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(dataToSend),

			success: function(data) {
				Swal.fire({
					icon: 'success',
					title: data.body.message
				}).then((result) => { window.location.reload(); })
				 //app.model.getEmployeeData();
			},
			error: function(data) {
			Swal.fire({
				title: "Unable to Add Employee!!",
				text: data.responseJSON.message,
				icon: "warning",
				button: "ok",
			});
			}

		});
	},
	employeeUpdate: function() {
		var employeeClientId = $('.employeeClientId').val();
		var employeeId = $('.employeeId').val();
		var employeeGroup = $('.employeeGroup').val();
		var employeeName = $('.employeeName').val();
		//var employeeEmail = $('.employeeEmail').val();
		var employeeMobileNo = $('.employeeMobileNo').val();
		//var employeeRoleId = $('.employeeRoleId').val();
		var employeeReportingTo = $('.employeeReportingTo').val();
		var employeeRemarks = $('.employeeRemarks').val();

		if (employeeClientId == null) {
			Swal.fire("Invalid Client Selection", "Please Select the Valid Client Name", "error");
			return;
		}
		if (employeeName.trim().length == 0) {
			Swal.fire("Invalid Employee Name", "Please Enter Valid Employee Name", "error");
			return;
		}
		
		
		/*if (employeeReportingTo == null) {
			Swal.fire("Invalid ReportingTo selection !!", "Please Select Valid ReportingTo !!", "error");
			return;
		}*/

		var dataToSend = {
			"clientId": employeeClientId,
			"employeeId":employeeId,
			"employeeName": employeeName,
			"employeeMobileNo": employeeMobileNo,
			"employeeReportingTo": employeeReportingTo,
			"employeeGroup": employeeGroup,
			"remarks": employeeRemarks
		}
				var myhtml = document.createElement("div");
		myhtml.innerHTML = "<span style=color:blue><b> Your Request is Processing !! </b></span></br></br>" +
			"<span style=color:black> Please Wait... </span>";
		swal.fire({
			html: myhtml,
			showCloseButton: true,
			showConfirmButton: false,			
		});

		$.ajax({
			method: 'POST',
			url: "/user/employees/edit",
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(dataToSend),

			success: function(data) {
				Swal.fire({
					icon: 'success',
					title: data.body.message
				}).then((result) => { window.location = "/user/employees/list"; })
				 //app.model.getEmployeeData(); /user/employees/list
			},
			error: function(data) {
			Swal.fire({
				title: "Unable to Update Employee!!",
				text: data.responseJSON.message,
				icon: "warning",
				button: "ok",
			});
			}

		});
	},
		portalEmployeeRegister: function() {
			var employeeCode = $('.employeeCode').val();
		var employeeGroup = $('.employeeGroup').val();
		var employeeName = $('.employeeName').val();
		var employeeEmail = $('.employeeEmail').val();
		var employeeMobileNo = $('.employeeMobileNo').val();
		var employeeRoleId = $('.employeeRoleId').val();
		var employeeReportingTo = $('.employeeReportingTo').val();
		var employeeRemarks = $('.employeeRemarks').val();
		var employeeType = $('.employeeType').val();
		
		
		if (employeeCode.trim().length == 0) {
			Swal.fire("Invalid Employee Code", "Please Enter Valid Employee Code", "error");
			return;
		}

		if (employeeName.trim().length == 0) {
			Swal.fire("Invalid Employee Name", "Please Enter Valid Employee Name", "error");
			return;
		}
		if (employeeEmail.trim().length == 0) {
			Swal.fire("Invalid Employee Mail", "Please Enter Valid Employee Mail", "error");
			return;
		}
		if (employeeRoleId == null) {
			Swal.fire("Invalid Role Selection", "Please Assign the Valid Role !!", "error");
			return;
		}
		/*if (employeeReportingTo == null) {
			Swal.fire("Invalid ReportingTo selection !!", "Please Select Valid ReportingTo !!", "error");
			return;
		}*/

		var dataToSend = {
			"employeeCode":employeeCode,
			"employeeName": employeeName,
			"employeeEmail": employeeEmail,
			"employeeMobileNo": employeeMobileNo,
			"employeeRoleId": employeeRoleId,
			"employeeReportingTo": employeeReportingTo,
			"employeeGroup": employeeGroup,
			"remarks": employeeRemarks,
			"employeeType" : employeeType
		}
				var myhtml = document.createElement("div");
		myhtml.innerHTML = "<span style=color:blue><b> Your Request is Processing !! </b></span></br></br>" +
			"<span style=color:black> Please Wait... </span>";
		swal.fire({
			html: myhtml,
			showCloseButton: true,
			showConfirmButton: false,			
		});

		$.ajax({
			method: 'POST',
			url: "/user/employees/portal/add",
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(dataToSend),

			success: function(data) {
				Swal.fire({
					icon: 'success',
					title: data.body.message
				}).then((result) => { window.location.reload(); })
				 //app.model.getEmployeeData();
			},
			error: function(data) {
			Swal.fire({
				title: "Unable to Register Employee",
				text: data.responseJSON.message,
				icon: "warning",
				button: "ok",
			});
	}
	});
	},
	portalEmployeeUpdate: function() {
		var employeeId = $('.employeeId').val();
		var employeeGroup = $('.employeeGroup').val();
		var employeeName = $('.employeeName').val();
		var employeeMobileNo = $('.employeeMobileNo').val();
		var employeeReportingTo = $('.employeeReportingTo').val();
		var employeeRemarks = $('.employeeRemarks').val();
		var employeeType = $('.employeeType').val();
		var employeeStatus = $('.EditEmployeeStatus').val();

		if (employeeName.trim().length == 0) {
			Swal.fire("Invalid Employee Name", "Please Enter Valid Employee Name", "error");
			return;
		}
		
		/*if (employeeReportingTo == null) {
			Swal.fire("Invalid ReportingTo selection !!", "Please Select Valid ReportingTo !!", "error");
			return;
		}*/

		var dataToSend = {
			"employeeId":employeeId,
			"employeeName": employeeName,
			"employeeMobileNo": employeeMobileNo,
			"employeeReportingTo": employeeReportingTo,
			"employeeGroup": employeeGroup,
			"remarks": employeeRemarks,
			"employeeType" : employeeType,
			"employeeStatus": employeeStatus
		}
				var myhtml = document.createElement("div");
		myhtml.innerHTML = "<span style=color:blue><b> Your Request is Processing !! </b></span></br></br>" +
			"<span style=color:black> Please Wait... </span>";
		swal.fire({
			html: myhtml,
			showCloseButton: true,
			showConfirmButton: false,			
		});

		$.ajax({
			method: 'POST',
			url: "/user/employee/portal/edit",
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(dataToSend),

			success: function(data) {
				Swal.fire({
					icon: 'success',
					title: data.body.message
				}).then((result) => { window.location = "/user/employees/portal/list"; })
				 //app.model.getEmployeeData();
			},
			error: function(data) {
			Swal.fire({
				title: "Unable to Register Employee",
				text: data.responseJSON.message,
				icon: "warning",
				button: "ok",
			});
	}
	});
	},
	getProjectsInfoByDefaultOptionSelected : function (ele){
		var getProjectId=ele;
		$.ajax({
			method: 'GET',
			url: "/api/project/list",
			success: function(data) {
				processProjectData(data);
			}
		});
		let processProjectData = (data) => {
			let projectIdAndName = '<option value="" disabled> -- Select Project --</option>';
			data.forEach((data) => {
				if(data.id == getProjectId){
					projectIdAndName += `<option value=${data.id} selected>${data.projectName}</option>`;
				}
				else{
			projectIdAndName += `<option value=${data.id}>${data.projectName}</option>`;		
				}
			});
			$('.projectIdAndNameInfo').html(projectIdAndName);
			app.model.getStatusData();
	 		app.model.getMileStoneListProjectSelection();
		    app.model.getProjectContactPerson();
		};
	}
	,
	getProjectData : function (ele){
		$.ajax({
			method: 'GET',
			url: "/api/project/list",
			success: function(data) {
				processProjectData(data);
			}
		});
		let processProjectData = (data) => {
			let Info = ' ';
			let projectIdAndName = '<option value="" selected disabled> -- Select Project --</option>';
			data.forEach((data) => {
				const mydate  = `${data.createdOn}`;
				// Create a UTC date object. The moment constructor will recognize the date as UTC since it includes the 'Z' timezone specifier.
				let utcDate = moment(mydate);

				Info += `<tr><td>1</td>
							 <td class="text-center" data-toggle="modal"	data-target="#ClientPreview"><img src="/dist/img/preview4.png" height="25px;"></td>
							 <td  style="white-space: nowrap">${data.projectName}</td>
							 <td  style="white-space: nowrap">${utcDate.format("DD-MM-YYYY HH:mm:ss")}</td>`;
				if (`${data.status}` == 'true') {
					Info += `<td><span class="badge badge-success">Active</span></td>`;
				} else {
					Info += `<td><span class="badge badge-danger">InActive</span></td>`;
				}

				Info += `</tr>`;
				
			projectIdAndName += `<option value=${data.id}>${data.projectName}</option>`;	
				
			});
			$('.projectListInfo').empty().append(Info);
			$('.projectIdAndNameInfo').html(projectIdAndName);
		};
	},
	getProjectsListBasedOnEmployeeRole : function (ele){
		$.ajax({
			method: 'GET',
			url: "/api/project/list/employee/rolewise",
			success: function(data) {
				processProjectData(data);
			}
		});
		let processProjectData = (data) => {
			let projectIdAndName = '<option value="" selected disabled> -- Select Project --</option>';
			data.forEach((data) => {
			projectIdAndName += `<option value=${data.id}>${data.project_name}</option>`;	
				
			});
			$('.projectDetailsBasedOnRoleWise').html(projectIdAndName);
			$(ele).html(projectIdAndName);
		};
	},
	getProjectsListBasedOnEmployeeRole2 : function (ele){
		
		if ($(ele)[0][0] != undefined){
			 app.model.getTaskListBasedOnProjectWise();
			return;
		}
		
		$.ajax({
			method: 'GET',
			url: "/api/project/list/employee/rolewise",
			success: function(data) {
				processProjectData(data);
			}
		});
		let processProjectData = (data) => {
			let projectIdAndName = '<option value="" selected disabled> -- Select Project --</option>';
			data.forEach((data) => {
			projectIdAndName += `<option value=${data.id}>${data.project_name}</option>`;	
				
			});
			$(ele).html(projectIdAndName);
		};
	},
		getProjectListForClient : function (ele){
		var clientId =$('.clientId').val();
		$.ajax({
			method: 'GET',
			url: "/api/project/list/"+clientId+"/clientwise",
			success: function(data) {
				processProjectData(data);
			}
		});
		let processProjectData = (data) => {
			let ProjectDetails = '';
			data.forEach((data) => {
			ProjectDetails += `<option value=${data.id}>${data.projectId}  -  ${data.projectName}</option>`;	
				
			});
			$('.getProjectList').html(ProjectDetails);
		};
	},
	
	getTaskListBasedOnProjectWise : function (ele){
		var projectId = $('.projectDetailsBasedOnRoleWise').val();
		$.ajax({
			method: 'GET',
			url: "/api/task/list/"+projectId+"/projectwise",
			success: function(data) {
				processProjectData(data);
			}
		});
		let processProjectData = (data) => {
			let TaskIdAndName = '<option value="" selected disabled> -- Select Task --</option>';
			data.forEach((data) => {
			TaskIdAndName += `<option value=${data.id} data-taskStatus=${data.status_description}>${data.task_id}  -  ${data.task_name}</option>`;	
				
			});
			$('.TaskDetailsBasedOnProjectWise').html(TaskIdAndName);
		};
	},
	projectRegister: function() {
		var projectClient = $('.projectClient').val();
		var projectName = $('.projectName').val();
		var projectOwner = $('.projectOwner').val();
		var projectManager = $('.projectManager').val();
		var projectContactPerson = $('.projectContactPerson').val();
		var projectStartDate = $('.projectStartDate').val();
		var projectEndDate = $('.projectEndDate').val();
		var projectTag = $('.projectTag').val();
		var projectRemarks = $('.projectRemarks').val();
		var projectStatus = $('.projectStatus').val();
		var projectCode = $('.projectCode').val();
		var defaultContactPerson = $('.defualtContactPerson').val();

		if (projectClient == null) {
			Swal.fire("Invalid Client Name", "Please Select the Client Name", "error");
			return;
		}
		if (projectName.trim().length == 0) {
			Swal.fire("Invalid Project Name", "Please Enter Valid Company Email", "error");
			return;
		}
		if (projectOwner == null) {
			Swal.fire("Invalid project Owner", "Please Select the Project Owner", "error");
			return;
		}
		if (projectManager == null) {
			Swal.fire("Invalid project Manager", "Please Select the Project Manager!", "error");
			return;
		}
		if (projectContactPerson == null) {
			Swal.fire("Invalid project Contact Person", "Please Select the ContactPerson Details", "error");
			return;
		}
		
		if( projectEndDate != "" && projectStartDate != ""){
			if ( projectEndDate <=  projectStartDate ){
			Swal.fire("Invalid project End Date Selection", "Project Due Date should not be Less than Start Date!!", "error");
			return;
		}
		}
		
		var ContactPerson = [] ;
		var name = 0;
		projectContactPerson.forEach(function(data) {
			var name = {
				"contactPersonId": data
			}
			var name = ContactPerson.push(name);
		});
		
		
		if (!jQuery.isEmptyObject(defaultContactPerson)  && !jQuery.isEmptyObject(projectContactPerson) ){
			var Status = projectContactPerson.indexOf(defaultContactPerson[0]);
			
			if (Status == -1) {
				Swal.fire("Invalid Default Contact Person", "Default Person Should be One Of the Assigned Contact Persons", "error");
				return;
			}
		}

		var dataToSend = {
			"projectClient": projectClient,
			"projectName": projectName,
			"projectManager": projectManager,
			"projectOwner": projectOwner,
			"projectStartDate": projectStartDate,
			"projectEndDate": projectEndDate,
			"projectTag":projectTag,
			"projectRemarks": projectRemarks,
			"contactPersons":ContactPerson,
			"projectStatus" : projectStatus,
			"projectCode" : projectCode,
			"defaultContactPerson" : defaultContactPerson[0]			
			}
		
		var myhtml = document.createElement("div");
		myhtml.innerHTML = "<span style=color:blue><b> Your Request is Processing !! </b></span></br></br>" +
			"<span style=color:black> Please Wait... </span>";
		swal.fire({
			html: myhtml,
			showCloseButton: true,
			showConfirmButton: false,			
		});

		$.ajax({
			method: 'POST',
			url: "/admin/project/add",
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(dataToSend),

			success: function(data) {
			Swal.fire({
					icon: 'success',
					title: data.body.message
				}).then((result) => { window.location.reload(); })
			},
			error: function(data) {
				Swal.fire({
					icon: 'error',
					title: data.body.message
				})
			}

		});
	},
	defaultcontactPerson : function (){
		var  projectContactPerson = $('.projectContactPerson').val();
		var ContactPerson = [] ;
		var name = 0;
		projectContactPerson.forEach(function(data) {
			var name = {
				"contactPersonId": data
			}
			var name = ContactPerson.push(name);
		});
		
			$.ajax({
			method: 'GET',
			url: "/ajax/employee/"+ContactPerson[0].contactPersonId+"/info",
			success: function(data) {
				processSelectedData(data);
			}
		});
		let processSelectedData = (data) => {
			let Info = '';
			data.forEach((data) => {
				Info += `<option value=${data.id} selected>${data.employee_name}</option>`;
			});
			$('.defualtContactPerson').html(Info);
		};
		
	},
	projectUpdate: function() {
		var projectId = $('.projectId').val();
		var UpdateprojectName = $('.projectName').val();
		var UpdateprojectOwner = $('.projectOwner').val();
		var UpdateprojectManager = $('.projectManager').val();
		var UpdateprojectContactPerson = $('.projectContactPerson').val();
		var UpdateprojectStartDate = $('.projectStartDate').val();
		var UpdateprojectEndDate = $('.projectEndDate').val();
		var UpdateprojectTag = $('.projectTag').val();
		var UpdateprojectRemarks = $('.projectRemarks').val();
		var UpdateprojectStatus = $('.projectStatus').val();
		var status = $('.status').val();
		var defaultContactPerson = $('.defualtContactPerson').val();
		
		var projectCode = $('.projectCode').val();

		if (UpdateprojectName.trim().length == 0) {
			Swal.fire("Invalid Project Name", "Please Enter Valid Company Email", "error");
			return;
		}
		if (UpdateprojectOwner == null) {
			Swal.fire("Invalid project Owner", "Please Select the Project Owner", "error");
			return;
		}
		if (UpdateprojectManager == null) {
			Swal.fire("Invalid project Manager", "Please Select the Project Manager!", "error");
			return;
		}
		if (UpdateprojectContactPerson == null) {
			Swal.fire("Invalid project Contact Person", "Please Select the ContactPerson Details", "error");
			return;
		}
		
		if( UpdateprojectEndDate != "" && UpdateprojectStartDate != ""){
			if ( UpdateprojectEndDate <=  UpdateprojectStartDate ){
			Swal.fire("Invalid project End Date Selection", "Project Due Date should not be Less than Start Date!!", "error");
			return;
		}
		}
		
		
		
		var UpdatedContactPerson = [] ;
		
		var name = 0;
		UpdateprojectContactPerson.forEach(function(data) {
			var name = {
				"contactPersonId": data
			}
			var name = UpdatedContactPerson.push(name);
		});
		
		if (!jQuery.isEmptyObject(defaultContactPerson)  && !jQuery.isEmptyObject(UpdateprojectContactPerson) ){
			var Status = UpdateprojectContactPerson.indexOf(defaultContactPerson[0]);
			
			if (Status == -1) {
				Swal.fire("Invalid Default Contact Person", "Default Person Should be One Of the Assigned Contact Persons", "error");
				return;
			}
		}

		var dataToSend = {
			"projectId": projectId,
			"projectName": UpdateprojectName,
			"projectManager": UpdateprojectManager,
			"projectOwner": UpdateprojectOwner,
			"projectStartDate": UpdateprojectStartDate,
			"projectEndDate": UpdateprojectEndDate,
			"projectTag":UpdateprojectTag,
			"projectRemarks": UpdateprojectRemarks,
			"contactPersons":UpdatedContactPerson,
			"projectStatus" : UpdateprojectStatus,
			"status" : status,
			"projectCode" : projectCode,
			"defaultContactPerson" : defaultContactPerson[0]
		}
		
		var myhtml = document.createElement("div");
		myhtml.innerHTML = "<span style=color:blue><b> Your Update Request is Processing !! </b></span></br></br>" +
			"<span style=color:black> Please Wait... </span>";
		swal.fire({
			html: myhtml,
			showCloseButton: true,
			showConfirmButton: false,			
		});

		$.ajax({
			method: 'POST',
			url: "/admin/project/update",
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(dataToSend),

			success: function(data) {
				Swal.fire({
					icon: 'success',
					title: data.body.message
				}).then((result) => { window.location = "/web/project/list"; })
			
			},
			error: function(data) {
				Swal.fire({
					icon: 'error',
					title: 'Unable To Add Project !!'
				})
			}

		});
	},

	getGroupData : function (ele){
		$.ajax({
			method: 'GET',
			url: "/api/group/list",
			success: function(data) {
				processGroupData(data);
			}
		});
		let processGroupData = (data) => {
			let Info = ' ';
			data.forEach((data) => {
				const mydate  = `${data.createdOn}`;
				// Create a UTC date object. The moment constructor will recognize the date as UTC since it includes the 'Z' timezone specifier.
				let utcDate = moment(mydate);

				Info += `<tr><td>1</td>
							 <td class="text-center" data-toggle="modal"	data-target="#ClientPreview"><img src="/dist/img/preview4.png" height="25px;"></td>
							 <td  style="white-space: nowrap">${data.groupName}</td>
							 <td  style="white-space: nowrap">${data.groupDescription}</td>
							 <td  style="white-space: nowrap">${data.clientId.clientName}</td>
							 <td  style="white-space: nowrap">${utcDate.format("DD-MM-YYYY HH:mm:ss")}</td>`;
				if (`${data.status}` == 'true') {
					Info += `<td><span class="badge badge-success">Active</span></td>`;
				} else {
					Info += `<td><span class="badge badge-danger">InActive</span></td>`;
				}

				Info += `</tr>`;
			});
			//$('#example1>tbody').html(Info);
			$('.groupListInfo').empty().append(Info);
		};
	},
		getGroupDataClientWise : function (ele){
			var clientId = $('.employeeClientId').val();
		$.ajax({
			method: 'GET',
			url: "/api/group/list/"+clientId,
			success: function(data) {
				processGroupData(data);
			}
		});
		let processGroupData = (data) => {
			let Info = '<option value="" disabled selected> -- Select Group --</option>';
			data.forEach((data) => {
				Info += `<option value=${data.id}>${data.groupName}</option>`;
			});
			//$('#example1>tbody').html(Info);
			$('.groupInfoClientWise').empty().append(Info);
		};
	},
	getCategoryListBasedOnTheProjectSelection : function (){
			var projectId = $('.ticketproject').val();
		$.ajax({
			method: 'GET',
			url: "/ajax/client/group/"+projectId+"/info",
			success: function(data) {
				processClientGroupData(data);
			}
		});
		let processClientGroupData = (data) => {
			let Info = '<option value="" disabled selected> -- Select Module --</option>';
			data.forEach((data) => {
				Info += `<option value=${data.id}>${data.groupName}</option>`;
			});
			//$('#example1>tbody').html(Info);
			$('.ClientCategoryList').empty().append(Info);
		};
	}
	,
	getReporingToClientWise : function (ele){
			var clientId = $('.employeeClientId').val();
		$.ajax({
			method: 'GET',
			url: "/api/employees/list/"+clientId,
			success: function(data) {
				processEmployeeData(data);
			}
		});
		let processEmployeeData = (data) => {
			let Info = '<option value="" disabled selected> -- Select Employee --</option>';
			data.forEach((data) => {
				Info += `<option value=${data.id}>${data.employeeName}</option>`;
			});
			//$('#example1>tbody').html(Info);
			$('.employeeInfoClientWise').empty().append(Info);
		};
	},
	groupRegister: function() {
		var clientId = $('.clientId').val();
		var groupName = $('.groupName').val();
		var groupDescription = $('.groupDescription').val();
		var groupRemarks = $('.groupRemarks').val();

		if (clientId == null) {
			Swal.fire("Invalid Client Selection !!", "Please Select the Client Information", "error");
			return;
		}
		if (groupName.trim().length == 0) {
			Swal.fire("Invalid Group Name !!", "Please Enter the Valid Group Name !!", "error");
			return;
		}

		var dataToSend = {
			"clientId": clientId,
			"groupName": groupName,
			"groupDescription": groupDescription,
			"remarks": groupRemarks
		}
				var myhtml = document.createElement("div");
		myhtml.innerHTML = "<span style=color:blue><b> Your Request is Processing !! </b></span></br></br>" +
			"<span style=color:black> Please Wait... </span>";
		swal.fire({
			html: myhtml,
			showCloseButton: true,
			showConfirmButton: false,			
		});

		$.ajax({
			method: 'POST',
			url: "/user/group/add",
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(dataToSend),

			success: function(data) {
				Swal.fire({
					icon: 'success',
					title: data.body.message
				}).then((result) => { window.location.reload(); })
				 //app.model.getGroupData();
			},
			error: function(data) {
				Swal.fire({
					icon: 'error',
					title: ' Not Successfully Added Company !!'
				})
			}

		});
	},	
		groupUpdate: function() {
		var groupId = $('.previewGroupId').val();
		var groupName = $('.GroupName').val();
		var groupDescription = $('.GroupDescription').val();
		
		var groupStatus = $('.GroupStatus').val();

		if (groupId == null) {
			Swal.fire("Invalid Group Details !!", "Please enter Valid Group Details !!", "error");
			return;
		}
		if (groupName.trim().length == 0) {
			Swal.fire("Invalid Group Name !!", "Please Enter the Valid Group Name !!", "error");
			return;
		}

		var dataToSend = {
			"groupId": groupId,
			"groupName": groupName,
			"groupDescription": groupDescription,
			"groupStatus" : groupStatus
		}
				var myhtml = document.createElement("div");
		myhtml.innerHTML = "<span style=color:blue><b> Your Request is Processing !! </b></span></br></br>" +
			"<span style=color:black> Please Wait... </span>";
		swal.fire({
			html: myhtml,
			showCloseButton: true,
			showConfirmButton: false,			
		});

		$.ajax({
			method: 'POST',
			url: "/user/group/edit",
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(dataToSend),

			success: function(data) {
				Swal.fire({
					icon: 'success',
					title: data.message
				}).then((result) => { window.location.reload(); })
				 //app.model.getGroupData();
			},
			error: function(data) {
				Swal.fire({
					icon: 'error',
					title: data.message
				})
			}

		});
	},
	getTagData : function (ele){
		
		$.ajax({
			method: 'GET',
			url: "/api/tag/list",
			success: function(data) {
				processTagData(data);
			}
		});
		let processTagData = (data) => {
			let Info = ' ';
			let TagIdAndName = '<option value="" selected disabled> -- Select Tag --</option>';
				var i = 1;
			data.forEach((data) => {
			
				const mydate  = `${data.createdOn}`;
				// Create a UTC date object. The moment constructor will recognize the date as UTC since it includes the 'Z' timezone specifier.
				let utcDate = moment(mydate);

				Info += `<tr><td>${i}</td>
							 <td  style="white-space: nowrap">${data.tagName}</td>
							 <td  style="white-space: nowrap">${data.tagDescription}</td>
							 <td  style="white-space: nowrap">-</td>
							 <td  style="white-space: nowrap">${utcDate.format("DD-MM-YYYY HH:mm:ss")}</td>`;
				if (`${data.status}` == 'true') {
					Info += `<td><span class="badge badge-success">Active</span></td>`;
				} else {
					Info += `<td><span class="badge badge-danger">InActive</span></td>`;
				}

				Info += `</tr>`;
				
				TagIdAndName += `<option value=${data.id}>${data.tagName}</option>`;
				i++;
			});
			//$('#example1>tbody').html(Info);
			$('.tagListInfo').empty().append(Info);
			$('.tagIdAndNameInfo').html(TagIdAndName);
		};
	},
	tagRegister: function() {
		var tagName = $('.tagName').val();
		var tagDescription = $('.tagDescription').val();
		var tagRemarks = $('.tagRemarks').val();

		
		if (tagName.trim().length == 0) {
			Swal.fire("Invalid Tag Name !!", "Please Enter the Valid Tag Name !!", "error");
			return;
		}

		var dataToSend = {
			"tagName": tagName,
			"tagDescription": tagDescription,
			"tagRemarks": tagRemarks
		}
				var myhtml = document.createElement("div");
		myhtml.innerHTML = "<span style=color:blue><b> Your Request is Processing !! </b></span></br></br>" +
			"<span style=color:black> Please Wait... </span>";
		swal.fire({
			html: myhtml,
			showCloseButton: true,
			showConfirmButton: false,			
		});

		$.ajax({
			method: 'POST',
			url: "/user/tag/add",
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(dataToSend),

			success: function(data) {
				Swal.fire({
					icon: 'success',
					title: data.body.message
				}).then((result) => { window.location.reload(); })
				// app.model.getTagData();
			},
			error: function(data) {
				Swal.fire({
					icon: 'error',
					title: ' Not Successfully Added Company !!'
				})
			}

		});
	},
	tagUpdate: function() {
		var tagId = $('.tagId').val();
		var tagName = $('.editTagName').val();
		var tagStatus = $('.EditTagStatus').val();
		var tagDescription = $('.projectTypeDescription').val();
		
		if (tagName.trim().length == 0) {
			Swal.fire("Invalid Tag Name !!", "Please Enter the Valid Tag Name !!", "error");
			return;
		}

		var dataToSend = {
			"tagId":tagId,
			"tagName": tagName,
			"tagDescription": tagDescription,
			"tagStatus": tagStatus
		}
				var myhtml = document.createElement("div");
		myhtml.innerHTML = "<span style=color:blue><b> Your Request is Processing !! </b></span></br></br>" +
			"<span style=color:black> Please Wait... </span>";
		swal.fire({
			html: myhtml,
			showCloseButton: true,
			showConfirmButton: false,			
		});

		$.ajax({
			method: 'POST',
			url: "/user/tag/edit",
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(dataToSend),

			success: function(data) {
				Swal.fire({
					icon: 'success',
					title: data.body.message
				}).then((result) => { window.location.reload(); })
				// app.model.getTagData();
			},
			error: function(data) {
				Swal.fire({
					icon: 'error',
					title: "Unable to Update the Project Type!!"
				})
			}

		});
	},
	getRoleData : function (ele){
		$.ajax({
			method: 'GET',
			url: "/api/role/list",
			success: function(data) {
				processRoleData(data);
			}
		});
		let processRoleData = (data) => {
			let Info = '<option value="" selected disabled>-- Select Role --</option>';
			data.forEach((data) => {
				Info += `<option value=${data.id}>${data.roleDescription}</option>`;
			});
			//$('#example1>tbody').html(Info);
			$('.roleListInfo').empty().append(Info);
		};
	},
	getAdministratorEmpData : function (ele){
		$.ajax({
			method: 'GET',
			url: "/api/role/administrator/list",
			success: function(data) {
				processAdminstratorData(data);
			}
		});
		let processAdminstratorData = (data) => {
			let Info = '<option value="" selected disabled>-- Select Project Owner --</option>';
			data.forEach((data) => {
				Info += `<option value=${data.id}>${data.employee_name}</option>`;
			});
			//$('#example1>tbody').html(Info);
			$('.projectOwnerInfo').empty().append(Info);
		};
	},
	getManagerEmpData : function (ele){
		$.ajax({
			method: 'GET',
			url: "/api/role/manager/list",
			success: function(data) {
				processManagerData(data);
			}
		});
		let processManagerData = (data) => {
			let Info = '<option value="" selected disabled>-- Select Project Manager --</option>';
			data.forEach((data) => {
				Info += `<option value=${data.id}>${data.employee_name}</option>`;
			});
			//$('#example1>tbody').html(Info);
			$('.projectManagerInfo').empty().append(Info);
		};
	},
	getProjectEmployeeData : function (ele){
		$.ajax({
			method: 'GET',
			url: "/api/role/employee/list",
			success: function(data) {
				processProjEmployeeData(data);
			}
		});
		let processProjEmployeeData = (data) => {
			let Info = ' ';
			data.forEach((data) => {
				Info += `<option value=${data.id}>${data.employee_name}</option>`;
			});
			//$('#example1>tbody').html(Info);
			$('.projectEmployeeInfo').empty().append(Info);
			$('.projectDefaultAssignEmployeeInfo').empty().append(Info);
		};
	},
	getMileStoneListProjectSelection: function(){
		var projectId = $('.taskproject').val();
		$.ajax({
			method: 'GET',
			url: "/api/milestone/"+projectId+"/project",
			success: function(data) {
				processProjEmployeeData(data);
			}
		});
		let processProjEmployeeData = (data) => {
			let Info = '<option value="" disabled selected>-- Select Stage --</option>';
			data.forEach((data) => {
				Info += `<option value=${data.id}>${data.milestoneName}</option>`;
			});
			//$('#example1>tbody').html(Info);
			$('.mileStoneInfo').empty().append(Info);
		};
	},
	getMileStoneListProjectSelectionForProjectPreview: function(ele){
		var projectId = ele;
		$.ajax({
			method: 'GET',
			url: "/api/milestone/"+projectId+"/project",
			success: function(data) {
				processProjEmployeeData(data);
			}
		});
		let processProjEmployeeData = (data) => {
			let Info = '<option value="" disabled selected>-- Select Stage --</option>';
			data.forEach((data) => {
				Info += `<option value=${data.id}>${data.milestoneName}</option>`;
			});
			//$('#example1>tbody').html(Info);
			$('.mileStoneInfo').empty().append(Info);
		};
	},
	taskCreate: function() {
		var taskProject = $('.taskproject').val();
		var taskName = $('.taskName').val();
		var taskDescription = $('.taskDescription').val();
		var taskPriority = $('.taskPriority').val();
		var taskAssignTo = $('.taskAssignTo').val();
		var taskFlag = $('.taskflag').val();
		var milestoneId = $('.milestoneId').val();
		var taskBillableType = $('.taskBillableType').val();
		var taskStartDate = $('#TaskStartDate').val();
		//var taskEndDate = $('#TaskEndDate').val();
		var taskTargetDate = $('#TaskTargetDate').val();
		
		if (taskStartDate.trim().length != 0 && taskTargetDate.trim().length != 0){
			if (Date.parse(taskTargetDate) < Date.parse(taskStartDate)) {
			Toast.fire({
				icon: 'warning',
				title: '"Target Date" should not be less than "TasK Start Date" !!!'
			})
			return;
		}
		}
		

		if (taskProject == null) {
			Swal.fire("Invalid Project Selection", "Please Select the Valid Project !!", "error");
			return;
		}
		if (taskName.trim().length == 0) {
			Swal.fire("Invalid Task Name", "Please Enter Valid Task	 Name", "error");
			return;
		}
		if (taskPriority.trim().length == 0) {
			Swal.fire("Invalid Priority !!", "Please Select the Priority !!", "error");
			return;
		}
		if (milestoneId == null) {
			Swal.fire("Invalid Stage Selection !!", "Please Select the Stage !!", "error");
			return;
		}
		
			var AssignPerson = [] ;
		if (taskAssignTo != undefined){
			//var name = 0;
		taskAssignTo.forEach(function(data) {
			var name = {
				"employeeId": data
			}
			var name = AssignPerson.push(name);
		});
		}
		
		 myDropzonecustomer.processQueue();
		 
        var filename = [];
        
        for (let i = 0; i < newDropzonecustomer.files.length; i++) {
	
		if (newDropzonecustomer.files[i].status == 'error'){
			Swal.fire("Invalid Attachments !!", "Please Check the Attachments !!", "error");
			return;
		}
	
            var fileupload = {
                fileName: newDropzonecustomer.files[i].upload.filename,
                fileType: newDropzonecustomer.files[i].type
            }
            filename.push(fileupload);

        }

		var dataToSend = {
			"projectId": taskProject,
			"taskName": taskName,
			"taskDescription": taskDescription,
			"taskPriority": taskPriority,
			"flag":taskFlag,
			"milestoneId":milestoneId,
			"billableType":taskBillableType,
			"fileName":filename,
			"taskStartDate":taskStartDate,
			//"taskEndDate":taskEndDate,
			"taskTargetDate" :taskTargetDate,
			"taskAssign":AssignPerson
		}
				var myhtml = document.createElement("div");
		myhtml.innerHTML = "<span style=color:blue><b> Your Request is Processing !! </b></span></br></br>" +
			"<span style=color:black> Please Wait... </span>";
		swal.fire({
			html: myhtml,
			showCloseButton: true,
			showConfirmButton: false,			
		});

		$.ajax({
			method: 'POST',
			url: "/web/task/create",
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(dataToSend),

			success: function(data) {
				
				if (!data) {
					Toast.fire({
						icon: 'warning',
						title: 'Unable to Add the Task !!!'
					})
				} else {
					Toast.fire({
						icon: 'success',
						title: data.body.message
					}).then((result) => { window.location.reload(); })
				}
				$('.taskName').val("");
				$('.taskDescription').summernote('reset');
				 $('.taskAssignTo').empty();
				$('.milestoneId').val("");
			},
			error: function(data) {
				Swal.fire({
					icon: 'error',
					title: "Unable to Add the Task !!"
				})
			}

		});
	},
	
	saveMoM: function() {
		var ProjectId = $('.projectId').val();
		var clientId = $('.clientId').val();
		var momDate = $('.momDate').val();
		var agenda = $('.agenda').val();
		var attendes = $('.attendes').val();
		var MoMDescription = $('.Description').val();
		var location = $('.location').val();
		var fromTime = $('.fromTime').val();
		var toTime = $('.toTime').val();
		var actionPoints = $('.actionPoints').val();
		
		if (ProjectId == null) {
			Swal.fire("Invalid Project Selection", "Please Select the Valid Project !!", "error");
			return;
		}
		
		if (agenda.trim().length == 0) {
			Swal.fire("Invalid Agenda", "Please Enter Valid Agenda !!", "error");
			return;
		}

if (fromTime.trim().length == "") {
			Swal.fire("Invalid From Time", "Please Select the Valid From Time !!", "error");
			return;
		}
		
		if (toTime.trim().length == "") {
			Swal.fire("Invalid To Time", "Please Select the Valid To Time !!", "error");
			return;
		}
		
		 myDropzonecustomer.processQueue();
        var filename = [];
        for (let i = 0; i < newDropzonecustomer.files.length; i++) {
            var fileupload = {
                fileName: newDropzonecustomer.files[i].name,
                fileType: newDropzonecustomer.files[i].type
            }
            filename.push(fileupload);

        }

		var dataToSend = {
			"clientId":clientId,
			"projectId": ProjectId,
			"momDate":momDate,
			"agenda": agenda,
			"attendes": attendes,
			"description": MoMDescription,
			"locationName":location,
			"fromTime":moment(fromTime).format('DD/MMM/YYYY HH:MM A'),//moment(fromTime).format('DD/MMM/YYYY HH:MM A'),
			"toTime":moment(toTime).format('DD/MMM/YYYY HH:MM A'),
			"fileName":filename,
			"actionPoints":actionPoints,
		}
				var myhtml = document.createElement("div");
		myhtml.innerHTML = "<span style=color:blue><b> Your Request is Processing !! </b></span></br></br>" +
			"<span style=color:black> Please Wait... </span>";
		swal.fire({
			html: myhtml,
			showCloseButton: true,
			showConfirmButton: false,			
		});

		$.ajax({
			method: 'POST',
			url: "/web/project/mom/add",
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(dataToSend),

			success: function(data) {
				
				if (!data) {
					Toast.fire({
						icon: 'warning',
						title: 'Unable to Add the Task !!!'
					})
				} else {
					Toast.fire({
						icon: 'success',
						title: data.body.message
					})
				}
				$('.agenda').val("");
				$('.attendes').val("");
				 $('.location').val("");
				 $('.Description').summernote('reset');
				$('.actionPoints').val("");
				$('.fromTime').val("");
				$('.toTime').val("");
			},
			error: function(data) {
				Swal.fire({
					icon: 'error',
					title: "Unable to Add the Task !!"
				})
			}

		});
	}
	,
	getProjectIdIntoToTaskModalPopUp : function(ele){
		var projectId = $(ele)[0].dataset.projectid;
		$('#HideBasedOnClickEvent').hide();
		app.model.getProjectsInfoByDefaultOptionSelected(projectId);
	//	$('.taskproject').val(projectId);
		$('.taskMileStoneprojectId').val(projectId);
		document.getElementById('TaskStartDate').valueAsDate = new Date();
		document.getElementById('TaskTargetDate').valueAsDate = new Date();
	//	app.model.getMileStoneListProjectSelectionForProjectPreview(projectId);
		// app.model.getProjectContactPersonForProjectPreviewTaskModal(projectId);
		
		 
	},
	getProjectIdIntoToTaskModalPopUpInTaskListScreen : function(ele){
		$('#HideBasedOnClickEvent').show();
		document.getElementById('TaskStartDate').valueAsDate = new Date();
		document.getElementById('TaskTargetDate').valueAsDate = new Date();
	},
	getTicketData: function(ele) {
		$.ajax({
			method: 'GET',
			url: "/api/ticket/list",
			success: function(data) {
				processTicketData(data);
			}
		});
		let processTicketData = (data) => {
			let Info = ' ';
			data.forEach((data) => {
				const mydate  = `${data.createdOn}`;
				// Create a UTC date object. The moment constructor will recognize the date as UTC since it includes the 'Z' timezone specifier.
				let utcDate = moment(mydate);

				Info += `<tr><td>1</td>
									<td class="text-center" data-toggle="modal"	data-target="#ClientPreview"><img src="/dist/img/preview4.png" height="25px;"></td>
										<td  style="white-space: nowrap">${data.ticketId}</td>
										<td  style="white-space: nowrap">${data.ticketName}</td>
										<td  style="white-space: nowrap">${data.contactPerson}</td>
										<td data-toggle="tooltip" title=${data.address}>
											<span class="leftDesc">${data.address}</span>
										</td>
										<td  style="white-space: nowrap">${data.contactNo}</td>
										<td  style="white-space: nowrap">${data.state}</td>
										<td  style="white-space: nowrap"><a href="${data.website}" target="_blank">${data.website}</a></td>`;
										/*<td  style="white-space: nowrap">${utcDate.format("DD-MM-YYYY HH:mm:ss")}</td>`*/
										if(`${data.status}` == 'true'){
											Info += `<td><span class="badge badge-success">Active</span></td>`;
										}else{
											Info += `<td><span class="badge badge-danger">InActive</span></td>`;
										}
										
									Info +=`</tr>`;
			});
			//$('#example1>tbody').html(Info);
			$('#ticketlist').empty().append(Info);
			$('.clientIdNameInfoInSelectAttr').html(clientIdAndName);
		};

	},
		ticketRegister: function() {
		var ticketproject = $('.ticketproject').val();
		var ticketName = $('.ticketName').val();
		var ticketDescription = $('.ticketDescription').val();
		var ticketPriority = $('.ticketPriority').val();
		var ticketAssignTo = $('.ticketAssignTo').val();
		var ticketTargetDate = $('.ticketTargetDate').val();
		var ticketRemarks = $('.ticketRemarks').val();
		var ticketType = $('.ticketType').val();
		var ticketDivisionCode = $('.ticketDivisionCode').val();
		var OnBehalf = null;
		var ticketRequestMode = $(".ticketModeOfRequest").val();
		//var ticketModeOfRequest =   $(".ticketModeOfRequest").is(":visible");
		var ticketOnBehalf =   $(".ticketOnBehalf").is(":visible");

		if (ticketproject == null) {
			Swal.fire("Invalid Project Selection", "Please Select the Valid Project !!", "error");
			return;
		}
		if (ticketName.trim().length == 0) {
			Swal.fire("Invalid Ticket Name", "Please Enter Valid Ticket Name", "error");
			return;
		}
		if (ticketDescription.trim().length == 0) {
			Swal.fire("Invalid Description !!", "Please Enter Valid Description Mail", "error");
			return;
		}
		if (ticketPriority == null) {
			Swal.fire("Invalid Task Priority", "Please Select the Task Priority !!", "error");
			return;
		}
		
		if (ticketOnBehalf){
			 OnBehalf = $(".ticketOnBehalf").val();
			if (OnBehalf == null){
				Swal.fire("Invalid OnBehalf Selection", "Please Select OnBehalf Employee Selection !!", "error");
				return;
			}
		}
		
		
		
		/*if (ticketAssignTo == null) {
			Swal.fire("Invalid Task Assign selection !!", "Please Select Valid AssignTo !!", "error");
			return;
		}*/
		
		 myDropzonecustomer.processQueue();
        var filename = [];
        for (let i = 0; i < newDropzonecustomer.files.length; i++) {
            var fileupload = {
                fileName: newDropzonecustomer.files[i].upload.filename,
                fileType: newDropzonecustomer.files[i].type
            }
            filename.push(fileupload);

        }

		var dataToSend = {
			"projectId": ticketproject,
			"ticketName": ticketName,
			"ticketDescription": ticketDescription,
			"ticketPriority": ticketPriority,
			"ticketAssignTo": ticketAssignTo,
			"ticketDueDate": ticketTargetDate,
			"ticketRemarks": ticketRemarks,
			"ticketType" : ticketType,
			"ticketRequestMode":ticketRequestMode,
			"ticketDivisionCode":ticketDivisionCode,
			"onBehalfEmployee":OnBehalf,
			"fileName":filename
		}
				var myhtml = document.createElement("div");
		myhtml.innerHTML = "<span style=color:blue><b> Your Request is Processing !! </b></span></br></br>" +
			"<span style=color:black> Please Wait... </span>";
		swal.fire({
			html: myhtml,
			showCloseButton: true,
			showConfirmButton: false,			
		});

		$.ajax({
			method: 'POST',
			url: "/web/ticket/add",
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(dataToSend),

			success: function(data) {
				Swal.fire({
					icon: 'success',
					title: data.body.message
				}).then((result) => { window.location = "/web/ticket/list"; })
				 
			},
			error: function(data) {
				Swal.fire({
					icon: 'error',
					title: data.responseJSON.message
				})
			}

		});
	},
	taskUpdate: function() {
		var taskId = $('.getTaskIdForFetchTaskDetails').val();
		var UpdatetaskDescription = $('.UpdatetaskDescription').val();
		var UpdatetaskPriority = $('.UpdatetaskPriority').val();
		var UpdatetaskAssignTo = $('.taskAssignTo').val();
		var UpdatetaskTargetDate = $('.UpdatetaskTargetDate').val();
		var UpdatetaskStatus = $('.updateTaskStatus').val();
		//var UpdatetaskType = $('.updateTicketType').val();
		var UpdatepercentageCompletion = $('.percentageCompletion').val();
		var updateBillableType = $('.UpdateBillableType').val();
		var updateFlag = $('.UpdateFlag').val();
		
		if (UpdatetaskPriority == null) {
			Swal.fire("Invalid Task Priority", "Please Select the Task Priority !!", "error");
			return;
		}
		
		var AssignPerson = [] ;
		if (UpdatetaskAssignTo != undefined){
			var name = 0;
		UpdatetaskAssignTo.forEach(function(data) {
			var name = {
				"employeeId": data
			}
			var name = AssignPerson.push(name);
		});
		}
		
		
		 myDropzonecustomer.processQueue();
        var filename = [];
        for (let i = 0; i < newDropzonecustomer.files.length; i++) {
            var fileupload = {
                 fileName: newDropzonecustomer.files[i].upload.filename,
                fileType: newDropzonecustomer.files[i].type
            }
            filename.push(fileupload);
        }

		var dataToSend = {
			"taskId": taskId,
			"taskDescription": UpdatetaskDescription,
			"taskPriority": UpdatetaskPriority,
			"taskTargetDate": UpdatetaskTargetDate,
			"taskStatus": UpdatetaskStatus,
			//"taskType":UpdatetaskType,
			"fileName":filename,
			"flag":updateFlag,
			"taskAssign" : AssignPerson,
			"percentageCompletion":UpdatepercentageCompletion,
			"billableType":updateBillableType
		}
				var myhtml = document.createElement("div");
				myhtml.innerHTML = "<span style=color:blue><b> Your Request is Processing !! </b></span></br></br>" +
				"<span style=color:black> Please Wait... </span>";
		swal.fire({
			html: myhtml,
			showCloseButton: true,
			showConfirmButton: false,			
		});

		$.ajax({
			method: 'POST',
			url: "/web/task/update",
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(dataToSend),

			success: function(data) {
				
				if (!data) {
					Toast.fire({
						icon: 'warning',
						title: 'Unable to Update the Ticket Details !!'
					})
				} else {
					Toast.fire({
						icon: 'success',
						title: data.body.message
					}).then((result) => { window.location = "/web/task/list"; })
				}
			},
			error: function(data) {
				Toast.fire({
					icon: 'error',
					title:"Unable to Update the Task"
				})
			}

		});
	},
	changeTaskStatus : function (ele){
		var status = $(ele).find(':selected')[0].dataset.status;
		var statusId = $(ele).find(':selected')[0].dataset.statusid;
		var taskId = $(ele).find(':selected')[0].dataset.taskid;
	//	alert(status +" & Id : "+statusId + ", & Task Id : " + taskId);
		
		if (taskId == null) {
			Swal.fire("Invalid Status Selection", "Please Select the valid Status !!", "error");
			return;
		}
		
		var dataToSend = {
			"taskId": taskId,
			"taskStatus": statusId,
		}
				var myhtml = document.createElement("div");
		myhtml.innerHTML = "<span style=color:blue><b> Your Request is Processing !! </b></span></br></br>" +
			"<span style=color:black> Please Wait... </span>";
		swal.fire({
			html: myhtml,
			showCloseButton: true,
			showConfirmButton: false,			
		});

		$.ajax({
			method: 'POST',
			url: "/web/task/status/update",
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(dataToSend),

			success: function(data) {
				Toast.fire({
					icon: 'success',
					title: "Updated the Task Status"
				})//.then((result) => { window.location.reload(); })
			},
			error: function(data) {
				Toast.fire({
					icon: 'error',
					title: "Unable to Update Task Status!!"
				})
			}

		});
		
		
		$(ele).css("background-color", "#E6DADA");//.attr('class', 'highlight');
	//	$(ele).find(':selected')[0].dataset.status;
	},
	getStatusData : function (ele){
		$.ajax({
			method: 'GET',
			url: "/api/status/list",
			success: function(data) {
				processStatusData(data);
			}
		});
		let processStatusData = (data) => {
			let Info = '<oprion selected value="" disabled>--  Select Status --</option>';
			data.forEach((data) => {
				Info += `<option value=${data.statusName}>${data.statusDescription}</option>`;
			});
			$('.ticketStatusInfo').html(Info);
		};
		
	},
	saveTaskTimeLog : function (ele){
		var taskId=  $('.getTaskIdForFetchTaskDetails').val(); //$(ele)[0].dataset.taskid; 
		var logDate = $('.taskLogDate').val();
		var logStartTime = $('.taskFromTime').val();
		var logEndTime = $('.taskToTime').val();
		var logHours = $('.totalHours').val();
		var remarks = $('.logHoursRemarks').val();
		var flagType = $('.flag').val();
		
		
		if (taskId == null || taskId == undefined) {
			Swal.fire("Invalid Task Selection ", "Please Enter the Valid Task !!", "error");
			return;
		}

		if (logDate.trim().length == 0) {
			Swal.fire("Invalid Log Date", "Please Enter the Valid Log date !!", "error");
			return;
		}
		//Log hours Validation based On Selection of Option
		if ($(".taskFromTime").is(":visible") == true){

			if (logStartTime.trim().length == 0) {
				Swal.fire("Invalid From Time", "Please Enter Valid From Time", "error");
				return;
			}

			if (logEndTime.trim().length == 0) {
				Swal.fire("Invalid To Time", "Please Enter Valid To Time", "error");
				return;
			}

			if (moment(logStartTime, 'hh:mm a') > moment(logEndTime, 'hh:mm a')) {
				Swal.fire("Invalid Time", "Please Enter Valid Time", "error");
				return;
			}
		}
		else {
			if (logHours.trim().length == 0) {
				Swal.fire("Invalid Log Hours", "Please Enter Valid Log Hours!!", "error");
				return;
			}
		}
		
		
		if (remarks.trim().length <= 10){
			Swal.fire("Please Enter Remarks !!", "Minimum 10 Characters Need to Fill !! ", "error");
				return;
		}
		
		
	
		var date = new Date();
		//var CurrentDate = date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
		var dd = String(date.getDate()).padStart(2, '0');
		var mm = String(date.getMonth() + 1).padStart(2, '0'); //January is 0!
		var yyyy = date.getFullYear();

		today = yyyy + '-' + mm + '-' + dd;

		if (logDate > today) {
			Swal.fire("Invalid Log Date", "You Cannot Select Future Date for Adding Log Hours!!", "error");
			return;
		}
		
		var dataToSend = {
			"taskId": taskId,
			"taskLogDate": logDate,
			"taskFromTime": logStartTime,
			"taskToTime": logEndTime,
			"flagType":flagType,
			"taskLogHours":logHours,
			"remarks": remarks
		}
				var myhtml = document.createElement("div");
		myhtml.innerHTML = "<span style=color:blue><b> Your Request is Processing !! </b></span></br></br>" +
			"<span style=color:black> Please Wait... </span>";
		swal.fire({
			html: myhtml,
			showCloseButton: true,
			showConfirmButton: false,			
		});

		$.ajax({
			method: 'POST',
			url: "/web/task/loghours/add",
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(dataToSend),

			success: function(data) {
				Swal.fire({
					icon: 'success',
					title: data.message
				})//.then((result) => { window.location.reload(); })
					 $('.taskLogDate').val("");
		 $('.taskFromTime').val("");
		 $('.taskToTime').val("");
		 $('.logHoursRemarks').val("");
		 $('.totalHours').val("");
		 $('.projectDetailsBasedOnRoleWise ').val("");
		 $('.TaskDetailsBasedOnProjectWise ').val("");
				 app.model.getlogHoursData(taskId);
			},
			error: function(data) {
				Swal.fire({
					icon: 'error',
					title: "Unable to Add the Log Hours !!"
				})
			}

		});
	},
	editTimeLog : function (ele){
		var taskId=   $('.taskId').val(); 
		var logTableId = $('.logTableId').val();
		var logDate = $('#logdate').val();
		//var logStartTime = $('.tasktEditFromTime').val();
		//var logEndTime = $('.taskEditToTime').val();
		var logHours = $('.logHours').val();
		var remarks = $('#tasklogremarks').val();
		var flag = $('.flagEdit').val();
		
		
		/*	$.ajax({
			method: 'GET',
			url: "/api/task/loghours/"+logTableId,
			success: function(data) {
				if (data == false) {
					Swal.fire({
						title: "You don't have access to Edit the time log !! ",
						icon: "warning",
						button: "ok",
					});
				}
				else{*/
					if (logDate.trim().length == 0) {
			Swal.fire("Invalid Log Date", "Please Enter the Valid Log date !!", "error");
			return;
		}
		if (logHours.trim().length == 0) {
			Swal.fire("Invalid Log Hours", "Please Enter Valid Log Hours", "error");
			return;
		}
		/*if (logEndTime.trim().length == 0) {
			Swal.fire("Invalid To Time", "Please Enter Valid To Time", "error");
			return;
		}
		
		if (moment(logStartTime, 'hh:mm a') > moment(logEndTime, 'hh:mm a')) {
			 Swal.fire("Invalid Time", "Please Enter Valid Time", "error");
			return;
		}*/
		
		var dataToSend = {
			"taskId": taskId,
			"LogTableId": logTableId,
			"taskLogDate": logDate,
			"taskLogHours":logHours,
			//"taskFromTime": logStartTime,
			//"taskToTime": logEndTime,
			"flagType":flag,
			"remarks": remarks
		}
				var myhtml = document.createElement("div");
				myhtml.innerHTML = "<span style=color:blue><b> Your Request is Processing !! </b></span></br></br>" +
			"<span style=color:black> Please Wait... </span>";
		swal.fire({
			html: myhtml,
			showCloseButton: true,
			showConfirmButton: false,			
		});

		$.ajax({
			method: 'POST',
			url: "/web/task/loghours/edit",
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(dataToSend),

			success: function(data) {
				Swal.fire({
					icon: 'success',
					title: data.message
				})
				  window.location.reload();
			},
			error: function(data) {
				Swal.fire({
					icon: 'error',
					title: "Unable to Edit Log Hours !!"
				})
			}

		});
/*				}
			}
		});*/
		
		
		

		
	},
	getlogHoursData : function (ele){
		var taskId = $('.getTaskIdForFetchTaskDetails').val();
		$.ajax({
			method: 'GET',
			url: "/api/task/loghours/"+taskId+"/info",
			success: function(data) {
				processloghoursData(data);
			}
		});
		let processloghoursData = (data) => {
			let Info = '';
			var i = 1;
			data.forEach((data) => {
				var mydate  = `${data.log_date}`;
				var LogHours =  `${data.log_hours}`;
				//var CreatedOndate  = `${data.createdOn}`;
				// Create a UTC date object. The moment constructor will recognize the date as UTC since it includes the 'Z' timezone specifier.
				let utcDate = moment(mydate);
				//let CreatedutcDate =moment(CreatedOndate);
			
			Info += `<tr><td>${i}</td>
			<td hidden>${data.id}</td>
			<td style="cursor: pointer;"><img src="/dist/img/Edit2.png" height="25px;" data-toggle="modal"	data-target="#TimeLogEdit-modal" title=${data.fromtime} '-' ${data.fromtime} ></td>
			<td>${data.task_name}</td>
			<td>${data.project_name}</td>
			<td  class="text-center">${utcDate.format("DD-MM-YYYY")}</td>
			<td hidden>${data.logDate}</td>`;
		if (LogHours == "null"){
			Info += `<td class="text-center">${data.fromtime}</td>
			<td  class="text-center">${data.totime}</td>
			<td  class="text-center">${data.TimeDiff}</td>`;
		}
		else{
			Info +=`	<td class="text-center">-</td>
			<td  class="text-center">-</td>
			<td  class="text-center">${LogHours}</td>`;
		}
		
		Info += `<td  class="text-center">${data.flag}</td>
			<td>${data.employee_name}</td>
			<td>${data.remarks}</td>
			<td hidden>${data.task_id}</td></tr>`;
							i++;
			});
			$('.logHoursInfo').html(Info);
		};
	},
	saveTaskCommentsLog : function (ele){
		var taskId=   $(ele)[0].dataset.taskcommentsid; 
		var taskComment = $('.taskComment').val();

		if (taskComment.trim().length == 0) {
			Swal.fire("Invalid Comemnts", "Please Enter the Valid Comments !!", "error");
			return;
		}
		
		
		var dataToSend = {
			"taskId": taskId,
			"taskcomments": taskComment,
		}
				var myhtml = document.createElement("div");
		myhtml.innerHTML = "<span style=color:blue><b> Your Request is Processing !! </b></span></br></br>" +
			"<span style=color:black> Please Wait... </span>";
		swal.fire({
			html: myhtml,
			showCloseButton: true,
			showConfirmButton: false,			
		});

		$.ajax({
			method: 'POST',
			url: "/web/task/comment/add",
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(dataToSend),

			success: function(data) {
				Toast.fire({
					icon: 'success',
					title: data.message
				})
				 $('.taskComment').val('');
				 app.model.getTaskChatData(taskId);
			},
			error: function(data) {
				Swal.fire({
					icon: 'error',
					title: "Unable to Add Comment!!"
				})
			}

		});
	},
	getTaskChatData : function (ele){
		var taskId = $('.getTaskIdForFetchTaskDetails').val();
		$.ajax({
			method: 'GET',
			url: "/api/task/comment/"+taskId+"/info",
			success: function(data) {
				processCommentsData(data);
			}
		});
		let processCommentsData = (data) => {
			let Info = '';
			data.forEach((data) => {
				var CreatedOndate  = `${data.created_on}`;
				// Create a UTC date object. The moment constructor will recognize the date as UTC since it includes the 'Z' timezone specifier.
				let CreatedutcDate =moment(CreatedOndate);
				Info += `<div class="direct-chat-msg right">
															<div class="direct-chat-infos clearfix">
																<span class="direct-chat-name float-right">${data.employee_name}</span>
																 <span class="direct-chat-timestamp float-left">${CreatedutcDate.format("DD-MM-YYYY HH:mm:ss")}</span>
															</div>
															<img class="direct-chat-img" src="/dist/img/user-sm.png" alt="message user image">
															<div class="direct-chat-text">${data.comments}</div>
														</div>`;
			});
			$('.commentsInfo').html(Info);
		};
	},
	getProjectContactPerson : function (ele){
		var projectId = $('.taskproject').val();
		$.ajax({
			method: 'GET',
			url: "/api/employee/project/assign/"+projectId+"/info",
			//url: "/api/employee/project/"+ticketId+"/info",
			success: function(data) {
				processCommentsData(data);
			}
		});
		let processCommentsData = (data) => {
			let Info = '';
			data.forEach((data) => {
				Info += `<option value=${data.contact_person}>${data.employee_name}</option>`;
			});
			$('.contactPeronsInfo').html(Info);
		};
		
	},
	getProjectContactPersonForProjectPreviewTaskModal : function (ele){
		var projectId = ele;
		$.ajax({
			method: 'GET',
			url: "/api/employee/project/assign/"+projectId+"/info",
			//url: "/api/employee/project/"+ticketId+"/info",
			success: function(data) {
				processCommentsData(data);
			}
		});
		let processCommentsData = (data) => {
			let Info = '';
			data.forEach((data) => {
				Info += `<option value=${data.contact_person}>${data.employee_name}</option>`;
			});
			$('.contactPeronsInfo').html(Info);
		};
		
	},
	getSelectedProjectContactPersonList : function (ele){
		var taskId = $('.getTaskIdForFetchTaskDetails').val();
		$.ajax({
			method: 'GET',
			url: "/api/task/employee/assign/"+taskId+"/info",
			//url: "/api/employee/project/"+ticketId+"/info",
			success: function(data) {
				processCommentsData(data);
			}
		});
		let processCommentsData = (data) => {
			let Info = '';
			data.forEach((data) => {
				Info += `${data.employee_name},`;
			});
			app.model.getSelectedProjectContactPerson(Info.split(","),taskId);
		};
	},
		getSelectedProjectContactPerson : function (selectedValues,taskId){
		$.ajax({
			method: 'GET',
			url: "/api/employee/project/"+taskId+"/info",
			success: function(data) {
				processSelectedData(data);
			}
		});
		let processSelectedData = (data) => {
			let Info = '';
			data.forEach((data) => {
				Info += `<option value=${data.contactPerson.id}>${data.contactPerson.employeeName}</option>`;
			});
			$('.SelectedContactPeronsInfo').html(Info);
			$(".SelectedContactPeronsInfo option").each(function () {
       					selectedValues.forEach((selectedValues) => {
       				    if ($(this).html() == selectedValues) {
       				        $(this).attr("selected", "selected");
       				        return;
       				    }
       					});
       				});
		};
	},
	getSelectedClientEmployeeList : function (){
		var projectId = $('.ticketproject').val();
		
		if (projectId == undefined){
			Swal.fire("Invalid Project Selection", "Please Select the Valid Project !!", "error");
			return;
		}
		
		$.ajax({
			method: 'GET',
			url: "/api/employeelist/all/project/"+projectId+"/info",
			success: function(data) {
				processSelectedData(data);
			}
		});
		let processSelectedData = (data) => {
			let Info = '<option value="" disabled selected>-- Select Client Employee --</option>';
			data.forEach((data) => {
				Info += `<option value=${data.id}>${data.employeeName}</option>`;
			});
			$('.EmployeeListClientWise').html(Info);
		};
	},
	changePasswordByEndUser: function(ele) {
		var id = $("#empId").val();
		var oldPassword = $('#old-password').val();
		var newPassword = $('#new-password').val();

		if (oldPassword.trim().length == 0) {
			Swal.fire({
				title: "Old Password field was Empty !!",
				text: "Please Enter Old Password !!",
				icon: "warning",
				button: "ok",
			});
			return;
		}
		
		if (newPassword.trim().length == 0) {
			Swal.fire({
				title: "New Password field was Empty  !!",
				text: "Please Enter New Password !!",
				icon: "warning",
				button: "ok",
			});
			return;
		}
		
		var dataToSend = {
			userId: id,
			oldPassword:oldPassword,
			newPassword: newPassword,
		}

		var myhtml = document.createElement("div");
		myhtml.innerHTML = "<span style=color:blue><b> Your Request were Processing !! </b></span></br></br>" +
			"<span style=color:black> Please Wait... </span>";
		swal.fire({
			html: myhtml,
			showCloseButton: true,
			showConfirmButton: false,
		});

		$.ajax({
			method: 'POST',
			url: "/ajax/user/changepassword",
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(dataToSend),
			success: function(data) {
				Swal.fire({
					icon: 'success',
					title: "" + data.message + "!!"
				}).then((result) => { window.location.reload(); })
			},
			error: function(data) {
			Swal.fire({
				title: "Password Not Changed!!",
				text: data.responseJSON.message,
				icon: "warning",
				button: "ok",
			});
			}

		});
		},
		empPasswordChangeByAdmin: function(ele) {
		var id = $(".previewEmployeeId").val();
		var newPassword = $('.NewPassword').val();

		if (newPassword.trim().length == 0) {
			Swal.fire({
				title: "Password field was Empty  !!",
				text: "Please Enter Valid Password !!",
				icon: "warning",
				button: "ok",
			});
			return;
		}
		
		var dataToSend = {
			userId: id,
			newPassword: newPassword,
		}

		var myhtml = document.createElement("div");
		myhtml.innerHTML = "<span style=color:blue><b> Your Request were Processing !! </b></span></br></br>" +
			"<span style=color:black> Please Wait... </span>";
		swal.fire({
			html: myhtml,
			showCloseButton: true,
			showConfirmButton: false,
		});

		$.ajax({
			method: 'POST',
			url: "/ajax/admin/changepassword",
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(dataToSend),
			success: function(data) {
				Swal.fire({
					icon: 'success',
					title: "" + data.message + "!!"
				}).then((result) => { window.location.reload(); })
			},
			error: function(data) {
			Swal.fire({
				title: "Password Not Changed!!",
				text: data.responseJSON.message,
				icon: "warning",
				button: "ok",
			});
			}

		});
		},
		getUserLogginInfo : function (){
		$.ajax({
			method: 'GET',
			url: "/ajax/user/logtime/info",
			success: function(data) {
				processTimeLogData(data);
			}
		});
		let processTimeLogData = (data) => {
			let Info = '';
			let EmployeeLoginName='';
				var i = 1;
			data.forEach((data) => {
				const mydate  = `${data.createdOn}`;
				let utcDate = moment(mydate);
				Info += `${utcDate.format("DD-MM-YYYY HH:mm:ss")}`;
				EmployeeLoginName +=`${data.EmployeeName}`;
				
			});
			$('.UserLoginInfo').html(Info);
			$('.EmployeeLoginName').html(EmployeeLoginName);
		};
	}
	,
			getEmployyEmail : function (){
			var EmployeeEmailID=$(".employeeEmail").val();	
			console.log(EmployeeEmailID);
		$.ajax({
			method: 'GET',
			url: "/api/employee/"+EmployeeEmailID,
			success: function(data) {
				if(data==true){
					$(".employeeEmail").val("");
						Swal.fire({
				title: "Email Already Exist ",
				icon: "warning",
				button: "ok",
			});
				}
			}
		});
	
	},
	getEmployeCodeValidation : function (){
			var EmployeeCode=$(".employeeCode").val();	
		$.ajax({
			method: 'GET',
			url: "/api/employee-code/"+EmployeeCode,
			success: function(data) {
				if(data==true){
					$(".employeeCode").val("");
						Swal.fire({
				title: "Employee Code Already Exist ",
				icon: "warning",
				button: "ok",
			});
				}
			}
		});
	
	},
	saveMailConfig : function (ele){
		var projectId =  $('.projectid').val(); 
		var toMail =  $('.toMail').val(); 
		var ccMail =  $('.ccMail').val(); 

		if (projectId == null) {
			Swal.fire("Invalid Project Selection", "Please Select the Project !!", "error");
			return;
		}
		
		if (toMail.trim().length == 0) {
			Swal.fire("To Mail field was Empty", "Please Enter the To Mail !!", "error");
			return;
		}
		
		var dataToSend = {
			"projectId": projectId,
			"toMail": toMail,
			"ccMail": ccMail,
		}
				var myhtml = document.createElement("div");
		myhtml.innerHTML = "<span style=color:blue><b> Your Request is Processing !! </b></span></br></br>" +
			"<span style=color:black> Please Wait... </span>";
		swal.fire({
			html: myhtml,
			showCloseButton: true,
			showConfirmButton: false,			
		});

		$.ajax({
			method: 'POST',
			url: "/admin/mail/config/save",
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(dataToSend),

			success: function(data) {
				Swal.fire({
					icon: 'success',
					title: data.body.message
				}).then((result) => { window.location.reload(); })
			},
			error: function(data) {
				Swal.fire({
					icon: 'error',
					title: data.message
				})
			}

		});
	},
	editMailConfig : function (ele){
		var LogId =  $('.LogId').val(); 
		var toMail =  $('.toMail').val(); 
		var ccMail =  $('#ccMail').val(); 
		var remarks =  $('#MailConfigRemarks').val(); 

		if (LogId.trim().length == 0) {
			Swal.fire("Invalid Client Details", "Please Select the Valid Client Details !!", "error");
			return;
		}
		
		if (toMail.trim().length == 0) {
			Swal.fire("To Mail field was Empty", "Please Enter the To Mail !!", "error");
			return;
		}
		
		var dataToSend = {
			"id": LogId,
			"toMail": toMail,
			"ccMail": ccMail,
			"remarks":remarks
		}
				var myhtml = document.createElement("div");
		myhtml.innerHTML = "<span style=color:blue><b> Your Request is Processing !! </b></span></br></br>" +
			"<span style=color:black> Please Wait... </span>";
		swal.fire({
			html: myhtml,
			showCloseButton: true,
			showConfirmButton: false,			
		});

		$.ajax({
			method: 'POST',
			url: "/admin/mail/config/edit",
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(dataToSend),

			success: function(data) {
				Swal.fire({
					icon: 'success',
					title: data.body.message
				}).then((result) => { window.location.reload(); })
			},
			error: function(data) {
				Swal.fire({
					icon: 'error',
					title: data.message
				})
			}

		});
	},
	getClientDataForNotificationConfig: function(ele) {
		$.ajax({
			method: 'GET',
			url: "/api/client/list/notification-config",
			success: function(data) {
				processClientDataForNotif(data);
			}
		});
		let processClientDataForNotif = (data) => {
			let clientIdAndName = '<option selected disabled value="">-- Select Client --</option>';
			data.forEach((data) => {
				clientIdAndName += `<option value=${data.id}>${data.clientName}</option>`;			
			});
			$('.clientDetails').html(clientIdAndName);
		};

	},
	saveNotificationsConfig : function (ele){
		var clientId =  $('#clientId').val(); 
		var EmployeeCreationStatus =  $('#EmployeeCreation').val(); 
		var TicketCreateUpdateStatus =  $('#TicketCreateUpdate').val(); 
		var TicketAssignStatus =  $('#TicketAssign').val(); 

		if (clientId == null) {
			Swal.fire("Invalid Client Selection", "Please Select the Client !!", "error");
			return;
		}
		
		var EmployeeCreation =	$('#EmployeeCreation').is(':checked') ? "true" : "false";//"Checked" : "UnChecked";
		var TicketCreateUpdate =	$('#TicketCreateUpdate').is(':checked') ? "true" : "false"; 
		var TicketAssign = $('#TicketAssign').is(':checked') ?  "true" : "false";
		var Comments = $('#Comments').is(':checked') ?  "true" : "false";
		
		var dataToSend = {
			"clientId": clientId,
			"employeeCreation": EmployeeCreation,
			"ticketCreateUpdate": TicketCreateUpdate,
			"ticketStatus":TicketAssign,
			"comments":Comments
		}
				var myhtml = document.createElement("div");
		myhtml.innerHTML = "<span style=color:blue><b> Your Request is Processing !! </b></span></br></br>" +
			"<span style=color:black> Please Wait... </span>";
		swal.fire({
			html: myhtml,
			showCloseButton: true,
			showConfirmButton: false,			
		});

		$.ajax({
			method: 'POST',
			url: "/admin/notification/config/save",
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(dataToSend),

			success: function(data) {
				Swal.fire({
					icon: 'success',
					title: data.body.message
				}).then((result) => { window.location.reload(); })
			},
			error: function(data) {
				Swal.fire({
					icon: 'error',
					title: data.message
				})
			}

		});
	},
	editNotificationsConfig : function (ele){
		var id = $(ele).closest('tr')[0].dataset.lineid;
		if (id == null) {
			Swal.fire("Invalid Client Selection", "Please Select the Client !!", "error");
			return;
		}
		
		var EmployeeCreation =	$(ele).closest('tr').find('.EmployeeAddNotif').is(':checked') ? "true" : "false";//"Checked" : "UnChecked";
		var TicketCreateUpdate =	$(ele).closest('tr').find('.TicketCreationUpdationNotif').is(':checked') ? "true" : "false"; 
		var TicketAssign = $(ele).closest('tr').find('.TicketStatusChangeNotif').is(':checked') ?  "true" : "false";
		var Comments = $(ele).closest('tr').find('.CommentsNotif').is(':checked') ?  "true" : "false";
		
		var dataToSend = {
			"id": id,
			"employeeCreation": EmployeeCreation,
			"ticketCreateUpdate": TicketCreateUpdate,
			"ticketStatus":TicketAssign,
			"comments":Comments
		}
				var myhtml = document.createElement("div");
		myhtml.innerHTML = "<span style=color:blue><b> Your Request is Processing !! </b></span></br></br>" +
			"<span style=color:black> Please Wait... </span>";
		swal.fire({
			html: myhtml,
			showCloseButton: true,
			showConfirmButton: false,			
		});

		$.ajax({
			method: 'POST',
			url: "/admin/notification/config/edit",
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(dataToSend),

			success: function(data) {
				Swal.fire({
					icon: 'success',
					title: data.body.message
				})//.then((result) => { window.location.reload(); })
			},
			error: function(data) {
				Swal.fire({
					icon: 'error',
					title: data.message
				})
			}

		});
	},
	createMileStone : function (ele){
		var projectid=   $('.projectId').val(); 
		var milestoneName = $('.milestoneName').val();
		var milestoneDescription = $('.milestoneDescription').val();
		var targetdate = $('.milestoneTargetDate').val();
		var categoryId= $('.categoryId').val();
		

		if (milestoneName.trim().length == 0) {
			Swal.fire("Invalid Stage Name", "Please Enter the Valid Stage Name !!", "error");
			return;
		}
		
		var dataToSend = {
			"projectId": projectid,
			"mileStoneName":milestoneName,
			"mileStoneDescription":milestoneDescription,
			"targetDate": targetdate,
			"categoryId":categoryId
		}
				var myhtml = document.createElement("div");
		myhtml.innerHTML = "<span style=color:blue><b> Your Request is Processing !! </b></span></br></br>" +
			"<span style=color:black> Please Wait... </span>";
		swal.fire({
			html: myhtml,
			showCloseButton: true,
			showConfirmButton: false,			
		});

		$.ajax({
			method: 'POST',
			url: "/web/milestone/create",
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(dataToSend),

			success: function(data) {
				Swal.fire({
					icon: 'success',
					title: data.body.message
				}).then((result) => { window.location.reload(); })
			},
			error: function(data) {
				Swal.fire({
					icon: 'error',
					title: data.message
				})
			}

		});
	},
	updateMileStone : function (ele){
		var EditStageId=   $('.EditStageId').val(); 
		var EditStageName = $('.EditStageName').val();
		var EditStageDesc = $('.EditStageDesc').val();
		var EditStageTargetDate = $('.EditStageTargetDate').val();

		if (EditStageName.trim().length == 0) {
			Swal.fire("Invalid Stage Name", "Please Enter the Valid Stage Name !!", "error");
			return;
		}
		
		if (EditStageDesc.trim().length == 0) {
			Swal.fire("Invalid Stage Name", "Please Enter the Valid Stage Name !!", "error");
			return;
		}
		
		var dataToSend = {
			"mileStoneId": EditStageId,
			"mileStoneName":EditStageName,
			"mileStoneDescription":EditStageDesc,
			"targetDate": EditStageTargetDate
		}
				var myhtml = document.createElement("div");
		myhtml.innerHTML = "<span style=color:blue><b> Your Request is Processing !! </b></span></br></br>" +
			"<span style=color:black> Please Wait... </span>";
		swal.fire({
			html: myhtml,
			showCloseButton: true,
			showConfirmButton: false,			
		});

		$.ajax({
			method: 'POST',
			url: "/web/milestone/update",
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(dataToSend),

			success: function(data) {
				Swal.fire({
					icon: 'success',
					title: data.body.message
				}).then((result) => { window.location.reload(); })
			},
			error: function(data) {
				Swal.fire({
					icon: 'error',
					title: "Unable to Update the Stage Details !!"
				})
			}

		});
	},
	saveMOMNotablePoints : function (ele){
		var MoMId=    $(ele)[0].dataset.momid;
		var projectId=    $(ele)[0].dataset.projectid;
		var description=   $('.mom_description').val();
		var targetDate=   $('.mom_targetDate').val();
		var responsiblePerson=   $('.mom_responsiblePerson').val();
		var stage=   $('.mom_stage').val();
		
		if (description.trim().length == 0) {
			Swal.fire("Invalid Description", "Please Enter the Valid Description !!", "error");
			return;
		}
		
		if (targetDate.trim().length == 0) {
			Swal.fire("Invalid Target Date", "Please Enter the Valid Target Date !!", "error");
			return;
		}
		
		if (responsiblePerson.trim().length == 0) {
			Swal.fire("Invalid Responsible Person", "Please Enter the Valid Responsible Person !!", "error");
			return;
		}
		
		var dataToSend = {
			"momId":MoMId,
			"projectId":projectId,
			"description": description,
			"targetDate": targetDate,
			"employeeId":responsiblePerson,
			"stageId":stage
		}
				var myhtml = document.createElement("div");
		myhtml.innerHTML = "<span style=color:blue><b> Your Request is Processing !! </b></span></br></br>" +
			"<span style=color:black> Please Wait... </span>";
		swal.fire({
			html: myhtml,
			showCloseButton: true,
			showConfirmButton: false,			
		});

		$.ajax({
			method: 'POST',
			url: "/web/mom/notable-point/add",
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(dataToSend),

			success: function(data) {
				Swal.fire({
					icon: 'success',
					title: " Added the Notable Point!!"
				})
				 $('.mom_description').val('');
				 app.model.getMoMNotablePointsByMoMId();
			},
			error: function(data) {
				Swal.fire({
					icon: 'error',
					title: "Unable to Add Notable Point!!"
				})
			}

		});
	},
	getMoMNotablePointsByMoMId : function (ele){
		var MoMId = $('.MoMId').val();
		$.ajax({
			method: 'GET',
			url: "/api/mom/notable-points/"+MoMId+"/list",
			success: function(data) {
				processCommentsData(data);
			}
		});
		let processCommentsData = (data) => {
			let Info = '';
			let i=1;
			data.forEach((data) => {
				var CreatedOndate  = `${data.target_date}`;
				// Create a UTC date object. The moment constructor will recognize the date as UTC since it includes the 'Z' timezone specifier.
				let CreatedutcDate =moment(CreatedOndate);
				var TaskId = `${data.task_id}`;
				Info += `<tr><td>${i}</td>
			<td hidden>${data.id}</td>
			<td>${data.description}</td>
			<td  class="text-center">${CreatedutcDate.format("DD-MM-YYYY")}</td>
			<td class="text-center" data-employeeId=${data.employee_id}>${data.employee_name}</td>
			<td data-stageId=${data.stage_id} class="text-center">${data.milestone_name}</td>`
			if (TaskId == "null"){
				Info += `
			<td class="text-center"><img src="/dist/img/edit.png" height="25px;" Onclick="app.model.ConvertNotablePointToTask(this);"  data-notablepointid=${data.id} ></td>
			</tr>`;
			}
			else{
				Info += `<td class="text-center text-bold">Task Id: ${data.task_id}</td></tr>`
			}
							i++;
			});
			$('.NotablePointsInfo').html(Info);
		};
	},
	ConvertNotablePointToTask : function (ele){
			var NotablePointId=    $(ele)[0].dataset.notablepointid;
		Swal.fire({
			title: 'Do you want to Convert To Task !!',
			showDenyButton: true,
			icon: 'warning',
			confirmButtonText: 'Create Task',
			denyButtonText: `Don't Create`,
		}).then((result) => {
			/* Read more about isConfirmed, isDenied below */
			if (result.isConfirmed) {

				var url = "/web/mom/notable-point/convert/" + NotablePointId + "/task";
				$.ajax({
					method: 'POST',
					url: url,
					contentType: "application/json; charset=utf-8",
					success: function(data) {
						
						if (!data) {
							swal.fire({
								icon: 'warning',
								title: 'Unable to Convert to Task!!'
							})
						} else {
							swal.fire({
								icon: 'success',
								title: data.message 
							}).then((result) => { window.location.reload(); })
}
					},
					error: function(data) {
						var myhtml = document.createElement("div");
						myhtml.innerHTML = "<span style=color:blue><b> Unable to Converted ! " + data.message + " </b></span></br></br>" +
							"<span style=color:black> Please Wait... </span>";
						swal.fire({
							html: myhtml,
							showCloseButton: true,
							showConfirmButton: false,
							timer: 1500

						});
					}

				});
			} else if (result.isDenied) {
				Swal.fire('Notable Point was Not Converted to Task !!', '', 'info')
			}
		})
		
	},
	TriggerTimeFormat : function(ele){
		if (ele == "Hours") {
			$('.StartEndTimeFormat').show();//.css('display','none');
			$('.HoursTimeFormat').hide();
			$('.taskFromTime').val("");
			$('.taskToTime').val("");
			$('.totalHours').val("");
		}
		else {
			$('.StartEndTimeFormat').hide();//.css('display','none');
			$('.HoursTimeFormat').show();
			$('.totalHours').val("");
			$('.taskFromTime').val("");
			$('.taskToTime').val("");
		}
		
	},
	getProjectContactPersonForTimeSheet : function (ele){
		var projectId = $('.taskproject').val();
		$.ajax({
			method: 'GET',
			url: "/api/employee/project/assign/"+projectId+"/info",
			//url: "/api/employee/project/"+ticketId+"/info",
			success: function(data) {
				processCommentsData(data);
			}
		});
		let processCommentsData = (data) => {
			let Info = '<option value="0">All</option>';
			data.forEach((data) => {
				Info += `<option value=${data.contact_person}>${data.employee_name}</option>`;
			});
			$('.contactPeronsInfo').html(Info);
		};
		
	}
	
	};
	
app.groupmoduleinit = function() {
	app.model.getClientData();
	app.model.getGroupData();
}

app.employeemoduleinit = function() {
	 app.model.getClientData();
	app.model.getEmployeeData();
}

app.projectmoduleinit = function() {
	 app.model.getClientData();
	 //	app.model.getProjectData();
}

app.tasktmoduleinit = function() {
	 app.model.getProjectData();
	 app.model.getStatusData();
	 app.model.getMileStoneListProjectSelection();
	 app.model.getProjectContactPerson();
}

app.ticketViewModuleInit = function() {
	 //app.model.getTicketData();
}

app.taskPreviewModuleInit = function(){
	app.model.getSelectedProjectContactPersonList();
	app.model.getlogHoursData();
	app.model.getTaskChatData();
}
