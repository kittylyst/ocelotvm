package octest;

/**
 *
 * @author ben
 */
public class StaticCalls {

    private static int j;
    
    public static void main(String[] args) {
        call3(46);
    }

    public static int call1() {
        return call2();
    }

    private static int call2() {
        return 23;
    }

    public static int call3(int i) {
        return ++i;
    }

    public static int call4() {
        return call3(46);
    }

    public static int getJ() {
        return j;
    }
    
    public static void incJ() {
        j++;
    }
    
    public static void setJ(int k) {
        j = k;
    }
    
    public static int adder(int i, int j) {
        return i + j;
    }
}
