<!DOCTYPE html>
<html lang="en">
<head>
	<title>Tier 1 Create User Accounts</title>
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
			margin: 5% 5% 10% 7%;
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




<nav class="navbar navbar-default">
	<div class="container">
		<ul class="nav navbar-nav">
			<li><a href="/BankingApp/employee/Tier1Dashboard">Home</a></li>
			<ul class="nav navbar-nav navbar-right">
				<form action="/BankingApp/logout" method="post">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					<button type="submit" class="btn btn-default navbar-btn">Log out</button>
				</form>
			</ul>

		<ul class="nav navbar-nav navbar-right">
			<a href="/BankingApp/employee/Tier1Profile"><button type="button" class="btn btn-default navbar-btn">My Profile</button></a>

		</ul>
	</div>
</nav>


<div>
		<c:if test="${not empty error_msg}">
		     ${error_msg}
		</c:if>	
			
	</div>



<div class="container-fluid" id="loginBox">
	<div class="row">
		<div class="col-lg-14">
			<div class="jumbotron">

			<h3><strong>Create User Accounts</strong></h3>
			<form action="/BankingApp/employee/createAccounts" method="post">

					<div class="form-group">
						<label>User Email</label>
						<input type="text" class="form-control" name="username" placeholder="User Email" required>
					</div>

					<div class="form-group">
						<label>Checking Account</label>
								<select class="form-control" name="checking">
								  <option value="">--please select--</option>
								  <option value="yes">Yes</option>
								  <option value="no">No</option>
								</select>
							<br>
							<label>Savings Account</label>
								<select class="form-control" name="savings">
								  <option value="">--please select--</option>
								  <option value="yes">Yes</option>
								  <option value="no">No</option>
								</select>
							<br>
							<label>Credit Card</label>
								<select class="form-control" name="credit">
								  <option value="">--please select--</option>
								  <option value="yes">Yes</option>
								  <option value="no">No</option>
								</select>
					</div>
				   <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

					 <input type="submit" value="Create">

			</form>

			</div>
		</div>
	</div>
</div>







<script   src="https://code.jquery.com/jquery-3.2.1.js"   integrity="sha256-DZAnKJ/6XZ9si04Hgrsxu/8s717jcIzLy3oi35EouyE="   crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
</body>
</html>
