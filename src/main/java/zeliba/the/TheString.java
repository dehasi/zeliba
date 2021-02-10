package zeliba.the;

import static java.util.Objects.requireNonNull;

class TheString {

    private final String string;

    private TheString(String string) {
        this.string = requireNonNull(string);
    }

    public static TheString the(String string) {
        return new TheString(string);
    }

    public String substring(int fromIncluded, int toExluded) {
        if (fromIncluded < 0)
            fromIncluded = 0;

        if (toExluded > string.length())
            toExluded = string.length();

        if (toExluded - fromIncluded < 0)
            return "";

        return string.substring(fromIncluded, toExluded);
    }
}
