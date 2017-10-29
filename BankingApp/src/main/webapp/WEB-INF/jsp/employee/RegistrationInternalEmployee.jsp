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
	<script src="/lib/jquery.min.js"></script>
	<script src="/lib/jquery.plugin.js"></script>

	<div class="jumbotron logo">
		<div class="container">
		  <h2 id="title">GROUP10 BANK</h2>
		</div>
	</div>
	<br>


<nav class="navbar navbar-default">
	<div class="container">
		<ul class="nav navbar-nav">
			<li><a href="/BankingApp/employee/AdminDashboard">Home</a></li>
        	<!-- <li class="dropdown">
	          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Create User<span class="caret"></span></a>
	          <ul class="dropdown-menu">
	            <li><a href="/BankingApp/employee/RegistrationInternalEmployee">Internal</a></li>
	            <li><a href="/BankingApp/employee/RegistrationExternalEmployer">External</a></li>
	          </ul>
        	</li>

        	<li><a href="/BankingApp/employee/PendingRequestManagement">Pending Request</a></li>
		</ul> -->


		<ul class="nav navbar-nav navbar-right">
			<a href="/BankingApp/employee/AdminUserDetails"><button type="button" class="btn btn-default navbar-btn">My Profile</button></a>
			<a href="/BankingApp/login/logout"><button type="button" class="btn btn-default navbar-btn">Log out</button></a>
		
		</ul>
	</div>
</nav>


	<div class="container">
		<div class="row">
			<div class="col-lg-10">
				<div class="panel-heading">
	    				<h3 class="panel-title"><strong>Registration</strong></h3>
	 				 </div>
				<div class="jumbotron">
			  <form action="/BankingApp/employee/internalreg" method="POST">


					<div class="form-group">
					    <input type="hidden" class="form-control" id="id" name ="id" value="0">
					  </div>



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
							        <select class="form-control" name="role" id="role" required>
							          	<option value="ROLE_REGULAR">Employee</option>
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
					    <label for="country">Country</label>
					    <input type="text" class="form-control" id="country" name="country" placeholder="Country" required>
					  </div>

					  <div class="form-group">
					    <label for="zipcode">Zip Code</label>
					    <input type="text" class="form-control" id="zipcode" name="zipcode" placeholder="Zip Code" required>
					  </div>

					  <div class="form-group">
					    <label for="phone">Phone Number</label>
					    <input type="text" class="form-control" id="phone" name="phone" placeholder="Phone Number" required>
					  </div>

					  <div class="form-group">
					    <label for="dob">Date of Birth</label>
					    <input type="text" class="form-control" id="dob" name="dob" placeholder="mm/dd/yyyy" required>
					  </div>

					  <div class="form-group">
					    <label for="ssn">SSN</label>
					    <input type="text" class="form-control" id="ssn" name="ssn" placeholder="XXX-XX-XXXX" required>
					  </div>

						<div id = "errorBox" class="form-group"></div>
					  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>


					  <button type="submit" class="btn btn-default" id="submitForm" onclick="Validate()" value="Submit">Submit</button>

			  </form>

				</div>
			</div>
		</div>
	</div>

	<script language="JavaScript" type="text/javascript">
	    function Validate() {
	    	$("#errorBox").html("");
	            var name=$('#firstName').val();
	            var city = $('#city').val();
	            var state = $('#state').val();
	            var pincode = $('#pincode').val();
	            var userEmail=$('#email').val();
	            var ssn = $('#ssn').val();
	            var phone = $('#phone').val();
	            var address = $('#address').val();
	            var dateOfBirth = $('#dateOfBirth').val();
	            filter = /^[A-z]+$/;
	            if(!filter.test(name))
	            {
	                $("#firstName").borderColor="red";
	                window.setTimeout(function () {
	   					 document.getElementById('firstName').focus();
					}, 0);
	                $("#errorBox").html("Name can contain only alphabets");
	                return false;
	            }
	            var filter = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
	            if(!filter.test(userEmail))
	            {
	                $("#email").focus();
	                $("#errorBox").html("Please Enter a Valid Email Address");
	                return false;
	            }
	            filterCity = /^[A-z]+$/;
	            if(!filterCity.test(city))
	            {
	                $("#city").borderColor="red";
	                $("#errorBox").html("City can contain only alphabets");
	                return false;
	            }
	            filterState = /^[A-z]+$/;
	            if(!filterState.test(state))
	            {
	                $("#state").borderColor="red";
	                $("#errorBox").html("State can contain only alphabets");
	                return false;
	            }

	            var filter1 =  /^[0-9]+$/;
	            if(!filter1.test(pincode))
	            {
	                $("#pincode").focus();
	                $("#errorBox").html("Zipcode can contain only numbers");
	                return false;
	            }
	            if(!filter1.test(phone))
	            {
	                $("#phone").focus();
	                $("#errorBox").html("Please Enter a Valid phone");
	                return false;
	            }
	            var filterdob =  /^\d{2}\/\d{2}\/\d{4}$/;
	            if(!filterdob.test(dateOfBirth))
	            {
	                $("#dateOfBirth").focus();
	                $("#errorBox").html("Please Enter a Valid date of birth");
	                return false;
	            }

	            var filterssn =  /^[0-9]{3}\-?[0-9]{2}\-?[0-9]{4}$/;
	            if(!filterssn.test(ssn))
	            {
	                $("#ssn").focus();
	                $("#errorBox").html("Please Enter a Valid SSN");
	                return false;
	            };
	    }
	</script>
	<script   src="https://code.jquery.com/jquery-3.2.1.js"   integrity="sha256-DZAnKJ/6XZ9si04Hgrsxu/8s717jcIzLy3oi35EouyE="   crossorigin="anonymous"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
	</body>
	</html>
