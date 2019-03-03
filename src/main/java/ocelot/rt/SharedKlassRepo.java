package ocelot.rt;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Holds all the loaded classes to date. Operations should be concurrent safe so
 * any data races are benign.
 *
 * @author ben
 */
public final class SharedKlassRepo {

    public static final String OBJSIG = "java/lang/Object";
    public static final String STRSIG = "java/lang/String";
    public static final String SYSSIG = "java/lang/System";

    private final ConcurrentMap<String, OtKlass> loadedClasses = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, OtMethod> methodCache = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, OtField> fieldCache = new ConcurrentHashMap<>();
    
    private SharedKlassRepo() {
    }

    public static SharedKlassRepo of() {
        final SharedKlassRepo out = new SharedKlassRepo();
        // FIXME Object::<init> needs to be in the method cache.
        OtMethod m = OtMethod.OBJ_INIT;
        out.methodCache.put(m.getClassName() + "." + m.getNameAndType(), m);

        // Add System
        
        
        return out;
    }

//    private OtKlass makeSystem() {
//        final OtKlass out = new OtKlass(SYSSIG);
//        final OtField f =
//        out.addField(null);
//        return out; 
//    }
    
    public OtKlass newKlass(String toCreate) {
        return loadedClasses.get(toCreate);
    }

    public OtMethod lookupMethodExact(final String className, final short cpIndex) {
        OtKlass klass = loadedClasses.get(className);
        String otherMethodName = klass.getMethodNameByCPIndex(cpIndex);
        OtMethod out = methodCache.get(otherMethodName);
        if (out == null)
            throw new IllegalStateException("Method of signature "+ otherMethodName +" from index "+ cpIndex +" not found on class "+ className);

        return out;
    }

    public OtMethod lookupMethodVirtual(final String className, final short cpIndex) {
        OtKlass klass = loadedClasses.get(className);
        String otherMethodName = klass.getMethodNameByCPIndex(cpIndex);
        OtMethod out = methodCache.get(otherMethodName);
        if (out != null)
            return out;

        while (klass != null) {
            klass = loadedClasses.get(klass.getParent());
            out = methodCache.get(otherMethodName);
            if (out != null)
                return out;
        }

        throw new IllegalStateException("Method of signature "+ otherMethodName +" from index "+ cpIndex +" not found in virtual lookup on class "+ className);
    }


    public void add(OtKlass klass) {
        loadedClasses.put(klass.getName(), klass);
        for (OtMethod m : klass.getMethods()) {
            methodCache.put(m.getClassName() + "." + m.getNameAndType(), m);
        }
        for (OtField f : klass.getFields()) {
            fieldCache.put(f.getKlass().getName() +"." + f.getName() +":"+ f.getType(), f);
        }
    }

    public OtKlass lookupKlass(final String className, final short cpIndex) {
        OtKlass klass = loadedClasses.get(className);
        String otherKlassName = klass.getKlassNameByCPIndex(cpIndex);
        return loadedClasses.get(otherKlassName);
    }

    public OtField lookupField(final String className, final short cpIndex) {
        OtKlass klass = loadedClasses.get(className);
        String fieldName = klass.getFieldByCPIndex(cpIndex);
        
        return fieldCache.get(fieldName);
    }
}
