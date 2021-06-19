package zeliba.the;

import static java.lang.Character.isWhitespace;

class TheString {

    private final String string;

    private TheString(String string) {
        this.string = string != null ? string : "";
    }

    public static TheString the(String string) {
        return new TheString(string);
    }

    public boolean isEmpty() {
        return string.isEmpty();
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }

    public boolean isBlank() {
        for (int i = 0; i < string.length(); i++)
            if (!isWhitespace(string.charAt(i))) return false;

        return true;
    }

    public boolean isNotBlank() {
        return !isBlank();
    }

    public String substring(int fromIncluded, int toExcluded) {
        if (fromIncluded < 0) fromIncluded = 0;

        if (toExcluded > string.length()) toExcluded = string.length();

        if (toExcluded - fromIncluded < 0) return "";

        return string.substring(fromIncluded, toExcluded);
    }
}
