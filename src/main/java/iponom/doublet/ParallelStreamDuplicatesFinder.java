package iponom.doublet;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Ilya Ponomarev.
 */
public class ParallelStreamDuplicatesFinder implements DuplicatesFinder {
    private static final int PREFIX_SIZE = 128;

    public ParallelStreamDuplicatesFinder() {
        this.pathHashGenerator = new PathHashGenerator();
    }

    private PathHashGenerator pathHashGenerator;

    @Override
    public Stream<List<String>> search(Path directory) throws IOException {
        Map<GroupKey, List<PathEntry>> map = Files.walk(directory).parallel()
                .filter((path) -> !Files.isDirectory(path))
                .map(this::createPathEntry)
                .collect(Collectors.groupingBy((entry) -> entry.getKey()));
        return map.entrySet().stream().parallel().filter((entry) -> entry.getValue().size() > 1)
                .map((listEntry) -> listEntry.getValue().stream()
                        .map((pathEntry) -> directory.relativize(pathEntry.getValue()))
                        .sorted(Comparator.comparing(Path::getNameCount))
                        .map((path) -> path.toString()).collect(Collectors.toList())
                )
                .sorted(Comparator.comparing((list) -> list.get(0)));
    }

    private PathEntry createPathEntry(Path path) {
        try {
            long size = Files.size(path);
            long hash = pathHashGenerator.pathHash(path);
            byte[] prefix = getPrefix(path, size > PREFIX_SIZE ? PREFIX_SIZE : (int) size);
            return new PathEntry(new GroupKey(size, hash, prefix), path);
        } catch (IOException e) {
            throw new DoubletException(e);
        }
    }

    private byte[] getPrefix(Path path, int size) throws IOException {
        if (size < 1) return null;
        byte[] arr = new byte[size];
        try (InputStream is = new BufferedInputStream(Files.newInputStream(path), size)) {
            is.read(arr);
            return arr;
        }
    }
}
