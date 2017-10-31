<!DOCTYPE html>
<html lang="en">
<head>
	<title>Change Password</title>
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
		function checkPassword(){

		var password=$('#newPassword').val();
		var filter = /^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\S+$).{5,20}$/;
		var confirm = $('#confirmPassword').val();
		if(!(password === confirm))
		{
			alert("Password Mismatch");
			return false;
		}
		// if(!filter.test(password))
		// {
		//
	  //   	alert("Please Enter a valid password");
		// 	return false;
		// }

		}
	</script>


</head>
<body>


<div class="jumbotron logo">
	<div class="container">
	  <h2 id="title">WELCOME TO SSGROUP10 BANK</h2>
	</div>
</div>

<div class="container" id="loginBox">
	<div class="row">
		<div class="col-lg-6">
			<div class="jumbotron">
			<h3>New Password</h3>
			  <form action="/BankingApp/changepassword" method="POST" id="submit">
				  <div class="form-group">
				    <label for="newPassword">Please Enter New Password</label>
				    <input type="password" class="form-control" id="newPassword" name="newPassword" placeholder="New Password" required>
				  </div>

				  <div>

				  	<ul>
				  		<li>Password length: 5 to 20</li>
				  		<li>Rules: number, lower case letter, upper case letter and special character(!@#$%^&+=)</li>
				  	</ul>
				  </div>

				  <div class="form-group">
				    <label for="confirmPassword">Please Confirm New Password</label>
				    <input type="password" class="form-control" name="confirmpassword" placeholder="Confirm Password" id ="confirmPassword" required>

				  </div>
					<input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
				  <button type="submit" class="btn btn-default" onClick="checkPassword()" id="submit">Submit</button>
			  </form>
			</div>
		</div>
	</div>
</div>


<script   src="https://code.jquery.com/jquery-3.2.1.js"   integrity="sha256-DZAnKJ/6XZ9si04Hgrsxu/8s717jcIzLy3oi35EouyE="   crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
</body>
</html>
