/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

    public static final String OBJSIG = "Ljava/lang/Object;";
    public static final String STRSIG = "Ljava/lang/String;";

//    private static final ConcurrentMap<String, JVMKlass> loadedClasses = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, OCKlass> loadedClasses = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, OCMethod> methodCache = new ConcurrentHashMap<>();

    public ClassRepository() {
    }

    // TEST: Put Object & String in the cache
    static {
//        JVMKlass.Bldr b = new JVMKlass.Bldr();
//        JVMKlass obj = b.name(OBJSIG).parent(null).storage(0).build();
//        loadedClasses.put(OBJSIG, obj);
//        // Now String
//        loadedClasses.put(STRSIG, b.name(STRSIG).parent(obj).storage(3).build());
    }

    public OCKlass newKlass(String toCreate) {
        return loadedClasses.get(toCreate);
    }

    public OCMethod lookupMethod(final String className, final short cpIndex) {
        OCKlass klass = loadedClasses.get(className);
        return klass.getMethodByCPIndex(cpIndex);
        // FIXME Fully qualified name... 
//        return clz.getMethodByName(methName);

//        CPEntry cpe = klass.getCPEntry(cpIndex);
//        String methName = klass.resolveAsString(cpe.getIndex());
//        return methodCache.get(methName);
    }

    public void add(OCKlass klass) {
        loadedClasses.put(klass.getName(), klass);
        for (OCMethod m : klass.getMethods()) {
            methodCache.put(m.getClassName() + "." + m.getNameAndType(), m);
        }
    }

    public OCKlass lookupKlass(final String className, final short cpIndex) {
        OCKlass klass = loadedClasses.get(className);
        return klass.getKlassByCPIndex(cpIndex);
//        CPEntry cpe = klass.getCPEntry(cpIndex);
//        String klassName = klass.resolveAsString(cpe.getIndex());
//
//        OCKlass out = loadedClasses.get(klassName);
//        return out;
    }

}
