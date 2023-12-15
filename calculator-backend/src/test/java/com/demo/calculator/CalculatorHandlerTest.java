package com.demo.calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculatorHandlerTest {

    private CalculatorHandler calculatorHandler;

    @BeforeEach
    void setUp() {
        calculatorHandler = new CalculatorHandler();
    }

    @Test
    void testAddOperation() {
        Map<String, Object> input = createInput("add", 2.0, 3.0);
        Map<String, Object> result = calculatorHandler.handleRequest(input, null);

        assertSuccessResponse(result, 5.0);
    }

    @Test
    void testSubtractOperation() {
        Map<String, Object> input = createInput("subtract", 8.0, 3.0);
        Map<String, Object> result = calculatorHandler.handleRequest(input, null);

        assertSuccessResponse(result, 5.0);
    }

    @Test
    void testMultiplyOperation() {
        Map<String, Object> input = createInput("multiply", 4.0, 2.5);
        Map<String, Object> result = calculatorHandler.handleRequest(input, null);

        assertSuccessResponse(result, 10.0);
    }

    @Test
    void testDivideOperation() {
        Map<String, Object> input = createInput("divide", 10.0, 2.0);
        Map<String, Object> result = calculatorHandler.handleRequest(input, null);

        assertSuccessResponse(result, 5.0);
    }

    @Test
    void testDivideByZeroOperation() {
        Map<String, Object> input = createInput("divide", 10.0, 0.0);

        Map<String, Object> result = calculatorHandler.handleRequest(input, null);

        int statusCode = (int) result.get("statusCode");

        if (statusCode == 400) {
            assertEquals("Division by zero is not allowed", result.get("error"));
        } else {
            fail("Unexpected status code: " + statusCode);
        }
    }

    private Map<String, Object> createInput(String operation, double number1, double number2) {
        Map<String, Object> input = new HashMap<>();
        input.put("operation", operation);
        input.put("number1", number1);
        input.put("number2", number2);
        return input;
    }

    private void assertSuccessResponse(Map<String, Object> result, double expectedResult) {
        assertEquals(200, result.get("statusCode"));

        Map<String, String> headers = (Map<String, String>) result.get("headers");
        assertEquals("*", headers.get("Access-Control-Allow-Origin"));
        assertEquals("GET, OPTIONS, POST", headers.get("Access-Control-Allow-Methods"));
        assertEquals("Content-Type,X-Amz-Date,Authorization,X-Api-Key,X-Amz-Security-Token", headers.get("Access-Control-Allow-Headers"));

        Map<String, Object> responseBody = (Map<String, Object>) result.get("body");
        assertEquals(expectedResult, (Double) responseBody.get("result"));
    }

    private void assertErrorResponse(Map<String, Object> result, int expectedStatusCode, String expectedErrorMessage) {
        assertEquals(expectedStatusCode, result.get("statusCode"));

        Map<String, String> headers = (Map<String, String>) result.get("headers");
        assertEquals("*", headers.get("Access-Control-Allow-Origin"));
        assertEquals("GET, OPTIONS, POST", headers.get("Access-Control-Allow-Methods"));
        assertEquals("Content-Type,X-Amz-Date,Authorization,X-Api-Key,X-Amz-Security-Token", headers.get("Access-Control-Allow-Headers"));

        Map<String, Object> responseBody = (Map<String, Object>) result.get("body");
        assertEquals(expectedErrorMessage, responseBody.get("error"));
    }
}
