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
					<a href="/BankingApp/customer/profile"><button type="button" class="btn btn-default navbar-btn">My Profile</button></a>
					<a href="/BankingApp/logout"><button type="button" class="btn btn-default navbar-btn">Log out</button></a>
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
							<p><strong><h4>Checking Account Number: <span>${checking.accountNumber}</span></h4></strong></p>
							<p><strong><h4>Current Balance: $<span>${checking.balance}</span></h4></strong></p>
						</div>

						<div>
							<p><h4>Pending Transactions</h4></p>
							<table class="table table-hover">
								<thead>
									<tr>
										<th>#</th>
										<th>Initiator ID</th>
										<th>Payer</th>
										<th>Payee</th>
										<th>Amount</th>
										<th>Date</th>
										<th>Description</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="pendingChecking" items="${pendingChecking}" varStatus="loop">
										<tr>
											<th scope="row">${loop.index + 1}</th>
											<td>${pendingChecking.initiatorID}</td>
											<td>${pendingChecking.fromAccountID}</td>
											<td>${pendingChecking.toAccountID}</td>
											<td>${pendingChecking.amount}</td>
											<td>${pendingChecking.stamp}</td>
											<td>${pendingChecking.description}</td>
										</tr>
									</c:forEach>
								</tbody>
								</table>

								<p><h4>Completed Transactions</h4></p>
								<table class="table table-hover">
									<thead>
										<tr>
											<th>#</th>
											<th>Amount</th>
											<th>Payer</th>
											<th>Payee</th>
											<th>Description</th>
											<th>Reviewer ID</th>
											<th>Status</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="completedChecking" items="${completedChecking}" varStatus="loop">
											<tr>
												<th scope="row">${loop.index + 1}</th>
												<td>${completedChecking.amount}</td>
												<td>${completedChecking.fromAccountID}</td>
												<td>${completedChecking.toAccountID}</td>
												<td>${completedChecking.description}</td>
												<td>${completedChecking.reviewerID}</td>
												<td>${completedChecking.status}</td>
											</tr>
										</c:forEach>
									</tbody>
									</table>

							<a href="/BankingApp/customer/downloadStatements"><button type="button" class="btn btn-default">Download Statements (need to be updated)</button></a>
						</div>
				</div>

	<div id="savingAccount" class="tab-pane fade">
		<p><strong><h4>Saving Account Number: <span>${savings.accountNumber}</span></h4></strong></p>
		<p><strong><h4>Current Balance: $<span>${savings.balance}</span></h4></strong></p>
		<div>
			<p><h4>Pending Transactions</h4></p>
							<table class="table table-hover">
								<thead>
									<tr>
										<th>#</th>
										<th>Initiator ID</th>
										<th>Payer</th>
										<th>Payee</th>
										<th>Amount</th>
										<th>Date</th>
										<th>Description</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="pendingSavings" items="${pendingSavings}" varStatus="loop">
										<tr>
											<th scope="row">${loop.index + 1}</th>
											<td>${pendingSavings.initiatorID}</td>
											<td>${pendingSavings.fromAccountID}</td>
											<td>${pendingSavings.toAccountID}</td>
											<td>${pendingSavings.amount}</td>
											<td>${pendingSavings.stamp}</td>
											<td>${pendingSavings.description}</td>
										</tr>
									</c:forEach>
								</tbody>
								</table>

								<p><h4>Completed Transactions</h4></p>
								<table class="table table-hover">
									<thead>
										<tr>
											<th>#</th>
											<th>Amount</th>
											<th>Payer</th>
											<th>Payee</th>
											<th>Description</th>
											<th>Reviewer ID</th>
											<th>Status</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="completedSavings" items="${completedSavings}" varStatus="loop">
											<tr>
												<th scope="row">${loop.index + 1}</th>
												<td>${completedSavings.amount}</td>
												<td>${completedSavings.fromAccountID}</td>
												<td>${completedSavings.toAccountID}</td>
												<td>${completedSavings.description}</td>
												<td>${completedSavings.reviewerID}</td>
												<td>${completedSavings.status}</td>
											</tr>
										</c:forEach>
									</tbody>
									</table>

							<a href="/BankingApp/customer/downloadStatements"><button type="button" class="btn btn-default">Download Statements (need to be updated)</button></a>
		</div>
	</div>

	<div id="creditCard" class="tab-pane fade">
		<p><strong><h4>Credit Card Number: <span>${credit.accountNumber}</span></h4></strong></p>
		<p><strong><h4>Current Amount Due: $<span>${credit.currentAmountDue}</span></h4></strong></p>
		<a href="/BankingApp/customer/payCredit"><button type="button" class="btn btn-default">Make Payment</button></a>
		<br>
		<div>
			<p><h4>Pending Transactions</h4></p>
							<table class="table table-hover">
								<thead>
									<tr>
										<th>#</th>
										<th>Initiator ID</th>
										<th>Payer</th>
										<th>Payee</th>
										<th>Amount</th>
										<th>Date</th>
										<th>Description</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="pendingCredit" items="${pendingCredit}" varStatus="loop">
										<tr>
											<th scope="row">${loop.index + 1}</th>
											<td>${pendingCredit.initiatorID}</td>
											<td>${pendingCredit.fromAccountID}</td>
											<td>${pendingCredit.toAccountID}</td>
											<td>${pendingCredit.amount}</td>
											<td>${pendingCredit.stamp}</td>
											<td>${pendingCredit.description}</td>
										</tr>
									</c:forEach>
								</tbody>
								</table>

								<p><h4>Completed Transactions</h4></p>
								<table class="table table-hover">
									<thead>
										<tr>
											<th>#</th>
											<th>Amount</th>
											<th>Payer</th>
											<th>Payee</th>
											<th>Description</th>
											<th>Reviewer ID</th>
											<th>Status</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="completedCredit" items="${completedCredit}" varStatus="loop">
											<tr>
												<th scope="row">${loop.index + 1}</th>
												<td>${completedCredit.amount}</td>
												<td>${completedCredit.fromAccountID}</td>
												<td>${completedCredit.toAccountID}</td>
												<td>${completedCredit.description}</td>
												<td>${completedCredit.reviewerID}</td>
												<td>${completedCredit.status}</td>
											</tr>
										</c:forEach>
									</tbody>
									</table>

							<a href="/BankingApp/customer/downloadStatements"><button type="button" class="btn btn-default">Download Statements (need to be updated)</button></a>
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
