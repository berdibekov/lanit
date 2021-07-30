
 #The results of the completed test task:
 
 1. Person and cars application - REST API application based on Spring boot framework(Spring Data,MVC).
   
 1. Database - H2 In-memory (with persons,cars)
    - src\main\resources\import.sql - init database script    
 1. Functionality 
    - According to technical specification
    
 1. Integration test for REST requests
    - /person 
    - /car
 
 1. Unit test for 
    - date format validator
    - Model format validator
 
  
 API documentation (Swagger)  
 -
 Lunch application. API documentation will be on http://localhost:8080/swagger-ui/index.html
 
#Compile and run instruction


- Run from Intellij IDEA.
    - In Intellij IDEA click to project folder->add framework support->Maven.
    - Lunch src\main\java\com\berdibekov\PersonCarApplication.java.

- From Console 
    - Windows
        - run make.bat
        - run dist\app.bat
    - UNIX (Linux,Mac OS)     
        - sh make.sh
        - scripts dist\app.sh
 
