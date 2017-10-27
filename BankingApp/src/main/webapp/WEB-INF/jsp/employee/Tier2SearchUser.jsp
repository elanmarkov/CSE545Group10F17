<!DOCTYPE html>
	<html lang="en">
	<head>
		<title>Tier 2 Search</title>
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
				margin: 5% 5% 5% 10%;
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
			<li><a href="/BankingApp/employee/Tier2Dashboard">Home</a></li>
        	

		<ul class="nav navbar-nav navbar-right">
			<a href="/BankingApp/employee/Tier2Profile"><button type="button" class="btn btn-default navbar-btn">My Profile</button></a>
			<a href="/BankingApp/logout"><button type="button" class="btn btn-default navbar-btn">Log out</button></a>

		</ul>
	</div>
</nav>



<div class="container" id="loginBox">
	<div class="row">
		<div class="col-lg-16">
			<div class="jumbotron">		
				<h3><strong>Search Employee</strong></h3>
					<form class="form-margin" action = "/BankingApp/tier2/searchInternalUser" method = "post">
		            	<div>
		            		<input class="form-control" type="text" name="employeeID" placeholder="Employee ID" required>
		            	</div>
		            	<br>
			       		<button type="submit" class="btn btn-sm btn-primary">Search Employee</button>
				     </form>

	
					  <table class="table table-hover">
						    <thead>
						      <tr>
						        <th>Employee ID</th>
						        <th>Employee Email</th>
						        <th>Action</th>
						      </tr>
						    </thead>
						    <tbody>
						    	<c:choose>
	                        		<c:when test="${empty employeeObj}">
	                        			<tr>
	                                    	<td colspan="3">No Results</td>
	                                	</tr>
	                        		</c:when>

	                        		<c:otherwise>
                                		<tr>
                                			<td>${employeeObj.id}</td>
                                			<td>${employeeObj.email}</td>
                                			<td>
                                				<form action = "/BankingApp/tier2/showInternalAccount" method = "post" style="display:inline">
		                                    		<input type="hidden" name="employeeID" value="${employeeObj.id}">
		                                    		<button type="submit" class="btn btn-sm btn-primary">Show Account</button>
		                                    	</form>
                                			</td>
                                		</tr>
	                        		</c:otherwise>
	                        	</c:choose>
						    </tbody>
						  </table>
				  </form>
				</div>
			</div>
		</div>
</div>

<div class="container" id="loginBox">
	<div class="row">
		<div class="col-lg-16">
			<div class="jumbotron">		
				<h3><strong>Search Customer</strong></h3>

				<form class="form-margin" action = "/BankingApp/tier2/searchExternalUser" method = "post">
					<div>
	            		<input class="form-control" type="text" name="customerID" placeholder="Customer ID">
	            	</div>
	            	<br>
		       		<button type="submit" class="btn btn-sm btn-primary">Search Customer</button>
	            </form>
	            <br>
	                    <table id="content-table" class="table table-hover">
	                        <thead>
	                            <tr>
	                                <th>Customer ID</th>
	                                <th>Customer Email</th>
	                                <th>Action</th>
	                            </tr>
	                        </thead>
	                        <tbody>
						    	<c:choose>
	                        		<c:when test="${empty employeeObj}">
	                        			<tr>
	                                    	<td colspan="3">No Results</td>
	                                	</tr>
	                        		</c:when>

	                        		<c:otherwise>
                                		<tr>
                                			<td>${customerObj.id}</td>
                                			<td>${customerObj.email}</td>
                                			<td>
                                				<form action = "/BankingApp/tier2/showExternalAccount" method = "post" style="display:inline">
		                                    		<input type="hidden" name="customerID" value="${customerObj.id}">
		                                    		<button type="submit" class="btn btn-sm btn-primary">Show Account</button>
		                                    	</form>
		                                    	<form action = "/BankingApp/tier2/deleteExternalUser" method = "post" style="display:inline">
		                                    		<input type="hidden" name="customerID" value="${customerObj.id}">
		                                    		<button type="submit" class="btn btn-sm btn-primary">Delete Account</button>
		                                    	</form>
                                			</td>
                                		</tr>
	                        		</c:otherwise>
	                        	</c:choose>

						    			      
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