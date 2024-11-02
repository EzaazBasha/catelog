package com.example;

import org.junit.jupiter.api.Test;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AppTest {

    @Test
    public void testCalculateConstantTerm() throws Exception {
        List<Integer> xValues = Arrays.asList(1, 2, 3);
        List<BigInteger> yValues = Arrays.asList(BigInteger.valueOf(4), BigInteger.valueOf(5), BigInteger.valueOf(6));
        int k = 2;

        // Use reflection to access the private calculateConstantTerm method
        Method method = ShamirSecretSharing.class.getDeclaredMethod("calculateConstantTerm", List.class, List.class, int.class);
        method.setAccessible(true);  // Allows access to private methods

        // Invoke the private method
        BigInteger result = (BigInteger) method.invoke(null, xValues, yValues, k);

        assertNotNull(result, "The result should not be null");
    }
}
