package com.hci.hciresearchprojectapp;

public class TextCompare {



    public static double getLevenshteinDistance(String textField, String typeField)
    {
        String longer= textField, shorter=typeField;
        if(textField.length() < typeField.length()) {
            longer = typeField;
            shorter = textField;
        }
        int longerLength = longer.length();
        if (longerLength == 0)
        {
            return 1.0;
        }
        return ((longerLength - CheckDistance(longer,shorter))/ (double) longerLength)*100;
    }
    public static int CheckDistance(String textField, String typeField)
    {
        textField=textField.toLowerCase();
        typeField=typeField.toLowerCase();

        int[] costs = new int[typeField.length() + 1];
        for (int i = 0; i <= textField.length(); i++) {
            int lastValue = i;
            for (int j = 0; j <= typeField.length(); j++) {
                if (i == 0)
                    costs[j] = j;
                else {
                    if (j > 0) {
                        int newValue = costs[j - 1];
                        if (textField.charAt(i - 1) != typeField.charAt(j - 1))
                            newValue = Math.min(Math.min(newValue, lastValue),
                                    costs[j]) + 1;
                        costs[j - 1] = lastValue;
                        lastValue = newValue;
                    }
                }
            }
            if (i > 0)
                costs[typeField.length()] = lastValue;
        }
        return costs[typeField.length()];
    }
}