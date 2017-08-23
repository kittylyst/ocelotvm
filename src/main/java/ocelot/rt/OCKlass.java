package ocelot.rt;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ben
 */
public class OCKlass {

    private final String name;
    private final Map<String, OCMethod> methLookup = new HashMap<>();

    public OCKlass(String className, List<OCMethod> methods) {
        name = className;
        for (OCMethod m : methods) {
            methLookup.put(m.getNameAndType(), m);
        }
    }
    
    public OCMethod getMethodByName(String nameAndType) {
        return methLookup.get(nameAndType);
    }

    public long allocateObj() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Collection<OCMethod> getMethods() {
        return methLookup.values();
    }

    public String getName() {
        return name;
    }

    public OCMethod getMethodByCPIndex(short cpIndex) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public OCKlass getKlassByCPIndex(short cpIndex) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
