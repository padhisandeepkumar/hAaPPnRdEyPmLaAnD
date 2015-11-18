<%@page import="com.replad.utils.StringUtilities"%>
<%@page import="com.replad.utils.DateUtils"%>
<%@page import="java.io.File"%>
<%@page import="com.replad.utils.LoadProperties"%>
<%@page import="com.replad.utils.StringUtilities"%>
<%@page import="com.replad.bean.work.WorkHelper"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.replad.sub.work.UserTicketHelper"%>
<%@page import="com.replad.sub.work.UserTicketDetailBean"%>
<%@page import="java.util.List"%>
<style type="text/css">
tr>th {
	background-color: #3c763d;
	color: #F4F2F5;
}

.fa-check-circle-o {
	color: #00CC00;
}

.fa-spinner {
	color: #B40404;
}

.bottom {
	font-size: 0.8em;
}

.fa-close {
	color: #FE2E2E;
}

table>thead>tr>th {
	text-align: center;
}

table#ordertable {
	font-size: 0.8em;
}

.panel-heading.contains-buttons { .clearfix; .panel-title { .pull-left;
	padding-top: 1px;
}

}
.nav-pills>li.active>a, .nav-pills>li.active>a:focus {
	color: black;
	background-color: #fcd900;
}

.nav-pills>li.active>a:hover {
	background-color: #fcd900;
	color: black;
}

.nav-pills>li {
	color: #F4F2F5;
}

.nav-pills>li {
	background-color: #FCE75D;
	color: #0C0C0C;
}
</style>

<script type="text/javascript" src="./js/jQuery.succinct.min.js"></script>
<%
String userName = (null!=session.getAttribute("username")?session.getAttribute("username").toString() : "");
	boolean isSignIn = (StringUtilities.isBlank(userName));
	System.out.println("manageEmployee--->isSignIn=="+isSignIn);
	if(isSignIn) {
		response.sendRedirect("/adminLogin.jsp");
	} 
%>
<div class="container-fluid" style="padding-top: 85px;">
	<div class="panel panel-default">
		<div class="panel-heading" style='height: 60px;'>
			<div class="pull-left">
				<h4>Manage employee</h4>
			</div>
			<div class="btn-group pull-right">
				<ul class="nav nav-pills">
					<li class="active" onclick='javascript: addEmployee();'><a
						href="#" data-toggle="tab">Add Details</a>
				</ul>
			</div>
		</div>
		<div class="panel-body">
			<div class='form-group'>
				<div class="col-sm-4" style='padding-left: 0px;'>
					<label class="form-control-label col-sm-4" for="text"
						style='padding-left: 0px;'>Category</label>
					<div class="col-sm-8" style='padding-left: 0px;'>
						<select class="form-control input-sm" id="categoryCombo">
							<option value="0">Select</option>
						</select>
					</div>
				</div>

			<div class="col-sm-4" style='padding-left: 0px;'>
					<label class="form-control-label col-sm-4" for="text"
						style='padding-left: 0px;'>Category</label>
					<div class="col-sm-8" style='padding-left: 0px;'>
						<select class="form-control input-sm" id="categoryStatus">
							<option value="-1">Select</option>
							<option value="1">Active</option>
							<option value="0">In-Active</option>
						</select>
					</div>
				</div>
				
			</div>
		</div>

		<div class="panel-footer clearfix" style='padding: 5px;'>
			<span id='messageSpan'></span>
			<div class="pull-right" onclick='javascript: loadEmployeeGrid();'>
				<a href="#" class="btn btn-primary btn-sm">Get employee details</a>
			</div>
		</div>

	</div>

	<div class="panel panel-default">
		<div class="panel-body">
			<table class="table table-bordered table-hover" id='ordertable'>
				<thead>
					<tr>
						<th>Select</th>
						<th>Name</th>
						<th>Category</th>
						<th>Joining Date</th>
						<th>Mobile Number</th>
					</tr>
				</thead>
			</table>
		</div>
		<div class="panel-footer clearfix">
			<span id='messageSpan'></span>
			<div class="pull-right">
				<a href="#" onclick='javascript:deleteEmployee();' class="btn btn-primary btn-sm">Delete</a> 
				<a href="#" onclick='javascript:editEmployee();' class="btn btn-primary btn-sm">Edit</a> 
				<a href="#" class="btn btn-primary btn-sm">Active</a>
			</div>
		</div>
	</div>

</div>



<script>
	var statusGV = '';
	var empTypeGV = '';

	function deleteEmployee() {
		var chkId = '';
		var checkId = [];
		var key = '';
		var container = '';
		$("tbody tr td:nth-child(1)").each(function(i, val) {
			key = $(val).html();
			container = $($.parseHTML($(val).html()));
			if (container.is(":checked")) {
				chkId = container.attr("id").split("_")[1];
				if (parseInt(chkId) > 0) {
					checkId.push(chkId);
				}
			}
		});

		var url = "action";
		var params = {
			"nav_action" : "deleteEmployee",
			"selectedIds" : checkId.toString()
		};

		$.ajax({
			url : url,
			async : false,
			type : "POST",
			data : params,
			dataType : "text",
			success : function(data, textStatus, jqXHR) {
				$("#messageSpan").removeClass();

				if (parseInt(data) == 1) {
					$("#messageSpan").addClass('successMsg');
					$('#messageSpan').text(
							'Successfully deleted the selected employees.');
					loadEmployeeGrid();
				} else if (data != 1) {
					$("#messageSpan").addClass('failureMsg');
					$('#messageSpan').text('Failed to delete the employees.');
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert('Error while deleting the employee profile.')
			}
		});
	}

	$(function() {
		populateCombo("categoryCombo");

		loadEmployeeGrid();

		$('.table-hover tr').click(function() {
			removeRowStyle();
			var rowId = $(this).attr("row-key");
			if (typeof rowId != 'undefined') {
				$(this).css('background-color', '#777');
				openWorkDetails(rowId);
			}
		});

		$('.truncate').succinct({
			size : 75
		});

		// on click of status button initializing the value to the global variable statusGV
		$("div.btn-group[id='statusBtnValue']").each(function() {
			var group = $(this);
			$('button', group).each(function() {
				var button = $(this);
				//$(this).removeClass();
				button.removeClass('active');//button.addClass('btn btn-default');
				button.click(function() {
					statusGV = $(this).val();
					$(this).addClass('active');
				});
			});
		});
		// on click of employee type button initializing the value to the global variable empTypeGV
		$("div.btn-group[id='emptypeBtnValue']").each(function() {
			var group = $(this);
			$('button', group).each(function() {
				var button = $(this);
				button.removeClass('active');//button.addClass('btn btn-default');
				button.click(function() {
					empTypeGV = $(this).val();
					$(this).addClass('active');
				});
			});
		});

	});

	function setchecked(obj) {
		if (obj.checked) {
			$(obj).attr("checked", "checked");
		} else {
			$(obj).removeAttr('checked');
		}
	}
	function loadEmployeeGrid() {
		var trId = 0;
		var ctr = 0;
		var url = "action";
		var categoryCombo = $('#categoryCombo').val();
		var categoryStatus = $('#categoryStatus').val();
		var params = {"nav_action" : "renderEmployeeDetailGridData", "categoryStatus": categoryStatus, "categoryCombo" : categoryCombo};
		$.ajax({
					url : url,
					type : "GET",
					data : params,
					dataType : "json",
					success : function(data, textStatus, jqXHR) {
						var tbody = $('#ordertable');
						$("#ordertable").find("tr:gt(0)").remove();
						var tr = $("<tr id='row_"+trId+"'>");
						var td = $('<td />');
						var descTD = '', statusTD = '', createdDateTD = '', scheduleDateTD = '', expectedDateTD = '', closedDateTD = '';
						$.each(data, function() {
							$.each(this, function(idx, value) {
								if (idx == 'id') {
									idTD = td.clone().html("<input type='checkbox' id='chkBx_"+ value+ "' value='"+ value + "' onchange='setchecked(this);' />");
								} else if (idx == 'name') {
									nameTD = td.clone().css('text-align','center').html(value);
								} else if (idx == 'category') {
									categoryTD = td.clone().text(value);
								} else if (idx == 'joining_date') {
									joiningDateTD = td.clone().text(value);
								} else if (idx == 'mobile_primary') {
									mobileNumTD = td.clone().text(value);
								}
							});
							var container = $($.parseHTML($(idTD).html()));
							tr = $("<tr id='row_"+ container.val()+ "' onclick=''>");
							tr.append(idTD);
							tr.append(nameTD);
							tr.append(categoryTD);
							tr.append(joiningDateTD);
							tr.append(mobileNumTD);

							tbody.append(tr);
						});
					},
					error : function(jqXHR, textStatus, errorThrown) {
						alert('error');
					}
				});
	}

function editEmployee(){
	var chkId = '';
	var checkId = [];
	var key = '';
	var container = '';
	$("tbody tr td:nth-child(1)").each(function(i, val) {
		key = $(val).html();
		container = $($.parseHTML($(val).html()));
		if (container.is(":checked")) {
			chkId = container.attr("id").split("_")[1];
			if (parseInt(chkId) > 0) {
				checkId.push(chkId);
			}
		}
	});
	if(checkId.length==0){
		alert("Please Select at least one Employee");
		return;
	}
	if(checkId.length>1){
		alert("Please Select only one Employee");
		return;
	}
	$('#contentDiv').load('addEmployee.jsp');
	var url = "action";
	var params = { "nav_action" : "populateEmployeDataToEdit", "selectedEmpId":checkId[0]};
	$.ajax({
		async: false,
		url : url,
		type : "GET",
		data : params,
		dataType : "json",
		async: false,
		success : function(data, textStatus, jqXHR) {
			$.each(data, function() {
				$.each(this, function(idx, value) {
					if (idx == 'id') {
						$("#employeeId").val(value);
					} else if (idx == 'first_name') {
						$("#fname").val(value);
					} else if (idx == 'middle_name') {
						$("#mname").val(value);
					} else if (idx == 'last_name') {
						$("#lname").val(value);
					} else if (idx == 'mobile_primary') {
						$("#primaryMobile").val(value);
					} else if (idx == 'mobile_secondary') {
						$("#secondaryMobile").val(value);
					} else if (idx == 'joining_date') {
						$("#joiningDate").val(value);
					} else if (idx == 'id_proof') {
						$("#idCombo").val(value);
					} else if (idx == 'address_proof') {
						$("#addressCombo").val(value);
					} else if (idx == 'employee_type') {
						$("#typeCombo").val(value);
					} else if (idx == 'employment_grade') {
						$("#gradeCombo").val(value);
					}else if (idx == 'category_type') {
						$("#categoryCombo").val(value);
					}else if (idx == 'status') {
						$("#employeeStatus").val(value);
					}
				});
			});
		}
	});
}
</script>