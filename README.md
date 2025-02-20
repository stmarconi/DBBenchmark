
### DB Benchmark Application
A simple java application which executes INSERT and SELECT benchmarks on a JDBC compliant database.

### Requirements
1. Java 17
2. Maven
3. PostgreSQL

### Setup & Execution
1. Install Maven dependencies: mvn clean install
2. Setup your database credential in application.properties
3. Create required tables in your local database (see sql scripts in /sql folder)
4. Run the Java application