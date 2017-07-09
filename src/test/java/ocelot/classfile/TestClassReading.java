package ocelot.classfile;

import java.io.IOException;
import ocelot.Utils;
import static org.junit.Assert.*;
import org.junit.Test;
import static ocelot.classfile.CPType.*;

/**
 *
 * @author ben
 */
public class TestClassReading {
    
    @Test
    public void check_valid_header() throws IOException, ClassNotFoundException {
        String fName = "Println.class";
        byte[] buf = Utils.pullBytes(fName);
        OcelotClassReader ce = new OcelotClassReader(buf, fName);
        ce.parseHeader();
        assertEquals("Major version should be 52", 52, ce.getMajor());
        assertEquals("Minor version should be 0", 0, ce.getMinor());
        assertEquals("Constant Pool should contain 34 items", 34, ce.getPoolItemCount());
        ce.parseConstantPool();
        CPEntry cp = ce.getCPEntry(1);
        assertEquals("Entry 1 should be a METHODREF", METHODREF, cp.getType());
        int other = cp.getRef().getOther();
        assertEquals("Entry 1 should point at Entry 6", 6, other);
        CPEntry cp2 = ce.getCPEntry(other);
        assertEquals("Entry 6 should be a CLASS", CLASS, cp2.getType());
        other = cp2.getRef().getOther();
        assertEquals("Entry 6 should point at Entry 27", 27, other);
        cp2 = ce.getCPEntry(other);
        assertEquals("Entry 27 should be a UTF8", UTF8, cp2.getType());
        assertEquals("Entry 27 should be java/lang/Object", "java/lang/Object", cp2.getStr());
        
        fName = "optjava/bc/SimpleTests.class";
        buf = Utils.pullBytes(fName);
        ce = new OcelotClassReader(buf, fName);
        ce.parseHeader();
        assertEquals("Major version should be 52", 52, ce.getMajor());
        assertEquals("Minor version should be 0", 0, ce.getMinor());
        assertEquals("Constant Pool should contain 21 items", 21, ce.getPoolItemCount());
        ce.parseConstantPool();
    }
    
}
