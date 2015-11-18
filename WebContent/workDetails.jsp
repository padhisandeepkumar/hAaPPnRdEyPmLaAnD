<style>
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

table>thead>tr>th {
	text-align: center;
}

table#workDetailtable {
	font-size: 0.8em;
}
tr>th {
	background-color: #3c763d;
	color: #F4F2F5;
}
</style>
<input type='hidden' name='mode' id='mode' value='' />
<div id='workMgmt'>
	<div class="container-fluid" style="padding-top: 85px;">
		<div class="panel panel-default">
			<div class="panel-heading" style='height: 60px;'>
				<div class="pull-left"><h4>Work Details</h4></div>
				<div class="btn-group pull-right">
					<ul class="nav nav-pills">
						<li class="active" onclick='javascript:displayWorkDetails();'>
							<a href="#" data-toggle="tab">Work Details</a>
						<li onclick='javascript:workManagement();'>
							<a href="#" data-toggle="tab">Upload Work Details</a>
					</ul>
				</div>
			</div>
			
			<div class="panel-body" style='display:block'>
				<div class="form-group">
					<div class="col-sm-4" style='padding-left: 0px;'>
						<label class="form-control-label col-sm-4" for="text"
							style='padding-left: 0px;'>Work Group Name</label>
						<div class="col-sm-8" style='padding-left: 0px;'>
							<select class="form-control input-sm" id="workGroupCombo">
								<option value="0">Select</option>
							</select>
						</div>
					</div>
				</div>
			</div>
			
			<div class="panel-footer clearfix" style='padding: 5px;'>
				<div class="pull-right">
					<a href="javascript: loadWorkDetailsGrid();" class="btn btn-primary btn-sm">Get Work Details</a>
				</div>
			</div>
			
		</div>
		
		
		<div class="panel panel-default">
			<div class="panel-body" style='display:block'>
				<table class="table table-bordered table-hover" id='workDetailtable'>
					<thead>
						<tr>
							<th>Select</th>
							<th>Work Group</th>
							<th>Sub Work Description</th>
							<th>Per hour Rate</th>
						</tr>
					</thead>
				</table>
			</div>
			
			<div class="panel-footer clearfix" style='padding: 5px;'>
				<span id='messageSpan'></span>
				<div class="pull-right">
					<a href="javascript: displayAddSubServiceModal('add');" class="btn btn-primary btn-sm">Add</a>
					<a href="javascript: deleteWorkDetails();" class="btn btn-primary btn-sm">Delete</a>
					<a href="javascript: displayAddSubServiceModal('modify');" class="btn btn-primary btn-sm">Modify</a>
				</div>
			</div>
			
		</div>
	</div>
</div>

<!-- Add service modal Starts -->
<div id="addSubServiceModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" style='font-size: 12px; font-weight: bold;'>Create a Work Group / Sub Service</h4>
				</div>
				<div class="modal-body">
					<div class="input-group">
						<select class="selectpicker regularfont-12px" data-style="btn-warning" id='subworkCmbBx' name='subworkCmbBx' 
						style='width:250px;height: 30px;padding: 2px 2px;font-size: 12px;border-radius: 5px;background:#ece65f;'>
							<option id='0' value='0'>Select Works Group</option>
						</select>
					</div>
					<div class='separator1'></div>
					<div class="input-group">
						<span class="input-group-addon"><i class="fa fa-cog"></i><span style='color:red;font-size:12px;'>&nbsp;*</span></span> 
						<input type="text" autocomplete='off' class="form-control" id="subserviceDesc" placeholder="Sub Service Description" autofocus maxlength="255" style='width:430px;'>
						<span class="input-group-addon"><i class="fa fa-inr"></i><span style='color:red;font-size:12px;'>&nbsp;*</span></span>
						<input type="text" autocomplete='off' class="form-control" id="subserviceFee" placeholder="Fee" style='padding:5px;width:45px;'>
					</div>
				</div>
				<div class="modal-footer">
					<span class="pull-left" id="errorMsg"></span>
					<button type="button" class="btn btn-danger btn-xs" onClick="createModifyService($('#mode').val());">
						Create Service
					</button>
				</div>
			</div>
		</div>
	</div>
<!-- Add service modal Ends -->
<script>
$( document ).ready(function() {
	loadWorkDetailsGrid();
	lodaWorkGroup($("#workGroupCombo"));
});

var selectedSubserviceId = '';
function displayAddSubServiceModal(modeVal){
	$('#mode').val(modeVal);
	lodaWorkGroup($("#subworkCmbBx"));
	$("#messageSpan").text('');
	
	if(modeVal=='modify'){
		var chkId = '';
		var checkId = [];
		var key = '';
		var container = '';
		$("tbody tr td:nth-child(1)").each(function(i, val) {
			key = $(val).html();
			container = $($.parseHTML($(val).html()));
			if (container.is(":checked")) {
				chkId = container.attr("id").split("_")[1];
				if (parseInt(chkId) > 0 && checkId.length<=2) {
					checkId.push(chkId);
				}else{
					return false;
				}
			}
		});
		if(checkId.length==1){
			if (parseInt(chkId) > 0) {
				showModal('addSubServiceModal');
				var rowObj = $('#row_'+chkId);
				var workGrp = rowObj.children("td:nth-child(2)").text();
				var subServiceDesc = rowObj.children("td:nth-child(3)").text();
				var subServiceFees = rowObj.children("td:nth-child(4)").text();
				
				selectedSubserviceId = chkId;
				$("#subworkCmbBx option").each(function (a, b) {
		            if ($(this).html() == workGrp ) $(this).attr("selected", "selected");
		        });
				$("#subserviceDesc").val(subServiceDesc);
				$("#subserviceFee").val(subServiceFees);
			}
		}else{
			$("#messageSpan").text('Please select only one service to modify').addClass("errormsg");
			return false;
		}
	}else if(modeVal=='add'){
		showModal('addSubServiceModal');
	}
}

function createModifyService(mode){
	var selectedWorkId = $("#subworkCmbBx").val();
	var newSubServiceDesc = $("#subserviceDesc").val();
	var newSubServiceFee = $("#subserviceFee").val();
	
	var msg = '';
	if(parseInt(selectedWorkId)==0){
		msg = 'Please select a group for the sub-service';
	}else if(isEmpty(newSubServiceDesc)){
		msg = 'Please provide the sub-service description';
	}else if(isEmpty(newSubServiceFee)){
		msg = 'Please provide the hourly price for the sub-service';
	}
	
	if(msg!=''){
		$("#errorMsg").text(msg).css("color", "#fff");
	}else{
		var url = "action";
		var params = {"nav_action" : "createModifyService", "selectedSubserviceId":selectedSubserviceId, "selectedWorkId": selectedWorkId, "newSubServiceDesc":newSubServiceDesc, "newSubServiceFee":newSubServiceFee,"mode":mode};
		$
		.ajax({
			async : false,
			url : url,
			type : "POST",
			data : params,
			success : function(data, textStatus, jqXHR) {
				if(parseInt(data)<0){
					if(mode=='modify'){
						$("#errorMsg").text('Failed to modify the service').css("color", "#fff");
					}else{
						$("#errorMsg").text('Failed to create the service').css("color", "#fff");
					}
				}else{
					closeAllModal();
					loadWorkDetailsGrid();
					$("#messageSpan").addClass('successMsg');
					if(mode=='modify'){
						$('#messageSpan').text('Successfully Modified the service.');
					}else{
						$('#messageSpan').text('Successfully Created the service.');
					}
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert('error while creating / modifying a service');
			}
		});
	}
}

	function loadWorkDetailsGrid() {
		var trId = 0;
		var ctr = 0;
		var selectedWorkGroup = $("#workGroupCombo").val();
		var url = "action";
		var params = {
			"nav_action" : "renderWorkDetailGridData", "selectedWorkGrpId": selectedWorkGroup
		};
		$
				.ajax({
					async : false,
					url : url,
					type : "GET",
					data : params,
					dataType : "json",
					success : function(data, textStatus, jqXHR) {
						var tbody = $('#workDetailtable');
						$("#workDetailtable").find("tr:gt(0)").remove();
						var tr = $("<tr id='row_"+trId+"'>");
						var td = $('<td />');
						var idTD = '', worknameTD = '', subworkdescTD = '', priceTD = '';
						$
								.each(
										data,
										function() {
											//tr = $("<tr id='row_"+ctr+"'>");ctr=ctr+1;
											$
													.each(
															this,
															function(idx, value) {
																if (idx == 'id') {
																	idTD = td
																			.clone()
																			.html(
																					"<input type='checkbox' id='chkBx_"
																							+ value
																							+ "' value='"
																							+ value
																							+ "' onchange='setchecked(this);' />");
																} else if (idx == 'workname') {
																	worknameTD = td
																			.clone()
																			.css(
																					'text-align',
																					'center')
																			.html(
																					value);
																} else if (idx == 'subworkdesc') {
																	subworkdescTD = td
																			.clone()
																			.text(
																					value);
																} else if (idx == 'price') {
																	priceTD = td
																			.clone()
																			.text(
																					value);
																}
															});
											var container = $($.parseHTML($(
													idTD).html()));
											tr = $("<tr id='row_"
													+ container.val()
													+ "' onclick=''>");
											tr.append(idTD);
											tr.append(worknameTD);
											tr.append(subworkdescTD);
											tr.append(priceTD);

											tbody.append(tr);
										});
					},
					error : function(jqXHR, textStatus, errorThrown) {
						alert('error while rendering the service detail grid');
					}
				});
	}
	
	
	function deleteWorkDetails() {
		if (confirm('Selected service will be deleted permanently. Kindly confirm.')) {
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
			var params = {"nav_action" : "deleteWorkDetails", "selectedIds" : checkId.toString()};
			
			$.ajax({
				url : url,
				async : false,
				type : "GET",
				data : params,
				dataType : "text",
				success : function(data, textStatus, jqXHR) {
					$("#messageSpan").removeClass();

					if (parseInt(data)>=0) {
						$("#messageSpan").addClass('successMsg');
						$('#messageSpan').text(
								'Successfully deleted the selected Works.');
						loadWorkDetailsGrid();
					} else if (data != 1) {
						$("#messageSpan").addClass('failureMsg');
						$('#messageSpan').text('Failed to delete the selected works.');
					}
				},
				error : function(jqXHR, textStatus, errorThrown) {
					alert('Error while deleting the work details.')
				}
			});
		}
	}
</script>