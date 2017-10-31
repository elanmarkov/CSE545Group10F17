<!DOCTYPE html>
<html lang="en">
<head>
	<title>Credit Card Payment</title>
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
			<div class="col-lg-6">
				<div class="jumbotron">

					<div>
						<h4><strong>Credit Card Payment</strong></h4>
					</div>

					<div>
						<p><h5>Account Number: <span>${creditCard.credit_card_number}</span></h5></p>
						<p><h5>Due Date: <span>${creditCard.due_date}</span></h5></p>
						<p><h5>Next Closing Date: <span>${creditCard.cycle_date}</span></h5></p>
					</div>

<br>
					<form action="/BankingApp" method="post">
						<div class="form-group">
							<h5><strong>Amount to Pay</strong></h5>
							<p><h5>${creditCard.current_amount_due}</h5></p>
						</div>

						<div class="form-group">
							<h5><strong>Checking Account Balance</strong></h5>
							<p><h5>${checking.balance}</h5></p>
						</div>

						<div class="form-group">
							<button type="submit" class="btn btn-primary" name="submit">Pay</button>
						</div>
					  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					</form>
				</div>
			</div>
		</div>
	</div>


	<script   src="https://code.jquery.com/jquery-3.2.1.js"   integrity="sha256-DZAnKJ/6XZ9si04Hgrsxu/8s717jcIzLy3oi35EouyE="   crossorigin="anonymous"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
</body>
</html>
