# Java File Repository

Java File Repository is a lightweight Java library inspired by JpaRepository that allows you to store and manage data in text files. This library provides convenient methods for accessing data such as `findById`, `findAll`, `save`, `update`, and `deleteById`. It is developed using Java 17.

## Getting Started

### Prerequisites

- Java 17 or higher
- Any Java development environment (e.g., Eclipse, IntelliJ, or VS Code)
- Maven

### Installation

1. Clone the Java File Repository repository:

   ```bash
   git clone https://github.com/example/java-file-repository.git
   ```

2. Build and install the library using Maven:

   ```bash
   cd java-file-repository
   mvn clean install
   ```

3. Add the following dependency to your project's `pom.xml` file:

   ```xml
   <dependency>
       <groupId>org.mounangabouka.brodygaudel</groupId>
       <artifactId>javaTextRepository</artifactId>
       <version>1.0-SNAPSHOT</version>
   </dependency>
   ```

4. Refresh your project dependencies, and you're ready to use Java File Repository in your project.

### Usage

1. Import the necessary classes into your Java or Kotlin file:

   ```java
   import com.example.repository.FileRepository;
   import com.example.model.YourModel;
   ```

2. Create an instance of the `FileRepository` class, specifying the file path where the data will be stored:

   ```java
   FileRepository<YourModel> repository = new FileRepository<>("data.txt");
   ```

3. Use the provided methods to interact with the data:

   - Find an object by its ID:

     ```java
     YourModel model = repository.findById(id);
     ```

   - Find all objects:

     ```java
     List<YourModel> models = repository.findAll();
     ```

   - Save a new object:

     ```java
     YourModel model = new YourModel();
     // Set the properties of the model
     repository.save(model);
     ```

   - Update an existing object:

     ```java
     YourModel model = repository.findById(id);
     // Modify the properties of the model
     repository.update(model);
     ```

   - Delete an object by its ID:

     ```java
     repository.deleteById(id);
     ```

## Example

Here's a simple example of using Java File Repository:

```java
import com.example.repository.FileRepository;
import com.example.model.User;

public class Main {
    public static void main(String[] args) {
        Repository<User> userRepository = new TxtRepository<>("users.txt", User.class);

        User user1 = new User("john.doe@example.com", "John Doe");
        userRepository.save(user1);

        User user2 = new User("jane.smith@example.com", "Jane Smith");
        userRepository.save(user2);

        User foundUser = userRepository.findById(1);
        System.out.println(foundUser);

        List<User> allUsers = userRepository.findAll();
        System.out.println(allUsers);

        user2.setName("Jane Johnson");
        userRepository.update(user2);

        userRepository.deleteById(1);
    }
}
```

## Contributions

Contributions to Java File Repository are welcome! If you find any issues or have suggestions for improvements, please feel free to create a pull request or submit an issue in the GitHub repository.

## License

Java File Repository is open-source software licensed under the MIT License. See the [LICENSE](https://github.com/example/java-file-repository/blob/main/LICENSE) file for more information.
