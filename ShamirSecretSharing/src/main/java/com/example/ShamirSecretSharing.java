package com.example;

import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ShamirSecretSharing {
    public static void main(String[] args) {
        String jsonFilePath = "input.json"; // Change this to your JSON file path

        try {
            JSONTokener tokener = new JSONTokener(new FileReader(jsonFilePath));
            JSONObject data = new JSONObject(tokener);
            JSONObject keys = data.getJSONObject("keys");

            int n = keys.getInt("n");
            int k = keys.getInt("k");

            List<Integer> xValues = new ArrayList<>();
            List<BigInteger> yValues = new ArrayList<>();

            for (int i = 1; i <= n; i++) {
                JSONObject root = data.getJSONObject(String.valueOf(i));
                int x = Integer.parseInt(String.valueOf(i));
                String base = root.getString("base");
                String value = root.getString("value");

                // Convert value from the specified base to a decimal integer
                BigInteger y = new BigInteger(value, Integer.parseInt(base));

                xValues.add(x);
                yValues.add(y);
            }

            BigInteger c = calculateConstantTerm(xValues, yValues, k);
            System.out.println("The secret (constant term c) is: " + c);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static BigInteger calculateConstantTerm(List<Integer> xValues, List<BigInteger> yValues, int k) {
        BigInteger secret = BigInteger.ZERO;

        for (int i = 0; i < k; i++) {
            BigInteger term = yValues.get(i);
            for (int j = 0; j < k; j++) {
                if (i != j) {
                    // Lagrange basis polynomial
                    BigInteger numerator = BigInteger.valueOf(-xValues.get(j));
                    BigInteger denominator = BigInteger.valueOf(xValues.get(i) - xValues.get(j));
                    term = term.multiply(numerator).divide(denominator);
                }
            }
            secret = secret.add(term);
        }
        return secret;
    }
}
