package ru.ilka.leetcode.entity;

import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringsWithNumbersComparator implements Comparator<String> {

    private static final Pattern NUMBER_PATTERN = Pattern.compile("[0-9]+");

    @Override
    public int compare(String a, String b) {
        Matcher matcherA = NUMBER_PATTERN.matcher(a);
        Matcher matcherB = NUMBER_PATTERN.matcher(b);

        while (matcherA.find() && matcherB.find()) {
            int aStartIdx = matcherA.start();
            int bStartIdx = matcherB.start();

            if (a.substring(0, aStartIdx).equals(b.substring(0, bStartIdx))) {
                int aNumber = Integer.parseInt(matcherA.group());
                int bNumber = Integer.parseInt(matcherB.group());
                if (aNumber != bNumber) {
                    return aNumber - bNumber;
                }
            } else {
                return a.compareToIgnoreCase(b);
            }
        }

        return a.compareToIgnoreCase(b);
    }

}
