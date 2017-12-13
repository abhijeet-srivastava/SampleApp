package com.cvent;

import java.util.Arrays;

/**
 * Created by a.srivastava on 3/11/17.
 */

public class dbsInterview {
    boolean[] primes=new boolean[10000];
    public static void main(String[] args) {
        dbsInterview dbc = new dbsInterview();
        dbc.solve1();
    }

    private void solve1() {
        int number = 25;
        int prime = lowestPrimeFactor(number);
        System.out.printf("%d %d%c",prime, number-prime, '\n');
        
    }
    public void fillSieve() {
        Arrays.fill(primes,true);        // assume all integers are prime.
        primes[0]=primes[1]=false;       // we know 0 and 1 are not prime.
        for (int i=2;i<primes.length;i++) {
            //if the number is prime,
            //then go through all its multiples and make their values false.
            if(primes[i]) {
                for (int j=2;i*j<primes.length;j++) {
                    primes[i*j]=false;
                }
            }
        }
    }

    public boolean isPrime1(int n) {
        return primes[n]; //simple, huh?
    }

    public static int lowestPrimeFactor (int num) {
        if(num == 2 || num == 3) {
            return num;
        }
        if (isEven(num)) {
            return 2;
        }
        if (num%3 == 0) {
            return 3;
        }
        for (int i = 5; i*i <= num; i++) {
            if ((num%i) == 0
                    && (isPrime(i))) {
                return i;
            }
        }
        return 0;
    }

    public static boolean isPrime(int n) {
        if(n < 2) return false;
        if(n == 2 || n == 3) return true;
        if(isEven(n) || n%3 == 0) return false;
        for(long i = 6L; i*i < n; i += 6) {
            if(n%(i-1) == 0 || n%(i+1) == 0) return false;
        }
        return true;
    }
    public static boolean isEven(int num) {
        return ((num & 1) == 0);
    }

}
