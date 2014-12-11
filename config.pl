#!/usr/bin/perl



use strict;

my $PKG = "org.adoptopenjdk.ocelotvm";
my $SUB_DIR = $PKG;
$SUB_DIR =~ s/\./\//g;

my $SRC_DIR = "./src/main/java/$SUB_DIR";
my $OPCODES = "opcodes.txt";

my $OP_FILE = "Opcodes.java";
my $MAIN_FILE = "Main.java";


my $main_text = <<HEAD;
package $PKG;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static $PKG.Opcodes.*;

/**
 */
public class Main {

    /**
     * \@param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        byte[] buffy = Files.readAllBytes(Paths.get(args[0]));
	Main m = new Main();
        m.interpret(buffy);
    }

    private void interpret(byte[] buffy) {
        int i = 0;
        while (i < buffy.length) {
            switch (buffy[i++]) {

HEAD

my $op_text = <<OPS;
package org.adoptopenjdk.ocelotvm;

/**
 */
public class Opcodes {
OPS



open(FILE, "<", $OPCODES) || die("Can't open $OPCODES \n");
open(MAIN, ">", "$SRC_DIR/$MAIN_FILE") || die("Can't open $MAIN_FILE for writing \n");
open(OPS, ">", "$SRC_DIR/$OP_FILE") || die("Can't open $OP_FILE for writing \n");

print OPS $op_text;
print MAIN $main_text;

# cat opcodes.txt.dst | awk '{print "public static final byte " $1 "=" (byte) $2 ";"}'
# cat opcodes.txt.dst | awk '{print "case " $1 ":\ncall_" $1 "();\nbreak;"}'
my $interp_text = "";
my $methods;
while (<FILE>) {
    chomp;
    my ($name, $val) = split /\s+/; 
    print OPS "\tpublic static final byte $name = (byte) $val;\n";
    my $dispatch_name = "do_". lc($name);
    $interp_text .= <<INTERP;
                case $name:
                    $dispatch_name();
                    break;
INTERP
    $methods .= <<METH;
    private void $dispatch_name() {
        System.out.println("$name not supported yet."); 
    }
METH
}
$interp_text .= <<INTERP_END;
            }
        }
    }
INTERP_END

print OPS "}\n";
print MAIN $interp_text;
print MAIN $methods;
print MAIN "}\n";

close(OP);
close(MAIN);
close(FILE);


1;
