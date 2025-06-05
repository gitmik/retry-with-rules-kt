# Kotlin Library

A sample Kotlin library project that can be used in both Java and Kotlin projects.

## Requirements

- Java 11 or higher
- Maven 3.6 or higher

## Building the Project

To build the project, run:

```bash
mvn clean install
```

## Usage

Add the following dependency to your project's `pom.xml`:

```xml
<dependency>
    <groupId>com.example</groupId>
    <artifactId>kotlin-library</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

### Example Usage in Kotlin

```kotlin
import com.example.library.Example

fun main() {
    val example = Example()
    println(example.greet("World")) // Outputs: Hello, World!
    println(example.add(2, 3)) // Outputs: 5
}
```

### Example Usage in Java

```java
import com.example.library.Example;

public class Main {
    public static void main(String[] args) {
        Example example = new Example();
        System.out.println(example.greet("World")); // Outputs: Hello, World!
        System.out.println(example.add(2, 3)); // Outputs: 5
    }
}
```

## Running Tests

To run the tests, execute:

```bash
mvn test
```

## License

This project is licensed under the MIT License - see the LICENSE file for details. 