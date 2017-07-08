package ocelot.classfile;

import java.io.IOException;
import ocelot.Utils;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author ben
 */
public class TestClassEntry {
    
    @Test
    public void check_valid_header() throws IOException, ClassNotFoundException {
        String fName = "Println.class";
        byte[] buf = Utils.pullBytes(fName);
        ClassEntry ce = new ClassEntry(buf, fName);
        ce.parseHeader();
        assertEquals("Major version should be 52", 52, ce.getMajor());
        assertEquals("Minor version should be 0", 0, ce.getMinor());
        assertEquals("Constant Pool should contain 34 items", 34, ce.getPoolItems());
        ce.parseConstantPool();
        
        fName = "optjava/bc/SimpleTests.class";
        buf = Utils.pullBytes(fName);
        ce = new ClassEntry(buf, fName);
        ce.parseHeader();
        assertEquals("Major version should be 52", 52, ce.getMajor());
        assertEquals("Minor version should be 0", 0, ce.getMinor());
        assertEquals("Constant Pool should contain 21 items", 21, ce.getPoolItems());
        ce.parseConstantPool();
    }
    
}
