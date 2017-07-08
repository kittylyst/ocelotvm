package ocelot.classfile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.objectweb.asm.ClassReader;

/**
 *
 * @author ben
 */
public final class OcelotReader {

    private final Path classDir;
    
    public OcelotReader(String pathToClasses) {
        classDir = Paths.get(pathToClasses);
    }
    
    public ClassEntry scan(String cName) throws IOException {
        final Path clzPath = classNameToPath(cName);
        final byte[] buf = Files.readAllBytes(clzPath);
        ClassEntry out = null;
        try (final InputStream in = Files.newInputStream(clzPath)) {
            try {
                final ClassReader cr = new ClassReader(in);
                out = new ClassEntry(buf, clzPath.toString());
//                out.init(cr);
            } catch (Exception e) {
                throw new IOException("Could not read class file "+ clzPath, e);
            }
        }
        return out;
    }

    public static Path classNameToPath(String classname) {
        return null;
    }
    
}
