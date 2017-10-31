<!DOCTYPE html>
<html lang="en">
<head>
	<title>Admin Dashboard</title>
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

		#tabContent{
			margin: 5% 5% 10% 10%;
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


<div class="container">
	<div class="row">
		<div class="col-lg-6">
			<div class="jumbotron">
			  <h3>Create Internal User</h3>
			  <a href="/BankingApp/employee/RegistrationInternalEmployee"><button type="button" class="btn btn-default navbar-btn">Create</button></a>
			</div>
		</div>

		<div class="col-lg-6">
			<div class="jumbotron">
			  <h3>Pending Request</h3>
			  <a href="/BankingApp/employee/AdminPendingRequest"><button type="button" class="btn btn-default navbar-btn">View Details</button></a>
			</div>
		</div>
	</div>
</div>

<div class="container">
	<div class="row">
		<div class="col-lg-6">
			<div class="jumbotron">
			  <h3>System Logs</h3>
			  <a href="/BankingApp/employee/SystemLogs"><button type="button" class="btn btn-default navbar-btn">View Details</button></a>
			</div>
		</div>

		<div class="col-lg-6">
			<div class="jumbotron">
			  <h3>User Search</h3>
			  <a href="/BankingApp/employee/AdminSearchUser"><button type="button" class="btn btn-default navbar-btn">View Details</button></a>
			</div>
		</div>
	</div>
</div>






<script   src="https://code.jquery.com/jquery-3.2.1.js"   integrity="sha256-DZAnKJ/6XZ9si04Hgrsxu/8s717jcIzLy3oi35EouyE="   crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
</body>
</html>
