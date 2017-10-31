<!DOCTYPE html>
<html lang="en"> 
<head>
	<title>User Management</title>
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
			margin: 5% 5% 10% 8%;
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
			<li><a href="#">Home</a></li>
        	<li class="dropdown">
	          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Create User<span class="caret"></span></a>
	          <ul class="dropdown-menu">
	            <li><a href="#">Internal</a></li>
	            <li><a href="#">External</a></li>
	          </ul>
        	</li>

        	<li><a href="#">Pending Request</a></li>
		</ul>


		<ul class="nav navbar-nav navbar-right">
			<a href="#"><button type="button" class="btn btn-default navbar-btn">My Profile</button></a>
			<a href="#"><button type="button" class="btn btn-default navbar-btn">Log out</button></a>
		</ul>
	</div>
</nav>


<div class="container-fluid" id="loginBox">
	<div class="row">
		<div class="col-lg-14">
			<div class="jumbotron">
			<h3><strong>User Management</strong></h3>

			<form action="/BankingApp" method="post">
					<div class="form-group">
						<label>Customer ID</label>
						<input type="number" class="form-control" name="customer.id" placeholder="Customer ID">
					</div>

					<div class="form-group">
						<label>Request Type</label>
						<select id="requestType" name="requestType" required>
							<option value="">Select Type</option>
							<option value="account">Account Details</option>
							<option value="transaction">Transaction Access</option>
							<option value="registration">New Registration</option>
						</select>
					</div>

					<div class="form-group">
					 	<button type="submit" class="btn btn-primary" name="submit">Submit</button>
					</div>
			</form>
			</div>
		</div>
	</div>


	<div class="row">
		<div class="col-lg-14">
			<div class="jumbotron">
			<h3><strong>Pending Authorization</strong></h3>			
				<table class="table table-hover">
					    <thead>
					      <tr>
                               <th>External User</th>
                               <th>Authorization Type</th>
					      </tr>
					    </thead>
					    
					      <tbody>
        					<tr>
	                    		<td>${authorization.external_userID}</td>
                    			<td>${authorization.auth_Type}</td>
	                		</tr>
                        				
	                </tbody>
				  </table>
			
			
			

			</div>
		</div>
	</div>



	<div class="row">
		<div class="col-lg-14">
			<div class="jumbotron">
			<h3><strong>Completed Authorization</strong></h3>			
				<table class="table table-hover">
					    <thead>
					      <tr>
                               <th>External User</th>
                               <th>Authorization Type</th>
                               <th>Action</th>
					      </tr>
					    </thead>
					    
					      <tbody>
        					<tr>
	                    		<td>${authorization.external_userID}</td>
                    			<td>${authorization.auth_Type}</td>
								<td>
	                        		<form action = "viewaccountdetails" method = "post" style="display:inline">
                                		<input type="hidden" name="extUserID" value="${authorization.external_userID}">
                                		<input type="hidden" name="userType" value="external">
                                		<button type="submit" class="btn btn-xs btn-primary">View Account</button>
                                	</form>
                                	<form action = "viewtransaction" method = "post" style="display:inline">
                                		<input type="hidden" name="extUserID" value="${authorization.external_userID}">
                                		<input type="hidden" name="userType" value="external">                      
                                		<button type="submit" class="btn btn-xs btn-primary">View Transactions</button>
                                	</form>
								</td>
	                		</tr>
                        				
	                </tbody>
				  </table>
			
			

			</div>
		</div>
	</div>


</div>








<script   src="https://code.jquery.com/jquery-3.2.1.js"   integrity="sha256-DZAnKJ/6XZ9si04Hgrsxu/8s717jcIzLy3oi35EouyE="   crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
</body>
</html>