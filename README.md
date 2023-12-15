# calculator-application
Application for calculator to perform arithmetic operations

## Overview

The calculator application is a web-based application built using Java nad Lambda function for the backend and React for the frontend. It is a production-ready calculator API that can perform
basic arithmetic operations (add, subtract, multiply, or divide) on two numbers. It contains a responsive web UI part of the application with React and the application allows users to input two numbers and
select an operation. The calculator API is deployed using AWS Lambda and AWS API Gateway. The React application is deploted on AWS using AWS S3 Bucket.

## Installation

To get started with the application, follow these steps:

Clone the Git repository: git clone https://github.com/rohansingh13/calculator-application.git

## AWS Deployment

### React Application
The React application is deployed on AWS S3.

- **Application URL:** [http://calculator-demo-app.s3-website.eu-north-1.amazonaws.com/]

### API Endpoint
The calculator API is deployed on AWS Lambda and exposed through API Gateway.

- **API Endpoint URL:** [https://owqc808789.execute-api.eu-north-1.amazonaws.com/test/calculator]

#### API Documentation

Below is the documentation on how to use the deployed API:

##### Perform Arithmetic Operation

You can provide the operation with add/subtract/multiply/divide operation.

- **Endpoint:** `POST /calculate`
- **Description:** `Perform basic arithmetic operations on two numbers.`
- **Request Body:**
  ```json
  {
    "number1": 5,
    "number2": 3,
    "operation": "add"
  }

- **Response:**
  ```json
   {
      "statusCode": 200,
      "body": {
        "result": 8
      },
      "headers": {
        "Access-Control-Allow-Origin": "*",
        "Access-Control-Allow-Methods": "POST",
        "Access-Control-Allow-Headers": "Content-Type"
      }
  }

### Error Handling

If an error occurs, the API will respond with a JSON object containing an error message:

  ```json
  {
    "statusCode": 400,
    "error": "Division by zero is not allowed"
  }
