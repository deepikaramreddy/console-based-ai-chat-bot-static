import java.io.*;
import java.util.*;

public class ChatBot {
    static Map<String, Map<String, String>> topics = new HashMap<>();
    static Scanner scanner = new Scanner(System.in);
    static String userName;

    public static void main(String[] args) {
        initializeTopics();

        // Display past conversation history
        System.out.println("=== Previous Conversation History ===");
        displayConversationHistory();
        System.out.println("=====================================\n");

        while (true) {
            System.out.println("Welcome to the ChatBot!");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Forgot Password");
            System.out.println("Type 'exit' to quit.");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().toLowerCase();
            logConversation("User selected: " + choice);

            switch (choice) {
                case "1":
                    registerUser();
                    break;
                case "2":
                    if (loginUser()) {
                        chatWithBot();
                    }
                    break;
                case "3":
                    forgotPassword();
                    break;
                case "exit":
                    System.out.println("Goodbye! Have a great day! 😊");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void registerUser() {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Choose a username: ");
        String username = scanner.nextLine();
        System.out.print("Set a password: ");
        String password = scanner.nextLine();

        try (FileWriter writer = new FileWriter("resources/user_data.txt", true)) {
            writer.write(username + "," + password + "," + name + "\n");
            System.out.println("Registration successful!\n");
        } catch (IOException e) {
            System.out.println("An error occurred during registration.");
        }
    }

    private static boolean loginUser() {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        try (BufferedReader reader = new BufferedReader(new FileReader("resources/user_data.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(username) && parts[1].equals(password)) {
                    userName = parts[2]; // Retrieve the user's name
                    System.out.println("Login successful! Welcome, " + userName + "!\n");
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred during login.");
        }

        System.out.println("Invalid username or password. Please try again.\n");
        return false;
    }

    private static void forgotPassword() {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();

        try (BufferedReader reader = new BufferedReader(new FileReader("resources/user_data.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(username)) {
                    System.out.println("Your password is: " + parts[1] + "\n");
                    return;
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while retrieving your password.");
        }

        System.out.println("Username not found.\n");
    }

    private static void initializeTopics() {
        Map<String, String> frontend = new HashMap<>();
frontend.put("html", "HTML (Hypertext Markup Language) is the structure of a web page. It provides the basic skeleton of a webpage, with elements like headings, paragraphs, links, images, and other media. HTML uses tags such as <h1>, <p>, <a>, and <img> to define content and structure. The content is rendered by web browsers, and HTML works alongside CSS and JavaScript to create interactive and styled web pages.");
frontend.put("css", "CSS (Cascading Style Sheets) is used to control the presentation of a web page. It allows developers to apply styles to HTML elements, controlling aspects like color, layout, typography, and spacing. CSS is crucial for creating visually appealing and responsive web designs. With CSS, developers can use classes, IDs, and selectors to target specific HTML elements and apply design rules. CSS also supports advanced features like animations and media queries.");
frontend.put("javascript", "JavaScript is a programming language that adds interactivity and dynamic behavior to a web page. Unlike HTML and CSS, which are static, JavaScript enables real-time interaction, such as responding to user inputs, updating content, and validating forms. JavaScript runs in the user's browser, making it an essential tool for client-side development. It works with the DOM (Document Object Model) to manipulate HTML elements, offering functionalities like event handling, AJAX, and animation effects.");

Map<String, String> backend = new HashMap<>();
backend.put("java", "Java is a versatile, object-oriented programming language that is widely used for backend development. Known for its portability, Java runs on the Java Virtual Machine (JVM), allowing programs to be executed on any device with the JVM installed. Java is highly secure, scalable, and has a large ecosystem of libraries and frameworks like Spring. It's used to build enterprise-level applications, banking systems, web services, and more.");
backend.put("python", "Python is a high-level, interpreted programming language known for its simplicity and readability. It is widely used in backend development due to its ease of learning and extensive library support. Python frameworks like Django and Flask make backend web development faster by providing tools for routing, database handling, and security. Python is also popular in other fields like data science, machine learning, and automation, offering versatility in both backend and other domains.");
backend.put("nodejs", "Node.js is a runtime environment that enables JavaScript to be used for backend development. It is built on Chrome’s V8 JavaScript engine and is known for its non-blocking, event-driven architecture, making it suitable for building scalable, real-time applications. Node.js is often used for applications like chat services, gaming platforms, and live content updates. Its large ecosystem of npm (Node Package Manager) libraries helps developers build efficient backend systems quickly.");

Map<String, String> database = new HashMap<>();
database.put("mysql", "MySQL is a relational database management system (RDBMS) that uses SQL (Structured Query Language) for querying and managing data. It is one of the most popular database systems and is widely used in web applications. MySQL organizes data into tables with rows and columns and supports features like indexing, transactions, and data integrity. It is used by many platforms like WordPress, Facebook, and other content management systems.");
database.put("mongodb", "MongoDB is a NoSQL database that stores data in JSON-like documents, offering greater flexibility compared to traditional relational databases. MongoDB is schema-less, meaning it does not require a predefined structure for data storage. It is especially useful for applications that need to handle large amounts of unstructured or semi-structured data. Its flexible schema allows developers to adapt to changing data requirements easily, and it is widely used in modern web applications.");
database.put("postgresql", "PostgreSQL is an advanced open-source relational database that emphasizes extensibility and SQL compliance. It supports complex queries, foreign keys, joins, and transactions, making it ideal for large-scale, enterprise applications. PostgreSQL is highly customizable through extensions, such as PostGIS for geospatial data. It is commonly used in scenarios requiring high data integrity and complex relational data models, such as finance, healthcare, and analytics systems.");

Map<String, String> frameworks = new HashMap<>();
frameworks.put("react", "React is a powerful JavaScript library developed by Facebook for building user interfaces, particularly for single-page applications. React’s core concept is the use of reusable components, which allows developers to create complex UIs by composing smaller, independent components. It uses a virtual DOM to efficiently update the user interface, enhancing performance by minimizing direct changes to the browser’s DOM. React’s ecosystem also includes tools for managing application state, routing, and more.");
frameworks.put("spring", "Spring is a comprehensive, open-source framework for building enterprise applications in Java. It provides tools for developing robust backend systems, including features like dependency injection, aspect-oriented programming, and transaction management. The Spring framework simplifies the development of Java-based applications and is commonly used for building web applications, RESTful APIs, and microservices. Spring Boot, a subproject of Spring, makes it easy to set up and configure applications with minimal boilerplate code.");
frameworks.put("angular", "Angular is a TypeScript-based framework developed by Google for building single-page web applications. It provides a comprehensive solution for developing dynamic web apps, including tools for routing, form validation, HTTP client, and data binding. Angular’s component-based architecture helps developers manage complex user interfaces by breaking them down into smaller, reusable components. It also includes advanced features like two-way data binding and dependency injection, making it an ideal choice for large-scale, enterprise-level applications.");

topics.put("frontend", frontend);
topics.put("backend", backend);
topics.put("database", database);
topics.put("frameworks", frameworks);

    }

    private static void chatWithBot() {
        while (true) {
            System.out.println("Choose a topic to learn about:");
            for (String topic : topics.keySet()) {
                System.out.println("- " + topic);
            }
            System.out.println("Type 'exit' to quit.");

            String choice = scanner.nextLine().toLowerCase();
            logConversation("User chose topic: " + choice);

            if (choice.equals("exit")) {
                System.out.println("Do you have any queries before you go? (yes/no)");
                String queryResponse = scanner.nextLine().toLowerCase();

                if (queryResponse.equals("yes")) {
                    System.out.println("Please ask your question:");
                    String userQuery = scanner.nextLine();
                    logConversation("User query: " + userQuery);
                    System.out.println("Thank you for your query! We'll note it for improvement.\n");
                }

                System.out.println("See you again, " + userName + "! 😊");
                return;
            }

            if (topics.containsKey(choice)) {
                handleTopic(choice);
            } else {
                System.out.println("Invalid choice. Please try again.\n");
            }
        }
    }

    private static void handleTopic(String topic) {
        Map<String, String> subtopics = topics.get(topic);
        System.out.println("\nYou selected " + topic + ". Here are the subtopics:");
        for (String subtopic : subtopics.keySet()) {
            System.out.println("- " + subtopic);
        }
        System.out.println("Type the subtopic to learn more, or type 'back' to return to the main menu.");

        while (true) {
            String subtopicChoice = scanner.nextLine().toLowerCase();
            logConversation("User chose subtopic: " + subtopicChoice);

            if (subtopicChoice.equals("back")) {
                return;
            }

            if (subtopics.containsKey(subtopicChoice)) {
                System.out.println(subtopics.get(subtopicChoice) + "\n");
                System.out.println("Do you have any queries about this topic? (yes/no)");

                String queryResponse = scanner.nextLine().toLowerCase();
                if (queryResponse.equals("yes")) {
                    System.out.println("Please ask your question:");
                    String userQuery = scanner.nextLine();
                    logConversation("User query: " + userQuery);
                    System.out.println("Thank you for your query! We'll note it for improvement.\n");
                }

                // Clear input buffer by consuming the newline
                scanner.nextLine(); // This is the fix to ensure we consume the extra newline

                // After answering the query, ask if they want to learn more
                System.out.println("Would you like to learn more about this subtopic? (yes/no)");
                String learnMoreResponse = scanner.nextLine().toLowerCase();
                if (learnMoreResponse.equals("no")) {
                    return;
                }
            } else {
                System.out.println("Invalid subtopic. Please try again or type 'back' to return.");
            }
        }
    }

    private static void logConversation(String content) {
        try (FileWriter writer = new FileWriter("resources/conversation_history.txt", true)) {
            writer.write(content + "\n");
        } catch (IOException e) {
            System.out.println("An error occurred while saving conversation history.");
        }
    }

    private static void displayConversationHistory() {
        try (BufferedReader reader = new BufferedReader(new FileReader("resources/conversation_history.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("No previous conversation history found.\n");
        }
    }
}
