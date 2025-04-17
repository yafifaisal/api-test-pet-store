# Pet Store API Automation Framework

## 🌟 Overview

This project is an automated testing framework for Pet Store API using:

- **Java**
- **JUnit 5**
- **RestAssured**
- **Allure for Reporting**

The framework is designed to test the Pet Store API with features such as:

- Data-driven testing with flexible CSV files
- Schema validation for API responses
- Configurable environments (dev/stg)
- Modular and reusable test scripts
- Logging and error handling

---

## 🚀 Getting Started

### 🛠 Prerequisites

- **Java 11+**
- **Maven 3.x**
- **Allure Command-Line Tool** (for reports)

### 💾 Installation

1. **Clone the repository:**
   ```bash
   unzip amartha.zip
   ```
2. **Install Allure:**
   ```bash
   brew install allure
   ```
   or download from [Allure](https://docs.qameta.io/allure/).

---

## ⚙️ Configuration

### Environment Configuration

Update the API keys and environment details in:

- `config/config.test.properties`
- `config/config.main.properties`

**Example:**

```properties
base_url=https://petstore.swagger.io/v2
```

### Switching Environments

Specify the environment when running tests:

```bash
mvn clean test -Denv=test
```

---

## 📝 Test Cases

### ✅ Supported Test Scenarios:

**Pet Store API (REST)**

- Create a new Pet (POST /pet)
- Finds Pet By Status (GET /pet/findByStatus?status={status})
- Finds Pet By Id (GET /pet/{petId})

### 🗂 Test Data Structure:

- CSV files located under `src/test/resources/data/`

**Example: create-pet.csv**

```csv
scenario,expectedStatus,petName
valid request,200,Cat1
valid request,200,Cat2
```

---

## 🧪 Running Tests

### Run All Tests:

```bash
mvn clean test -Denv=test
```

### Run a Specific Test:

```bash
mvn -Dtest=PetStoreTests clean test -Denv=test
```

### Run Tests with Allure Report:

```bash
mvn clean test -Denv=test
mvn allure:serve
```

---

## 📊 Allure Reporting

### Generating Report:

After running tests, generate the report using:

```bash
mvn allure:serve
```

### Viewing Report:

The report will automatically open in your default web browser.

---

## 🛠️ Adding New Test Cases

1. **Create a new CSV file:**

   - Example: `data/pet/update-pet.csv`

2. **Define test data:**

```csv
scenario,expectedStatus,petName
valid request,200,Cat1
valid request,200,Cat2
```

3. **Implement the test method:**

- Create a new test class under `src/test/java/tests/`
- Follow the structure of existing test files.

---

## 💡 Best Practices

1. **Data-Driven Testing:**

   - Use CSV files to manage test cases efficiently.

2. **Modular Structure:**

   - Keep utility classes separate from test logic.

3. **Log Important Details:**

   - Use `LogUtil` for informative logs during test execution.

4. **Schema Validation:**

   - Always validate API responses using JSON schemas.

5. **Configuration Management:**
   - Keep environment-specific data in separate config files.

---

## ❓ Troubleshooting

- **Tests Fail with 401 Unauthorized:**
  - Check API key and secret in the `config` folder.
- **Allure Report Not Generated:**
  - Make sure you have installed the Allure CLI tool.
- **Connection Issues:**
  - Ensure you are connected to the internet and the Pet Store API endpoint is accessible.

---

## ✨ Happy Testing!
