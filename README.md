# API Testing Kata - Hotel Booking System

A REST API test automation framework built with **Java, REST Assured, and Cucumber BDD**.

## 🎯 Overview

This project demonstrates API testing best practices using the [Hotel Booking API](https://automationintesting.online/). It covers authentication, booking creation, retrieval, updates, and deletion with comprehensive test scenarios.

## 🛠 Tech Stack

- **Java 17**
- **Maven** - Build tool
- **REST Assured 5.5.2** - API testing library
- **Cucumber 7.22.2** - BDD framework
- **JUnit 5** - Testing framework

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

### 3. Configure API URL
Edit `src/test/resources/config.properties`:
```properties
base.url=https://automationintesting.online
username=admin
password=password
```

### 4. Run Tests
```bash
# Run all tests
mvn clean test

# Run specific feature
mvn test -Dcucumber.features="src/test/resources/features/auth.feature"

# Run by tag
mvn test -Dcucumber.filter.tags="@smoke"
```

## 📁 Project Structure

```
src/test/
├── java/com/booking/
│   ├── client/           # API client
│   ├── services/         # Business logic
│   ├── stepdefinitions/  # Cucumber steps
│   ├── models/           # DTOs
│   ├── hooks/            # Setup/teardown
│   └── utils/            # Helpers
│
└── resources/
    ├── features/         # Feature files
    ├── config.properties # Configuration
    └── logback.xml       # Logging
```

## 📊 Features Tested

- ✅ User authentication (login)
- ✅ Create booking
- ✅ Retrieve booking
- ✅ Update booking
- ✅ Partial update booking
- ✅ Delete booking
- ✅ End-to-end scenarios

## 📈 Test Reports

After running tests, view reports:
- **Cucumber Report:** `target/cucumber-reports/cucumber.html`
- **ExtentReport:** `test-output/ExtentReport/ExtentReport.html`

## 🔗 API Documentation

- **Booking OpenAPI Spec:** `src/test/resources/spec/booking.yaml`

## 📝 Running Tests in IDE

### IntelliJ IDEA
1. Right-click on feature file → "Run Feature"
2. Or right-click on TestRunner.java → "Run"

### VS Code
1. Install "Cucumber (Gherkin) Full Support" extension
2. Right-click on feature file → "Run Feature"

## ⚙️ Configuration

Edit `src/test/resources/config.properties` to customize:
- API base URL
- Authentication credentials

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
