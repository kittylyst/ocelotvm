package ocelot.classfile;

import java.io.IOException;
import java.util.List;
import ocelot.Utils;
import static org.junit.Assert.*;
import org.junit.Test;
import static ocelot.classfile.CPType.*;

/**
 *
 * @author ben
 */
public class TestClassReading {

    private OcelotClassReader ce;
    private byte[] buf;
    
    @Test
    public void check_cp_for_hello_world() throws IOException, ClassNotFoundException {
        String fName = "Println.class";
        buf = Utils.pullBytes(fName);
        ce = new OcelotClassReader(buf, fName);
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

//        for (int i =1; i < ce.getPoolItemCount(); i++) {
//            System.out.println(ce.resolveAsString(i));
//        }
        assertEquals("Entry 1 should be Object's ctor", "java/lang/Object.<init>:()V", ce.resolveAsString(1));
        ce.parseBasicTypeInfo();
        assertTrue(fName+" should be public", ce.isPublic());
        assertFalse(fName+" should not be abstract", ce.isAbstract());
        assertFalse(fName+" should not be annotation", ce.isAnnotation());
        
        CPEntry self = ce.getThisClass();
        String toStr = ce.resolveAsString(self.getIndex());
        assertEquals("Self class should resolve to kathik/Println", "kathik/Println", toStr);
        CPEntry superClz = ce.getSuperClass();
        toStr = ce.resolveAsString(superClz.getIndex());
        assertEquals("Superclass should resolve to java/lang/Object", "java/lang/Object", toStr);   
    }

    @Test
    public void check_simple_fields() throws IOException, ClassNotFoundException {
        String fName = "octest/SimpleFieldsAndMethods.class";
        byte[ ]buf = Utils.pullBytes(fName);
        ce = new OcelotClassReader(buf, fName);
        ce.parseHeader();
        assertEquals("Major version should be 52", 52, ce.getMajor());
        assertEquals("Minor version should be 0", 0, ce.getMinor());
        assertEquals("Constant Pool should contain 24 items", 24, ce.getPoolItemCount());
        ce.parseConstantPool();
        
        ce.parseBasicTypeInfo();
        assertTrue(fName+" should be public", ce.isPublic());
        assertFalse(fName+" should not be abstract", ce.isAbstract());
        assertFalse(fName+" should not be annotation", ce.isAnnotation());
        
        ce.parseFields();
        List<OcelotClassReader.CPField> fields = ce.getFields();
        assertEquals(fName+ " should have 1 field", 1, fields.size());
        int idx = fields.get(0).getNameIndex();
        assertEquals(fName+ " should have a field called a", "a", ce.getCPEntry(idx).getStr());
        idx = fields.get(0).getDescIndex();
        assertEquals(fName+ " should have a field called a of type I", "I", ce.getCPEntry(idx).getStr());
    }

}
