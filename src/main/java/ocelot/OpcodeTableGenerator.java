package ocelot;

import java.util.Arrays;

/**
 *
 * @author ben
 */
public class OpcodeTableGenerator {

    public static void main(String[] args) {
        OpcodeTableGenerator otg = new OpcodeTableGenerator();
        otg.run();
    }

    private void run() {
        Opcode[] table = new Opcode[256];
        for (Opcode op : Opcode.values()) {
            table[op.getOpcode()] = op;
        }
        System.out.println(Arrays.toString(table));
        String[] opNames = new String[Opcode.values().length];
        int i = 0;
        for (Opcode op : Opcode.values()) {
            opNames[i++] = op.name();
        }
        Arrays.sort(opNames);
        System.out.println(Arrays.toString(opNames));
   }
    
}
