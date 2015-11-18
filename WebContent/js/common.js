document.write("<script type='text/javascript' src='js/successModal.js'></script>");

/* Every time a modal is shown, if it has an autofocus element, focus on it. */
$( document ).ready(function() {
	$('.modal').on('shown.bs.modal', function() {
		$(this).find('[autofocus]').focus();
	});
});

var maxServicePerUser = '';

function registerUser(){
	var url = "action";
	var email = $('#regEmail').val();
	var contactNum = $('#regMobile').val();
	var password1 = $('#regPwd1').val();
	var password2 = $('#regPwd2').val();
	var address = $('#regAddress').val();
	var newUserName = $('#newUserName').val();
	//var moreServiceInfo = $('#moreServiceInfo').val();
	
	var msg = '';
	if(isEmpty(newUserName)){
		msg = "Please enter the user name";
	}else if(isEmpty(email)){
		msg = "Please enter your email id";
	}else if(!validateEmail(email)){
		msg = "Please enter a valid email";
	}else if(isEmpty(contactNum)){
		msg = "Please enter your mobile number";
	}else if(!validPhoneNumber(contactNum)){
		msg = "Please enter a valid phone number";
	}else if(isEmpty(address)){
		msg = "Please enter your correspondence address";
	}else if(password1!=password2){
		msg = 'Password were not matching';
	}
	
	if(msg!=''){
		failureMessage('registerUserErrorMsg', msg);
		return false;
	}else{
		var errorMsg = '';
		var params = {"nav_action":"registerUser", "email":email, "newUserName":newUserName, "contactNum":contactNum, "address":address, "password1":password1, "password2": password2};
		$.ajax({
				url : url, type: "POST", data: params, dataType: "text",
				success: function(data, textStatus, jqXHR){
					
					/**
		   	    	 * -1	:	if user is not unique.
					 * -2	:	if user creation failed. 
					 * -3	: 	email sent failed.
					 * -4	: 	given email id already registered.  
					 * 	0	:	user creation failed due to some reason.
					 * 	1	:	successfully user created.
		   	    	 */
		   	    	 
					if(parseInt(data)==1){
		   	    		successModal("User created successfully. Check your registered email to activate.");
		   	    		$('#successAlertModal').on('hidden.bs.modal', function () {
		   	    			$(window).attr("location","index.jsp");
		   	    		});
		   	    	}
					else{
						if(data==0){
			   	    		errorMsg = ('User account creation is failed, try after sometime');
			   	    	}else if(data==-1){
							errorMsg = ('User Id already available.');
			   	    	}else if(data==-3){
			   	    		errorMsg = ('Email sending failed, try after sometime');
			   	    	}else if(data==-4){
			   	    		errorMsg = ('Given Email Id is already registered with us.');
			   	    	}
						failureMessage('userCredErrorMsg',  errorMsg);
						return false;
					}
				},
				error: function(){
					failureMessage('userCredErrorMsg',  'Failed to register the user, try after sometime.');
				}
		});
	}
}

function resetUserDtls(){
	$("#resetMsg").text('');
	var email = $('#regEmail2Reset').val();
	if(isEmpty(email)){
		failureMessage('resetMsg',  "Please filup all mandatory field.");
		return false;
	}
	
	if(!validateEmail(email)){
		failureMessage('resetMsg',  "Please enter a valid email");
		return false;
	}
	
	var params = {"nav_action":"resetUserDetails", "email":email};
	var url = "action";
	$.ajax({
		url : url, type: "POST", data: params, dataType: "text",
		success: function(data, textStatus, jqXHR){
			/**
			 * 1	:	if user reset successfully.
			 * 0	:	user is not available 
			 */
			if(parseInt(data)==1){
				successMessage('resetMsg',  'Your login details sent to your registered email id.');
			}else if(data==0){
				failureMessage('resetMsg',  'User email is not available, register to login.');
			}
		},
		error: function(){
			failureMessage('resetMsg',  'Failed to reset the user, try after sometime.');
		}
	});
}	

/* Onclick of the work/service opens up the service detail modal...starts*/
function openServiceDialog(workgrpid, workgrpname, selectedService){
	var title = workgrpname+" Services";
	$("#servicesModalTitle").html(title);
	var url = "action";
	var params = {"nav_action":"getSubWorks", "grpId":workgrpid, "selectedService":selectedService, "workgrpname": workgrpname};
	$.ajax({
			async: false,
			url : url,
			type: "GET",
			data: params,
			dataType: "html",
			success: function(data, textStatus, jqXHR){
				if(data=='page1'){
					window.location.replace(data);
				}else{
					$("#servicesModalBody").html(data);
					showServiceModal('guestServicesModal', workgrpid, selectedService);
				}
			}
	});
}//...ends here


function openUserLogin(){
	var title = "Register/Signin to avail Services";
	$("#servicesModalTitle").html(title);
	var url = "action";
	var params = {"nav_action":"getSubWorks"};
	$.ajax({
			async: false,
			url : url,
			type: "GET",
			data: params,
			dataType: "html",
			success: function(data, textStatus, jqXHR){
				if(data=='page1'){
					window.location.replace(data);
				}else{
					$("#servicesModalBody").html(data);
					showModal('servicesModal');					
				}
			}
	});
}

/* get all the selected subworks and other details set into session.
 * Required to display after user login OR register.*/
function serveWithRegister(){
	$( "input[id^='msgCntr']" ).val("");// id starts with msgCntr
	var selectedSubWorks = $("#sel1").val();
	var email = $('#regEmail').val();
	var contactNum = $('#regMobile').val();
	var password1 = $('#regPwd1').val();
	var password2 = $('#regPwd2').val();
	var address = $('#regAddress').val();
	
	if(isEmpty(email) || isEmpty(contactNum) || isEmpty(address)){
		failureMessage('userCredErrorMsg',  "Please filup all mandatory field.");
		return false;
	}
	
	if(!validateEmail(email)){
		failureMessage('userCredErrorMsg',  "Please enter a valid email");
		return false;
	}
	
	if(!validPhoneNumber(contactNum)){
		failureMessage('userCredErrorMsg',  "Please enter a valid phone number");
		return false;
	}
	if(password1!=password2){
		$('#userCredErrorMsg').text('Password were not matching.');
		return false;
	}
	var url = "action";
	var params = {"nav_action":"serveWithRegister", "selectedSubWorks":selectedSubWorks, "email":email, "contactNum":contactNum, "address":address, "password1":password1, "password2": password2};
	$.ajax({
			url : url,
			type: "get",
			data: params,
			success: function(data, textStatus, jqXHR){
				if(data=='-1'){
					failureMessage('userCredErrorMsg',  "Password were not matching.");
					return false;
				}
			}
	});
}

function serveWithSignin(){
	updateErrorMsg('servicesModal','');
	var url = "action";
	var params = {"nav_action":"serveWithSignin", "selectedSubWorks":$("#selectedSubWorks").val(), "username": $("#username").val(), "password": $("#password").val()};
	
	$.ajax({
			url : url,
			type: "get",
			data: params,
			success: function(data, textStatus, jqXHR){
		    	if(!isNaN(data)){
		    		if(parseInt(data)==0){
		    			updateErrorMsg('servicesModal', 'User login credentials not matching.');
			    	}else if(parseInt(data)==-1){
			    		updateErrorMsg('servicesModal', 'Please activate the user account. Your account activation email is just delivered.');
			    	}
		    	}else{
		    		window.location.replace(data);
		    	}
			}
	});
}

function hideSingleModal(id){
	clearAllModalData();
	$("#"+id).modal('hide');
}

/*--ends here */
/* Hides all opened modal and shows up the modal with the given id... starts*/
function showModal(id){
	clearAllModalData();
	$('.modal').modal('hide');
	$(document.body).on('hidden.bs.modal', function () {
		$("#"+id).removeData('bs.modal')
	});
	$("#"+id).modal('show');
}//...ends here

/* Hides all opened modal and shows up the particular subwork modal with the given id... starts*/
function showServiceModal(id, selectedWorkGroupId, selectedSubGroupIdInSearch){
	clearAllModalData();
	$('.modal').modal('hide');
	$("#"+id).modal('show');
	populateSubWorks(selectedWorkGroupId, selectedSubGroupIdInSearch);
}//...ends here

function closeAllModal(){
	$('.modal').modal('hide');
}

function openCheckout(){
	window.location.replace("checkout.jsp");
}
function orderDetails(){
	$(window).attr("location","welcome.jsp");
}

function validateCreate(){
	var errorMessage = "";
	$('.alert-success').show();
	$("#ErrMsg").text('');
	if($("#selectedServices").children().length===0){
		errorMessage = 'Please select a service to create.';
	}
	else if($("#serviceSchDate").val()===''){
		errorMessage = 'Please select a schedule date for your service.';
	}
	else if(!Boolean($("#agreementChkbx").is(":checked"))){
		errorMessage = 'Please check the agreement checkbox.';
	}
	
	if(errorMessage.trim().length>0){
		$("#ErrMsg").text(errorMessage);
		return false;
	}else{
		$('.alert-success').hide();
		return true;
	}
}
function createOrder(){
	if(validateCreate()){
		var selectedServiceIds = "";
		var priorityValue = '';
		var scheduleDate = $("#serviceSchDate").val();
		var customerId = $('#userName').val();
		var cusrAddress = $('#custAddress').val();
		var extraInfo = $('#moreInfo').val();
		var couponCode = $.trim($('#couponCode').val());
		
		if(!isEmpty(couponCode)){
			if("1" != validateRegisterUserPromoCode()){
				//put some validation messge and show
				return false;
			}
		}
		
		$('#selectedServices').children().each(function () {
			if($(this).parent().children().index(this)===0){
				selectedServiceIds+=(this.id).substr((this.id).lastIndexOf("_")+1);
			}else{
				selectedServiceIds+=","+(this.id).substr((this.id).lastIndexOf("_")+1);
			}
		});
		priorityValue = $('input:radio[name=priorityRadio]:checked').val();
		
		var url = "action";
		var params = {"nav_action":"createOrder", "username":customerId, "cusrAddress":cusrAddress, "subWorkId":selectedServiceIds, "priority":priorityValue, "serviceReqDate":scheduleDate, "extraInfo":extraInfo, "couponCode": couponCode};
		
		$.ajax({
		    url : url,
		    type: "post",
		    data: params,
		    dataType : "text",
			success : function(data, textStatus, jqXHR) {
				$(window).attr("location","welcome.jsp");
			},
		    error: function (jqXHR, textStatus, errorThrown){
		    	alert('checkout.jsp > createServices >> error');
		    }
		});
	}
}

function resetModalData(id){
	var myBackup = $('#'+id).clone();
	$('#'+id).modal('hide').remove();
    var myClone = myBackup.clone();
    $('body').append(myClone);
}

// Auto complete for work search box...starts
function searchAutoComplete(obj, loggedInUser){
	//alert('searchAutoComplete');
	var url = "action";
	var params = {"nav_action":"autoCompleteSubworks"};
	$.ajax({
        url: url,
        type: "get",
        data: params,
        dataType: "json",
        success: function(data, textStatus, jqXHR){
        	$(obj).autocomplete({
                minLength: 1,
				delay: 500,
                source: data,
                focus: function(event, ui) {
                	$(obj).val(ui.item.label);
                    return false;
                },
                select: function(event, ui) {
                	$(obj).val(ui.item.label);
                	openWork(ui.item.value, loggedInUser);
                    return false;
                }
            });
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            alert(textStatus);
        }
    });
}//..ends here

/* on select a service in the search box opens up the modal window...starts*/
function openWork(subworkId, loggedInUser){
	var url = "action";
	var params = {"nav_action":"getWorkGroupDetails", "subworkId": subworkId};
	
	$.ajax({
		url: url,
        type: "get",
        data: params,
        dataType: "json",
        success: function(data, textStatus, jqXHR){
        	//var jsondata = $.parseJSON(data);
        	var workid = data[0].value;
        	var workname = data[0].label;
        	if(isEmpty(loggedInUser)){
        		openServiceDialog(workid, workname, subworkId);
        	}else{
        		renderSubServiceDIV(workid, subworkId);
        		if(!isEmpty(subworkId) && document.getElementById("servChk_"+subworkId)){
        			$('#serviceCmbBx').val(workid);
        			document.getElementById("servChk_"+subworkId).checked=true;
					addService(document.getElementById("servChk_"+subworkId));
				}
        	}
        }
	});
}//...ends here

function addService(obj){
	$('.alert-success').hide();
	var pendingServiceCount = $('ul#pendingServiceList li').length;
	var selectedVal = $('#'+obj.id).parent().text().split('Rs.')[0];
	var charges = ($('#'+obj.id).parent().text().split('Rs.')[1]).split('/')[0];
	//var totalCharges = $("#totalCtr").text().split('Rs.')[1];
	var totalCharges = $("#totalCtr").text();
	var selectedServiceCount = ($("#selectedServices").children() ? $("#selectedServices").children().length : "0");
	var totalService = parseInt(selectedServiceCount)+ parseInt(pendingServiceCount);
	
	var flag = true;
	if($('#'+obj.id).is(":checked")){
		$('ul#pendingServiceList li').each(function() { 
		    var id = this.id;
		    if(obj.id.split('_')[1]==id){
		    	$('#'+obj.id).attr("checked",false);
		    	$("#ErrMsg").text('This is a pending service yet to serve.');flag = false;
		    }
		});
		
		$('#selectedServices span[id^="bdg_"]').each(function(index) {
			var id = this.id.split("_")[2];
			if(obj.id.split('_')[1]==id){
		    	$("#ErrMsg").text('This service is already selected.');flag = false;
		    }
		});
	}
	
	if(flag){
		$("#ErrMsg").text('');
		if($('#'+obj.id).is(":checked") && totalService==3){
			$('.alert-success').show();
			$("#ErrMsg").text('Per customer only '+maxServicePerUser+' services are allowed.');
			$('#'+obj.id).attr("checked",false);
			return false;
		}
		else{
			if(obj.checked){
				if(totalService===parseInt(maxServicePerUser)){
					$("#ErrMsg").text('You have already '+pendingServiceCount+' pending services. Per customer only '+maxServicePerUser+' service allowed.');
					$('#'+obj.id).attr("checked",false);
					return false;
				}
				
	 			if($("#selectedServices").children().length==0){
	 				$('<p>', {
	     			    'id' : 'selectedServices'
	     			}).appendTo($("#selectedServiceDetails"));
	 			}
	 			var selVal= selectedVal.trim();
	 			createAddServiceElement(obj.id, selVal);
	 			totalCharges = parseInt(totalCharges)+parseInt(charges);
	 			totalservices++;
	 		}else{
	 			$("#bdg_"+obj.id).remove();
	 			if($("#selectedServices").children().length==0){
	 				$("#selectedServiceDetails").empty();
	 			}
	 			totalCharges = parseInt(totalCharges)-parseInt(charges);
	 			totalservices--;
	 		}
	 		//$("#totalCtr").text('Rs.'+totalCharges);
			$("#totalCtr").text(totalCharges);
		}
	}
}


function createAddServiceElement(sid, value){
	var ele = document.createElement("span");
	ele.className="badge badge-primary";
	ele.id="bdg_"+sid;
	
	$('<i>', {
	    'class' : 'fa fa-times',
	    'title' : 'delete service',
	    'onclick' : 'deleteService(this);'
	}).appendTo(ele).append("&nbsp;");
	
	
	$('<span>', {
	    'class' : 'serviceBdg regularfont-12px'
	}).text(value).appendTo(ele);
	
$("#selectedServices").append(ele).append("&nbsp;");
}

function renderSubServiceDIV(selectedServiceId, subworkId){
	var i=0;
	var url = "action";
	var params = {
		"nav_action" : "renderSubWorkDetails", "selectedServiceId":selectedServiceId
	};
	$.ajax({
		async : false,
		url : url,
		type : "get",
		data : params,
		dataType : "json",
		success : function(data, textStatus, jqXHR) {
			 $.each(data, function(idx, value) {
				var subWorkId = value.id;
 				var subWorkName = value.desc;
 				var serviceCost = value.price;
 				
				 if(i==0){
					 i++;
					 $("#subWorkCheckbox").empty();
					$("#subWorkContainer").empty()
					 
					$('<div>', {
      			    'class' : 'checkbox',
      			    'id' : 'subWorkCheckbox'
      			}).appendTo($("#subWorkContainer"));
				 }
 				
 				var ele1 = document.createElement("label");
 				$('<input>', {
 					'type' : 'checkbox',
 					'id' : 'servChk_'+subWorkId,
 					'onchange' : 'addService(this);',
 					'style':'float:left;position:relative;'
 				}).appendTo(ele1);
 				
 				$('<span>', {
 					'class':'ellipsis190 boldfont-12px','style':'float:left;','text':subWorkName
 				}).appendTo(ele1);
 				
 				
 				$('<span>', {
     			    'class' : 'badge btn-success boldfont-12px', 'style':'float:right;margin-left:10px;',
     			}).text(serviceCost).appendTo(ele1);
 				
 				$("#subWorkCheckbox").append(ele1);
 				$("#subWorkCheckbox").append('<div style="height:10px;"></div>');
 				
		}); 
			checkedAlreadySelectedServices();
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('error');
		}
	});
}

function enableOtherServiceDetails(obj){
	if(obj.checked){
		$("#otherDtls").removeAttr('disabled');
	}else{
		$("#otherDtls").attr("disabled","true");
	}
}

/* user login */
function validateSigninDetails(){
	$("#loginerrormsg").text('');
	var username = $("#username").val();
	var password = $("#password").val();
	
	var errorMsg = "";
	if(username.trim().length==0){
		errorMsg = "Username must not be blank";
	}
	else if(!validateEmail(username.trim())){
		errorMsg = "Please provide a valid email id for username.";
	}
	else if(password.trim().length==0){
		errorMsg = "Password must not be blank";
	}
	if(errorMsg){
		failureMessage('loginerrormsg',  errorMsg);
		flag = false;
	}else{
		flag = true;
	}
	return flag;
}

function validateEmail(email){
	var re = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
	if(!re.test(email)){
	    return false;
	}else{
		return true;		
	}
}
function login(){
	updateErrorMsg('signinModal','');
	var errorMsg = '';
	var flag = validateSigninDetails();
	if(Boolean(flag)){
		var url = "action";
		var params = {"nav_action":"login", "username": $("#username").val(), "password": $("#password").val()};
		$.ajax({
		    url : url,
		    type: "get",
		    data: params,
		    dataType: "text",
		    success: function(data, textStatus, jqXHR){
		    	//alert(data);
		    	if(!isNaN(data)){
		    		if(parseInt(data)==0 || parseInt(data)==-3){
		    			errorMsg = 'Your login credentials are not matching.';
			    	}else if(parseInt(data)==-1){
			    		errorMsg = 'Your account activation mail sent to you, Please activate your account.';
			    	}else if(parseInt(data)==-2){
			    		errorMsg = 'Sorry you are not yet registered. Please register to login.';
			    	}
		    		failureMessage('loginerrormsg',  errorMsg);
		    	}else{
		    		//window.location.replace(data);
		    		$(location).attr('href', data);
		    	}
		    },
		    error: function (jqXHR, textStatus, errorThrown){
		    	alert('login >> error');
		    }
		});
	}
}//...ends here

function updateErrorMsg(parentDIVId, msg){
	$('#'+parentDIVId).find('span').each(function() { 
	    var id = this.id;
	    if(id=='msgCntr'){
	    	$(this).text(msg);
	    }
	});
}
/* Invalidating the session when logout */
function invalidateSession(url){
	alert('Invalidate');
	var request =  new XMLHttpRequest();
    request.open("GET", "index", false);
    request.send(null);
	if(url==null || url=="" || url==undefined){
		logOut('index.jsp');
	}else{
		logOut(url);
	}
}//.. ends here

function logOut(redirectUrl){
	var url = "action";
	var params = {"nav_action":"logout", "redirectURL": redirectUrl};
	
	$.ajax({
	    url : url,
	    type: "get",
	    data: params,
	    dataType: "text",
	    success: function(data, textStatus, jqXHR){
	    	if(parseInt(data)==0){
	    		alert('Failed to logout');
	    	}else{
	    		$(location).attr('href', redirectUrl);
	    	}
	    },
	    error: function (jqXHR, textStatus, errorThrown){
	    	alert('error');
	    }
	});
}

function openTermsWindow(){
	window.open('terms.jsp',"popupWindow", "width=600,height=600,scrollbars=yes");
}

function openPrivacyWindow(){
	window.open('privacyPolicy.jsp',"popupWindow", "width=600,height=600,scrollbars=yes");
}

function enablePassword(){
	$('#pwd1').removeAttr("readonly");
	$('#pwd2').removeAttr("readonly");
}
function enable2Edit(obj){
	var elId = $(obj).parent().prev().get(0).id;
	$('#'+elId).removeAttr("readonly");
}

function removeSessionAttributes(attributes){
	var url = "action";
	var params = {"nav_action":"removeSessionAttributes", "attributes": attributes};
	
	$.ajax({
	    url : url,
	    type: "get",
	    data: params,
	});
}

/* on page load populating the logged in user details*/
function loadUSerDetails(){
	var url = "action";
	var params = {
		"nav_action" : "getUserDetails"
	};
	$.ajax({
		url : url,
		type : "get",
		data : params,
		dataType : "json",
		success : function(data, textStatus, jqXHR) {
			$("#regUname").val(data.username);
			$("#email").val(data.email);
			$("#mobile").val(data.mobile);
			$("#address").val(data.address);
			$("#pincode").val(data.pincode);
//			$("#pwd1").val(data.pincode);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('error');
		}
	});
}

function resetModal(){
	$('.modal').on('hidden.bs.modal', function(){
	    $(this).find('form')[0].reset();
	    //$(this).find('#msgCntr')[0].text('');
	    $("#msgCntr").text('');
	    
	    /*$('#'+parentDIVId).find('div').each(function() { 
		    var id = this.id;
		    if(id.indexOf('service')==0){
		    	alert($(this).id);
		    }
		});*/
	});
}

$( document ).ready(function() {
	$(".title").text("replad");
	$(".title").attr("class", "redClass");
});

function colorToHex(color) {
    if (color.substr(0, 1) === '#') {
        return color;
    }
    var digits = /(.*?)rgb\((\d+), (\d+), (\d+)\)/.exec(color);

    var red = parseInt(digits[2]);
    var green = parseInt(digits[3]);
    var blue = parseInt(digits[4]);

    var rgb = blue | (green << 8) | (red << 16);
    return digits[1] + '#' + rgb.toString(16);
};

/* tabindex starts from 0*/
function showTab(tabIndex){
	$("#orderDtlTab li:eq("+tabIndex+") a").tab('show');
}

function userSelection(selObj){
	var val = selObj.value;
	// signin radio selected if val is 1 else register selected
	if(parseInt(val)==1){
		$('#signinRow').show();
		$('#registerRow').hide();
		
		$('#serviceModalSigninBtn').show();
		$('#serviceModalRegisterBtn').hide();
	}else{
		$('#signinRow').hide();
		$('#registerRow').show();
		
		$('#serviceModalRegisterBtn').show();
		$('#serviceModalSigninBtn').hide();
	}
}

function populateCombo(dataFor){
	var url = "action";
	var params = {"nav_action":"populateComboData", "dataFor":dataFor};
	$.ajax({
			async : false,url : url, type: "GET", data: params, dataType: "json",
			success: function(data, textStatus, jqXHR){
				$.each(data, function(idx, value) {
					$("#"+dataFor).append($('<option>', {
					    value: idx, text: value
					}));
				});
			},
			error: function(){
				alert('Failed to register the user, try after sometime.')
			}
	});
}

function successMessage(eleId, message){
	$("#"+eleId).attr("class", "pull-left successMsg");
	$("#"+eleId).text(message);
}

function failureMessage(eleId, message){
	$("#"+eleId).attr("class", "pull-left errormsg");
	$("#"+eleId).text('');
	$("#"+eleId).text(message);
}

//This method used to check empty String
function isEmpty(str) {
    return (!str || 0 === str.trim().length);
}

//Email Vlaidation Code
function validateEmail(inputText){  
var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;  
if(inputText.match(mailformat)){  
	return true;  
}else{    
	return false;  
  }  
}  

//This method allows only integer to type	
function validPhoneNumber(inputtxt){  
  var phoneno = /^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/;  
  if((inputtxt.match(phoneno))){  
      return true;  
    }else{  
        return false;  
    }  
}  

function createGuestService(){
	var msg = '';
	var work_group_id = $("#work_group_id").val();
	var sub_work_id = $("#subworkCmbBx").children(":selected").attr("id");
	var guestUserName = $.trim($('#guestUserName').val());
	var guestregEmail = $.trim($('#guestregEmail').val());
	var guestregMobile = $.trim($('#guestregMobile').val());
	var guestregAddress = $.trim($('#guestregAddress').val());
	var guestMoreInfo = $.trim($('#guestMoreInfo').val());
	var guestPromoCode = $.trim($('#guestPromoCode').val());
	var scheduleServiceDateId = $.trim($('#scheduleServiceDateId').val());
	var serviceAggreementhidden = $('#serviceAggreementhidden').val();
	
	var recaptchaResponse = $('#g-recaptcha-response').val();
	var recaptchaChallenge = $('#recaptcha_challenge_field').val();
	
	if(sub_work_id=='0'){
		msg = "Please select a sub-work.";
	}else if(isEmpty(guestUserName)){
		msg = "Please enter the user name";
	}else if(isEmpty(guestregEmail)){
		msg = "Please enter your email id";
	}else if(!validateEmail(guestregEmail)){
		msg = "Please enter a valid email";
	}else if(isEmpty(guestregMobile) ){
		msg = "Please enter your mobile number";
	}else if(!validPhoneNumber(guestregMobile)){
		msg = "Please enter a valid phone number";
	}else if(isEmpty(guestregAddress)){
		msg = "Please enter your correspondence address";
	}else if(!isEmpty(guestPromoCode) && '0'==validatePromoCode()){
		msg = "Copoun Code is not Valid";			
	}else if(isEmpty(recaptchaResponse)){
		msg = 'Captcha validation failed.Please Try again.';
	}else if(isEmpty(scheduleServiceDateId)){
		msg = 'Captcha validation failed.Please Try again.';
	}else if(serviceAggreementhidden === "off"){
		alert(serviceAggreementhidden);
		msg = 'Please agree to our terms and conditions.';
	}
	
	if(msg!=''){
		grecaptcha.reset();
		failureMessage('guestServiceErrorMsg', msg);
		return false;
	}else{
		$(".fa-spinner").css("display","block");
		$('#guestServiceErrorMsg').text('');
		var url = "action";
		var params = { "nav_action" : "guestServiceCreate", "work_group_id":work_group_id, "sub_work_id":sub_work_id, "guestUserName":guestUserName, "guestregEmail" : guestregEmail, "guestregMobile" : guestregMobile, "guestregAddress" : guestregAddress, "guestMoreInfo":guestMoreInfo, "guestPromoCode": guestPromoCode, "recaptchaResponse":recaptchaResponse, "recaptchaChallenge":recaptchaChallenge, scheduleServiceDateId: scheduleServiceDateId};
		$.ajax({
			async : false,
			url : url,
			type : "POST",
			data : params,
			success : function(data, textStatus, jqXHR) {
				grecaptcha.reset();
				$(".fa-spinner").css("display","none");
				if(data==1){
					successModal("Service request created. The Service details sent to your email id");
					$("#work_group_id").val("");
				}else if(data==-99){
					failureMessage('guestServiceErrorMsg',  "Failed to create the service. Please provide a valid email address.");
				} else{
					failureMessage('guestServiceErrorMsg',  "Failed to create the service. Please Try again."+data);
				}
			}
		});
	}
}

function failureMessage(eleId, message){
	$("#"+eleId).attr("class", "pull-left errormsg");
	$("#"+eleId).text(message);
}
/* Populates the sub-work details based on the selected work-group_id*/
var availableSubServices = "";
function populateSubWorks(workGroupId, selectedSubGroupIdInSearch) {
//       var workgrpid = $(obj).children(":selected").attr("id");
	$("#work_group_id").val(workGroupId);
       var url = "action";
       var params = {
               "nav_action" : "populateSubworksCombo",
               "grpId" : workGroupId
       };
       $.ajax({
               async : false,
               url : url,
               type : "GET",
               data : params,
               dataType : "json",
               success : function(data, textStatus, jqXHR) {
            	   availableSubServices = "";
            	   availableSubServices = data;
                       $('#subworkCmbBx').html('');
                       $('#subworkCmbBx').append($('<option/>', {
                    	  		id : "0",
                    	   		value : "0",
                               text : "Select a Sub Work"
                       }));
                       $.each(data, function(idx, value) {
                               $('#subworkCmbBx').append($('<option/>', {
                            	   id : value.id,
                            	   value : value.id,
                                   text : value.desc
                               }));
                       });
                       $('select[name^="subworkCmbBx"] option[value='+selectedSubGroupIdInSearch+']').attr("selected","selected");
               }
       });
}

function getSubworkTarrif(obj){
	var selectedSubServiceId = $(obj).find('option:selected').attr('id');
	 $.each(availableSubServices, function(idx, value) {
		 if(selectedSubServiceId === value.id){
			 $('#serviceTarrif').text(value.price);
		 }else if(selectedSubServiceId === "0"){
			 $('#serviceTarrif').text("0");
		 }
 });
}

function clearAllModalData() {
	$('.modal-body input[type=text]').each(function () {
		$(this).val('');
	});
	$('.modal-body input[type=password]').each(function () {
		$(this).val('');
	});
	$('.modal-body textarea').each(function () {
		$(this).val('');
	});
	if($('#serviceTarrif')){
		$('#serviceTarrif').text("0");
	}
	var obj = $(".modal-footer").find( "span" );
	obj.addClass('errormsg').text('Mandatory fields are marked as *');
}

function validatePromoCode(){
	$('#refreshCoupon').addClass('fa-spin');
	$('#refreshCoupon').css('color', '#c12e2a');
	var msg = '' ;
	var returnValue = 0;
	var guestPromoCode = $.trim($('#guestPromoCode').val());
	if(isEmpty(guestPromoCode)){
		msg = "Copoun Code is not Valid";
	}
	if(msg!=''){
		failureMessage('guestServiceErrorMsg', msg);
		return false;
	}else{
		var url = "action";
		var params = { "nav_action" : "validateCoupon", "guestPromoCode":guestPromoCode};
		$.ajax({
			async: false,
			url : url,
			type : "GET",
			data : params,
			success : function(data, textStatus, jqXHR) {
				returnValue = data;
				$('#refreshCoupon').removeClass('fa fa-refresh fa-spin');
				$('#refreshCoupon').css('color', '#0C0C0C');
				if(data==1){
					$('#refreshCoupon').addClass('fa fa-check-square-o');
					$('#refreshCoupon').css('color', '#00c600');
					successMessage('guestServiceErrorMsg', 'Coupan applied sucessfully.');
				}else if(data==0){
					$('#refreshCoupon').addClass('fa fa-refresh');
					failureMessage('guestServiceErrorMsg',  "Coupon code is not valid 11.");
				}else{
					$('#refreshCoupon').addClass('fa fa-refresh');
					failureMessage('guestServiceErrorMsg',  "Failed to apply coupan.");
				}
			}
		
		});
	}
	return returnValue;
}

function validateRegisterUserPromoCode(){
	$('#refreshCoupon').addClass('fa-spin');
	$('#refreshCoupon').css('color', '#c12e2a');
	var msg = '' ;
	var returnValue = 0;
	var registerUserPromoCode = $.trim($('#regUserPromoCode').val());
	if(isEmpty(registerUserPromoCode)){
		msg = "Copoun Code is not Valid";
	}
	if(msg!=''){
		failureMessage('guestServiceErrorMsg', msg);
		return false;
	}else{
		var url = "action";
		var params = { "nav_action" : "validateRegisterUserCoupon", "registerUserPromoCode":registerUserPromoCode};
		$.ajax({
			async: false,
			url : url,
			type : "GET",
			data : params,
			success : function(data, textStatus, jqXHR) {
				returnValue = data;
				$('#refreshCoupon').removeClass('fa fa-refresh fa-spin');
				$('#refreshCoupon').css('color', '#0C0C0C');
				if(data==1){
					$('#refreshCoupon').addClass('fa fa-check-square-o');
					$('#refreshCoupon').css('color', '#00c600');
					successMessage('guestServiceErrorMsg', 'Coupan applied sucessfully.');
				}else if(data==0){
					$('#refreshCoupon').addClass('fa fa-refresh');
					failureMessage('guestServiceErrorMsg',  "Coupon code is not valid.");
				}else{
					$('#refreshCoupon').addClass('fa fa-refresh');
					failureMessage('guestServiceErrorMsg',  "Failed to apply coupan.");
				}
			}
		
		});
	}
	return returnValue;	 
 }

function sendFeedbackToReplad(){
	var msg = '';	
	var feedbackEmail = $.trim($('#feedbackEmail').val());
	var feedbackMobile = $.trim($('#feedbackMobile').val());
	var feedbackDescription = $.trim($('#feedbackDescription').val());
	
	//alert('Send the details to feedback@replad.com');
	if(isEmpty(feedbackEmail)){
		msg = "Please valid email id.";
	}else if(!validateEmail(feedbackEmail)){
		msg = "Please enter a valid email";
	}else if(isEmpty(feedbackMobile)){
		msg = "Please enter mobile number. ";
	}else if(!validPhoneNumber(feedbackMobile)){
		msg = "Please enter a valid phone number";
	}else if(isEmpty(feedbackDescription)){
		msg = "Please enter your feedback details.";
	}
	
	if(msg!=''){
		failureMessage('feedbackErrorMsg', msg);
		return false;
	}else{
		var url = "action";
		var params = { "nav_action" : "submitFeedback", "feedbackEmail":feedbackEmail, "feedbackMobile":feedbackMobile, "feedbackDescription":feedbackDescription};
		$.ajax({
			url : url,
			type : "POST",
			data : params,
			success : function(data, textStatus, jqXHR) {
				if(data==1){
					successModal("Thank you for your feedback.");
				}
			}
		
		});
	}
}

function exportExcel(){
	var url = "export";
	var params = { "nav_action" : "submitFeedback"};
	$.ajax({
		url : url,
		type : "GET",
		data : params,
		success : function(data, textStatus, jqXHR) {
			
		}
	
	});
}

function gotoHome(){
	$(window).attr("location","index.jsp");
}

function addEmployee(){
	$('#contentDiv').load('addEmployee.jsp');
}

function workManagement(){
	$('#contentDiv').load('workMgmt.jsp');
}

function employeeManagement(){
	$('#contentDiv').load('manageEmployee.jsp');
}

function ticketManagement(){
	$('#contentDiv').load('manageTickets.jsp');
}

function workDetailsRendering(){
	$('#contentDiv').load('workDetails.jsp');
}

/* to populate the work group combo box. Pass the select box object as parameter.*/
function lodaWorkGroup(obj){
	var url = "action";
	var params = {"nav_action" : "populateWorksCombo"};
	$.ajax({
		async : false,
		url : url,
		type : "GET",
		data : params,
		dataType : "json",
		success : function(data, textStatus, jqXHR) {
			$.each(data, function(idx, value) {
 				obj.append(new Option(value, idx));
			});
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('error > lodaWorkGroup');
		}
	});
}

function updateHiddenFieldValue(){
	alert("1");
}
