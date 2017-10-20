<!DOCTYPE html>
<html>
<head>
	<title>Registration</title>
	<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
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
			margin: 5% 5% 30% 30%;
		}

		.hidden{
			visibility: hidden;
		}
	</style>




</head>
<body>


<div class="jumbotron logo">
	<div class="container">
	  <h2 id="title">GROUP10 BANK</h2>
	</div>	
</div>

<br>

<div class="container">
	<div class="row">
		<div class="col-lg-10">
			<div class="panel-heading">
    				<h3 class="panel-title"><strong>Registration</strong></h3>
 				 </div>
			<div class="jumbotron">
			  <form action="/BankingApp/employee/internalreg" method="POST">
			  	<fieldset>
			  		
				  <div class="form-group">
				    <label for="firstName">Name</label>
				    <input type="text" class="form-control" id="firstName" name ="name" placeholder="Last Name, First Name" required>
				  </div>

				  <div class="form-group">
						<label for="email">Email</label>	      
						<input type="email" class="form-control" name="email" id="email" placeholder="Email" required>	
				  </div>

				  <div class="form-group">
						<label for="select">Role</label>
						        <select class="form-control" name="designation" id="designation" required>
						          <option value="">Select Option</option>
						          <option value="ROLE_REGULAR">Regular</option>
						          <option value="ROLE_MANAGER">Manager</option>
						          <option value="ROLE_ADMIN">Admin</option>
						        </select>
				  </div>

				  <div class="form-group">
				    <label for="address">Address</label>
				    <input type="text" class="form-control" id="address" name="address" placeholder="Address" required>
				  </div>

				  <div class="form-group">
				    <label for="city">City</label>
				    <input type="text" class="form-control" id="city" name="city" placeholder="City" required>
				  </div>

				  <div class="form-group">
				    <label for="state">State</label>
				    <input type="text" class="form-control" id="state" name="state" placeholder="State" required>
				  </div>

				  <div class="form-group">
				    <label for="pincode">Address</label>
				    <input type="text" class="form-control" id="pincode" name="pincode" placeholder="Zip Code" required>
				  </div>
				  
				  <div class="form-group">
				    <label for="phone">Phone Number</label>
				    <input type="text" class="form-control" id="phone" name="phone" placeholder="Phone Number" required>
				  </div>

				  <div class="form-group">
				    <label for="dateOfBirth">Date of Birth</label>
				    <input type="text" class="form-control" id="dateOfBirth" name="DoB" placeholder="mm/dd/yyyy" required>
				  </div>

				  <div class="form-group">
				    <label for="ssn">SSN</label>
				    <input type="text" class="form-control" id="ssn" name="SSN" placeholder="XXX-XX-XXXX" required>
				  </div>
				  
				   <div class="form-group">
				    <label for="userName">User Name</label>
				    <input type="text" class="form-control" id="userName" name="userName" placeholder="User Name" required>
				  </div>

				  <button type="submit" class="btn btn-default" id="submitForm" value="Submit">Submit</button>
				  </fieldset>
			  </form>
			</div>
		</div>
	</div>
</div>


<script   src="https://code.jquery.com/jquery-3.2.1.js"   integrity="sha256-DZAnKJ/6XZ9si04Hgrsxu/8s717jcIzLy3oi35EouyE="   crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
</body>
</html>