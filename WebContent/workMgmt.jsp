<html>
<script type="text/javascript" src="./js/bootstrap-filestyle.min.js"></script>
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
</style>

</body>
<%
%>

 <form action="" method="post" enctype="multipart/form-data">
<div id='workMgmt'>
	<div class="container-fluid" style="padding-top: 85px;">
		 <div class="panel panel-default">
		 	<div class="panel-heading" style='height:60px;'>
            	<div class="pull-left">
            	<h4>Work Management</h4></div>
				<div class="btn-group pull-right">
					<ul class="nav nav-pills">
						<li onclick='javascript: workDetailsRendering();'><a href="#"
							data-toggle="tab">Work Details</a>
						<li class="active" onclick='javascript:displayInput();'><a
							href="#" data-toggle="tab">Upload Work Details</a>
					</ul>
				</div>
			</div>
			<div class="panel-body" id='employeeDetailsInput' style='display:block'>
				<div class='form-group'>
					<div class="col-lg-12 col-md-12 col-sm-12" style='padding-left: 0px;'>
						<label class="form-control-label col-sm-2" for="text" style='padding-left: 0px;'>Upload Work Details</label>
						<div class="input-group col-sm-6">
							<input class="filestyle" type="file" name="myFile" />
							<span style='color:#C12E2A; font-size:1.2rem;'>Browse the file contains the work details with price</span>
						</div>
					</div>
				</div>
			</div>
			<div class="panel-footer clearfix" style='padding:5px;'>
              <span id='messageSpan'></span>
              <div class="pull-right">
       <!--            <button type="submit" class="btn btn-primary btn-sm">Save work details</button> -->
        		<input type="submit" value="Save work details" class="btn btn-primary btn-sm">
              </div>
          </div>
		 </div>
	</div>
</div>
</form>
 </body>
</html>