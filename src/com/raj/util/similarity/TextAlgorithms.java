package com.raj.util.similarity;

public class TextAlgorithms {

    public static String longestSubstring(String str1, String str2) {

        StringBuilder sb = new StringBuilder();
        if (str1 == null || str1.isEmpty() || str2 == null || str2.isEmpty())
            return "";

        // ignore case
        str1 = str1.toLowerCase();
        str2 = str2.toLowerCase();

        // java initializes them already with 0
        int[][] num = new int[str1.length()][str2.length()];
        int maxlen = 0;
        int lastSubsBegin = 0;

        for (int i = 0; i < str1.length(); i++) {
            for (int j = 0; j < str2.length(); j++) {
                if (str1.charAt(i) == str2.charAt(j)) {
                    if ((i == 0) || (j == 0))
                        num[i][j] = 1;
                    else
                        num[i][j] = 1 + num[i - 1][j - 1];

                    if (num[i][j] > maxlen) {
                        maxlen = num[i][j];
                        // generate substring from str1 => i
                        int thisSubsBegin = i - num[i][j] + 1;
                        if (lastSubsBegin == thisSubsBegin) {
                            // if the current LCS is the same as the last time
                            // this block ran
                            sb.append(str1.charAt(i));
                        } else {
                            // this block resets the string builder if a
                            // different LCS is found
                            lastSubsBegin = thisSubsBegin;
                            sb = new StringBuilder();
                            sb.append(str1.substring(lastSubsBegin, i + 1));
                        }
                    }
                }
            }
        }

        return sb.toString();
    }
    
    public boolean fuzzyContains(String text, String pattern, double errorTolerance) {
        String commonString = longestSubstring(text, pattern);
        int textIndex = text.indexOf(commonString);
        int patternIndex = pattern.indexOf(commonString);
        int prefixSize = patternIndex;
        String commonPatternString = text.substring(textIndex - prefixSize, textIndex + pattern.length() - prefixSize);
        System.out.println("'" + commonPatternString + "' compared with '" + pattern + "'");
        double r = StringRank.compareStrings(commonPatternString, pattern);
        return r + errorTolerance >= 1 ? true : false;
    }
    
    public double contains(String text, String pattern) {
        String commonString = longestSubstring(text, pattern);
        int textIndex = text.indexOf(commonString);
        int patternIndex = pattern.indexOf(commonString);
        int prefixSize = patternIndex;
        String commonPatternString = text.substring(textIndex - prefixSize, textIndex + pattern.length() - prefixSize);
        System.out.println("'" + commonPatternString + "' compared with '" + pattern + "'");
        return StringRank.compareStrings(commonPatternString, pattern);
    }
    
    public static void main(String[] args) {
        System.out.println(StringRank.compareStrings("good movie", "A movie"));
        boolean d = new TextAlgorithms().fuzzyContains("This is a good movies in the ", "not god movie", 0.2);
        System.out.println(d);
        double d2 = new TextAlgorithms().contains("This is a good movie", "not god movie");
        System.out.println(d2);

    }
}
