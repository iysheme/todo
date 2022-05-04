package com.todo;

import org.apache.commons.collections4.list.TreeList;
import org.apache.log4j.Logger;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Application {
    private static final Application APPLICATION = new Application();

    private Application() {
    }

    public static Application getApplication() {
        return APPLICATION;
    }

    private final Logger logger = Logger.getLogger(Application.class);

    private final Scanner sc = new Scanner(System.in);

    private User user = null;

    public boolean register() {
        logger.info("Enter email");
        String email = sc.next();
        if (CommonsStorage.getByEmail(email) != null) {
            logger.warn("Email is already used. Try a different one");
            return false;
        }

        logger.info("Enter password");
        String password = sc.next();
        logger.info("Re-enter password");
        String password1 = sc.next();
        if (!password.equals(password1)) {
            logger.warn("Passwords do not match");
            return false;
        }

        user = CommonsStorage.addUser(email, password);
        logger.info("Registration successful");

        return true;
    }

    public boolean login() {
        logger.info("Enter email");
        String email = sc.next();

        logger.info("Enter password");
        String password = sc.next();

        User user = CommonsStorage.getUser(email, password);
        if (user == null) {
            logger.warn("Invalid username/password");
            return false;
        }

        logger.info("Logged in successfully!");
        return true;
    }

    public void logout() {
        if (!isLoggedIn()) {
            logger.warn("You have not logged in");
            return;
        }

        user = null;
        logger.info("Logged out successfully");
    }

    private boolean isLoggedIn() {
        return user != null;
    }

    public boolean addTodo() {
        if (!isLoggedIn()) {
            logger.warn("You have not logged in");
            return false;
        }

        logger.info("Enter todo name");
        String name = sc.next();

        logger.info("Enter todo details");
        String details = sc.next();

        Todo todo = CommonsStorage.addTodo(user, name, details);
        logger.info("Todo added successfully");

        return true;
    }

    public boolean getAllTodos() {
        if (!isLoggedIn()) {
            logger.warn("You have not logged in");
            return false;
        }

        TreeList<Todo> todos = CommonsStorage.getTodos(user);
        logger.info(String.format("%s", todos));

        return true;
    }

    public boolean getActiveTodos() {
        if (!isLoggedIn()) {
            logger.warn("You have not logged in");
            return false;
        }

        TreeList<Todo> todos = CommonsStorage.getActiveTodos(user);
        logger.info(String.format("%s", todos));

        return true;
    }

    public boolean getCompletedTodos() {
        if (!isLoggedIn()) {
            logger.warn("You have not logged in");
            return false;
        }

        TreeList<Todo> todos = CommonsStorage.getCompletedTodos(user);
        logger.info(String.format("%s", todos));

        return true;
    }

    public boolean deleteTodo() {
        if (!isLoggedIn()) {
            logger.warn("You have not logged in");
            return false;
        }

        TreeList<Todo> todos = CommonsStorage.getTodos(user);
        todos.forEach(todo -> {
            logger.info(String.format("%d -> %s", todos.indexOf(todo) + 1, todo));
        });

        logger.info("Enter todo index");
        int index = sc.nextInt();

        Todo todo = CommonsStorage.removeTodo(user, index);
        logger.info(String.format("Todo removed"));

        return true;
    }

    public boolean updateTodo() {
        if (!isLoggedIn()) {
            logger.warn("You have not logged in");
            return false;
        }

        TreeList<Todo> todos = CommonsStorage.getTodos(user);
        todos.forEach(todo -> {
            logger.info(String.format("%d -> %s", todos.indexOf(todo) + 1, todo));
        });

        logger.info("Enter todo index");
        int index = sc.nextInt();

        int option;
        logger.info("\n" +
                "Enter 1 to update todo name\n" +
                "Enter 2 to update todo details\n" +
                "Enter 3 to update todo status\n");
        try {
            option = sc.nextInt();
            if (option < 1 || option > 3) throw new InputMismatchException();
        }
        catch (InputMismatchException e) {
            logger.warn("Invalid option");
            return false;
        }

        if (option == 1) {
            logger.info("Enter new name");
            String name = sc.next();

            CommonsStorage.updateTodoByName(user, index, name);
        }
        else if (option == 2) {
            logger.info("Enter new details");
            String details = sc.next();

            CommonsStorage.updateTodoByDetails(user, index, details);
        }
        else {
            logger.info("Enter 1 to set status to completed");
            logger.info("Enter 2 to set status to active");
            int statusIn = sc.nextInt();

            Todo.TodoStatus status = statusIn == 1 ? Todo.TodoStatus.COMPLETED : Todo.TodoStatus.ACTIVE;
            CommonsStorage.updateTodoByStatus(user, index, status);
        }

        logger.info("Updated");
        return true;
    }

    public boolean searchTodo() {
        if (!isLoggedIn()) {
            logger.warn("You have not logged in");
            return false;
        }

        TreeList<Todo> todos = CommonsStorage.getTodos(user);
        todos.forEach(todo -> {
            logger.info(String.format("%d -> %s", todos.indexOf(todo) + 1, todo));
        });

        logger.info("Enter todo index");
        int index = sc.nextInt();

        int option;
        logger.info("\n" +
                "Enter 1 to search todo by name\n" +
                "Enter 2 to search todo by details\n" +
                "Enter 3 to search todo by day created\n");
        try {
            option = sc.nextInt();
            if (option < 1 || option > 3) throw new InputMismatchException();
        }
        catch (InputMismatchException e) {
            logger.warn("Invalid option");
            return false;
        }

        if (option == 1) {
            logger.info("Enter name");
            String name = sc.next();

            TreeList<Todo> searchedTodos = CommonsStorage.searchTodoByName(user, name);
            logger.info(String.format("%s", searchedTodos));
        }
        else if (option == 2) {
            logger.info("Enter new details");
            String details = sc.next();

            TreeList<Todo> searchedTodos = CommonsStorage.searchTodoByDetails(user, details);
            logger.info(String.format("%s", searchedTodos));
        }
        else {
            logger.info("Enter from 1 - 7 (Monday - Sunday");
            int day = sc.nextInt();

            TreeList<Todo> searchedTodos = CommonsStorage.searchTodoByDay(user, day);
            logger.info(String.format("%s", searchedTodos));
        }

        logger.info("Searched");
        return true;
    }
}
