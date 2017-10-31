<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>Withdraw</title>
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
			<li><a href="/BankingApp/customer/dashboard">Home</a></li>
			<li class="dropdown">
				<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Transaction<span class="caret"></span></a>
				<ul class="dropdown-menu">
					<li><a href="/BankingApp/customer/deposit">Deposit</a></li>
					<li><a href="/BankingApp/customer/withdraw">Withdraw</a></li>
				</ul>
			</li>

			<li class="dropdown">
				<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Funds transfer<span class="caret"></span></a>
				<ul class="dropdown-menu">
					<li><a href="/BankingApp/customer/transferBetweenAccounts">Between accounts</a></li>
					<li><a href="/BankingApp/customer/transferToOthers">Send to others</a></li>
				</ul>
			</li>

			<li><a href="/BankingApp/customer/pendingRequests">Pending Request</a></li>
			<li><a href="/BankingApp/authorizeMerchant">Merchant Panel</a></li>
		</ul>

		<ul class="nav navbar-nav navbar-right">
			<form action="/BankingApp/logout" method="post">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				<button type="submit" class="btn btn-default navbar-btn">Log out</button>
			</form>
		</ul>

		<ul class="nav navbar-nav navbar-right">
			<a href="/BankingApp/customer/profile"><button type="button" class="btn btn-default navbar-btn">My Profile</button></a>
		</ul>
	</div>
</nav>

<div class="container">
	<div class="row">
		<div class="col-lg-6">
			<div class="jumbotron">
				<form action="/BankingApp/withdrawMoney" method="post">
					<div class="form-group">
						<label>Please select the account you would like to withdraw</label>
							<select class="form-control" name="accountNumber" required>
							  <!-- <option value="">--please select your account--</option> -->
								<c:choose>
	                        		<c:when test="${not empty savings}">
										<option value="${savings.accountNumber}">Savings: ${savings.accountNumber} - balance: ${savings.balance}</option>
									</c:when>
		  	                    </c:choose>

		  	                    <c:choose>
	                        		<c:when test="${not empty checking}">
								  	<option value="${checking.accountNumber}">Checking: ${checking.accountNumber} - balance: ${checking.balance}</option>
	                        		</c:when>
	                    		 </c:choose>
							</select>
					</div>
					<div class="form-group">
						<label>Please specify the amount you would like to withdraw</label>
						<input type="number" class="form-control" name="amount" placeholder="$" required>
					</div>

					<div class="form-group">
					 <button type="submit" class="btn btn-primary" value="Submit">Withdraw</button>
					</div>
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				</form>
		</div>


	</div>
			</div>
		</div>
	</div>
</div>



<script   src="https://code.jquery.com/jquery-3.2.1.js"   integrity="sha256-DZAnKJ/6XZ9si04Hgrsxu/8s717jcIzLy3oi35EouyE="   crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
</body>
</html>
