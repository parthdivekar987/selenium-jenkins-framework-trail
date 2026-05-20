package com.onboarding.automation.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Centralized logging utility for test cases.
 * Provides colored, formatted logs for better visibility during test execution.
 */
public class TestLogger {

    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final String RESET = "\u001B[0m";
    private static final String BOLD = "\u001B[1m";
    private static final String CYAN = "\u001B[36m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RED = "\u001B[31m";
    private static final String BLUE = "\u001B[34m";
    private static final String MAGENTA = "\u001B[35m";

    /**
     * Log test case start
     */
    public static void testStart(String testCaseId, String testCaseName) {
        String timestamp = LocalDateTime.now().format(TIME_FORMAT);
        System.out.println("\n" + BOLD + CYAN + "╔════════════════════════════════════════════════════════════════╗" + RESET);
        System.out.println(BOLD + CYAN + "║" + RESET + GREEN + " ✓ TEST CASE START" + RESET + CYAN + BOLD + "                                 ║" + RESET);
        System.out.println(BOLD + CYAN + "╠════════════════════════════════════════════════════════════════╣" + RESET);
        System.out.println(BOLD + CYAN + "║" + RESET + BLUE + " Test ID    : " + testCaseId + CYAN + BOLD + "                         ║" + RESET);
        System.out.println(BOLD + CYAN + "║" + RESET + BLUE + " Test Name  : " + testCaseName + CYAN + BOLD + "║" + RESET);
        System.out.println(BOLD + CYAN + "║" + RESET + BLUE + " Start Time : " + timestamp + CYAN + BOLD + "                      ║" + RESET);
        System.out.println(BOLD + CYAN + "╚════════════════════════════════════════════════════════════════╝" + RESET);
    }

    /**
     * Log test case end with status
     */
    public static void testEnd(String testCaseId, String status) {
        String timestamp = LocalDateTime.now().format(TIME_FORMAT);
        String statusColor = status.equalsIgnoreCase("PASSED") ? GREEN : RED;
        System.out.println("\n" + BOLD + CYAN + "╔════════════════════════════════════════════════════════════════╗" + RESET);
        System.out.println(BOLD + CYAN + "║" + RESET + statusColor + " ✓ TEST CASE END" + RESET + CYAN + BOLD + "                                   ║" + RESET);
        System.out.println(BOLD + CYAN + "╠════════════════════════════════════════════════════════════════╣" + RESET);
        System.out.println(BOLD + CYAN + "║" + RESET + BLUE + " Test ID    : " + testCaseId + CYAN + BOLD + "                         ║" + RESET);
        System.out.println(BOLD + CYAN + "║" + RESET + statusColor + " Status     : " + status + CYAN + BOLD + "                        ║" + RESET);
        System.out.println(BOLD + CYAN + "║" + RESET + BLUE + " End Time   : " + timestamp + CYAN + BOLD + "                      ║" + RESET);
        System.out.println(BOLD + CYAN + "╚════════════════════════════════════════════════════════════════╝" + RESET);
    }

    /**
     * Log step execution
     */
    public static void step(String stepNumber, String description) {
        System.out.println(BOLD + MAGENTA + "  [STEP " + stepNumber + "] " + RESET + description);
    }

    /**
     * Log action
     */
    public static void action(String action) {
        System.out.println(BOLD + YELLOW + "  ➜ ACTION: " + RESET + action);
    }

    /**
     * Log verification
     */
    public static void verify(String assertion) {
        System.out.println(BOLD + BLUE + "  ✓ VERIFY: " + RESET + assertion);
    }

    /**
     * Log info message
     */
    public static void info(String message) {
        System.out.println(BOLD + CYAN + "  [INFO] " + RESET + message);
    }

    /**
     * Log warning message
     */
    public static void warn(String message) {
        System.out.println(BOLD + YELLOW + "  [WARNING] " + RESET + message);
    }

    /**
     * Log error message
     */
    public static void error(String message) {
        System.out.println(BOLD + RED + "  [ERROR] " + RESET + message);
    }

    /**
     * Log success message
     */
    public static void success(String message) {
        System.out.println(BOLD + GREEN + "  [SUCCESS] " + RESET + message);
    }

    /**
     * Log element interaction
     */
    public static void interact(String element, String action) {
        System.out.println(BOLD + BLUE + "  [INTERACT] " + RESET + "Element: " + element + " | Action: " + action);
    }

    /**
     * Log assertion result
     */
    public static void assertLog(String assertion, boolean result) {
        String resultText = result ? "PASS" : "FAIL";
        String resultColor = result ? GREEN : RED;
        System.out.println(BOLD + BLUE + "  [ASSERT] " + RESET + assertion + " → " + resultColor + resultText + RESET);
    }

    /**
     * Log separator line
     */
    public static void separator() {
        System.out.println(CYAN + "─────────────────────────────────────────────────────────────────" + RESET);
    }
}
