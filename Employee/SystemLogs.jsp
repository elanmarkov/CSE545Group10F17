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

<div class="panel-body">
					<div class="col-lg-7">
						<form action="/BankingApp/searchLogs" method='POST'>
		      				<label for="userid" class="col-lg-3 control-label">User ID : </label>
							<div class="col-lg-9 form-margin">
		       					<input type="text" class="form-control" name="userid" placeholder="User ID">
		      				</div>
		      				<label for="type" class="col-lg-3 control-label">Type : </label>
		      				<div class="col-lg-9 form-margin">
     								<select class="form-control" name="type" required>
     									<option value="">Select Type</option>
        								<option value="internalEmployee">Employee</option>
        								<option value="externalCustomer">Customer</option>
     								</select>
     							
		      				<br>
						    <button type="submit" class="btn btn-primary">Submit</button>
						    </div>
						</form>
					</div>
				</div>
			</div>


<div class="container" id="loginBox">
	<div class="row">
		<div class="col-lg-12">
			<div class="panel-body no-padding" style="overflow-y: scroll; max-height:400px;">
			<h3><strong>System Logs</strong></h3>
			  <form>
				  <table class="table table-hover">
					    <thead>
					      <tr>
					        <th>Employee ID</th>
					        <th>Activity</th>
					        <th>Details</th>
					        <th>Timestamp</th>
					   
					      </tr>
					    </thead>
					    <tbody>
						<c:when test="${empty logs}">
                        			<tr>
                                    	<td colspan="2">No Logs found</td>
                                	</tr>
                        		</c:when>
                        		<c:otherwise>
                        			<c:forEach items="${log}" var="log">
										<tr>
											<td>${log.userid}</td>
											<td>${log.activity}</td>
											<td>${log.details}</td>
											<td>${log.timestamp}</td>
										</tr>
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