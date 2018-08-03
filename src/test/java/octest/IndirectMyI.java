package octest;

public final class IndirectMyI {
    public final MyInteger mi;

    public IndirectMyI(MyInteger m) {
        mi = m;
    }

    public MyInteger getMi() {
        return mi;
    }
}
