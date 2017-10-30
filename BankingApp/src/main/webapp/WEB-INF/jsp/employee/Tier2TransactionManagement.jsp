<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en"> 
<head>
	<title>Tier 2 Transaction Management</title>
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
			<li><a href="/BankingApp/employee/Tier2Dashboard">Home</a></li>
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
			<a href="/BankingApp/employee/Tier2Profile"><button type="button" class="btn btn-default navbar-btn">My Profile</button></a>
			<a href="/BankingApp/login/Logout"><button type="button" class="btn btn-default navbar-btn">Log out</button></a>

		</ul>
	</div>
</nav>



<div class="container-fluid" id="loginBox">
	<div class="row">
		<div class="col-lg-14">
			<div class="jumbotron">
			<h3><strong>Transaction List${requestDecision}</strong></h3>

				  <table class="table table-hover">
					    <thead>
					      <tr>
					        <th>Transaction ID</th>
							<th>Sender</th>
							<th>Receiver</th>
							<th>Amount</th>
							<th>Description</th>
							<th>Action</th>
					      </tr>
					    </thead>

					      <tbody>
					      	<c:choose>
					    	<c:when test="${empty transaction_list}">
                        			<tr>
                                    	<td colspan="7">No Pending Transaction</td>
                                	</tr>
                            </c:when>
                            <c:otherwise>
					      	<c:forEach items="${transaction_list}" var="transaction">
        					<tr>
	                    		<td>${transaction.id}</td>
								<td>${transaction.fromAccountID}</td>
								<td>${transaction.toAccountID}</td>
								<td>${transaction.amount}</td>
								<td>${transaction.description}</td>
								<td>
	                        		<form action = "/BankingApp/tier2/transactionReview" method = "post">
	                        			<div class="form-group">
											<input type="hidden" name="transactionID" value="${transaction.id}">
                                    		<select id="requestDecision" name="requestDecision" style="float" required>
		       									<option value="">Please Select</option>
		          								<option value="approve">Approve</option>
		          								<option value="reject">Reject</option>
		       								</select>
                                    		<button type="submit" class="btn btn-xs btn-default">Submit</button>
										</div>
										<input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
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

	<div class="row">
		<div class="col-lg-14">
			<div class="jumbotron">
			<h3><strong>Add New Transaction</strong></h3>

			<form action="/BankingApp/tier2/transactionNew" method="post">

					<div class="form-group">
						<label>Sender Account</label>
						<input type="number" class="form-control" name="senderAccountNumber" placeholder="Sender Account Number" required>
					</div>


					<div class="form-group">
						<label>Receiver Account</label>
						<input type="number" class="form-control" name="receiverAccountNumber" placeholder="Receiver Account Number" required>
					</div>

					<div class="form-group">
					    <label>Please Enter the Amount</label>
					    <input type="number" class="form-control" placeholder="$" name="amountToAdd" required>
				  	</div>
					 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

					<div class="form-group">
					 <button type="submit" class="btn btn-primary" name="submit">Add</button>
					</div>
				</form>

			</div>
		</div>
	</div>


</div>





<script   src="https://code.jquery.com/jquery-3.2.1.js"   integrity="sha256-DZAnKJ/6XZ9si04Hgrsxu/8s717jcIzLy3oi35EouyE="   crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
</body>
</html>
