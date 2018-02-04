package ocelot;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author ben
 */
public class Utils {

    public static byte[] pullBytes(String fName) throws IOException {
        try (final InputStream is = TestIntArithmetic.class.getClassLoader().getResourceAsStream(fName);final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[4096];
            for (;;) {
                int nread = is.read(buffer);
                if (nread <= 0) {
                    break;
                }
                baos.write(buffer, 0, nread);
            }
            return baos.toByteArray();
        }
    }

}
