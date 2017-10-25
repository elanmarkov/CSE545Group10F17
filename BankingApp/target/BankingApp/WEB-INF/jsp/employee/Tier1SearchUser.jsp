<!DOCTYPE html>
	<html lang="en">
	<head>
		<title>Tier 1 Search</title>
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
			<li><a href="/BankingApp/employee/Tier1Dashboard">Home</a></li>
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
			<a href="/BankingApp/employee/UserDetailsEmployee"><button type="button" class="btn btn-default navbar-btn">My Profile</button></a>
			<a href="/BankingApp/logout"><button type="button" class="btn btn-default navbar-btn">Log out</button></a>
		
		</ul>
	</div>
</nav>


<div class="container" id="loginBox">
	<div class="row">
		<div class="col-lg-16">
			<div class="jumbotron">		
				<h3><strong>Search Customer</strong></h3>

				<form class="form-margin" action = "/BankingApp/tier1/SearchExternalUser" method = "post">
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
                            				<form action = "/BankingApp/tier1/showExternalAccount" method = "post" style="display:inline">
	                                    		<input type="hidden" name="customerID" value="${customerObj.id}">
	                                    		<button type="submit" class="btn btn-sm btn-primary">Show Account</button>
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