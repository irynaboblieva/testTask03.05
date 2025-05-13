import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BcExecutorTests {

    private static class BcResult {
        String stdout;
        String stderr;
    }

    private BcResult executeBcCommand(String command) throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder("bc");
        builder.redirectErrorStream(false); // Do not merge stdout and stderr

        Process process = builder.start();

        process.getOutputStream().write((command + "\n").getBytes());
        process.getOutputStream().flush();
        process.getOutputStream().close();

        BufferedReader stdoutReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        BufferedReader stderrReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

        StringBuilder stdout = new StringBuilder();
        StringBuilder stderr = new StringBuilder();

        String line;
        while ((line = stdoutReader.readLine()) != null) {
            stdout.append(line.trim()).append("\n");
        }
        while ((line = stderrReader.readLine()) != null) {
            stderr.append(line.trim()).append("\n");
        }

        process.waitFor();

        BcResult result = new BcResult();
        result.stdout = stdout.toString().trim();
        result.stderr = stderr.toString().trim();

        return result;
    }

    // Simple multiplication with floating point
    @Test
    void testFloatMultiplication() throws Exception {
        BcResult result = executeBcCommand("scale=2; 1.18*4");
        assertEquals("4.72", result.stdout);
    }

    // Negative scale warning
    @Test
    void testNegativeScaleWarning() throws Exception {
        BcResult result = executeBcCommand("scale=-1; 2/5");
        assertFalse(result.stderr.isEmpty(), "stderr should contain an error");
        assertTrue(result.stderr.contains("negative number"), "Expected 'negative number' message");
    }

    // Hexadecimal evaluation
    @Test
    void testHexEvaluation() throws Exception {
        BcResult result = executeBcCommand("scale=2; obase=16; 1+1");
        assertTrue(result.stderr.isEmpty(), "stderr should be empty");
        assertEquals("2", result.stdout.trim(), "Expected output is 2");
    }

    // Wrong expressions (incorrect syntax)
    @Test
    void testMultipleOperatorsError() throws Exception {
        BcResult result = executeBcCommand("1++2");
        assertFalse(result.stderr.isEmpty(), "stderr should contain an error");
    }

    @Test
    void testTrailingOperatorError() throws Exception {
        BcResult result = executeBcCommand("1+");
        assertFalse(result.stderr.isEmpty(), "stderr should contain an error");
    }

    @Test
    void testLeadingOperatorError() throws Exception {
        BcResult result = executeBcCommand("+1");
        assertFalse(result.stderr.isEmpty(), "stderr should contain an error");
    }

    @Test
    void testSyntaxError() throws Exception {
        BcResult result = executeBcCommand(" +17373497854789");
        assertFalse(result.stderr.isEmpty(), "stderr should contain an error");
    }

    @Test
    void testDivideByZeroError() throws Exception {
        BcResult result = executeBcCommand("1/0");
        assertFalse(result.stderr.isEmpty());
    }

    @Test
    void testZeroDivisionWithScale() throws Exception {
        BcResult result = executeBcCommand("scale=2; 1/0");
        assertFalse(result.stderr.isEmpty());
    }

    // Output formatting test (2 and 2.0)
    @Test
    void testFormatDifference() throws Exception {
        BcResult result = executeBcCommand("scale=2; 2.0");
        assertEquals("2.0", result.stdout, "Output should be 2.0");

        result = executeBcCommand("2");
        assertEquals("2", result.stdout, "Output should be 2");
    }

    // Large numbers
    @Test
    void testLargeNumbers() throws Exception {
        String largeNumber = "9999999999999999999999999999999999999999999999999999999999999999";
        BcResult result = executeBcCommand(largeNumber);
        assertEquals(largeNumber, result.stdout, "Output for large number doesn't match");
    }

    // Negative number division
    @Test
    void testNegativeDivision() throws Exception {
        BcResult result = executeBcCommand("scale=0; -5/3");
        assertEquals("-1", result.stdout);
    }

    // Negative floating point division
    @Test
    void testNegativeFloatDivision() throws Exception {
        BcResult result = executeBcCommand("scale=2; -5/3");
        assertEquals("-1.66", result.stdout);
    }

    // Invalid double plus
    @Test
    void testDoublePlusError() throws Exception {
        BcResult result = executeBcCommand("2++2");
        assertFalse(result.stderr.isEmpty(), "stderr should contain an error");
    }

    // Invalid double negative
    @Test
    void testInvalidDoubleNegative() throws Exception {
        BcResult result = executeBcCommand("3--3");
        assertFalse(result.stderr.isEmpty(), "stderr should contain an error");
    }

    // Only operator error
    @Test
    void testOnlyOperatorError() throws Exception {
        BcResult result = executeBcCommand("+");
        assertFalse(result.stderr.isEmpty(), "stderr should contain an error");
    }

    // Hexadecimal input with a leading "0x"
    @Test
    void testHexadecimalInput() throws Exception {
        BcResult result = executeBcCommand("ibase=16; obase=10; 0x13");
        assertEquals("19", result.stdout.trim(), "Expected decimal output for 0x13");
    }



    // Negative scale warning
    @Test
    void testNegativeScaleWarning2() throws Exception {
        BcResult result = executeBcCommand("scale=-4; -0.4");

        // Check if the stderr contains the new error message
        assertTrue(result.stderr.contains("Math error: negative number"),
                "Expected warning for negative scale");

        // Ensure there's no output on stdout, as the calculation fails
        assertEquals("", result.stdout.trim(), "Expected no output on stdout due to error");
    }



    // Correct output for scale and division
    @Test
    void testScaleAndDivision() throws Exception {
        BcResult result = executeBcCommand("scale=3; 0.14");
        assertEquals(".14", result.stdout, "Expected correctly formatted output");
    }
}
