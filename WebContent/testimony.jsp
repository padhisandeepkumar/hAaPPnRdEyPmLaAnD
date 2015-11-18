<%@page import="com.replad.init.InitConfiguration"%>
<%@page import="com.replad.feedback.TestimonyHelper"%>
<%@page import="com.replad.feedback.TestimonyBean"%>
<%@page import="java.util.Map"%>
<html>
<head>
<title><%= InitConfiguration.commonPropertiesMap.get("page.title")%></title>

<link rel="stylesheet" type="text/css" href="./css/slick.css">
<link rel="stylesheet" type="text/css" href="./css/main.css">
<link rel="stylesheet" type="text/css" href="./css/common.css">
<link rel="stylesheet" type="text/css" href="./css/custom.css"/>

<script type="text/javascript" src="./js/slick.min.js"></script>

<style>

</style>
</head>
<body>
 <!--Testimonials Start-->
 <div class="container slider" id='testimonyslider'>
		
			<%
				Map<String, TestimonyBean> feedbackMap = new TestimonyHelper().getFeedbackDetails();
				TestimonyBean bean = null;
				String feedback = "", signature="", image ="";
				for(String feedbackKey:feedbackMap.keySet()){
					bean = feedbackMap.get(feedbackKey);
					feedback = bean.getCustomerComment(); signature = bean.getCustomerSignature(); image = bean.getImage();
			%>
			<div style="margin-top: 0.3%;">
				<div class="slide-content">
					<div class='span12 col-lg-12 col-md-12 col-sm-12 pagination-centered'>
						<img class="img-circle img-responsive center-block"
							src="<%= image%>" style="width: 100px; height: 100px;" />
					</div>
					<div class="col-lg-12 col-md-12 col-sm-12">
						<div class="container fontcolor009">
							<div class="col-lg-12 col-md-12 col-sm-12">
								<i class="fa fa-quote-left fa-4x pull-left"></i>
								<p class='testimony shadow'><%= feedback%></p>
							</div>
							<div class="pull-right testimony-signature shadow">- <%= signature%></div>
						</div>
					</div>
				</div>
			</div>
			<%} %>
		</div>
 <!--Testimonials End-->
</body>
</html>
<script type="text/javascript">
$('#testimonyslider').slick({
    arrows: false,
    autoplay: true,
    dots: false,
    infinite: true,
    speed: 2000,
    slidesToShow: 1
  });
    </script>
