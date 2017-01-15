package iponom.doublet;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Ilya Ponomarev.
 */
public interface DuplicatesFinder {

    Stream<List<String>> search(Path directory);

}
