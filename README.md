# Java BCrypt Decoder

Java BCrypt Decoder is a simple Java application that attempts to crack a given bcrypt hash using a wordlist. The application leverages parallel processing to optimize the cracking process.

## Features

- **BCrypt Hash Cracking**: Given a bcrypt hash, the program attempts to find the original password from a provided wordlist.
- **Parallel Processing**: Utilizes Java's `CompletableFuture` and `ExecutorService` to speed up the cracking process by distributing the work across multiple CPU cores.
- **User Input**: Prompts the user to input the bcrypt hash and the path to the wordlist file.
- **Time Measurement**: Outputs the time taken to find the password or confirm that it is not in the wordlist.

## Requirements

- Java 22
- `jBCrypt` library

## Dependencies

Make sure to include the `jBCrypt` dependency in your `pom.xml` if you are using Maven:

```xml
<dependency>
    <groupId>de.svenkubiak</groupId>
    <artifactId>jBCrypt</artifactId>
    <version>0.4.3</version>
</dependency>
```

## Usage

1. **Clone the repository**:
   ```sh
   git clone https://github.com/boyanroyachki/Java-BCrypt-Decoder.git
   cd Java-BCrypt-Decoder
   ```

2. **Compile the program**:
   ```sh
   javac -cp .;path\to\jBCrypt.jar org/commodolink/Main.java
   ```

3. **Run the program**:
   ```sh
   java -cp .;path\to\jBCrypt.jar org.commodolink.Main
   ```

4. **Provide inputs**:
   - Paste the bcrypt hash when prompted.
   - Provide the path to the wordlist file.

## Example

Here is an example of how to use the program:

```sh
$ java -cp .;path\to\jBCrypt.jar org.commodolink.Main

*  Paste password hash here: 
$2a$12$0BQSb2aEt3ERVUxNl2OW2uM3bP6PTRGo9L8SstYE9JsJwTx.PCGV2

** Paste path to wordlist here: 
C:\path\to\wordlist.txt

Wordlist loaded. Number of words: 10000
Password found: password123
Time taken: 1234 milliseconds
```

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
