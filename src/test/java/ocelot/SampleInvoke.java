package ocelot;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ben
 */
public class SampleInvoke {

    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        HashMap<String, String> hm = new HashMap<>();
        hm.put("now", "bar");
        Map<String, String> m = hm;
        m.put("foo", "baz");
    }
    
}

