### Subscription microservices

**Installation :**

````
cd emailService
mvn clean install
cd ../subscriptionService
mvn clean install
cd ../subscriptionSystem
mvn clean install
cd ..


docker-compose up
````

Used libraries and technologies :
- Spring Boot
- Spring Data JPA
- H2 database
- Swagger
- Docker
- Docker compose
- Lombok 
- Maven


***Swagger***

Swagger UI : http://localhost:9000/swagger-ui.html