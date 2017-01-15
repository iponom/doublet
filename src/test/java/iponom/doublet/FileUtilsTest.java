package iponom.doublet;

import org.junit.Test;

import java.nio.file.Paths;

import static org.junit.Assert.*;

/**
 * @author Ilya Ponomarev.
 */
public class FileUtilsTest {

    private static final String ROOT = "src/test/resources/root/";
    private static final String FILE_1 = "file01.txt";
    private static final String FILE_2 = "dir01/file01.txt";
    private static final String FILE_3 = "dir01/file02.txt";

    @Test
    public void checkEqual() throws Exception {
        assertTrue(FileUtils.equals(Paths.get(ROOT + FILE_1), Paths.get(ROOT + FILE_3)));
    }

    @Test
    public void checkNotEqual() throws Exception {
        assertFalse(FileUtils.equals(Paths.get(ROOT + FILE_2), Paths.get(ROOT + FILE_3)));

    }


}