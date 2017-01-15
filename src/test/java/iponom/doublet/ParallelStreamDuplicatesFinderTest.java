package iponom.doublet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

/**
 * @author Ilya Ponomarev.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class)
@TestPropertySource(properties = {"doublet.parallelism=4"})
public class ParallelStreamDuplicatesFinderTest {

    private static final String ROOT = "src/test/resources/root";

    @Configuration
    static class ContextConfiguration {

        @Bean
        PathHashGenerator pathHashGenerator() {
            return new PathHashGenerator();
        }

        @Bean
        ParallelStreamDuplicatesFinder duplicatesFinder() {
            return new ParallelStreamDuplicatesFinder();
        }

    }

    @Autowired
    ParallelStreamDuplicatesFinder duplicatesFinder;

    @Before
    public void setUp() throws Exception {
        duplicatesFinder.init();
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
