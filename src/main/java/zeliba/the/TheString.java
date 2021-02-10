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

    public String substring(int fromIncluded, int toExcluded) {
        if (fromIncluded < 0)
            fromIncluded = 0;

        if (toExcluded > string.length())
            toExcluded = string.length();

        if (toExcluded - fromIncluded < 0)
            return "";

        return string.substring(fromIncluded, toExcluded);
    }
}
