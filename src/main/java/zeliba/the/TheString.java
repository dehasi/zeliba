package zeliba.the;

class TheString {

    private final String string;

    private TheString(String string) {this.string = string;}

    public static TheString the(String string) {
        return new TheString(string);
    }

    public String substring(int beginIndex, int endIndex) {
        if (beginIndex < 0) {
            beginIndex = 0;
        }
        if (endIndex > string.length()) {
            endIndex = string.length();
        }
        int subLen = endIndex - beginIndex;
        if (subLen < 0) {
            return "";
        }

        return string.substring(beginIndex, endIndex);
    }
}
