package ocelot.rt;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import ocelot.classfile.CPEntry;

/**
 * Holds all the loaded classes to date. Operations should be concurrent safe so
 * any data races are benign.
 *
 * @author ben
 */
public final class ClassRepository {

    public static final String OBJSIG = "java/lang/Object";
    public static final String STRSIG = "java/lang/String";
    public static final String SYSSIG = "java/lang/System";

    private final ConcurrentMap<String, OCKlass> loadedClasses = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, OCMethod> methodCache = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, OCField> fieldCache = new ConcurrentHashMap<>();
    
    private ClassRepository() {
    }

    public static ClassRepository of() {
        final ClassRepository out = new ClassRepository();
        // FIXME Object::<init> needs to be in the method cache.
        OCMethod m = OCMethod.OBJ_INIT;
        out.methodCache.put(m.getClassName() + "." + m.getNameAndType(), m);

        // Add System
        
        
        return out;
    }

//    private OCKlass makeSystem() {
//        final OCKlass out = new OCKlass(SYSSIG);
//        final OCField f = 
//        out.addField(null);
//        return out; 
//    }
    
    public OCKlass newKlass(String toCreate) {
        return loadedClasses.get(toCreate);
    }

    public OCMethod lookupMethodExact(final String className, final short cpIndex) {
        OCKlass klass = loadedClasses.get(className);
        String otherMethodName = klass.getMethodNameByCPIndex(cpIndex);
        OCMethod out = methodCache.get(otherMethodName);
        if (out == null)
            throw new IllegalStateException("Method of signature "+ otherMethodName +" from index "+ cpIndex +" not found on class "+ className);

        return out;
    }

    public OCMethod lookupMethodVirtual(final String className, final short cpIndex) {
        OCKlass klass = loadedClasses.get(className);
        String otherMethodName = klass.getMethodNameByCPIndex(cpIndex);
        OCMethod out = methodCache.get(otherMethodName);
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


    public void add(OCKlass klass) {
        loadedClasses.put(klass.getName(), klass);
        for (OCMethod m : klass.getMethods()) {
            methodCache.put(m.getClassName() + "." + m.getNameAndType(), m);
        }
        for (OCField f : klass.getFields()) {
            fieldCache.put(f.getKlass().getName() +"." + f.getName() +":"+ f.getType(), f);
        }
    }

    public OCKlass lookupKlass(final String className, final short cpIndex) {
        OCKlass klass = loadedClasses.get(className);
        String otherKlassName = klass.getKlassNameByCPIndex(cpIndex);
        return loadedClasses.get(otherKlassName);
    }

    public OCField lookupField(final String className, final short cpIndex) {
        OCKlass klass = loadedClasses.get(className);
        String fieldName = klass.getFieldByCPIndex(cpIndex);
        
        return fieldCache.get(fieldName);
    }
}
