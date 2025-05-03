import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class BcTests {

    private final BcCalculator calculator = new BcCalculator();

    @ParameterizedTest
    @CsvSource({
            "1, 2, 3",
            "-3, -2, -5",
            "0, 5, 5"
    })
    void testAddition(int a, int b, String expected) {
        assertEquals(expected, calculator.add(a, b));
    }

    @ParameterizedTest
    @CsvSource({
            "7, 9, -2",
            "-5, -3, -2",
            "0, 10, -10"
    })
    void testSubtraction(int a, int b, String expected) {
        assertEquals(expected, calculator.subtract(a, b));
    }

    @ParameterizedTest
    @CsvSource({
            "3, 4, 12",
            "-3, 4, -12",
            "0, 10, 0"
    })
    void testMultiplication(int a, int b, String expected) {
        assertEquals(expected, calculator.multiply(a, b));
    }

    @ParameterizedTest
    @CsvSource({
            "5, 7, 0.714285",
            "-5, 7, -0.714285",
            "123456789, 987654321, 0.124999"
    })
    void testDivision(int a, int b, String expected) {
        String result = calculator.divide(a, b);
        assertEquals(expected, String.format("%.6f", Double.parseDouble(result)));
    }

    @Test
    void testDivisionByZero() {
        ArithmeticException exception = assertThrows(ArithmeticException.class, () -> calculator.divide(5, 0));
        assertEquals("division by zero", exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "1, 3, 0.333333",
            "10, 3, 3.333333"
    })
    void testPrecision(int a, int b, String expected) {
        String result = calculator.divide(a, b);
        assertEquals(expected, String.format("%.6f", Double.parseDouble(result)));
    }
}
