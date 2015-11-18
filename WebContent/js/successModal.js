
function successModal(successMessage){
	$('.modal').modal('hide'); // Close all open modal.
	var modal = document.createElement("div");
	modal.setAttribute("class", "modal fade");
	modal.setAttribute("id", "successAlertModal");
	
	var modaldialog = document.createElement("div");
	modaldialog.setAttribute("class", "modal-dialog");
	
	var modalcontent = document.createElement("div");
	modalcontent.setAttribute("class", "modal-content");
	
	var modalheader = document.createElement("div");
	modalheader.setAttribute("class", "modal-header modal-header-success");
		var modalcloseBtn = document.createElement("button");
		modalcloseBtn.setAttribute("type", "button");
		modalcloseBtn.setAttribute("class", "close");
		modalcloseBtn.setAttribute("data-dismiss", "modal");
		modalcloseBtn.setAttribute("aria-hidden", "true");
		modalcloseBtn.appendChild(document.createTextNode("x")); 
	modalheader.appendChild(modalcloseBtn); 
	modalheader.appendChild(document.createTextNode("Operation Successful")); 
	modalcontent.appendChild(modalheader);
	
	var modalbody = document.createElement("div");
	modalbody.setAttribute("class", "modal-body");
		var p = document.createElement("p");
			var t = document.createTextNode(successMessage);
		p.appendChild(t); 
	modalbody.appendChild(p); 
	modalcontent.appendChild(modalbody);
	
	var modalfooter = document.createElement("div");
	modalfooter.setAttribute("class", "modal-footer");
		var modalcloseBtn2 = document.createElement("button");
		modalcloseBtn2.setAttribute("type", "button");
		modalcloseBtn2.setAttribute("class", "btn btn-default btn-sm");
		modalcloseBtn2.setAttribute("data-dismiss", "modal");
			var tx = document.createTextNode("Close");
		modalcloseBtn2.appendChild(tx); 
	modalfooter.appendChild(modalcloseBtn2); 
	modalcontent.appendChild(modalfooter);
	modaldialog.appendChild(modalcontent); 
	modal.appendChild(modaldialog); 
	document.body.appendChild(modal);
	
	$("#successAlertModal").modal('show');
}