package octest;

public class UseMyI {

    public static int run() {
        MyInteger mi = new MyInteger(42);
        return mi.value;
    }

    public static int runC() {
        MyInteger mi = new MyInteger(42);
        return mi.getValue();
    }

    public static int run2() {
        MyInteger mi = new MyInteger(1337);
        IndirectMyI imi = new IndirectMyI(mi);
        return imi.mi.value;
    }

    public static int run2C() {
        MyInteger mi = new MyInteger(1337);
        IndirectMyI imi = new IndirectMyI(mi);
        return imi.getMi().getValue();
    }


}
