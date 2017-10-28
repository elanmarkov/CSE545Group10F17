<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<title>Login</title>
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
<!-- 
  	<script>
		window.onload = function() {
		  var recaptcha = document.forms["myForm"]["g-recaptcha-response"];
		  recaptcha.required = true;
		  recaptcha.oninvalid = function(e) {
		    alert("Please complete the captcha");
		  }
		}	
	</script>
 -->

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
			  <form name="myForm" action="<c:url value='j_spring_security_check' />" method="post">
				  <div class="form-group">
				    <label for="username">Username</label>
				    <input type="email" class="form-control" id="userEmail" name="username" placeholder="Username" required>
				  </div>

				  <div class="form-group">
			    	<label for="userPassword" >Password</label>
			    	<input type="password" class="form-control" id="userPassword" name="password" placeholder="Password" required>
			  	  </div>

			  	  <div class="form-group">
			    	<label for="role" >Role</label>
			    	<select class="form-control" name="roleSelection" required>
			    		<option value="ROLE_CUSTOMER">Individual Customer</option>
			    		<option value="ROLE_MERCHANT">Merchant</option>
			    		<option value="ROLE_REGULAR">Employee</option>
			    		<option value="ROLE_MANAGER">Manager</option>
			    		<option value="ROLE_ADMIN">Admin</option>
			    	</select>
			  	  </div>

			  	  <div>
			  	  		<!-- <div class="g-recaptcha" data-sitekey="6Lf84DQUAAAAAIbAPRxbmk2OiIfoYcKYZQ0TUU0H"></div>
			  	  		        <input type="hidden" name="recaptcha" data-rule-recaptcha="true">
 -->
			  	  		<br>
			  	  		<div id = "errorBox" class="form-group"></div>
			  	  		<input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
				  <button type="submit" class="btn btn-default" onClick ="checkEmail()" id="submit">Login</button>
				  
			  </form>

			</div>

			<br>

			<a href="/BankingApp/login/ForgotPassword">Forgot your password?</a>
		</div>
	</div>
</div>

<script src='https://www.google.com/recaptcha/api.js'></script>
<script   src="https://code.jquery.com/jquery-3.2.1.js"   integrity="sha256-DZAnKJ/6XZ9si04Hgrsxu/8s717jcIzLy3oi35EouyE="   crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
</body>
</html>