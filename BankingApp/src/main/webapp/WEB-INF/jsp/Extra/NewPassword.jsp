<!DOCTYPE html>
<html lang="en">
<head>
	<title>New Password</title>
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
			margin: 5% 5% 35% 35%;
		}

		.hidden{
			visibility: hidden;
		}
	</style>

	<!-- <script>  

    $(document).ready(function(){
    $('#submit').click(function(){
    var userEmail=$('#userEmail').val();  
    var filter = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
    if(!filter.test(userEmail))
    {
      $("#userEmail").focus();

      // $("#errorBox").html("Please Enter a Valid Email Address");
      return false;
    }
    
    });
  	});
  	</script>-->


</head>
<body>


<div class="jumbotron logo">
	<div class="container">
	  <h1 id="title">WELCOME TO SSGROUP10 BANK</h1>
	</div>	
</div>

<div class="alert alert-warning hidden" id="errorBox">
    <a href="#" class="close" data-dismiss="alert">&times;</a>
    <strong>Warning!</strong> Please Enter a Valid Email Address.
</div>

<div class="container" id="loginBox">
	<div class="row">
		<div class="col-lg-4">
			<div class="jumbotron">
			<h2>New Password</h2>
			  <form>
				  <div class="form-group">
				    <label for="newPassword">Please Enter New Password</label>
				    <input type="password" class="form-control" id="newPassword" placeholder="New Password" required>
				  </div>

				  <div>
				  	<ul>
				  		<li>The password length is 10</li>
				  		<li>Please contain at least one number</li>
				  		<li>Please contain at least one letter</li>
				  		<li>Please contain at least one special character(!@#$%^&*)</li>
				  	</ul>
				  </div>

				  <div class="form-group">
				    <label for="confirmPassword">Please Confirm New Password</label>
				    <input type="password" class="form-control" id="confirmPassword" placeholder="Confirm Password" required>
				  </div>

				  <button type="submit" class="btn btn-default" id="submit">Submit</button>
			  </form>
			</div>
		</div>
	</div>
</div>


<script   src="https://code.jquery.com/jquery-3.2.1.js"   integrity="sha256-DZAnKJ/6XZ9si04Hgrsxu/8s717jcIzLy3oi35EouyE="   crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
</body>
</html>