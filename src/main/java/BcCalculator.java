import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * BcCalculator provides basic arithmetic operations (+, -, *, /)
 * using Linux command-line calculator `bc`.
 */
public class BcCalculator {

    private static final int SCALE = 6; // Precision for division

    /**
     * Executes a shell command using bash.
     *
     * @param command shell command to run
     * @return output of the command
     */
    public String executeCommand(String command) {
        StringBuilder output = new StringBuilder();
        try {
            Process process = new ProcessBuilder("bash", "-c", command).start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new RuntimeException("Command failed with exit code: " + exitCode);
            }
        } catch (Exception e) {
            throw new RuntimeException("Command execution failed: " + e.getMessage(), e);
        }
        return output.toString().trim();
    }

    public String add(int a, int b) {
        return executeCommand("echo \"" + a + " + " + b + "\" | bc -l");
    }

    public String subtract(int a, int b) {
        return executeCommand("echo \"" + a + " - " + b + "\" | bc -l");
    }

    public String multiply(int a, int b) {
        return executeCommand("echo \"" + a + " * " + b + "\" | bc -l");
    }

    public String divide(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("division by zero");
        }
        return executeCommand("echo \"scale=" + SCALE + "; " + a + " / " + b + "\" | bc -l");
    }
}
