package iponom.doublet;

import org.junit.Before;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

/**
 * @author Ilya Ponomarev.
 */
public class ParallelStreamDuplicatesFinderTest {

    private static final String ROOT = "src/test/resources/root";

    ParallelStreamDuplicatesFinder duplicatesFinder;

    @Before
    public void setUp() throws Exception {
        duplicatesFinder = new ParallelStreamDuplicatesFinder();
    }

    @Test
    public void search() throws Exception {
        Path dir = Paths.get(ROOT);
        Stream<List<String>> stream = duplicatesFinder.search(dir);
        assertNotNull(stream);
        List<List<String>> list = stream.collect(Collectors.toList());
        assertEquals(1, list.size());
        List<String> files = list.get(0);
        assertEquals(2, files.size());
        assertTrue(files.get(0).length() < files.get(1).length());
    }

}