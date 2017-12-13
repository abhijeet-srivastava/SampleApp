package com.cvent.respondent.answers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by a.srivastava on 6/18/16.
 */
public class StockRoom {

    public static void main(String[] args) throws IOException {
        //testSegment();
        testRegex();
    }

    private static boolean testRegex() {
        String string = "abc";
        String regEx = "ab?&";
        return isValidString(string, regEx);
    }

    private static boolean isValidString(String string, String regex) {
        boolean strNull = string == null;
        boolean regExNull = regex == null;
        if (strNull && regExNull) {
            return true;
        } else if (strNull ^ regExNull) {
            return false;
        }
        boolean strConsumed = string.length() == 0;
        boolean regConsumed = regex.length() == 0;
        if (strConsumed && regConsumed) {
            return true;
        } else if (strConsumed ^ regConsumed) {
            return false;
        }else {
            char strChar = string.charAt(0);
            char regChar = regex.charAt(0);
            if (regChar == '?') {
                return isValidString(string, regex.substring(1))
                        || isValidString(string.substring(1), regex.substring(+1));
            } else if (regChar == '&') {
                return isValidString(string, regex.substring(1))
                        || isValidString(string.substring(1), regex.substring(1))
                        || isValidString(string.substring(2), regex.substring(1));

            } else if (strChar == regChar) {
                return isValidString(string.substring(1), regex.substring(1));
            } else {
                return false;
            }

        }
    }

    private static void testSegment() throws IOException {
       /* BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String inputString = br.readLine();
        String dictStr = br.readLine();*/
        String inputString = "iplaycricket";
        String dictStr = "i play cricket";
        Set<String> dictionary = new HashSet<>(Arrays.asList(dictStr.split("\\s")));
        List<String> outputList = new ArrayList<>();
        if (isValidSequence(inputString, dictionary, outputList)) {
            for (int i = outputList.size()-1; i >= 0; i--) {
                System.out.print(outputList.get(i) + " ");
            }
            
        } else {
            System.out.println("NO_SEQUENCE_FOUND");
        }
    }

    private static boolean isValidSequence(String inputString, Set<String> dictionary, List<String> outputList) {
        boolean isValid =false;
        for (int i = 1 ; i <= inputString.length(); i++) {
            String prefix = inputString.substring(0, i);
            String suffix = inputString.substring(i);
            if (dictionary.contains(prefix) && (suffix.length() == 0 || isValidSequence(suffix, dictionary, outputList))) {
                outputList.add(prefix);
                isValid = true;
                break;
            }
        }
        return isValid; 
    }
}
