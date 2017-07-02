package ocelot.rt;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author ben
 */
public class JVMHeap {


    private static final AtomicLong objCounter = new AtomicLong(1L);
    private List<JVMObj> heap = new LinkedList<>();

    public JVMObj newObj(String klzStr) {
        JVMObj o = new JVMObj(klzStr, objCounter.getAndIncrement());
        heap.add(o);
        return o;
    }

    public void delete(JVMObj o) {
        // No GC
    }
    
    public JVMObj findObject(long id) {
        for (JVMObj o : heap) {
            if (o.getId() == id)
                return o;
        }
        return null;
    }
    
}
