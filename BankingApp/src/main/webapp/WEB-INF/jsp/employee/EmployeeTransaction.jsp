<!DOCTYPE html>
<html lang="en">
<head>
	<title>Employee Transaction</title>
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



<div class="container-fluid">

	<div class="row">
		<div class="col-lg-16">
			<div class="jumbotron">
			<h3><strong>Pending Transaction Requests</strong></h3>			
				<table class="table table-hover">
					    <thead>
					      <tr>
				          	<th>Transaction ID</th>
							<th>Payer</th>
							<th>Payee</th>
							<th>Amount</th>
							<th>Type</th>
							<th>Critical Transaction</th>
							<th>Action</th>
					      </tr>
					    </thead>
					    
					      <tbody>
        					<tr>
	                    		<td>${transaction.id}</td>
								<td>${transaction.payer_id}</td>
								<td>${transaction.payee_id}</td>
								<td>${transaction.amount}</td>
								<td>${transaction.transaction_type}</td>
								<td>${transaction.critical}</td>
								<td>
	                        		<form action = "processdebitcredit" method = "post">
                                		<input type="hidden" name="transactionID" value="${transaction.id}">
                                		<input type="hidden" name="extUserID" value="external">
                                		<select id="requestType" name="requestType" style="float" required>
	       									<option value="">Please Select</option>
	          								<option value="approve">Approve</option>
	          								<option value="reject">Reject</option>
	       								</select>
                                		<button type="submit" class="btn btn-xs btn-default">Submit</button>
		                             </form>
								</td>
	                		</tr>
                        				
	                </tbody>
				  </table>
			
			

			</div>
		</div>
	</div>



	<div class="row">
		<div class="col-lg-16">
			<div class="jumbotron">
			<h3><strong>Completed Transaction Requests</strong></h3>			
				<table class="table table-hover">
					    <thead>
					      <tr>
				          	<th>Transaction ID</th>
							<th>Payer</th>
							<th>Payee</th>
							<th>Amount</th>
							<th>Type</th>
							<th>Critical Transaction</th>
							<th>Status</th>
							<th>Approver</th>
					      </tr>
					    </thead>
					    
					      <tbody>
        					<tr>
	                    		<td>${transaction.id}</td>
								<td>${transaction.payer_id}</td>
								<td>${transaction.payee_id}</td>
								<td>${transaction.amount}</td>
								<td>${transaction.transaction_type}</td>
								<td>${transaction.critical}</td>
								<td>${transaction.status}</td>
								<td>${transaction.approver}</td>
	                		</tr>
                        				
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