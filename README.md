# assignment
Home Assignment Link
1. This assignment has all the APIs mentioned in the requirement document including GET, POST, PUT and DELETE for the Customer using Inmemory H2 DB.. Because of time constraints, I have not added API documentation(Swagger) and JWT Token(Spring Security) for securing the endpoint.

2. The password for the DB is prenet in the application.yaml file and could be encrypted using jaspyt
3. Logs for the application will be present in the logs folder.
4. ExternalID/tracingId could be used for traking the request if the request is running across multiple applications.

5. The application could be run using mvn clean install and then spring-boot:run with default profile. Profiling could be dont using Spring profiles and creating different application.yaml files. For enterprice level, Config Server is the best option to maintain the Config Files.

6. All the test could be done(JUnits and Integration) using "mvn test". The jacoco report for the application is available at: http://localhost:63342/assignment/target/site/jacoco/index.html

		CURL for the application :
			1. curl --location 'http://localhost:8080/api/customers' \
		--header 'Content-Type: application/json' \
		--header 'Authorization: Bearer bearer any' \
		--header 'Cookie: JSESSIONID=6824D6E3556390D3603C9F798D26AD4A' \
		--data-raw '{
			"firstName":"Alpha",
			"lastName":"Beta",
			"emailAddress":"alpha.beta@gmail.com",
			"phoneNumber":"5282746589"
		}'

			2. Get the Customer ID from the created account and use that in the following request :
			curl --location 'http://localhost:8080/api/customers/f562a646-436f-4192-ac68-e84c9243f783' \
		--header 'Cookie: JSESSIONID=6824D6E3556390D3603C9F798D26AD4A'
			3. Delete 
			curl --location --request PUT 'http://localhost:8080/api/customers/f562a646-436f-4192-ac68-e84c9243f783' \
		--header 'Content-Type: application/json' \
		--header 'Authorization: Bearer bearer any' \
		--header 'Cookie: JSESSIONID=6824D6E3556390D3603C9F798D26AD4A' \
		--data-raw '{
			"firstName":"Alpha1",
			"lastName":"Beta1",
			"emailAddress":"alpha.beta1@gmail.com",
			"phoneNumber":"5282746589"
		}'
			4. Delete :
			curl --location --request DELETE 'http://localhost:8080/api/customers/f562a646-436f-4192-ac68-e84c9243f783' \
		--header 'Content-Type: application/json' \
		--header 'Authorization: Bearer bearer any' \
		--header 'Cookie: JSESSIONID=6824D6E3556390D3603C9F798D26AD4A' \
		--data-raw '{
			"firstName":"Ankita",
			"lastName":"Rathi",
			"emailAddress":"anoop.rathi2@gmail.com",
			"phoneNumber":"5282746589"
		}'

5. (Observability) -> Spring ACtuator URL once the application is up and running is :http://localhost:8080/actuator. Couple of examples: http://localhost:8080/actuator/metrics, http://localhost:8080/actuator/metrics/system.cpu.count, http://localhost:8080/actuator/health, etc.

6. (Containerization) -> Dockerfile is placed in the project folder. The docker image could created using:
	1.	docker build -t assignment-api .
	2.  docker run -d -p 8080:8080 --name assignment-api-container assignment-api
	3.  Run the CURL commands which are used initially to test the application here.
	4. the application could be stopped using : docker stop assignment-api-container






