package com.cvent.hacker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by a.srivastava on 1/7/17.
 */
public class Hackercup {
    private static long[][][] MATRIX = new long[6][21][401];
    private static  Map<Integer, Integer> indexMap;
    public static void main(String[] args) {
        indexMap = new HashMap<>();
        int[] sideArr = {4, 6, 8, 10, 12, 20};
        for (int ind = 0; ind < sideArr.length; ind++) {
            for (int i = 0; i <= 20; i++) {
                for (int j = 0; j <= 200; j++) {
                    MATRIX[ind][i][j] = -1;
                }
            }
        }
        Hackercup hc = new Hackercup();
        for (int ind = 0; ind < sideArr.length; ind++) {
            indexMap.put(sideArr[ind], ind);
            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < 200; j++) {
                    long val1 = hc.computeNs(i, sideArr[ind], j);
                    MATRIX[ind][i][j] = val1;
                }
            }
        }
        hc.testProbability();
    }

    

    private  void testProbability() {
        File file = new File("fighting_the_zombie.txt");
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String input = br.readLine();
            int testCount = Integer.parseInt(input);
            for (int i = 0; i < testCount; i++) {
                input = br.readLine();
                String[] arr = input.split("\\s");
                int H = Integer.valueOf(arr[0]);
                int S = Integer.valueOf(arr[1]);
                input = br.readLine();
                String[] spells = input.split("\\s");
                double probability = 0d;
                for (String spell : spells) {
                    double calculatedProb = calculateProb(H, spell);
                    if(calculatedProb > probability) {
                        probability = calculatedProb;
                    }
                }
                System.out.printf("Case #%d: %.6f%c", i+1, probability, '\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private double calculateProb(int h, String spell) {
        int[] parseSum = parseSpell(spell);
        int numRolls = parseSum[0];
        int numSides = parseSum[1];
        int adjustment = parseSum[2];
        h -= adjustment;
        double nE = Math.pow(numSides, numRolls);
        long nS = computeNsAtLeast(numRolls, numSides, h);
        return (nS/nE);
    }

    private long computeNsAtLeast(int numRolls, int numSides, int h) {
        long maxPossibleSum = numRolls * numSides;
        long computedNs = 0l;
        for (long sum = h; sum <= maxPossibleSum; sum++) {
            computedNs += computeNs(numRolls, numSides, sum);
        }
        return computedNs;
    }


    private static int[] parseSpell(String spell) {
        int[] res = new int[3];
        String[] arr = spell.split("d");
        int rolls = Integer.valueOf(arr[0]);
        res[0] = rolls;
        String[] arr1 = arr[1].split("\\+|-");
        int sides = Integer.valueOf(arr1[0]);
        res[1] = sides;
        int z = 0;
        if (arr1.length > 1) {
            z = Integer.valueOf(arr1[1]);
        }
        if (arr[1].contains("-")) {
            z *= -1;
        }
        res[2] = z;
        return res;
    }



    private long computeNs(int rolls, int sides, long sum) {
        int sidesInd = indexMap.get(sides);
        if (rolls == 1) {
            if (sum > 0 && sum <= sides) {
                return 1;
            } else {
                return 0;
            }
        }
        if (sum < rolls || sum > sides*rolls) {
            return 0;
        } else if (sum == rolls || sum == sides*rolls) {
            return 1;
        } else {
            long result = 0;
            for (int i = 1; i <= sides; i++) {
                long storedResult = -1;
                int col = Math.toIntExact(sum-i);
                if (col > 0) {
                    storedResult = MATRIX[sidesInd][rolls-1][col];
                    MATRIX[sidesInd][rolls-1][col] = storedResult;
                } 
                if (storedResult == -1) {
                    storedResult = computeNs(rolls-1, sides, sum-i);
                }
                result += storedResult;
            }
            return result;
        }
    }
}
