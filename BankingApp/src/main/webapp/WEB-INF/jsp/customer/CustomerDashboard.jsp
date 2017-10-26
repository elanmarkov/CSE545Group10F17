<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

	<!DOCTYPE html>
	<html lang="en">
	<head>
		<title>Customer Dashboard</title>
		<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
		<style type="text/css">
		.logo{

			margin-bottom: 0px;
		}

		#title{
			margin: auto;
			text-align: center;
		}

		#tabContent{
			margin: 5% 5% 5% 5%;
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
					<a href="#"><button type="button" class="btn btn-default navbar-btn">My Profile</button></a>
					<a href="#"><button type="button" class="btn btn-default navbar-btn">Log out</button></a>
				</ul>
			</div>
		</nav>



<div class="container-fluid float-left" id="tabContent">
	<div class="row">
		<div class="col-lg-17">
			<div class="jumbotron float-left">

				<ul class="nav nav-tabs">
					<li class="active"><a data-toggle="tab" href="#checkingAccount">Checking Account</a></li>
					<li><a data-toggle="tab" href="#savingAccount">Savings Account</a></li>
					<li><a data-toggle="tab" href="#creditCard">Credit Card</a></li>
				</ul>

				<div class="tab-content">
					<div id="checkingAccount" class="tab-pane fade in active">
						<div>
							<p><strong><h4>Checking Account Number: <span>${checkingAccount.accountNumber}</span></h4></strong></p>
							<p><strong><h4>Current Balance: <span>${checkingAccount.balance}</span></h4></strong></p>
						</div>

						<div>
							<p><h4>Last 5 transactions</h4></p>
							<table class="table table-hover">
								<thead>
									<tr>
										<th>#</th>
										<th>Description</th>
										<th>Transaction Type</th>
										<th>Payee</th>
										<th>Amount</th>
										<th>Status</th>
										<th>Date</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="transaction" items="${checkingList}" varStatus="loop">
										<tr>
											<th scope="row">${loop.index + 1}</th>
											<td>${transaction.description}</td>
											<td>${transaction.transactionType}</td>
											<td>${transaction.payee_id}</td>
											<td>${transaction.amount}</td>
											<td>${transaction.status}</td>
											<td>${transaction.timestamp_updated}</td>
										</tr>
									</c:forEach>



								</tbody>
								</table>
							<a href="/BankingApp/showCheckingTransactions"><button type="button" class="btn btn-default">View Details</button></a>
						</div>
				</div>

	<div id="savingAccount" class="tab-pane fade">
		<p><strong><h4>Saving Account Number: <span>${savingAccount.accountNumber}</span></h4></strong></p>
		<p><strong><h4>Current Balance: <span>${savingAccount.balance}</span></h4></strong></p>
		<div>
			<p><h4>Last 5 transactions</h4></p>
			<table class="table table-hover">
				<thead>
					<tr>
						<th>#</th>
						<th>Description</th>
						<th>Transaction Type</th>
						<th>Payee</th>
						<th>Amount</th>
						<th>Status</th>
						<th>Date</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="transaction" items="${savingsList}" varStatus="loop">
						<tr>
							<th scope="row">${loop.index + 1}</th>
							<td>${transaction.description}</td>
							<td>${transaction.transactionType}</td>
							<td>${transaction.payee_id}</td>
							<td>${transaction.amount}</td>
							<td>${transaction.status}</td>
							<td>${transaction.timestamp_updated}</td>
						</tr>
					</c:forEach>

				</tbody>
			</table>
			<a href="/BankingApp/showSavingTransactions"><button type="button" class="btn btn-default">View Details</button></a>
		</div>
	</div>

	<div id="creditCard" class="tab-pane fade">
		<p><strong><h4>Credit Card Number: <span>${creditCardNumber.accountNumber}</span></h4></strong></p>
		<p><strong><h4>Current Balance: <span>${creditCardNumber.balance}</span></h4></strong></p>
		<div>
			<p><h4>Last 5 transactions</h4></p>
			<table class="table table-hover">
				<thead>
					<tr>
						<th>#</th>
						<th>Description</th>
						<th>Transaction Type</th>
						<th>Payee</th>
						<th>Amount</th>
						<th>Status</th>
						<th>Date</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach var="transaction" items="${creditList}" varStatus="loop">
					<tr>
						<th scope="row">${loop.index + 1}</th>
						<td>${transaction.description}</td>
						<td>${transaction.transactionType}</td>
						<td>${transaction.payee_id}</td>
						<td>${transaction.amount}</td>
						<td>${transaction.status}</td>
						<td>${transaction.timestamp_updated}</td>
					</tr>
				</c:forEach>

				</tbody>
			</table>
			<a href="/BankingApp/showCreditTransactions"><button type="button" class="btn btn-default">View Details</button></a>
		</div>
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
