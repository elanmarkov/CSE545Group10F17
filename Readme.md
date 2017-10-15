Src/main/java/org/group10/...
	1. Controllers: All the controllers(logic code) goes here
	2. dao: contains the data access objects (support classes where queries are written)
			Each module should contain Interface class and "implementation class which extends JdbcDaoSupport class". 
			This implementaion class extending JdbcSupport Class should be specified in the src/main/resources/daoDetails.xml
			(The last 3 lines bean configuration for dataSource) 
	3. dbmodels: contains all the database tables models
	4. tests: for future unit testing purposes.
src/main/webapp/..
	1.Web-INF : 
		-- contains jsp folder: containing all the server pages
		--spring-servlet.xml : spring dispatcher 
		-- web.xml 		
	2. other files


Running the project:
1. Install Apache tomcat server.
While installing, configure give the appropriate port(lets say 1000)
 open tomcat-users.xml:
 Add these lines at the last just before the </tomcat-users>

 <role rolename="manager-gui"/>
<user username="tomcat" password="s3cret" roles="manager-gui"/>
2. Run tomcat server
3. Install mysql, while configuration the account should be configured for 
user: root
password:1234
4. Run mysql server, connect to the root user by specifying the password in the workbench directly or by command line:  Mysql -u root -p -h localhost
which prompts for a password, enter 1234

5. Open eclipse IDE, file-->import--> maven-->existing projects-->BankingApp.

6. Maven dependencies are already configured, might add as we go, Right click on project--> Run as--> Maven build--> in the goals field: clean install--> Apply-->OK. The project builds successfully if no errors.

7. Delpoying the project:
We have compiled everything into a single war file which should be deployed in tomcat.
Open tomcat-->start service: goto localhost:1000
							 Give the credentials if the browser asks for it: username: tomcat password:s3cret 
							 Click on ManagerApp.
							 At the bottom look for deploy project: Browse war, click deploy
							 The BankingApp should appear in the table at the top. click on it 
