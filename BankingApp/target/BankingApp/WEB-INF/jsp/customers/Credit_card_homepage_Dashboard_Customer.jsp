		<!DOCTYPE html>
		<html lang="en">
		<head>
			<title>Credit Card Dashboard</title>
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
				margin: 5% 5% 25% 10%;
			}

			.hidden{
				visibility: hidden;
			}

			#creditInfo{
				height: 10%;
				width: 50%;
				padding: 0px;
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
							<li><a href="#">Deposit</a></li>
							<li><a href="#">Withdraw</a></li>
						</ul>
					</li>

					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Funds transfer<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="#">Between accounts</a></li>
							<li><a href="#">Send to others</a></li>
						</ul>
					</li>

					<li><a href="#">Pending Request</a></li>
					<li><a href="/BankingApp/authorizeMerchant">Merchant Panel</a></li>
				</ul>


				<ul class="nav navbar-nav navbar-right">
					<a href="#"><button type="button" class="btn btn-default navbar-btn">My Profile</button></a>
					<a href="#"><button type="button" class="btn btn-default navbar-btn">Log out</button></a>
				</ul>
			</div>
		</nav>


			<div class="container">
				<div class="row">
					<div class="col-lg-14">
						<div class="jumbotron">


							<div class="panel panel-default" id="creditInfo">
								<div class="panel-heading">
									<h4><strong>Credit Card Dashboard</strong></h4> 
								</div>
								<div class="panel-body">
									<p><h5>Next Payment Date: <span>${creditAccount.dueDate}</span></h5></p>
									<p><h5>Outstanding Balance: <span>${creditAccount.availableBalance}</span></h5></p>
									<p><h5>Credit Limit: <span>${creditAccount.creditLimit}</span></h5></p>
									<p><h5>Current Due Amount: <span>${creditAccount.currentDueAmount}</span></h5></p>
									<p><h5>Cycle Date: <span>${creditAccount.cycleDate}</span></h5></p>
									<p><h5>APR: <span>${creditAccount.APR}</span></h5></p>
									<a href="/BankingApp/creditPayment"><button type="button" class="btn btn-default pull-right">Make Payment</button></a>

								</div>


							</div>


							<div>
								<p><h4>Credit Card Transaction History</h4></p>
								<table class="table table-hover">
									<thead>
										<tr>
											<th>#</th>
											<th>Description</th>
											<th>Payee</th>
											<th>Payer</th>
											<th>Amount</th>
											<th>Status</th>
											<th>Date</th>
										</tr>
									</thead>
									<tbody>
									<c:choose>			      
							    	<c:when test="${empty transationList}">
		                        			<tr>
		                                    	<td colspan="7">No Transaction</td>
		                                	</tr>
		                            </c:when>
		                            <c:otherwise>
										<c:forEach var="trans" items="${transationList}" varStatus="loop">
										<tr>
											<th scope="row">${loop.index + 1}</th>
											<td>${trans.description}</td>
											<td>${trans.payee_id}</td>
											<td>${trans.payer_id}</td>
											<td>${trans.amount}</td>
											<td>${trans.pendingStrg}</td>
											<td>${trans.timestamp_updated}</td>
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