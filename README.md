# Azure Functions to Invoke Azure ML Endpoint

This project contains two Azure Functions—one in Python and one in Java—that invoke an Azure Machine Learning (AML) online endpoint using a JSON payload.

---

## Prerequisites
- Azure Functions Core Tools
- Python 3.8+ and `pip`
- Java 11+ and Maven
- Azure Subscription with AML endpoint deployed

---

## Python Function Setup

**1. Navigate to the Python directory:**
```bash
cd python-function
```

**2. Install dependencies:**
```bash
pip install -r requirements.txt
```

**3. Configure local settings:**
Update `local.settings.json` with your AML endpoint URL and key.

**4. Run the function locally:**
```bash
func start
```

**5. Test with curl or Postman:**
```bash
curl -X POST http://localhost:7071/api/invoke \
  -H "Content-Type: application/json" \
  -d '{"data": "value"}'
```

---

## Java Function Setup

**1. Navigate to the Java directory:**
```bash
cd java-function
```

**2. Build and run the function:**
```bash
mvn clean package
mvn azure-functions:run
```

**3. Configure local settings:**
Update `local.settings.json` with your AML endpoint URL and key.

**4. Test with curl or Postman:**
```bash
curl -X POST http://localhost:7071/api/invokeAML \
  -H "Content-Type: application/json" \
  -d '{"data": "value"}'
```

---

## Environment Variables
Both versions require the following environment variables:
- `AML_ENDPOINT_URL`: Full URL to your Azure ML endpoint.
- `AML_API_KEY`: Authorization key for the AML endpoint.

---

## Deployment
You can deploy these functions using Azure CLI or directly from VS Code using the Azure Functions extension.
"# azure-functions-ml-invoke" 
