package ocelot.rt;

import ocelot.JVMStorage;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * The SharedSimpleHeap takes in object IDs (longs) such as are stored in JVMValue and
 returns the actual object corresponding to the ID.
 * 
 * @author ben
 */
public class SharedSimpleHeap implements JVMStorage {

    private static final AtomicLong objCounter = new AtomicLong(1L);
    private List<OtObj> heap = new LinkedList<>();

    public long allocateObj(OtKlass klass) {
        OtObj o = OtObj.of(klass, objCounter.getAndIncrement());
        heap.add(o);
        return o.getId();
    }

    public OtObj findObject(long id) {
        for (OtObj o : heap) {
            if (o.getId() == id)
                return o;
        }
        return null;
    }

    // FIXME Synchronization
    public void runGC() {
        // Clear the mark bit
        for (OtObj o : heap) {
            o.getMark().setLive(false);
        }

        // Scan from roots
        // FIXME
        // Sweep
        for (OtObj o : heap) {
            if (!o.getMark().isLive()) {
                // Remove dead object
            }
        }

    }

}
