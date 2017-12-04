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

    public static final String OBJSIG = "java/lang/Object";
    public static final String STRSIG = "java/lang/String";

//    private static final ConcurrentMap<String, JVMKlass> loadedClasses = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, OCKlass> loadedClasses = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, OCMethod> methodCache = new ConcurrentHashMap<>();

    private ClassRepository() {
    }

    public static ClassRepository of() {
        final ClassRepository out = new ClassRepository();
        // FIXME Object::<init> needs to be in the method cache.
        OCMethod m = OCMethod.OBJ_INIT;
        out.methodCache.put(m.getClassName() + "." + m.getNameAndType(), m);
        
        return out;
    }

    public OCKlass newKlass(String toCreate) {
        return loadedClasses.get(toCreate);
    }

    public OCMethod lookupMethod(final String className, final short cpIndex) {
        OCKlass klass = loadedClasses.get(className);
        String otherMethodName = klass.getMethodNameByCPIndex(cpIndex);

        return methodCache.get(otherMethodName);

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
        String otherKlassName = klass.getKlassNameByCPIndex(cpIndex);
        return loadedClasses.get(otherKlassName);
//        CPEntry cpe = klass.getCPEntry(cpIndex);
//        String klassName = klass.resolveAsString(cpe.getIndex());
//
//        OCKlass out = loadedClasses.get(klassName);
//        return out;
    }

}
