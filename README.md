# ATM-Simulation (Cash Dispensing only)
Cash Dispensing Simulation in ATM.

# Project Explanation
My solution for cash dispensing is to use the biggest bank note first and use the lower bank note later.

I have planned to create basic web UI calling web services to retrieve data from in-memory database and 
show the result on UI. I also must be able to finish developing this web application in short period of time.

Spring Boot is my best choice to support all of my requirements as I mentioned above because 
it simplifies coding process and provides all essential features for web development.
Spring framework also provides in-memory database as default database.

Tech Stack of Spring that I use in this project is:
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
2. Run "mvn clean install" to build, package and install the application locally.
3. Run "mvn spring-boot:run" to run the application
4. To access to Web UI, please open your browser and add "http://localhost:8080/"
5. You can use Web UI to test this app.

