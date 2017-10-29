<!DOCTYPE html>
<html lang="en">
<head>
	<title>Forget Password</title>
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
			margin: 5% 5% 25% 30%;
		}

		.hidden{
			visibility: hidden;
		}
	</style>


</head>
<body>

	<script language="JavaScript" type="text/javascript"> 
    function checkEmail(){
    var userEmail = $('#recoveryEmail').val();  
    console.log(userEmail);
    var filter = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
            if(!filter.test(userEmail))
            {
                $("#errorBox").html("Please Enter a Valid Email Address");
                return false;
            }
  	};
  	</script>


<div class="jumbotron logo">
	<div class="container">
	  <h2 id="title">WELCOME TO GROUP10 BANK</h2>
	</div>	
</div>


<div class="container" id="loginBox">
	<div class="row">
		<div class="col-lg-6">
			<div class="jumbotron">
			
			  <form action="BankingApp/login/forgotpassword/verifyemail" method="post">
				  <div class="form-group">
				    <label for="recoveryEmail">Please Enter Email for Password Recovery</label>
				    <input type="email" class="form-control" name="recoveryEmail" placeholder="Recovery Email" required>
				  </div>

				  <div id = "errorBox" class="form-group"></div>
				  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				  <button type="submit" class="btn btn-default" onclick="checkEmail()">Submit</button>
			  </form>
			</div>
		</div>
	</div>
</div>


<script   src="https://code.jquery.com/jquery-3.2.1.js"   integrity="sha256-DZAnKJ/6XZ9si04Hgrsxu/8s717jcIzLy3oi35EouyE="   crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
</body>
</html>