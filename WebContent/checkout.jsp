<%@page import="com.replad.utils.StringUtilities"%>
<%@page import="com.replad.init.InitConfiguration"%>
<%@page import="com.replad.utils.StringUtilities"%>
<%@page import="com.replad.sub.work.SubworkHelper"%>
<%@page import="com.replad.bean.work.WorkBean"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%= InitConfiguration.commonPropertiesMap.get("page.title")%></title>

<link rel="stylesheet" type="text/css"
	href="./font-awesome-4.3.0/css/font-awesome.css">
<link rel="stylesheet" type="text/css"
	href="./bootstrap-3.2.0/dist/css/bootstrap.css" />
<link rel="stylesheet" type="text/css"
	href="./bootstrap-3.2.0/dist/css/bootstrap-theme.css" />

<link rel="stylesheet" type="text/css" href="./css/jquery-ui.css">
<link rel="stylesheet" type="text/css"
	href="./css/jquery-ui.structure.css">
<link rel="stylesheet" type="text/css" href="./css/jquery-ui.theme.css">

<link rel="stylesheet" type="text/css" href="./css/common.css">

<!-- Bootsrap form validation : https://github.com/nghuuphuoc/bootstrapvalidator -->
<link rel="stylesheet" type="text/css"
	href="./bootstrapvalidator-master/dist/css/bootstrapValidator.css">
<link href="./css/bootstrap-datetimepicker.min.css" rel="stylesheet"
	media="screen">
<!-- <link rel="stylesheet" href="./roboto/webfonts/roboto_bold_macroman/stylesheet.css" type="text/css" charset="utf-8" />
<link rel="stylesheet" href="./roboto/webfonts/roboto_regular_macroman/stylesheet.css" type="text/css" charset="utf-8" /> -->
<style>
#uDtls:hover {
	color: #E96363;
}

#servicespb {
	padding: 5px;
}

.datepicker {
	position: absolute;
}

.icon-background6 {
	color: #40c040;
}

#orderDtlsBtn {
	margin-top: -10px;
	margin-right: -15px;
}

.serviceBdg {
	font-weight: normal;
	font-size: 12px;
}

.fa-times {
	color: #0C0C0C;
}

.fa-times:hover {
	color: #c12e2a;
	cursor: pointer;
}

#info1:hover {
	color: #c12e2a;
	cursor: pointer;
}
</style>
</head>
<%
 	int maxServicePerUser = Integer.parseInt(InitConfiguration.commonPropertiesMap.get("user.service.booking.limit").toString());
%>
<script type="text/javascript" src="./js/jquery-2.1.4.js"></script>
<script type="text/javascript"
	src="./bootstrap-3.2.0/dist/js/bootstrap.min.js"></script>
<script type="text/javascript" src="./js/jquery-ui.min.js"></script>

<!-- Bootsrap form validation : https://github.com/nghuuphuoc/bootstrapvalidator -->
<script type="text/javascript"
	src="./bootstrapvalidator-master/dist/js/bootstrapValidator.js"></script>

<script type="text/javascript"
	src="./js/bootstrap-datetimepicker.min.js" charset="UTF-8"></script>

<script type="text/javascript" src="./js/bootstrap-filestyle.js"></script>

<script type="text/javascript" src="./js/common.js"></script>
<script type="text/javascript" src="./js/form-validation.js"></script>

<script type="text/javascript">
var totalservices = 0;
$( document ).ready(function() {
	populateContactDetails();
	
	createServiceDetailDiv($('#serviceCmbBx'));
	getPendingServices();
	
	$('[data-toggle="tooltip"]').tooltip();
	$('.tooltip').addClass('successMsg');
	
	totalservices = ($("#pendingServiceCount") ? parseInt($("#pendingServiceCount").text()) : 0);
});
function getPendingServices(){
	var url = "action";
	var params = {
		"nav_action" : "getPendingServiceDetails"
	};
	$.ajax({
		url : url,
		type : "get",
		data : params,
		success : function(data, textStatus, jqXHR) {
			$('#pendingServices').html(data);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('ERROR : getPendingServices > checkOut.jsp');
		}
	});
}

var serviceContainer='';
function createServiceDetailDiv(obj){
	var selOptionObj = $(obj).find("option:selected");
	var seleOptionObjId = selOptionObj.val();
	var seleOptionObjText = selOptionObj.text();
	renderSubServiceDIV(seleOptionObjId,'');
}

function checkedAlreadySelectedServices(){
	if($("#selectedServices").children().length>0){
		$('#selectedServices').find('span').each(function() { 
		    var ids = this.id;
		  	if($("#subWorkCheckbox").children().length>0){
      			if(! ($('#'+ids.split('bdg_')[1]).is(":checked"))){
      				$('#'+ids.split('bdg_')[1]).attr('checked', true);
      			}
      		}
		});
	}
}

/* function addService(obj){
	$("#ErrMsg").text('You have already 3 pending');
	$("#ErrMsg").show();
} */

function deleteService(obj){
	$(obj).parent().remove();
	if($("#selectedServices").children().length==0){
		$("#selectedServiceDetails").empty();
	}
	uncheckService($(obj).parent().attr('id'));
}

function uncheckService(ids){
	$('.alert-success').hide();
	if($("#subWorkCheckbox").children().length>0){
		if($('#'+ids.split('bdg_')[1]).is(":checked")){
			$('#'+ids.split('bdg_')[1]).removeAttr('checked');
			
			var charges = ($('#'+ids.split('bdg_')[1]).siblings('.badge').text().split('Rs.')[1]).split('/')[0];
			//var totalCharges = $("#totalCtr").text().split('Rs.')[1];
			var totalCharges = $("#totalCtr").text();
			totalCharges = parseInt(totalCharges)-parseInt(charges);
			//$("#totalCtr").text('Rs.'+totalCharges);
			$("#totalCtr").text(totalCharges);
		}
	}
}

/* checking whether the agreed checkbox in checkout page is checked or not */
function checkAgreement(){
	$('.alert-success').hide();
	var agreed = $("#agreementChkbx").is(":checked");
	if(!Boolean(agreed)){
		$("#ErrMsg").text('Please check the agreement checkbox.');
		return false;
	}else{
		$("#orderDtlTab li:eq(1) a").tab('show');
	}
}

function populateContactDetails(){
	var url = "action";
	var params = {"nav_action":"getContactDetails"};
	$.ajax({
	    url : url,
	    type: "get",
	    data: params,
	    dataType : "json",
		success : function(data, textStatus, jqXHR) {
			$("#custAddress").text(data.contactaddress);
			$("#custMobile").val(data.contactnumber);
			$("#custEmail").val(data.email);
		},
	    error: function (jqXHR, textStatus, errorThrown){
	    	alert('login >> error');
	    }
	});
}

var maxServicePerUser = '<%= maxServicePerUser%>';
</script>

<body>
	<input id='selectedWorkId' type="hidden" value="${param.selectedWorkId}" />
	<input id='userName' type="hidden" value="${sessionScope.username}" />

	<div class="navbar navbar-default navbar-fixed-top"
		style="height: 80px;" role="navigation">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target=".navbar-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#"><strong class='title'></strong></a>
			</div>
			<%@ include file="loginUserDetails.jsp"%>
			<!--/.nav-collapse -->
		</div>
	</div>
	<div class="container-fluid" style="padding-top: 85px;">
		<div class="panel-body col-md-3">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">Select a Service</h3>
				</div>
				<div class="panel-body" id="servicespb">
					<div class="row">
						<div class="col-xs-12 col-md-12 boldfont-12px">
							<select class="form-control btn-success"
								onchange='createServiceDetailDiv(this);' id='serviceCmbBx'>
								<%
									Map<String, WorkBean> map = new SubworkHelper().getWorkDetails();

									WorkBean bean = null;
									String workname = "", iconFile = "";
									int i = 0;
									String selected = "";
									for (String workId : map.keySet()) {
										bean = map.get(workId);

										workname = bean.getWorkname();
										iconFile = bean.getIconfile();
										selected = "";
										if (StringUtilities.equals(request.getParameter("selectedWorkId"), workId)) {
											selected = "selected='selected'";
										}
								%>
								<option class="btn btn-default" value='<%=workId%>'
									<%=selected%>><%=workname%></option>
								<%
									}
								%>
							</select>
						</div>

						<div class="col-xs-12 col-md-12" id='subWorkContainer'>
							<div class="checkbox" id="subWorkCheckbox"></div>
						</div>
					</div>
				</div>

			</div>
		</div>

		<div class="panel-body col-md-9">
			<!-- tabbar starts here -->
			<div class="panel panel-primary">
				<div class="panel-heading">
					<a id='orderDtlsBtn' class="btn btn-default btn-md pull-right"
						style='padding: 5px;' href="javascript:orderDetails();"
						title='Order Details'> <span class="fa-stack fa-4x"
						style="font-size: 1em; font-weight: bold;"> <i
							class="fa fa-circle-thin fa-stack-2x icon-background6"></i> <i
							class="fa fa-shopping-cart fa-stack-1x"></i>
					</span>
					</a> New Service Request
				</div>

				<div class="panel-body">
					<div class="tab-content">
						<!-- Service Details Tab Content -->
						<div class="tab-pane fade in active" id="serviceTabContent">
							<div class="col-md-6">
								<p>
								<div>
									<p>
										<span class="label label-danger">Selected Service
											Details</span>
									</p>
									<div id="selectedServiceDetails">
										<p id='selectedServices'></p>
									</div>

									<p>
										<span class="label label-danger">Priority&nbsp;</span> <a
											href="#" data-toggle="tooltip"
											title="Normal: Request will be addressed as sooner as possible.High: Can be addressed within 24 hours(extra 100% on bill amount).Critical: Request will be addressed within 12Hours(cost 200% extra)." 
											class='regularfont-12px'> <span
											class="fa-stack fa-xs successMsg" id='info1'> <i
												class="fa fa-circle-thin fa-stack-2x"></i> <i
												class="fa fa-info fa-stack-1x"></i>
										</span>
										</a> <span class='regularfont-12px'><input type="radio" name='priorityRadio' id='priority'
											value="1" checked='çhecked'>&nbsp;Normal. &nbsp;<input
											type="radio" name='priorityRadio' id='priority' value="2">&nbsp;High.
										&nbsp;<input type="radio" name='priorityRadio' id='priority'
											value="3">&nbsp;Critical.</span>
									</p>
									<p>
										<span class="label label-danger">Extra Service Info.</span>
									</p>
									<div class="input-group">
										<span class="input-group-addon" style="cursor: pointer;"><i class="fa fa-info-circle"></i></span>
										<textarea class='form-control' maxlength="200" autocomplete='off' rows='3' id="moreInfo" placeholder="Please detail about your service (if any)." style='font-size:12px;padding-left:10px;width:99%;'></textarea>
									</div>
									
									
														<div class='separator1'></div>
														
														<div class="input-append date form_datetime">
											<div class='input-group date form_date' data-date='' data-date-format='dd MM yyyy' data-link-field='serviceSchDate' data-link-format='yyyy-mm-dd'>
												<input class='form-control datepicker' type='text' value=''readonly placeholder='Service Schedule Date' /> 
												<span class='input-group-addon'>
													<span class='glyphicon glyphicon-calendar' style='color: #ff3535;'></span>
												</span>
											</div>
											<input type='hidden' id='serviceSchDate' value='' />
									</div>
									<script>
										$('.form_date').datetimepicker({
											weekStart : 1,
											todayBtn : 1,
											autoclose : 1,
											todayHighlight : 1,
											startView : 2,
											minView : 2,
											forceParse : 0,
											startDate : new Date(),
											endDate : '+1w'
										});
									</script>
									<div class='separator1'></div>
					<div class="input-group">
						<span class="input-group-addon" style="cursor: pointer;color:#c12e2a"><i class="fa fa-ticket"></i></span>
						<input type="text" autocomplete='off' class="form-control" id="couponCode" placeholder="Enter the COUPON CODE" style='font-size:12px;padding-left:10px;height:28px;width:99.9%;color:#c12e2a;'></input>
						<span class="input-group-addon" style="cursor: pointer;" onClick="validateRegisterUserPromoCode();"><i class="fa fa-refresh" id='refreshCoupon'></i></span>
					</div>
								</div>
							</div>

							<div class="col-md-6">
								<p>
									<span class="label label-danger">&nbsp;Contact Details</span> <span class='errormsg'>(If address is not same)</span>
								</p>

								<div class="input-group">
									<span class="input-group-addon" style="cursor: pointer;"><i
										class="fa fa-home fa-home-sm"></i></span>
									<textarea class='form-control regularfont-12px' rows='3' id="custAddress"
										readonly style='padding-left:10px;'></textarea>
									<span class="input-group-addon" style="cursor: pointer;">
										<i class="fa fa-pencil-square-o"
										onclick="javascript:enable2Edit(this); "></i>
									</span>
								</div>
								<div style='height: 15px;'></div>
								<form>
								<div class="row">
									<div class="col-xs-8" style='padding-right:0px;'>
										<div class="input-group">
											<span class="input-group-addon" style="cursor: pointer;"><i class="fa fa-envelope fa-envelope-sm"></i></span>
               								<input type="text" class="form-control regularfont-12px" id="custEmail" readonly value='' style='padding-right:0px;width:99%;'></input>
										</div>
									</div>
									<div class="col-xs-4" style='padding-left:0px;'>
										<div class="input-group">
											<span class="input-group-addon" style="cursor: pointer;"><i class="fa fa-phone fa-phone-sm"></i></span> 
               								<input type="text" class="form-control phonenumberfont" id="custMobile" readonly value='' style='padding-right:0px;width:99%;'></input>
										</div>
									</div>
								</div>
								</form>
								
								<div style='height: 15px;'></div>
									<p>
										<span class="label label-danger">Service Charge Details</span>
									</p>
									<div>
										<span> 
											<span>Total Amount&nbsp; 
												<span class='boldfont-20px'>Rs.<span id='totalCtr'>0</span>
											</span>
											<span style='color: #c12e2a; font-family: robotobold; font-size: 14px;display:none;'>&nbsp;&nbsp;&nbsp;(Less Discount Rs.<span id='discountAmount'>0</span>)</span>
										</span>
										</span>
										<ul class='regularfont-12px'>
											<li>This is the cummulative total of 1hour per service.</li>
											<li>This not includes any consumable/spares used for the service.</li>
											<li>Service charge may vary based on the man hour spent and spares used.</li>
										</ul>
									</div>
									
								<div style='padding-top:1px;' id='pendingServices'>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div style='padding:15px;'>
					<p>
						<input type="checkbox" id='agreementChkbx'
							class='checkbox-primary'>&nbsp;<a
							href="javascript:showModal('agreementModal');"
							class='regularfont-14px' style='color:#0a416f;'>agree to the terms and conditions.</a>
					</p>
				</div>

				<div class="panel-footer" style='height:50px;'>
					<span id='ErrMsg' class='errormsg'></span>
					<div class="pull-right">
						<button type="button" class="btn btn-primary btn-xs pull-right" onClick="createOrder();">Proceed to Checkout</button>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- Modals starts from here -->
	<!-- Terms&Conditions modal -->
	<div id="agreementModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id='servicesModalTitle'>Terms and
						Conditions</h4>
				</div>
				<div class="modal-body" id="agreementModalBody">
					<%@ include file="terms.jsp"%>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-danger btn-xs"
						onClick="if(!$('#agreementChkbx').is(':checked')){$('#agreementChkbx').attr('checked', true);}closeAllModal();">
						<span class="glyphicons glyphicons-check"></span>&nbsp;&nbsp;Agreed
					</button>
				</div>
			</div>
		</div>
	</div>
	<!-- Terms&Conditions modal end -->
	<!-- User Setting modal -->
	<div id="userSettingModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id='userSettingModalTitle'>User
						Profile Setting</h4>
				</div>
				<div class="modal-body" id="servicesModalBody">
					<div class="input-group">
						<span class="input-group-addon"><i class="fa fa-user"></i></span>
						<input type="text" class="form-control" id="regUname" readonly
							value=''></input>
					</div>
					<span class="help-block"></span>
					<div class="input-group">
						<span class="input-group-addon" style="cursor: pointer;"><i
							class="fa fa-key"></i></span> <input type="password"
							class="form-control" name="pwd1" id="pwd1"
							placeholder="enter new password" readonly></input> <span
							class="input-group-addon" style="cursor: pointer;"><i
							class="fa fa-key" id='22'></i></span> <input type="password"
							class="form-control" name="pwd2" id="pwd2"
							placeholder="re-enter new assword" readonly> <span
							class="input-group-addon" style="cursor: pointer;"> <i
							class="fa fa-pencil-square-o"
							onclick="javascript:enablePassword(); arr.push('pwd1');arr.push('pwd2');"></i>
						</span> </input>
					</div>
					<span class="help-block"></span>
					<div class="input-group">
						<span class="input-group-addon" style="cursor: pointer;"><i
							class="fa fa-envelope"></i></span> <input type="text"
							class="form-control" id="email" readonly value=''> <span
							class="input-group-addon" style="cursor: pointer;"> <i
							class="fa fa-pencil-square-o"
							onclick="javascript:enable2Edit(this); arr.push('email');"></i>
						</span> </input>
					</div>
					<span class="help-block"></span>
					<div class="input-group">
						<span class="input-group-addon" style="cursor: pointer;"><i
							class="fa fa-mobile"></i></span> <input type="text" class="form-control"
							id="mobile" readonly value=''> <span
							class="input-group-addon" style="cursor: pointer;"> <i
							class="fa fa-pencil-square-o"
							onclick="javascript:enable2Edit(this); arr.push('mobile');"></i>
						</span> </input>
					</div>
					<span class="help-block"></span>
					<div class="input-group">
						<span class="input-group-addon" style="cursor: pointer;"><i
							class="fa fa-home"></i></span>
						<textarea class='form-control' rows='3' id="address" readonly>
	                  </textarea>
						<span class="input-group-addon" style="cursor: pointer;"> <i
							class="fa fa-pencil-square-o"
							onclick="javascript:enable2Edit(this); arr.push('address');"></i>
						</span>
					</div>
					<span class="help-block"></span>
					<div class="input-group">
						<span class="input-group-addon"><i
							class="fa fa-paper-plane"></i></span> <input type="pincode"
							class="form-control" id="pincode" readonly value=''> <span
							class="input-group-addon" style="cursor: pointer;"> <i
							class="fa fa-pencil-square-o"
							onclick="javascript:enable2Edit(this); arr.push('pincode');"></i>
						</span> </input>
					</div>
					<span class="help-block"></span>
				</div>
				<div class="modal-footer">
					<span id='msgCntr'></span>
					<button type="button" class="btn btn-danger btn-xs"
						onClick="updateUserSettings();">
						<i class="fa fa-sign-in fa-lg"></i>&nbsp;&nbsp;Update
					</button>
				</div>
			</div>
		</div>
	</div>
	<!-- Modal ends here -->
	
	<section class="slider-fluid" id="copyright">
		<%@ include file="footer.jsp" %>
	</section>
	
	
</body>
</html>