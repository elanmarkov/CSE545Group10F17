<!DOCTYPE html>
<html lang="en">
<head>
	<title>Pending Request Management</title>
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


<div class="container" id="loginBox">
	<div class="row">
		<div class="col-lg-12">
			<div class="panel-body no-padding" style="overflow-y: scroll; max-height:400px;">
			<h3><strong>Pending Request Management</strong></h3>
			  <form>
				  <table class="table table-hover">
					    <thead>
					      <tr>
					        <th>Transaction ID</th>
					        <th>Sender ID</th>
					        <th>Receiver ID</th>
					        <th>Transaction Amount</th>
					        <th>Transaction Type</th>
					        <th>Critical</th>
					        <th>Action</th>
					      </tr>
					    </thead>
					    <tbody>

					    	<c:when test="${empty pending_list}">
                        			<tr>
                                    	<td colspan="7">No Pending Transaction</td>
                                	</tr>
                            </c:when>
                            <c:otherwise>
                        			<c:forEach items="${pending_list}" var="transaction">
                        				<c:choose>
                        					<tr>
                                    		<td style="text-align:center">${transaction.id}</td>
											<td style="text-align:center">${transaction.payer_id}</td>
											<td style="text-align:center">${transaction.payee_id}</td>
											<td style="text-align:center">${transaction.amount}</td>
											<td style="text-align:center">${transaction.transaction_type}</td>
											<td style="text-align:center">${transaction.critical}</td>
											<td style="text-align:center">
												<form action = "/BankingApp/employee/PendingRequestManagement" method = "post">
		                                    
		                                    		<select id="requestType" name="requestType" required>
				       									<option value="">Select Type</option>
				          								<option value="approve">Approve Request</option>
				          								<option value="reject">Reject Request</option>
				       								</select>
		                                    		<button type="submit" class="btn btn-default">Submit</button>
		                                   		</form>
											</td>
                                		</tr>
                        				</c:when>
                        				            
                        				</c:choose>
                            		</c:forEach>
                        		</c:otherwise>
                        	</c:choose>			      
					    </tbody>
					  </table>
			  </form>
			</div>
		</div>
	</div>
</div>


<script   src="https://code.jquery.com/jquery-3.2.1.js"   integrity="sha256-DZAnKJ/6XZ9si04Hgrsxu/8s717jcIzLy3oi35EouyE="   crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
</body>
</html>