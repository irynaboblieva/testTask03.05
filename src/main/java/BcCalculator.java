import java.io.BufferedReader;
import java.io.InputStreamReader;

public class BcCalculator {

    // Метод для виконання команд у командному рядку через ProcessBuilder
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
        return executeCommand("echo \"" + a + " + " + b + "\" | bc");
    }

    public String subtract(int a, int b) {
        return executeCommand("echo \"" + a + " - " + b + "\" | bc");
    }

    public String multiply(int a, int b) {
        return executeCommand("echo \"" + a + " * " + b + "\" | bc");
    }

    public String divide(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("division by zero");
        }
        return executeCommand("echo \"scale=6; " + a + " / " + b + "\" | bc -l");
    }
}
