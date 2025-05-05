# BcCalculator

A simple Java-based calculator that performs basic arithmetic operations (+, -, *, /) using the `bc` command-line tool.

## Features
- Addition, subtraction, multiplication, and division
- Division with floating-point precision
- Error handling for division by zero

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

## Usage Example
```java
BcCalculator calculator = new BcCalculator();
System.out.println(calculator.add(1, 2));  // Output: 3.000000
System.out.println(calculator.divide(5, 0));  // Throws ArithmeticException
```

## Running Tests

To run the tests, execute the following command:

```bash
mvn test

This will run all unit tests, including tests for addition, subtraction, multiplication, division, and error handling (such as division by zero).