<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<title>Admin Pending Request Management</title>
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
		<div class="col-lg-16">
			<div class="jumbotron">
			<h3><strong>Pending Request Management</strong></h3>
				  <table class="table table-hover">
					    <thead>
					      <tr>
					        <th>Request ID</th>
					        <th>Internal User ID</th>
					        <th>New Address</th>
					        <th>State</th>
					        <th>City</th>
					        <th>Country</th>
					        <th>Zip Code</th>
					        <th>New Phone</th>
					        <th>Action</th>
					      </tr>
					    </thead>
					    <tbody>
        				<c:choose>
					    	<c:when test="${empty pending_list}">
                        			<tr>
                                    	<td colspan="7">No Pending Request</td>
                                	</tr>
                            </c:when>
                            <c:otherwise>
                        			<c:forEach items="${pending_list}" var="list">
                        					<tr>
                                    		<td>${list.id}</td>
                                    		<td>${list.userId}</td>
                                    		<td>${list.address}</td>
                                    		<td>${list.state}</td>
                                    		<td>${list.city}</td>
                                    		<td>${list.country}</td>
                                    		<td>${list.zipcode}</td>
                                    		<td>${list.phone}</td>
											<td>
												<form action = "/BankingApp/employee/adminPendingRequest" method = "post">
		                                    		<input type="hidden" name="requestID" value="${list.id}">
 		                                    		<input type="hidden" name="userId" value="${list.userId}">
		                                    		<select id="requestType" name="requestDecision" required>
				       									<option value="">Please Select</option>
				          								<option value="approve">Approve</option>
				          								<option value="reject">Reject</option>
				       								</select>
       												<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

		                                    		<button type="submit" class="btn btn-xs btn-default">Submit</button>
		                                   		</form>
											</td>
                                		</tr>
                            		</c:forEach>
                        		</c:otherwise>
                        	</c:choose>
					    </tbody>
					  </table>
			</div>
			</div>
		</div>
	</div>
</div>


<script   src="https://code.jquery.com/jquery-3.2.1.js"   integrity="sha256-DZAnKJ/6XZ9si04Hgrsxu/8s717jcIzLy3oi35EouyE="   crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
</body>
</html>
