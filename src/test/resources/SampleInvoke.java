public class SampleInvoke {

  public static int bar() {
    return 7;
  }

  public static int foo() {
    int i = 2;
    i = i + bar();
    return i;
  }

  public static void main(String[] args) {
    foo();
  }
}
