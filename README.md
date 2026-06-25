# API Testing Kata - Hotel Booking System

A REST API test automation framework built with **Java, REST Assured, Cucumber BDD and JUnit 5**.

## 🎯 Overview

This project demonstrates API testing best practices using the [Hotel Booking API](https://automationintesting.online/). It covers authentication, booking creation, retrieval, updates, and deletion with comprehensive test scenarios.

## 📌 Project Objectives

- Build a reusable and maintainable API automation framework.
- Validate hotel booking APIs using BDD and automated tests.
- Support parallel execution, reporting, and schema validation.
- Ensure code quality and scalability using industry best practices.

## 🛠 Tech Stack

- **Java 17**
- **REST Assured 5.x** - API testing library
- **Cucumber 7.x** - BDD framework
- **JUnit 5** - Testing framework
- **Maven** - Build tool


## 📁 Project Structure
```
src
├── test
│   ├── java
│   │   └── com.booking
│   │       ├── client
│   │       ├── constants
│   │       ├── hooks
│   │       ├── models
│   │       ├── runner
│   │       ├── services
│   │       ├── stepdefinitions
│   │       └── utils
│   └── resources
│       ├── features
│       ├── jsonSchema
│       ├── spec
│       ├── config.properties
│       ├── extent.properties
│       └── junit-platform.properties
```
## 🔍 APIs Covered

- **Login Auth**  
  `POST /auth/login`

- **Create Booking**  
  `POST /booking`

- **Booking Details**  
  `GET /booking{id}`

- **Modify Booking**  
  `PUT /booking{id}`

- **Cancel Booking**  
  `DELETE /booking{id}`

- **Patch Booking**  
  `PATCH /booking{id}`


## 📊 Features Tested

- ✅ User authentication (login)
- ✅ Create booking
- ✅ Retrieve booking
- ✅ Update booking
- ✅ Partial update booking
- ✅ Delete booking
- ✅ End-2-End user journey

## 🔍 Covered Scenarios

- User logs in successfully with valid credentials
- User attempts login with different invalid credentials
- User attempts authentication using an unsupported operation
- Successful hotel room booking creation
- Create a booking with invalid field values
- Create a booking when checkout date is earlier than check-in date
- Create bookings for different guest users
- Retrieve booking details using a valid booking ID
- Retrieve booking details using an invalid booking ID
- Attempt to retrieve a non-existing booking
- Update an existing booking successfully
- Update booking details with invalid data
- Partially update selected booking details
- Cancel an existing booking successfully
- Cancel a booking using an invalid booking ID
- Attempt booking operations using unsupported HTTP methods
- Validate GET booking response against JSON schema
- Successful end-to-end booking creation, retrieval, update, and cancellation

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.8 or higher
- Git

## 🚀 Quick Start

### 1. Clone Repository
```bash
git clone <repository-url>
cd API_Testing_Kata
```

### 2. Install Dependencies
```bash
mvn clean install
```

### 3. Configuration
Edit `src/test/resources/config.properties` to customize:
- API base URL
- Authentication credentials

Edit `src/test/resources/junit-platform.properties` to customize:
- Parallel execution settings
- Thread count configuration

Edit `src/test/resources/extent.properties` to customize:
- Report generation settings
- Report output location
- Execution metadata

### 4. Run Tests
```bash
# Run all tests
mvn clean test

# Run specific feature
mvn test -Dcucumber.features="src/test/resources/features/auth.feature"

# Run by tag
mvn test -Dcucumber.filter.tags="@smoke"
```

## 📈 Test Reports

After running tests, view reports:
- **ExtentReport:** `target/extend-reports d-MMM-YY HH-mm-ss/ExtentReport.html`
- **Cucumber Report:** `target/cucumber-reports/cucumber.html`

### Extent Report Dashboard
![ExtentDashboard.png](report/ExtentDashboard.png)

## 🔗 API Specification

- **Booking OpenAPI Spec:** `src/test/resources/spec/booking.yaml`

## 📝 Running Tests in IDE

### IntelliJ IDEA
* Right-click on feature file → "Run Feature"
* Right-click on TestRunner.java → "Run"
* Right-click on a Scenario or Tag → "Run"

## 🐛 Troubleshooting

**Tests not connecting to API?**
- Check internet connection
- Verify `base.url` in config.properties
- Ensure API is accessible: https://automationintesting.online

**Maven dependencies failing?**
```bash
mvn clean dependency:purge-local-repository install
```

**Java version not compatible?**
```bash
# Check version
java -version

# Ensure Java 17+ is installed
```

**Ready to test!** 🚀

```bash
mvn clean test
```

