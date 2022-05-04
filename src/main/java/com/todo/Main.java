package com.todo;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class);

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        BasicConfigurator.configure();

        logger.trace("Welcome to the application");

        requestOption();
    }

    private static void requestOption() {
        logger.info("\n" +
                "Enter 1 to register\n" +
                "Enter 2 to login\n" +
                "Enter 3 to logout\n" +
                "Enter any other key to exit");
        try {
            int option = sc.nextInt();
            if (option < 1 || option > 3) throw new InputMismatchException();

            doOption(option);
        }
        catch (InputMismatchException e) {
            logger.trace("Thank you for using the application");
        }

        sc.close();
    }

    private static void doOption(int option) {
        switch (option) {
            case 1:
                if (Application.getApplication().register()) {
                    dashboard();
                }
                else requestOption();
                break;
            case 2:
                if (Application.getApplication().login()) {
                    dashboard();
                }
                else requestOption();
                break;
            case 3:
                Application.getApplication().logout();
                requestOption();
                break;
            default:
                logger.warn("Option not supported");
        }
    }

    private static void dashboard() {
        Scanner sc = new Scanner(System.in);

        logger.info("\n" +
                "Enter 1 to add a todo)\n" +
                "Enter 2 to get all the todo)\n" +
                "Enter 3 to get all active todo)\n" +
                "Enter 4 to get all completed todo)\n" +
                "Enter 5 to delete a todo)\n" +
                "Enter 6 to update a todo)\n" +
                "Enter 7 to search for a todo)\n" +
                "Enter 8 to change password)\n" +
                "Exit (Enter any other input to exit)\n");
        try {
            int option = sc.nextInt();
            if (option < 1 || option > 8) throw new InputMismatchException();

            doDashboard(option);
        }
        catch (InputMismatchException e) {
            requestOption();
        }

        sc.close();
    }

    private static void doDashboard(int option) {
        switch (option) {
            case 1:
                if (Application.getApplication().addTodo()) {
                    dashboard();
                }
                else requestOption();
                break;
            case 2:
                if (Application.getApplication().getAllTodos()) {
                    dashboard();
                }
                else requestOption();
                break;
            case 3:
                if (Application.getApplication().getActiveTodos()) {
                    dashboard();
                }
                else requestOption();
                break;
            case 4:
                if (Application.getApplication().getCompletedTodos()) {
                    dashboard();
                }
                else requestOption();
                break;
            case 5:
                if (Application.getApplication().deleteTodo()) {
                    dashboard();
                }
                else requestOption();
                break;
            case 6:
                if (Application.getApplication().updateTodo()) {
                    dashboard();
                }
                else requestOption();
                break;
            case 7:
                if (Application.getApplication().searchTodo()) {
                    dashboard();
                }
                else requestOption();
                break;
            case 8:
                if (Application.getApplication().changePassword()) {
                    requestOption();
                }
                else dashboard();
                break;
            default:
                logger.warn("Option not supported");
        }
    }
}
