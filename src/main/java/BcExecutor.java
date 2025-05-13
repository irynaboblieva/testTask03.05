import java.io.BufferedReader;
import java.io.InputStreamReader;

public class BcExecutor {

    public static class BcResult {
        public final String stdout;
        public final String stderr;

        public BcResult(String stdout, String stderr) {
            this.stdout = stdout;
            this.stderr = stderr;
        }
    }

    public BcResult run(String expression) {
        StringBuilder stdout = new StringBuilder();
        StringBuilder stderr = new StringBuilder();
        try {
            ProcessBuilder builder = new ProcessBuilder("bash", "-c", "echo \"" + expression + "\" | bc -l");
            Process process = builder.start();

            BufferedReader stdOutReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader stdErrReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            String line;
            while ((line = stdOutReader.readLine()) != null) {
                stdout.append(line).append("\n");
            }
            while ((line = stdErrReader.readLine()) != null) {
                stderr.append(line).append("\n");
            }

            process.waitFor();

            return new BcResult(stdout.toString().trim(), stderr.toString().trim());
        } catch (Exception e) {
            throw new RuntimeException("Failed to execute bc", e);
        }
    }
}
