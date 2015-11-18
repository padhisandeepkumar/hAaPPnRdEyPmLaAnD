function go2EditTicket(){
	$('#contentDiv').load('editTicket.jsp');
}



function setchecked(obj) {
	if (obj.checked) {
		$(obj).attr("checked", "checked");
	} else {
		$(obj).removeAttr('checked');
	}
}
function loadTicketGrid() {
	var trId = 0;
	var ctr = 0;
	var url = "action";
	var ticketNumber = $('#ticketNumber').val();
	var categoryCombo = $('#categoryCombo').val();
	var employeeId = $('#employeeCombo').val();
	var fromDate = $('#fromDate').val();
	var toDate = $('#toDate').val();
	
	var params = {"nav_action" : "renderTicketDetailGridData", "ticketNumber":ticketNumber, "categoryCombo":categoryCombo, "employeeId":employeeId,
			"fromDate":fromDate, "toDate":toDate};
	$.ajax({
		async : false,
				url : url,
				type : "GET",
				data : params,
				dataType : "json",
				success : function(data, textStatus, jqXHR) {
					var tbody = $('#ordertable');
					$("#ordertable").find("tr:gt(0)").remove();
					var tr = $("<tr id='row_"+trId+"'>");
					var td = $('<td />');
					var idTD='' , work_group='' , sub_work_id='' , guest_email='' , guest_mobile='' , guest_address='' , createddate='' , guest_service_more_info='' , 
					ticket_id='' , name='' , extra_information='' , service_request_date='' , price_per_hour='' , discount_percentage='' , 
					total_price='' , total_work_hour='' , mst_employee_id= '', promo_code='', ticket_status='', employee_name='', commision='', guest_address='';
					$.each(data, function() {
						$.each(this, function(idx, value) {
							
							if (idx == 'id') {
								idTD = td.clone().html("<input type='checkbox' id='chkBx_"+ value+ "' value='"+ value + "' onchange='setchecked(this);' />&nbsp;&nbsp;<i class='fa fa-info-circle fa-lg' title='Click to view Customer details'></i>");
							} else if (idx == 'work_group_id') {
								work_group = td.clone().css('text-align','center').html(value);
							} else if (idx == 'guest_email') {
								guest_email = td.clone().text(value);
							} else if (idx == 'guest_mobile') {
								guest_mobile = td.clone().text(value);
							} else if (idx == 'createddate') {
								createddate = td.clone().text(value);
							} else if (idx == 'ticket_id') {
								ticket_id = td.clone().text(value);
							} else if (idx == 'name') {
								name = td.clone().css('text-align','center').html(value);
							} else if (idx == 'service_request_date') {
								service_request_date = td.clone().text(value);
							} else if (idx == 'price_per_hour') {
								price_per_hour = td.clone().text(value);
							} else if (idx == 'discount_percentage') {
								discount_percentage = td.clone().css('text-align','center').html(value);
							} else if (idx == 'price_per_hour') {
								price_per_hour = td.clone().text(value);
							} else if (idx == 'total_price') {
								total_price = td.clone().text(value);
							} else if (idx == 'total_work_hour') {
								total_work_hour = td.clone().text(value);
							} else if (idx == 'mst_employee_id') {
								mst_employee_id = td.clone().text(value);
							}else if (idx == 'promo_code') {
								promo_code = td.clone().text(value);
							}else if (idx == 'ticket_status') {
								trClass = (value.toLowerCase()=='closed'?'ticketclosed':(value.toLowerCase()=='cancelled' ? 'ticketcancelled' : ''));
								ticket_status = td.clone().text(value);
							} else if (idx == 'employee_name') {
								employee_name = td.clone().text(value);
							} else if (idx == 'commision') {
								commision = td.clone().text(value);
							}else if (idx == 'guest_address') {
								guest_address = td.clone().text(value);
							}
						});
						var container = $($.parseHTML($(idTD).html()));
						tr = $("<tr id='row_"+ container.val()+ "' onclick='' class='"+trClass+"'>");
						tr.append(idTD);
						tr.append(ticket_id);
						tr.append(name);
						tr.append(guest_address);
						tr.append(guest_mobile);
						tr.append(guest_email);
						tr.append(createddate);
						tr.append(service_request_date);
						tr.append(work_group);
						tr.append(price_per_hour);
						tr.append(discount_percentage);
						tr.append(total_price);
						tr.append(commision);
						tr.append(total_work_hour);
						tr.append(employee_name);
						tr.append(promo_code);
						tr.append(ticket_status);
						
						tbody.append(tr);
					});
				},
				error : function(jqXHR, textStatus, errorThrown) {
					alert('error: loadTicketGrid : '+textStatus);
				}
			});
}

function copyToClipboard() {
    $("body").append("<input type='text' id='temp' style='position:absolute;opacity:0;'>");
    
    var value = "";
    
    if($("#customerName")){
    	value+=$("#customerName").text()+'     ';
    }
    if($("#customerMobile")){
    	value+=$("#customerMobile").text()+'     ';
    }
    if($("#customerEmail")){
    	value+=$("#customerEmail").text()+'     ';
    }
    if($("#customerAddress")){
    	value+=$("#customerAddress").text()+'     ';
    }
    
    $("#temp").val(value).select();
    document.execCommand("copy");
    $("#temp").remove();
}