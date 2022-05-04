package com.todo;

import org.apache.commons.collections4.IterableMap;
import org.apache.commons.collections4.list.TreeList;
import org.apache.commons.collections4.map.HashedMap;

public class CommonsStorage {
    private static final IterableMap<String, User> users;
    private static final IterableMap<User, TreeList<Todo>> userTodos;

    static {
        users = new HashedMap<>();
        userTodos = new HashedMap<>();
    }

    public static User addUser(String email, String password) {
        User user = new User(email, password);
        users.put(email, user);

        return user;
    }

    public static User getUser(String email, String password) {
        User user = null;
        if (users.containsKey(email)) {
            User tmpUser = users.get(email);
            if (tmpUser.getPassword().equals(password)) {
                user = tmpUser;
            }
        }

        return user;
    }

    public static User getByEmail(String email) {
        return users.get(email);
    }

    public static Todo addTodo(User user, String name, String details) {
        Todo todo = new Todo(name, details);

        if (userTodos.containsKey(user)) {
            userTodos.get(user).add(todo);
        }
        else {
            TreeList<Todo> todos = new TreeList<>();
            todos.add(todo);
            userTodos.put(user, todos);
        }

        return todo;
    }

    public static TreeList<Todo> getTodos(User user) {
        return userTodos.get(user);
    }

    public static TreeList<Todo> getActiveTodos(User user) {
        TreeList<Todo> activeTodos = new TreeList<>();
        userTodos.get(user).forEach(todo -> {
            if (todo.getStatus().equals(Todo.TodoStatus.ACTIVE)) activeTodos.add(todo);
        });

        return activeTodos;
    }

    public static TreeList<Todo> getCompletedTodos(User user) {
        TreeList<Todo> completedTodos = new TreeList<>();
        userTodos.get(user).forEach(todo -> {
            if (todo.getStatus().equals(Todo.TodoStatus.COMPLETED)) completedTodos.add(todo);
        });

        return completedTodos;
    }

    public static Todo removeTodo(User user, int index) {
        return userTodos.get(user).remove(index - 1);
    }

    public static void updateTodoByName(User user, int index, String name) {
        Todo todo = userTodos.get(user).get(index - 1);
        todo.setName(name);
    }

    public static void updateTodoByDetails(User user, int index, String details) {
        Todo todo = userTodos.get(user).get(index - 1);
        todo.setDetails(details);
    }

    public static void updateTodoByStatus(User user, int index, Todo.TodoStatus status) {
        Todo todo = userTodos.get(user).get(index - 1);
        todo.setStatus(status);
    }

    public static TreeList<Todo> searchTodoByName(User user, String name) {
        TreeList<Todo> todos = new TreeList<>();
        userTodos.get(user).forEach(todo -> {
            if (todo.getName().equalsIgnoreCase(name)) todos.add(todo);
        });

        return todos;
    }

    public static TreeList<Todo> searchTodoByDetails(User user, String details) {
        TreeList<Todo> todos = new TreeList<>();
        userTodos.get(user).forEach(todo -> {
            if (todo.getDetails().equalsIgnoreCase(details)) todos.add(todo);
        });

        return todos;
    }

    public static TreeList<Todo> searchTodoByDay(User user, int day) {
        TreeList<Todo> todos = new TreeList<>();
        userTodos.get(user).forEach(todo -> {
            if (todo.getDayCreated().getDayOfWeek().getValue() == day) todos.add(todo);
        });

        return todos;
    }
}
