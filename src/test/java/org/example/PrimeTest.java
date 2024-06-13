package org.example;
import org.junit. Test;
import org.junit. Ignore;

import java.util.Scanner;

import static org.example.ParallelPrimeFinder.findNthPrime;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class PrimeTest {
    @Test
    public void testMessage() {
        System.out.println("For real integers");

        assertEquals(2, findNthPrime(1));
        assertEquals(541, findNthPrime(100));
    }

    @Test
    public void testFindNthPrime() {
        System.out.println("For zero and negative integers");
        try {
            findNthPrime(-1);
            fail("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("n must be greater than 0", e.getMessage());
        }
    }
}