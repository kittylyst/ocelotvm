package octest;

/**
 *
 * @author ben
 */
public class StaticCallsDouble {

    public static void main(String[] args) {
        call3(46);
    }

    public static double call1() {
        return call2();
    }

    private static double call2() {
        return 23.0;
    }

    public static double call3(double i) {
        return ++i;
    }

    public static double call4() {
        return call3(46);
    }

    public static double adder(double i, double j) {
        return i + j;
    }
}
