<%@page import="com.replad.mail.MailBodyUI"%>
<%@page import="com.replad.feedback.TestimonyBean"%>
<%@page import="com.replad.feedback.TestimonyHelper"%>
<%@page import="com.replad.landingPage.LandingPageHelper"%>
<%@page import="java.util.Map"%>
<%@page import="com.replad.mail.MailConfigurationBean"%>
<%@page import="com.replad.utils.StringUtilities"%>
<%@page import="com.replad.bean.work.WorkHelper"%>
<%@page import="com.replad.init.InitConfiguration"%>
<%@page import="net.tanesha.recaptcha.ReCaptcha"%>
<%@page import="net.tanesha.recaptcha.ReCaptchaFactory"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="google-site-verification" content="K0H4ApLEO51aMjPNylfe75N7SEXD66gQ-vxTfTA6ql4" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<title>replad</title>
<meta name=description content="home cleaning,home services,electrical,carpentry,masonary,appliance repair,servicing,plumbing,computer repair,painting,handyman job,bluecolar job,bangalore,household repair,househole restoration"/>
<meta name=keywords content=""/>
<meta name=author content=""/>
<meta name="viewport" content="width=device-width">
<link href="../../favicon.ico" rel="icon">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%= InitConfiguration.commonPropertiesMap.get("page.title")%></title>


<link rel="stylesheet" type="text/css" href="./bootstrap-3.2.0/dist/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="./bootstrap-3.2.0/dist/css/bootstrap-theme.min.css" />
<link rel="stylesheet" type="text/css" href="./css/slick.css">
<link rel="stylesheet" type="text/css" href="./css/main.css">
<link rel="stylesheet" type="text/css" href="./css/common.css">

<link rel="stylesheet" type="text/css" href="./font-awesome-4.3.0/css/font-awesome.css">
<link rel="stylesheet" type="text/css" href="./bootstrap-3.2.0/dist/css/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="./bootstrap-3.2.0/dist/css/bootstrap-theme.css" />

<link rel="stylesheet" type="text/css" href="./css/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="./css/jquery-ui.structure.css">
<link rel="stylesheet" type="text/css" href="./css/jquery-ui.theme.css">

<link rel="stylesheet" type="text/css" href="./font/iconfont/fontcustom.css">

<!-- Bootsrap form validation : https://github.com/nghuuphuoc/bootstrapvalidator -->
<link href="./css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">

<!-- Custmized for replad-->
<link rel="stylesheet" href="./css/custom.css"/>


<style>

.icon-circle {
	width: 50px;
	height: 50px;
	background-color: #F4F2F5;
	border: 2px solid #8585AB;
}

.icon-circle:hover {
	width: 50px;
	height: 50px;
	background-color: #FFE21C;
	border: 2px solid #0C0C0C;
}

.clsDatePicker {
z-index: 1051 !important;
}

</style>

<script type="text/javascript" src="./js/jquery-2.1.4.js"></script>
<script type="text/javascript" src="./js/jquery-ui.min.js"></script>
<script type="text/javascript" src="./js/common.js"></script>
<script type="text/javascript" src="./bootstrap-3.2.0/dist/js/bootstrap.min.js"></script>
<script type="text/javascript" src="./js/slick.min.js"></script>
<script type="text/javascript" src="./js/successModal.js"></script>
<script src="./js/smoothscroll.js"></script>

<script src="https://www.google.com/recaptcha/api.js"></script>
<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-65582669-1', 'auto');
  ga('send', 'pageview');

</script>
</head>

<%
	new WorkHelper().getWorkDesc(request);
	MailBodyUI mailBodyUI = new MailBodyUI();
	Map<String, String> contactDetails = mailBodyUI.getSupportContactDetails();
	String supportContactNumber = contactDetails.get("supportContactNumber");
	String feedbackEmailId = contactDetails.get("supportEmailId");
%>

<body>
	<input type='hidden' id='work_group_id' name='work_group_id' value='' />
	<!-- Header Section Start -->
	<div class="container header-container">
	  <nav class="navbar navbar-default navbar-fixed-top">
	    <div class="container-fluid">
	      <div class="col-lg-3 col-md-3 col-sm-3" style="padding-top: 2px;padding-bottom: 2px;">
				<!-- <a href="#"> <img src="./images/logo/repladLogo005.png" alt="replad"  class='pull-left' style='width:200px;height: 75px;padding-top:10px;' /></a> -->
				<!-- <a href="#"> <img src="./images/logo/repladLogo005.svg" alt="Kiwi standing on oval"></a> -->
				
				<a href="#" class="repladLogo"></a>
			</div>
	      <div class="col-lg-7 col-md-7 col-sm-7 supportSec">
	        <div class="supportInfo"><span>Contact Us</span>
	          <p><span><i class='fa fa-phone fa-sm' style='color:#c12e2a;'></i>&nbsp;<%=supportContactNumber %></span></p>
	        </div>
	        <div class="supportInfo"><span>Mail Us</span>
	          <p><span><i class='fa fa-envelope fa-sm' style='color:#c12e2a;'></i>&nbsp;<a href="mailto:<%=feedbackEmailId %>?Subject=CustomerCare" target="_top" style="color: #757575;"><%=feedbackEmailId %></a></span></p>
	        </div>
	        <div class="supportInfo"><span>Locations</span>
	          <p><span><i class='fa fa-globe fa-sm' style='color:#c12e2a;'></i>&nbsp;Bangalore</span></p>
	        </div>
	      </div>
	      <div class="col-lg-2 col-md-2 col-sm-2 loginSec pull-right">
	     	<a href="https://www.facebook.com/pages/Replad/1461464257479912" target="_blank">
	     		<i class="fa fa-facebook-square fa-2x facebook-color"></i>
	     	</a> 
	     	<a href="https://twitter.com/ireplad" target="_blank">
	     		<i class="fa fa-twitter-square fa-2x twitter-color"></i>
	     	</a> 
	     	<a href="https://plus.google.com/118169628102155475802/" target="_blank">
	     		<i class="fa fa-google-plus-square fa-2x googleplus-color"></i>
	     	</a>
	      </div>
	    </div>
	    <!-- /.container-fluid --> 
	    </nav>
	</div>
<!-- Header Section End -->
<!-- Offer Section -->
<section class="offerSection">
	<div class="offer-body">
		<div class="offer-button" id="offer-btn">
			<span class="close-btn">
				<i class="fa fa-times"></i>
			</span>
			<div class="button-content">
				Offers
			</div>
		</div>
		<div class="offer-content">
			<span class='couponcodeFont1'>To avail 10% discount on your first service request use Coupon Code <br/><br/><span class="label label-danger couponcodeFont2" style='padding: 2px;margin-top:10px;'>REPLAD10</span></span>
			<!-- <br/><br/>
			<span class='couponcodeFont1'>To avail extra 1% on your plumbing service request use Coupon Code <br/><br/><span class="label label-success couponcodeFont2" style='padding: 2px;margin-top:10px;'>SDVC-123</span></span>
			<br/><br/>
			<span class='couponcodeFont1'>To avail extra 1% on your plumbing service request use Coupon Code <br/><br/><span class="label label-warning couponcodeFont2" style='padding: 2px;margin-top:10px;'>SDVC-123</span></span>
			<br/><br/>
			<span class='couponcodeFont1'>To avail extra 1% on your plumbing service request use Coupon Code <br/><br/><span class="label label-primary couponcodeFont2" style='padding: 2px;margin-top:10px;'>SDVC-123</span></span>
			<br/><br/>
			<span class='couponcodeFont1'>To avail extra 1% on your plumbing service request use Coupon Code <br/><br/><span class="label label-danger couponcodeFont2" style='padding: 2px;margin-top:10px;'>SDVC-123</span></span> -->
		</div>
	</div>
</section>
<!-- Offer Section End -->
<!-- Service Section Start Here -->
<section id="service" class="serviceBackgroud">
  <div class="container serviceCategory">
	  <div class="searchBarSec row">
	    <div class="col-lg-2 col-md-2 col-sm-2"></div>
	    <div class="col-lg-8 col-md-8 col-sm-8">
	    	<h2 class="text-center headingBanner shadow">Search or choose from category for the required services</h2>
	      <form class="" role="search">
	        <div class="input-group" style="margin:0 0 20px;">
	          <input type="text" class="form-control" name="searchBox" id="searchBox" placeholder="Search Services" onclick="searchAutoComplete(this, '');">
	          <span class="input-group-addon searchIcon" style='background-color:#FFF120'> <i class="glyphicon glyphicon-search fontcolor009"></i> </span> </div>
	      </form>
	    </div>
	    <div class="col-lg-3 col-md-3 col-sm-3"></div>
	  </div>
	  
	  <div class="categorySec">
	    <div class="col-lg-4 col-md-4 col-sm-4 serviceBox" onClick="showServiceModal('guestServicesModal', '1', '0');">
	    	<span class="pull-left" style='font-size: 48px;'><i class="icon-tap fontcolor009"></i></span>
	    	<span class="statstitle fontcolor009">Plumbing</span>
	    	<div class="display-box">
	    		<ul>
	    			<li>General/Bathroom/Kitchen/Laundry Room/Outdoor Plumbing Services</li>
	    			<li>Drainage Cleaning/Septic System Inspection</li>
	    			<li>Installing & Repairing Fittings</li>
	    		</ul>
	    	</div>
	    </div>
	    <div class="col-lg-4 col-md-4 col-sm-4 serviceBox" onClick="showServiceModal('guestServicesModal', '2', '0');">
	    	<span class="pull-left" style='font-size: 48px;'><i class="icon-bulb fontcolor009"></i></span>
	    	<span class="statstitle fontcolor009">Electrical</span>
	    	<div class="display-box">
				<ul>
	    			<li>New Fittings/Geyser/UPS/Chimney/Water Purifier/Door-Bell Installation.</li>
	    			<li>Current Leakage/Wiring Checking/Wiring Installation</li>
	    			<li>Fan/Water Purifier/Sub-mersible Motor Repair & Servicing</li>
	    		</ul>
			</div>
	    </div>

	    <div class="col-lg-4 col-md-4 col-sm-4 serviceBox" onClick="showServiceModal('guestServicesModal', '3', '0');">
	    	<span class="pull-left" style='font-size: 48px;'><i class="icon-cutter fontcolor009"></i></span>
	    	<span class="statstitle fontcolor009">Carpentry</span>
	    	<div class="display-box">
				<ul>
	    			<li>Curtains/Blind Fitting/Door Lock/View Finder Installation.</li>
	    			<li>Replacement of bathroom doors/WaterProofing Sheet/Water Proofing Oil.</li>
	    			<li>Nailing/Wall Hanging/Photo Frame Installation.</li>
	    		</ul>
			</div>
	    </div>

	    <div class="col-lg-4 col-md-4 col-sm-4 serviceBox" onClick="showServiceModal('guestServicesModal', '4', '0');">
	    	<span class="pull-left" style='font-size: 48px;'><i class="icon-masonry fontcolor009"></i></span>
	    	<span class="statstitle fontcolor009">Masonry</span>
	    	<div class="display-box">
				<ul>
	    			<li>House/Bathroom/Outdoor/Kitchen Tile setting and replacement.</li>
	    			<li>Walls partitioning/ Wall Furring.</li>
	    			<li>Brick/Stone setting work.</li>
	    		</ul>
			</div>
	    </div>
	    <div class="col-lg-4 col-md-4 col-sm-4 serviceBox" onClick="showServiceModal('guestServicesModal', '5', '0');">
	    	<span class="pull-left" style='font-size: 48px;'><i class="icon-cleaner fontcolor009"></i></span>
	    	<span class="statstitle fontcolor009">Cleaning</span>
	    	<div class="display-box">
				<ul>
	    			<li>House/Toilet Deep Cleaning or General house Cleaning.</li>
	    			<li>After party/Festival/Occasional/Post Shifting Cleaning.</li>
	    			<li>New house acid wash and neutralization.</li>
	    			<li>Apartment house-keeping.</li>
	    		</ul>
			</div>
	    </div>

	    <div class="col-lg-4 col-md-4 col-sm-4 serviceBox" onClick="showServiceModal('guestServicesModal', '6', '0');">
	    	<span class="pull-left" style='font-size: 48px;'><i class="icon-brush fontcolor009"></i></span>
	    	<span class="statstitle fontcolor009">Painting</span>
	    	<div class="display-box">
				<ul>
	    			<li>Apartments/industrial buildings/Commercial properties/Storage units painting.</li>
	    			<li>Residential Long lasting Painting treatments.</li>
	    			<li>Kitchen Cabinet Spray Painting/Refinishing. </li>
	    		</ul>
			</div>
	    </div>

	    <div class="col-lg-4 col-md-4 col-sm-4 serviceBox" onClick="showServiceModal('guestServicesModal', '7', '0');">
	    	<span class="pull-left" style='font-size: 48px;'><i class="icon-sofa fontcolor009"></i></span>
	    	<span class="statstitle fontcolor009">Sofa Repair</span>
	    	<div class="display-box">
	    		<ul>
	    			<li>Sofa set repair and mite treatments.</li>
	    			<li>Sofa/Chair Cushion replacement.</li>
	    			<li>Sofa deep cleaning and refurbishing.</li>
	    			<li>Old sofa selling or restoration..</li>
	    		</ul>
	    	</div>
	    </div>

	    <div class="col-lg-4 col-md-4 col-sm-4 serviceBox" onClick="showServiceModal('guestServicesModal', '8', '0');">
	    	<span class="pull-left" style='font-size: 48px;'><i class="icon-pc fontcolor009"></i></span>
	    	<span class="statstitle fontcolor009">Appl. Repair</span>
	    	<div class="display-box">
	    		<ul>
	    			<li>Fridge/TV/Water Purifier/Oven/Washing Machine Repair.</li>
	    			<li>PC Repair/Servicing/Data Recovery/Networking.</li>
	    			<li>AMC for all Electrical and Electronic Appliances.</li>
	    			<li>Home Security device installation and servicing.</li>
	    		</ul>
	    	</div>
	    </div>

	    <div class="col-lg-4 col-md-4 col-sm-4 serviceBox" onClick="showServiceModal('guestServicesModal', '9', '0');">
	    	<span class="pull-left" style='font-size: 48px;'><i class="icon-toolbox fontcolor009"></i></span>
	    	<span class="statstitle fontcolor009">Others</span>
	    	<div class="display-box">
				<ul>
	    			<li>House warming arrangements.</li>
	    			<li>Party and Catering arrangements.</li>
	    			<li>School/College annual function arrangements.</li>
	    			<li>Organize and carry out Special Events.</li>
	    		</ul>
			</div>
	    </div>
	  </div>
  </div>
</section>

<style>
.secondary-services div.col-lg-3{
	padding:5px;
}
</style>
<!-- Service Section End Here -->
	
	<!--  Why Choose Us Start -->
	<div class="section-header sec-service-wrap">
		<div class="container">
			<div class='col-xs-12 col-md-12 col-md-12 text-center'>
				<span class='sectiontitleclass fontcolor009'>Why Choose Us?</span>
			</div>
		</div>
	</div>
	<section id='workingProc'>
		<div class="container" >
			<div class="row secondary-services workprocesstitle fontcolor009">
				<div class="col-lg-3 col-md-6 col-sm-12">
					<div class="small-text shadow text-center">
						Our Servicemen<br>
						<div class="very-small-text text-primary fontcolor009">Most of our servicemen have good industry experience which enables them to understand your requirements and your expectations.</div>
					</div>
				</div>
				<div class="col-lg-3 col-md-6 col-sm-12">
					<div class="small-text shadow text-center">
						Service Warranty<br>
						<div class="very-small-text">Our services come with a warranty of 7 days. Any issue/problem reported on our service provided will be resolved free of cost.</div>
					</div>
				</div>
				<div class="col-lg-3 col-md-6 col-sm-12">
					<div class="small-text shadow text-center">
						Estimate Beforehand<br>
						<div class="very-small-text">We believe in complete transparency and considering the nature of job, each one is individually quoted. Quotes can be done by emailing pictures or by appointment.</div>
					</div>
				</div>
				<div class="col-lg-3 col-md-6 col-sm-12">	
					<div class="small-text shadow text-center">
						Flexible with Times<br>
						<div class="very-small-text">We provide flexibility to reschedule any work beforehand by calling us or shooting an email to us.</div>
					</div>
				</div>
			</div>
		</div>
	</section>
	<!--  Why Choose Us End -->
	
	<!--  How we work Start -->
	<!-- <section class="sec-service-wrap" id='workingProc'> -->
	<div class="section-header sec-service-wrap">
		<div class="container">
			<div class='col-xs-12 col-md-12 col-md-12 text-center'>
				<span class='sectiontitleclass fontcolor009'>How we work?</span>
			</div>
		</div>
	</div>
	<div class="section" id='howwework'>
		<div class="container secondary-services fontcolor009">
			<div class="col-lg-3 col-md-6 col-sm-12">
				<div>
					<span class="fa-stack fa-5x center-block"> <i
						class="fa fa-circle fa-stack-2x icon-background"></i> <i
						class="fa fa-phone fa-stack-1x"></i>
					</span>
				</div>
				<div class='text-center' style='padding-top: 15px;'>
					<span class='workprocesstitle shadow'>Received a Service
						Request</span> <br /> <span class='workprocesssubtitle shadow'>Request
						for a Service through online registering, email, give a miss call
						to us.</span>
				</div>
			</div>

			<div class="col-lg-3 col-md-6 col-sm-12">
				<div>
					<span class="fa-stack fa-5x center-block"> <i
						class="fa fa-circle fa-stack-2x icon-background"></i> <i
						class="fa fa-check fa-stack-1x"></i>
					</span>
				</div>
				<div class='text-center' style='padding-top: 15px;'>
					<span class='workprocesstitle shadow'>Authenticate the
						Request</span> <br /> <span class='workprocesssubtitle shadow'>We
						call you to authenticate and understand your service request.</span>
				</div>
			</div>

			<div class="col-lg-3 col-md-6 col-sm-12">
				<div>
					<span class="fa-stack fa-5x center-block"> <i
						class="fa fa-circle fa-stack-2x icon-background"></i> <i
						class="fa fa-share fa-stack-1x"></i>
					</span>
				</div>
				<div class='text-center' style='padding-top: 15px;'>
					<span class='workprocesstitle shadow'>Service Allocated</span> <br />
					<span class='workprocesssubtitle shadow'>We allocate the service to concerned workmen. Pay only on completion of the requested service.</span>
				</div>
			</div>
			<div class="col-lg-3 col-md-6 col-sm-12">
				<div>
					<span class="fa-stack fa-5x center-block"> <i
						class="fa fa-circle fa-stack-2x icon-background"></i> <i
						class="fa fa-smile-o fa-stack-1x"></i>
					</span>
				</div>
				<div class='text-center' style='padding-top: 15px;'>
					<span class='workprocesstitle shadow'>7days Warranty</span> <br /> <span class='workprocesssubtitle shadow'>After receiving your satisfactory feedback or completion of 7 days warranty period we close the service request.</span>
				</div>
			</div>
		</div>
		<!-- </section> -->
		<!-- </section> -->
		<!--  How we work End -->

		<!--  Why Customer Like Us Start -->
		<div class="section-header image-slide-wrap sec-service-wrap">
			<div class="container">
				<div class='col-xs-12 col-md-12 col-sm-12 text-center'>
					<span class='sectiontitleclass fontcolor009'>Why Customer Like Us?</span>
				</div>
			</div>
		</div>

		<div class="section" id='testimony'>
			<div class="container">
				<%@ include file="testimony.jsp"%>
			</div>
		</div>
		<!-- Count Start -->


		<div class="section" id='stats'>
			<div class="container">
				<%@ include file="statistics.jsp"%>
			</div>
		</div>

		<!-- Count End -->
		<div class="container" id="footerContainer"
			style='padding-top: 15px; background-color: #F4F2F5; color: #0C0C0C;'>
			<div class="container">
				<div class="row">

					<div class="col-lg-8 col-md-8 col-sm-8 fontcolor009">
						<i class="fa fa-weixin fa-3x shadow" style='padding-top: 2px;'></i>
						<span class='statstitle'>About Us</span>
						<div class='12px shadow text-justify'
							style='padding-bottom: 20px; padding-top: 10px;'>
							We can restore anything Starting from rear plumbing to the facade
							of your home. Our service catalogue for restoration includes
							Plumbing, Sofa repair, carpentry works, electrical & fittings,
							cleaning, PC repair, painting & Masonry works. It also includes
							miscellaneous restoration & periodic maintenance work as well. We
							have also flexi AMC plan for our privileged customers. Based on
							the customer needs we can design an AMC plan to suit their
							maintenance requirement.
							<div class='separator1'></div>
							We also have expertise in freezing your important moments and
							making them memorable. Our service catalogue for event management
							includes house warming, parties, organizing special events,
							corporate team outings & functions, school/college annual
							functions etc. In nut shell, you name it and we will do it for
							you.
							<div class='separator1'></div>
							Resources are pillars of every organization and customer
							satisfaction is the key to growth of it. Being from service
							industry we realized this and have created a resource base having
							required/relevant proficiency in their trade. Their expertise
							bridges the gap between their understanding and customer's
							expectation.
							<div class='separator1'></div>
						</div>
					</div>

					<div class="col-lg-4 col-md-4 col-sm-4">
						<div class="col-lg-12 col-md-12 col-sm-12 fontcolor009">
							<i class="fa fa-comments-o fa-3x shadow"
								style='padding-top: 2px;'></i> <span
								class='statstitle'>Suggestions</span>
							<div class='12px shadow text-justify' style='padding-top: 10px;'>
								As our valued customer your Suggestions and Feedbacks are of
								utmost importance for us to improve our services. Hence please
								help us to serve you better. Kindly write to us <a
									href='mailto:<%=feedbackEmailId%>?Subject=Suggestions and Feedbacks'><i
									class='fa fa-envelope fa-lg' style='color: #ff3f20;'></i></a>
							</div>
						</div>

						<!-- <div class="col-lg-12 col-md-12 col-sm-12 fontcolor009"
							style='padding-top: 20px;'>
							<i class="fa fa-map-marker fa-3x shadow"
								style='padding-top: 2px;'></i> <span
								class='statstitle'> Address</span>
							<address>
								<div class='12px shadow text-justify' style='padding-top: 10px;'>
									No.28, S J R Layout, 2nd Phase, Hoodi, <br /> Kodigehalli Main
									Road<br /> Bangalore, Pin- 560048, <br /> Karnataka.
								</div>
							</address>
						</div> -->
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- Footer Start -->
	<div class="section primary-bg" id='copyright'>
		<div class="container">
			<%@ include file="footer.jsp" %>
		</div>
	</div>
	<!-- Footer End -->
	<div>
		<ul class="nav pull-right scroll-top">
  			<li><a href="#" title="Scroll to top" style='padding: 2px 4px;'><i class="fa fa-chevron-circle-up fa-4x"></i></a></li>
		</ul>
	</div>
	
	<!-- MODAL Starts here -->
	<!-- Guest Service Modal -->
	<div id="guestServicesModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true"><i class='fa fa-remove fa-sm'></i></button>
					<h4 class="modal-title" id='servicesModalTitle' style='font-size: 12px; font-weight: bold;'>Service Request</h4>
				</div>
				<div class="modal-body">
					<div class="input-group">

						<select class="input-group-addon selectpicker boldfont-12px" data-style="btn-warning" id='subworkCmbBx' name='subworkCmbBx' 
						onchange="getSubworkTarrif(this);" style='text-align:left;width:75%;height: 30px;padding: 2px 2px;font-size: 12px;border-radius: 5px;background:#ece65f;'>
							<option id='0'>Select a Sub Work</option>
						</select>
						&nbsp;
						<span class="label-group-addon" style='width:28%'>
							<small><span>Min. Price&nbsp;</span><span class='label label-danger'><i class="fa fa-inr fa-sm"></i>&nbsp;<span id='serviceTarrif'>0</span>&nbsp;/hr*</span></small>
						</span>
					</div>
					<div class='separator1'></div>
					
					<div class="input-group">
						<span class="input-group-addon" style="cursor: pointer;"><i class="fa fa-user"></i><span style='color:red;font-size:12px;'>&nbsp;*</span></span>
						<input type="text" maxlength="50" autocomplete='off' class="form-control" id="guestUserName" placeholder="Please enter your full name" style='font-size:12px;padding-left:10px;height:28px;width:95%;'></input>
						
						<span class="input-group-addon" style="cursor: pointer;"><i class="fa fa-calendar"></i><span style='color:red;font-size:12px;'>&nbsp;*</span></span>
						<input type="text" name="scheduleServiceDateId" id="scheduleServiceDateId" readonly="readonly" class="form-control clsDatePicker" 
						placeholder='Select the Schedule Date' style='font-size:12px;height:28px;padding-left:5px;width:99%;'></textarea>
					</div>
					
					<div class='separator1'></div>
					<div class="input-group">
						<span class="input-group-addon" style="cursor: pointer;"><i class="fa fa-envelope"></i><span style='color:red;font-size:12px;'>&nbsp;*</span></span>
						<input type="text" maxlength="50" autocomplete='off' class="form-control" id="guestregEmail" placeholder="Please enter your email id" style='font-size:12px;padding-left:10px;height:28px;width:95%;'></input>
						<span class="input-group-addon" style="cursor: pointer;"><i class="fa fa-mobile"></i><span style='color:red;font-size:12px;'>&nbsp;*</span></span>
						<input type="text" autocomplete='off' class="form-control" id="guestregMobile" placeholder="Please enter your mobile number" style='font-size:12px;padding-left:10px;height:28px;width:99%;' maxlength="10"></input>
					</div>
					<div class='separator1'></div>
					<div class="input-group">
						<span class="input-group-addon" style="cursor: pointer;"><i class="fa fa-home"></i><span style='color:red;font-size:12px;'>&nbsp;*</span></span>
						<textarea class='form-control' maxlength="200" autocomplete='off' rows='3' id="guestregAddress" placeholder="Please enter your correspondence address with landmark" style='font-size:12px;padding-left:10px;width:99%;'></textarea>
					</div>
					<div class='separator1'></div>
					<div class="input-group">
						<span class="input-group-addon" style="cursor: pointer;"><i class="fa fa-info-circle"></i></span>
						<textarea class='form-control' maxlength="200" autocomplete='off' rows='3' id="guestMoreInfo" placeholder="Please detail about your service (if any)." style='font-size:12px;padding-left:10px;width:99%;'></textarea>
					</div>
					
					<div class='separator1'></div>
					<div class="input-group" style='width:49%;'>
						<span class="input-group-addon" style="cursor: pointer;color:#c12e2a"><i class="fa fa-ticket"></i></span>
						<input type="text" autocomplete='off' maxlength="10" class="form-control" id="guestPromoCode" placeholder="THE COUPON CODE" style='font-size:12px;padding-left:10px;height:28px;width:99.9%;color:#c12e2a;'></input>
						<span class="input-group-addon" style="cursor: pointer;" onClick="validatePromoCode();"><i class="fa fa-refresh" id='refreshCoupon'></i></span>
					</div>
					
					<div class='separator1'></div>
					<div class="g-recaptcha" data-sitekey="6LdmbhATAAAAAMAFGgkR8bK-LfpcZ1kfzG-Ia2KR"></div>
					
					<div class='separator1'></div>
					<div style='padding:5px;'>
					<p>
					<input type="checkbox" name="serviceAggreementChkBx" id="serviceAggreementChkBx">
					<input type="hidden" name="serviceAggreementhidden" id="serviceAggreementhidden">
						 <a href="#" onclick="javascript:$('#agreementModal').modal('show');" 
							class='regularfont-14px' style='color:#0a416f;'>agree to the terms and conditions.</a> 
					</p>
				</div>
				
				</div>
					
				<div class="modal-footer">
					<i class="fa fa-spinner fa-pulse fa-2x pull-left" style='color:#1001f3;display:none;'></i>
					<span id="guestServiceErrorMsg" class='pull-left errormsg'>Mandatory fields are marked as &nbsp;*&nbsp;</span>
					<button type="button" class="btn btn-danger btn-xs pull-right" onClick="createGuestService();" id='guestServiceModalCreateBtn'>
						Create
					</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- Terms&Conditions modal -->
	<div id="agreementModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id='servicesModalTitle'>Terms and Conditions</h4>
				</div>
				<div class="modal-body" id="agreementModalBody">
					<%@ include file="terms.jsp"%>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-danger btn-xs" data-dismiss="modal" aria-hidden="true">Agreed</button>
				</div>
			</div>
		</div>
	</div>
	<!-- Terms&Conditions modal end -->
	
	<!-- MODAL Section - Ends -->
	<script type="text/javascript">
		$('#scheduleServiceDateId').datepicker({
			dateFormat : 'dd-mm-yy',
			minDate: '+0d',
			maxDate: '10d',
			altField : "#scheduleServiceDateId",
			altFormat : "yy-mm-dd"
		});

		$("#offer-btn").on('click', function(e) {
			console.log("offer btn clicked");
			$(".offerSection").toggleClass("closed");
			$(".close-btn").toggleClass("show");
		});
		
		$('#serviceAggreementChkBx').change(function () {
		    if ($(this).is(":checked")) {
		    	$('#serviceAggreementhidden').val("on");
		    } else {
		    	$('#serviceAggreementhidden').val("off");
		    }
		});
	</script>
</body>
</html>