package ocelot.rt;

import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author ben
 */
public class JVMHeap {

    private static final AtomicLong objCounter = new AtomicLong(0);
    
    public static Object newObj(String klzStr) {
        return new JVMObj(klzStr, objCounter.getAndIncrement());
    }
    
}
