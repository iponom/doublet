package iponom.doublet;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Ilya Ponomarev.
 */
public class FileUtils {

    private static int DEFAULT_BUFFER_SIZE = 8192 * 4;

    /**
     * Files have to have the same size
     * @param first - path to the first file
     * @param second - path to the second file
     * @return true if files have the same content
     */
    public static boolean equals(Path first, Path second) {
        try (
                InputStream is1 = new BufferedInputStream(Files.newInputStream(first), DEFAULT_BUFFER_SIZE);
                InputStream is2 = new BufferedInputStream(Files.newInputStream(second), DEFAULT_BUFFER_SIZE)
        ) {
            byte[] arr1 = new byte[DEFAULT_BUFFER_SIZE];
            byte[] arr2 = new byte[DEFAULT_BUFFER_SIZE];
            while (true) {
                int count1 = is1.read(arr1);
                int count2 = is2.read(arr2);
                if (count1 < 0 || count2 < 0) break;
                for (int i = 0; i < count1; i++) {
                    if (arr1[i] != arr2[i]) return false;
                }
            }
            return true;
        } catch (IOException e) {
            throw new DoubletException(e);
        }
    }

}
