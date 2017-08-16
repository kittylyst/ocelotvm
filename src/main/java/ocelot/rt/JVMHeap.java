package ocelot.rt;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * The JVMHeap takes in object IDs (longs) such as are stored in JVMValue and
 * returns the actual object corresponding to the ID.
 * 
 * @author ben
 */
public class JVMHeap {
    private static final AtomicLong objCounter = new AtomicLong(1L);
    private List<JVMObj> heap = new LinkedList<>();

    public JVMObj newObj(String klzStr) {
        JVMObj o = JVMObj.of(klzStr, objCounter.getAndIncrement());
        heap.add(o);
        return o;
    }

    public JVMObj findObject(long id) {
        for (JVMObj o : heap) {
            if (o.getId() == id)
                return o;
        }
        return null;
    }
 
    // FIXME Synchronization
    public void runGC() {
        // Clear the mark bit
        for (JVMObj o : heap) {
            o.getMark().setLive(false);
        }
        
        // Scan from roots
        // FIXME
        
        // Sweep
        for (JVMObj o : heap) {
            if (!o.getMark().isLive()) {
                // Remove dead object
            }
        }
        
    }
    
}
