package com.demo.calculator;

import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class CalculatorHandler implements RequestHandler<Map<String, Object>, Map<String, Object>> {

    @Override
    public Map<String, Object> handleRequest(Map<String, Object> input, com.amazonaws.services.lambda.runtime.Context context) {
        ObjectMapper objectMapper = new ObjectMapper();
        CalculatorRequest request = objectMapper.convertValue(input, CalculatorRequest.class);

        double result;
        Map<String, Object> responseBody = new HashMap<>();

        switch (request.getOperation()) {
            case "add":
                result = request.getNumber1() + request.getNumber2();
                break;
            case "subtract":
                result = request.getNumber1() - request.getNumber2();
                break;
            case "multiply":
                result = request.getNumber1() * request.getNumber2();
                break;
            case "divide":
                if (request.getNumber2() == 0.0) {
                    return createErrorResponse("Division by zero is not allowed");
                }
                result = request.getNumber1() / request.getNumber2();
                break;
            default:
                return createErrorResponse("Unsupported operation: " + request.getOperation());
        }

        responseBody.put("result", result);
        return createSuccessResponse(responseBody);
    }

    private Map<String, Object> createSuccessResponse(Object body) {
        Map<String, Object> response = new HashMap<>();
        response.put("statusCode", 200);
        response.put("body", body);
        response.put("headers", getCorsHeaders());
        return response;
    }

    private Map<String, Object> createErrorResponse(String errorMessage) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("statusCode", 400);
        errorResponse.put("error", errorMessage);
        errorResponse.put("headers", getCorsHeaders());
        return errorResponse;
    }

    private Map<String, String> getCorsHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Access-Control-Allow-Origin", "*");
        headers.put("Access-Control-Allow-Methods", "GET, OPTIONS, POST");
        headers.put("Access-Control-Allow-Headers", "Content-Type,X-Amz-Date,Authorization,X-Api-Key,X-Amz-Security-Token");
        return headers;
    }
}