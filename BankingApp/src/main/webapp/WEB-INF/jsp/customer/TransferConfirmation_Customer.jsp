<!DOCTYPE html>
<html lang="en">
<head>
	<title>Transfer Confirmation</title>
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
			margin: 5% 5% 25% 10%;
		}

		.hidden{
			visibility: hidden;
		}

		#creditInfo{
			/*background: whitesmoke;*/
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
				    		<h4><strong>Transfer Confirmation</strong></h4> 
				    	</div>
				    	<div class="panel-body">
				    		<p><h5>Transfer To: <span>${payee.accountNumber}</span></h5></p>
					    	<p><h5>Transfer From: <span>${payer.accountNumber}</span></h5></p>
					    	<p><h5>Amount: <span>${amount}</span></h5></p>
					    	<a href="#"><button type="button" class="btn btn-default pull-right">Make Another Payment</button></a>
					    	
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
