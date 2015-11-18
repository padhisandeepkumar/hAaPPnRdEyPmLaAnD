<!DOCTYPE HTML>
<html>
<head>
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<link rel="stylesheet" type="text/css"
	href="./bootstrap-3.2.0/dist/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="./css/slick.css">
<link rel="stylesheet" type="text/css"
	href="./font-awesome-4.2.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="./css/main.css">
<style>
#testimony>.slider div div>.text-center{
	width: 100%; 
	height: 350px;
	background-color:#f1c40f;
}

</style>
<script type="text/javascript" src="./js/jquery-2.1.4.js"></script>
<script type="text/javascript"
	src="./bootstrap-3.2.0/dist/js/bootstrap.min.js"></script>
<script type="text/javascript" src="./js/slick.min.js"></script>
<script type="text/javascript" src="./js/main.js"></script>
</head>

<body>
	<section class="image-slide-wrap" id='testimony'>
		<div class="slider" id='testimonyslider'>
			<div>
				<div class="text-center">
					<div class='span12 pagination-centered' style='padding-top: 20px;'>
						<img class="img-circle img-responsive center-block"
							src="./images/11.JPG" style="width: 150px; height: 150px;" />
					</div>
					<div class="row-fluid col-md-12">
						<div class="container" style='padding-top: 20px; color: #0C0C0C;'>
							<div class="col-lg-12">
								<i class="fa fa-quote-left fa-4x pull-left"></i>
								<p class='testimony'>Turpis arcu rutrum nunc, ac laoreet
									turpis augue a justo. Lorem ipsum dolor sit amet, consectetur
									adipisicing elit. Expedita, esse, animi maxime voluptate
									tempore at sunt labore incidunt nulla dignissimos iusto ad
									similique inventore distinctio quam repellendus itaque minus
									minima.Turpis arcu rutrum.</p>
							</div>
							<div class="pull-right">- John Doe</div>
						</div>
					</div>
				</div>
			</div>
			<div>
				<div class="text-center">
					<div class='span12 pagination-centered' style='padding-top: 20px;'>
						<img class="img-circle img-responsive center-block"
							src="./images/11.JPG" style="width: 150px; height: 150px;" />
					</div>
					<div class="row-fluid col-md-12">
						<div class="container" style='padding-top: 20px; color: #0C0C0C;'>
							<div class="col-lg-12">
								<i class="fa fa-quote-left fa-4x pull-left"></i>
								<p class='testimony'>Turpis arcu rutrum nunc, ac laoreet
									turpis augue a justo. Lorem ipsum dolor sit amet, consectetur
									adipisicing elit. Expedita, esse, animi maxime voluptate
									tempore at sunt labore incidunt nulla dignissimos iusto ad
									similique inventore distinctio quam repellendus itaque minus
									minima.Turpis arcu rutrum.</p>
							</div>
							<div class="pull-right">- John Doe</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
	<span style='display:none'>Do not remove this span or else the testimony slider won't work</span>
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
</body>
</html>