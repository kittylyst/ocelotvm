package octest;

public class UseMyI {

    public static int run() {
        MyInteger mi = new MyInteger(42);
        return mi.value;
    }

    public static int run2() {
        MyInteger mi = new MyInteger(1337);
        IndirectMyI imi = new IndirectMyI(mi);
        return imi.mi.value;
    }

}
