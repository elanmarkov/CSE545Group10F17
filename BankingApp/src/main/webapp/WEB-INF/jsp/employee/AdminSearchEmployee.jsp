<!DOCTYPE html>
	<html lang="en">
	<head>
		<title>Search Employee</title>
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


	<div class="container" id="loginBox">
		<h3><strong>Search Employee</strong></h3>
		<form class="form-margin" action = "/BankingApp/searchInternalUser" method = "post">
	            	<div>
	            		<input class="form-control" type="text" name="employeeID" placeholder="Employee ID" required>
	            	</div>
	            	<br>
		       		<button type="submit" class="btn btn-sm btn-primary">Search Employee</button>
	            </form>
		<div class="row">
			<div class="col-lg-12">
				<div class="panel-body no-padding" style="overflow-y: scroll; max-height:400px;">
				
				  <form>
					  <table class="table table-hover">
						    <thead>
						      <tr>
						        <th class="active">Employee ID</th>
						        <th class="active">Employee Email</th>
						        <th class="active">Action</th>
						       
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
	                                				<form action = "/BankingApp/showAccountDetails" method = "post" style="display:inline">
			                                    		
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

	<br><br>	
	<h3>Search Customer</h3>

	<form class="form-margin" action = "/BankingApp/SearchExternalUser" method = "post">
	            	<div class="panel-body no-padding" style="overflow-y: scroll; max-height:400px;">
	            		<input class="form-control" type="text" name="customerID" placeholder="Customer ID">
	            	</div>
	            	<br>

		       		<button type="submit" class="btn btn-sm btn-primary">Search Customer</button>
	            </form>

		<div class="row">
	                        <div class="col-lg-12">
	                
	                <div class="panel-body no-padding" style="overflow-y: scroll; max-height:400px;">
	                    <table id="content-table" class="table table-hover">
	                        <thead>
	                            <tr>
	                                <th class="active">Customer ID</th>
	                                <th class="active">Customer Email</th>
	                                <th class="active">Action</th>
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
	                                				<form action = "/BankingApp/showAccountDetails" method = "post" style="display:inline">
			                             
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