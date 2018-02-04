package ocelot;

import ocelot.rt.JVMObj;
import ocelot.rt.OCKlass;

/**
 *
 * @author ben
 */
public interface JVMStorage {

    public long allocateObj(OCKlass klass);

    public JVMObj findObject(long id);

    public void runGC();

}
