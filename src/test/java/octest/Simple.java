package octest;

/**
 *
 * @author ben
 */
public class Simple {

    public static void main(String[] args) {
        simple(); 
    }

    private static int simple() {
        Simple s = new Simple();
        
        return 16 + s.init();
    }

    private int init() {
        return 7;
    }
    
}
