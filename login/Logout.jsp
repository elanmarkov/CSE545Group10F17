<!DOCTYPE html>
<html lang="en">
<head>
	<title>Log out</title>
	<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<!-- <link rel="stylesheet" type="text/css" href="login.css"> -->
	
	<style type="text/css">
		.logo{
		margin-bottom: 0px;
		}


		#title{
			/*color: #FAEBD7;*/
			margin: auto;
			text-align: center;
		}

		#loginBox{
			margin: 5% 5% 35% 30%;
		}

		.hidden{
			visibility: hidden;
		}
	</style>

	<script language="JavaScript" type="text/javascript"> 
    function checkEmail(){
    console.log("called");
    var userEmail = $('#userEmail').val();  
    console.log(userEmail);
    var filter = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
            if(!filter.test(userEmail))
            {
                $("#errorBox").html("Please Enter a Valid Email Address");
                return false;
            }
  	};
  	</script>

  	<script>
		window.onload = function() {
		  var recaptcha = document.forms["myForm"]["g-recaptcha-response"];
		  recaptcha.required = true;
		  recaptcha.oninvalid = function(e) {
		    alert("Please complete the captcha");
		  }
		}	
	</script>


</head>
<body>


<div class="jumbotron logo">
	<div class="container">
	  <h2 id="title">WELCOME TO GROUP10 BANK</h2>
	</div>	
</div>

<div class="container" id="loginBox">
	<div class="row">
		<div class="col-lg-6">
			<div class="jumbotron">
			  <form name="logoutForm" action="/BankingApp/logout" method="post">
				  <div><h3><strong>Do you really want to log out?</strong></h3></div>
				  <div class="radio"><label><input type="radio" name="yes">Yes</label></div>
				  <div class="radio"><label><input type="radio" name="no">No</label></div>
        		<button type="submit" class="btn btn-xs btn-default">Submit</button>
			  </form>

			</div>


		</div>
	</div>
</div>

<script src='https://www.google.com/recaptcha/api.js'></script>
<script   src="https://code.jquery.com/jquery-3.2.1.js"   integrity="sha256-DZAnKJ/6XZ9si04Hgrsxu/8s717jcIzLy3oi35EouyE="   crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
</body>
</html>