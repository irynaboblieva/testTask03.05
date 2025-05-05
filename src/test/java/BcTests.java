import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

public class BcTests {

    private final BcCalculator calculator = new BcCalculator();

    // Positive tests for addition
    @ParameterizedTest
    @CsvSource({
            "1, 2, 3.000000",
            "-3, -2, -5.000000",
            "0, 5, 5.000000"
    })
    void testAddition(int a, int b, String expected) {
        // Test addition operation and compare results with expected output
        assertEquals(expected, String.format("%.6f", Double.parseDouble(calculator.add(a, b))));
    }

    // Positive tests for subtraction
    @ParameterizedTest
    @CsvSource({
            "7, 9, -2.000000",
            "-5, -3, -2.000000",
            "0, 10, -10.000000"
    })
    void testSubtraction(int a, int b, String expected) {
        // Test subtraction operation and compare results with expected output
        assertEquals(expected, String.format("%.6f", Double.parseDouble(calculator.subtract(a, b))));
    }

    // Positive tests for multiplication
    @ParameterizedTest
    @CsvSource({
            "3, 4, 12.000000",
            "-3, 4, -12.000000",
            "0, 10, 0.000000"
    })
    void testMultiplication(int a, int b, String expected) {
        // Test multiplication operation and compare results with expected output
        assertEquals(expected, String.format("%.6f", Double.parseDouble(calculator.multiply(a, b))));
    }

    // Positive tests for division
    @ParameterizedTest
    @CsvSource({
            "5, 7, 0.714285",
            "-5, 7, -0.714285",
            "123456789, 987654321, 0.124999"
    })
    void testDivision(int a, int b, String expected) {
        // Test division operation and compare results with expected output
        String result = calculator.divide(a, b);
        assertEquals(expected, String.format("%.6f", Double.parseDouble(result)));
    }

    // Test division by zero
    @Test
    void testDivisionByZero() {
        // Ensure that dividing by zero throws an ArithmeticException
        ArithmeticException exception = assertThrows(ArithmeticException.class, () -> calculator.divide(5, 0));
        assertEquals("division by zero", exception.getMessage());
    }

    // Tests for precision in division
    @ParameterizedTest
    @CsvSource({
            "1, 3, 0.333333",
            "10, 3, 3.333333"
    })
    void testPrecision(int a, int b, String expected) {
        // Test precision handling for division and compare results with expected output
        String result = calculator.divide(a, b);
        assertEquals(expected, String.format("%.6f", Double.parseDouble(result)));
    }

    // Negative test for invalid addition input
    @Test
    void testInvalidAdditionInput() {
        // Test invalid input for addition (non-numeric value) and expect a NumberFormatException
        try {
            calculator.add(1, Integer.parseInt("abc"));
            fail("Exception expected");
        } catch (NumberFormatException e) {
            assertEquals("For input string: \"abc\"", e.getMessage());
        }
    }

    // Negative test for invalid subtraction input
    @Test
    void testInvalidSubtractionInput() {
        // Test invalid input for subtraction (non-numeric value) and expect a NumberFormatException
        try {
            calculator.subtract(5, Integer.parseInt("xyz"));
            fail("Exception expected");
        } catch (NumberFormatException e) {
            assertEquals("For input string: \"xyz\"", e.getMessage());
        }
    }

    // Negative test for invalid multiplication input
    @Test
    void testInvalidMultiplicationInput() {
        // Test invalid input for multiplication (non-numeric value) and expect a NumberFormatException
        try {
            calculator.multiply(5, Integer.parseInt("invalid"));
            fail("Exception expected");
        } catch (NumberFormatException e) {
            assertEquals("For input string: \"invalid\"", e.getMessage());
        }
    }

    // Negative test for invalid decimal input (comma instead of dot)
    @Test
    void testInvalidDecimalInput() {
        // Test invalid decimal format (comma instead of dot) and expect a NumberFormatException
        try {
            calculator.divide(1, Integer.parseInt("10,5")); // Using comma instead of dot
            fail("Exception expected");
        } catch (NumberFormatException e) {
            assertEquals("For input string: \"10,5\"", e.getMessage());
        }
    }

    // Test overflow scenario with large numbers
    @Test
    void testOverflowWithLargeNumbers() {
        // Test overflow when adding large numbers and check the result
        String result = calculator.add(Integer.MAX_VALUE, Integer.MAX_VALUE);
        assertEquals("4294967294.000000", String.format("%.6f", Double.parseDouble(result)));
    }

    // Negative test for invalid input during division
    @Test
    void testInvalidInputDivision() {
        // Test invalid input during division (non-numeric value) and expect a NumberFormatException
        try {
            calculator.divide(Integer.parseInt("5a"), 2); // Invalid input
            fail("Exception expected");
        } catch (NumberFormatException e) {
            assertEquals("For input string: \"5a\"", e.getMessage());
        }
    }

    // Negative test for invalid expression in command
    @Test
    void testInvalidExpression() {
        // Test invalid expression with missing numbers or operators and expect a failure in execution
        try {
            calculator.executeCommand("echo \"5 + + 10\" | bc");
            fail("Exception expected");  // If no exception is thrown, the test should fail
        } catch (Exception e) {
            // Check if the exception message contains "command execution failed"
            assertTrue(e.getMessage().contains("Command execution failed"));
        }
    }
}
