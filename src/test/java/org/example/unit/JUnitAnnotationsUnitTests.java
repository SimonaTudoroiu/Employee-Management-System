package org.example.unit;

import org.junit.jupiter.api.*;

public class JUnitAnnotationsUnitTests {
    @BeforeEach
    public void setUp() {
        System.out.println("Before each method");
    }

    @AfterEach
    public void afterEachTest() {
        System.out.println("After each method");
    }

    @BeforeAll
    public static void setUpOnce() {
        System.out.println("Before any test is run, and is run only once.");
    }

    @AfterAll
    public static void afterAllTests() {
        System.out.println("After all tests are run, and is run only once.");
    }

    @Test
    public void testExampleOne_success() {

        System.out.println("In test method one.");

    }

    @Test
    public void testExampleTwo_success() {

        System.out.println("In test method two.");

    }
}
