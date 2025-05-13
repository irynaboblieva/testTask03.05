
# BcCalculator

A Java-based testing suite that ensures the `bc` command-line tool performs correctly for a variety of expressions. This suite focuses on checking the results and error messages produced by `bc`, and verifying that the tool handles various cases, including large numbers, invalid expressions, division by zero, and formatting issues.

## Features
- Verifies that the correct results are produced for various arithmetic operations (`+`, `-`, `*`, `/`).
- Ensures proper handling of error messages and warnings, such as division by zero or syntax errors.
- Checks the format of output, ensuring consistent results (e.g., distinguishing between `2` and `2.0`).
- Supports large numbers beyond the Java `int` type limits.
- Tests handling of invalid or incorrectly formatted expressions.

## Prerequisites
- Java 11+
- Linux with `bc` installed

## Installation
1. Clone the repository:
    ```bash
    git clone https://github.com/yourusername/BcCalculator.git
    cd BcCalculator
    ```

2. Build with Maven:
    ```bash
    mvn clean install
    ```

3. Install `bc`:
    ```bash
    sudo apt-get install bc
    ```

## Running Tests

To run the tests, execute the following command:

```bash
mvn test
```

This will run all unit tests, including tests for:
- Arithmetic operations (addition, subtraction, multiplication, and division)
- Error handling (e.g., division by zero, negative scale, syntax errors)
- Output formatting (e.g., ensuring `2` and `2.0` are treated differently)
- Large numbers beyond Java’s `Integer.MAX_VALUE`
- Invalid or malformed expressions

## Example `bc` Requests and Expected Responses

Here are some examples of valid and invalid input for `bc` along with the expected results:

| **Input Expression**           | **STDOUT/Result**                       | **STDERR/Error**                        |
|---------------------------------|-----------------------------------------|-----------------------------------------|
| `0011342349878977349729`       | ✅ STDOUT: `11342349878977349729`      |                                         |
| `0x13`                          |                                         | ⚠️ STDERR: `(standard_in) 1: syntax error`|
| `-234987379834`                | ✅ STDOUT: `-234987379834`              |                                         |
| ` +17373497854789`             |                                         | ⚠️ STDERR: `(standard_in) 1: syntax error`|
| ` *924328793497843`            |                                         | ⚠️ STDERR: `(standard_in) 1: syntax error`|
| `/-8434379374 `                |                                         | ⚠️ STDERR: `(standard_in) 1: syntax error`|
| ` - 0.89 `                      | ✅ STDOUT: `-.89`                       |                                         |
| `--3`                           |                                         | ⚠️ STDERR: `(standard_in) 1: syntax error`|
| `- - 3`                         | ✅ STDOUT: `3`                          |                                         |
| `4 + 2 * 3`                     | ✅ STDOUT: `10`                         |                                         |
| ` 5.6 * 1.20 - 2`              | ✅ STDOUT: `4.72`                       |                                         |
| `1/0`                           |                                         | ⚠️ STDERR: `Runtime error (func=(main), adr=3): Divide by zero` |
| `scale=3; 0.14`                 | ✅ STDOUT: `.14`                        |                                         |
| `scale=-4; -0.4`               | ✅ STDOUT: `-.4`                        | ⚠️ STDERR: `Runtime warning (func=(main), adr=6): negative scale, set to 0` |
| `5.0/-3`                        | ✅ STDOUT: `-1`                         |                                         |
| `scale=2; 5.0/-3`              | ✅ STDOUT: `-1.66`                      |                                         |

## Tests

The tests are designed to ensure that `bc` produces correct output for valid expressions, handles errors for invalid expressions, and produces appropriate warnings (such as for negative scale). Some of the important test cases include:

- **Basic Arithmetic**: Ensures that addition, subtraction, multiplication, and division are handled correctly.
- **Division by Zero**: Verifies that dividing by zero generates the appropriate error message (`"Runtime error (func=(main), adr=3): Divide by zero"`).
- **Negative Scale**: Checks that negative scale inputs result in a warning and correct output formatting.
- **Invalid Expressions**: Tests for invalid expressions, ensuring that they generate the appropriate syntax error messages.
- **Large Numbers**: Ensures that `bc` can handle large numbers beyond the Java `Integer.MAX_VALUE` limit.
- **Hexadecimal Input**: Tests that hexadecimal input is correctly handled and converted to decimal output.

## Why the Changes?

The goal of this project is not to build a Java calculator using `bc` as a backend. Instead, the focus is on validating that `bc` works correctly with various input expressions and produces the expected outputs or error messages.

Key points based on the requirements:
- The tests must check both the `stdout` and `stderr` streams produced by `bc`.
- Proper handling of invalid expressions and warnings, such as division by zero and negative scale.
- Ensuring correct formatting for output, such as distinguishing between `2` and `2.0`.
- Supporting large numbers beyond the limits of the Java `int` type.

---
