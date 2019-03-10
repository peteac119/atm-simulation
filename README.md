# ATM-Simulation (Cash Dispensing only)
Cash Dispensing Simulation in ATM for test

# Project Explanation

My solution to do cash dispensing is to use the biggest bank note first and use the lower bank note later.

To simplify a usage (or testing) of this application, I have planned to create Web UI calling web services to retrieve data and
 show the result on UI. In summary, I need web framework that run as web services and also provide UI to a user.

As a result, Spring Boot is the choice for me for this test project. 

Spring Boot provides all basic web service features, in-memory database and URL Mapping with view controller. 
It also simplifies the web development with Spring framework application.

It helps me finish this project faster and make me have enough time to do Web UI.
Techstack of Spring that I use in this project is:
- H2 database
- JPA
- Command Line Runner (which helps me populate test data on DB when the application starts up)

# Requirement
- Java JDK 8 (jdk1.8.0_201)
- Maven version 3.5.2 or above
- Chrome browser is recommended. You can use other web browsers, but not IE.

# Clone the project from GitHub
Please run "git clone https://github.com/peteac119/atm-simulation.git"
It only has master branch.

# How to run unit test
1. Please go inside project folder where pom.xml is.
2. Run "mvn test". It will run all unit tests in the project.

# How to build and run app
Please make sure that port 8080 is available.

1. Please go inside project folder where pom.xml is.
2. Run "mvn clean install". What will happen is Maven will build, run unit test and start web server.
3. To access to Web UI, please open your browser and add "http://localhost:8080/"
4. You can use Web UI to test this app.

Note: If "mvn clean install" doesn't run the application, you can try "mvn spring-boot:run" to run the application.

