<!DOCTYPE html>
<html lang="en">
<head>
	<title>My Profile</title>
	<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<!-- <link rel="stylesheet" type="text/css" href="login.css"> -->

	<style  type="text/css">
		.logo{

		margin-bottom: 0px;
		}
		#title{
			/*color: #FAEBD7;*/
			margin: auto;
			text-align: center;
		}
		#loginBox{
			margin: 5% 5% 10% 8%;
		}
		.hidden{
			visibility: hidden;
		}


	</style>


</head>
</body>

<div class="jumbotron logo">
	<div class="container">
	  <h2 id="title">GROUP10 BANK</h2>
	</div>
</div>


<nav class="navbar navbar-default">
	<div class="container">
		<ul class="nav navbar-nav">
			<li><a href="/BankingApp/employee/AdminDashboard">Home</a></li>

			<ul class="nav navbar-nav navbar-right">
				<form action="/BankingApp/logout" method="post">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					<button type="submit" class="btn btn-default navbar-btn">Log out</button>
				</form>
			</ul>

		<ul class="nav navbar-nav navbar-right">
			<a href="/BankingApp/employee/AdminProfile"><button type="button" class="btn btn-default navbar-btn">My Profile</button></a>

		</ul>
	</div>
</nav>


<div class="container-fluid" id="loginBox">
	<div class="row">
		<div class="col-lg-14">
			<div class="jumbotron">
			<h3><strong>User Details</strong></h3>

				  <table class="table table-hover">
					    <thead>
					      <tr>
					          <th>User ID</th>
						      <th>Name</th>
						      <th>Role</th>
						      <th>Email</th>
						      <th>Phone</th>
						      <th>Address</th>
					      </tr>
					    </thead>

					      <tbody>
        					<tr>
	                    		<td>${user.id}</td>
                    			<td>${user.name}</td>
                    			<td>${user.role}</td>
                    			<td>${user.email}</td>
                    			<td>${user.phone}</td>
                    			<td>${user.address}</td>
	                		</tr>
                        </tbody>
					  </table>
				<a href="#modifyaccount" class="btn btn-default"  data-toggle="modal">Modify Account</a>
				<a href="/BankingApp/login/ChangePassword" class="btn btn-default">Change Password</a>

			</div>
		</div>
	</div>

	<div class="row">
		<div class="col-lg-14">
			<div class="jumbotron">
			<h3><strong>PII Information</strong></h3>
				<table class="table table-hover">
					    <thead>
					      <tr>
					           <th>SSN</th>
                               <th>Date of Birth</th>
					      </tr>
					    </thead>

					      <tbody>
        					<tr>
	                    		<td>${pii.ssn}</td>
                    			<td>${pii.dob}</td>

	                		</tr>

	                </tbody>
				  </table>



			</div>
		</div>
	</div>

</div>

<div class="modal fade" id="modifyaccount" role="dialog">
    <div class="modal-dialog">

      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title"><strong>Account Info</strong></h4>
        </div>
        <div class="modal-body">
          <form action="/BankingApp/employee/AdminModify" method="POST">
          			 <div class="form-group">
					    <input type="hidden" class="form-control" id="id" name="id" value="${user.id}" required>
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
					    <label for="pincode">Zip Code</label>
					    <input type="text" class="form-control" id="pincode" name="zipcode" placeholder="Zip Code" required>
					  </div>

					  <div class="form-group">
					    <label for="country">Country</label>
					    <input type="text" class="form-control" id="country" name="country" placeholder="Country" required>
					  </div>

					  <div class="form-group">
					    <label for="phone">Phone Number</label>
					    <input type="text" class="form-control" id="phone" name="phone" placeholder="Phone Number" required>
					  </div>

						<div id = "errorBox" class="form-group"></div>
					  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

					  <button type="submit" class="btn btn-default" id="submitForm" onclick="Validate()" value="Submit">Submit</button>


			  </form>

        </div>
        <div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal" style="float:right">Close</button>
        </div>
      </div>

    </div>

</div>


<!-- <div class="modal fade" id="changepassword" role="dialog">
    <div class="modal-dialog">

      <!-- Modal content-->
      <!-- <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title"><strong>Change Password</strong></h4>
        </div>
        <div class="modal-body">
          <form action="/BankingApp/employee/internalreg" method="POST">

					  <div class="form-group">
					    <label for="oldPassward">Old Password</label>
					    <input type="password" class="form-control" id="oldPassward" name="oldPassward" placeholder="Old Password" required>
					  </div>

					  <div class="form-group">
					    <label for="newPassward">New Password</label>
					    <input type="password" class="form-control" id="newPassward" name="newPassward" placeholder="New Password" required>
					  </div>

					  <div class="form-group">
					    <label for="confirmPassward">Confirm Password</label>
					    <input type="password" class="form-control" id="confirmPassward" name="confirmPassward" placeholder="Confirm Password" required>
					  </div>

						<div id = "errorBox" class="form-group"></div>
					  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					  <button type="submit" class="btn btn-default" id="submitForm" onclick="Validate()" value="Submit">Submit</button>

			  </form>
        </div>
    	<div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>

        </div>
      </div>

    </div> -->
 -->

<!-- </div> -->

<!-- there was a </main> here -->

<script language="JavaScript" type="text/javascript">
    function Validate() {
    	$("#errorBox").html("");
            var phone = $('#newValue').val();
            var modifyaccount = document.getElementById("modifyaccountDropdown");
            var selectedValue = modifyaccount.selectedIndex;
            if(selectedIndex == 1)  {
            var filter1 =  /^[0-9]+$/;
            $("#errorBox").html(selectedValue);
            if(!filter1.test(phone))
            {
                $("#newValue").focus();
                $("#errorBox").html("Please Enter a Valid phone");
                return false;
            }
        }
        ;
    }
</script>

<script   src="https://code.jquery.com/jquery-3.2.1.js"   integrity="sha256-DZAnKJ/6XZ9si04Hgrsxu/8s717jcIzLy3oi35EouyE="   crossorigin="anonymous"></script>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>


</body>
</html>
