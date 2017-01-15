package iponom.doublet;

import lombok.AllArgsConstructor;

import java.nio.file.Path;
import java.util.Map;

/**
 * @author Ilya Ponomarev.
 */
@AllArgsConstructor
public class PathEntry implements Map.Entry<GroupKey, Path> {

    private GroupKey key;
    private Path value;

    @Override
    public GroupKey getKey() {
        return key;
    }

    @Override
    public Path getValue() {
        return value;
    }

    @Override
    public Path setValue(Path value) {
        this.value = value;
        return value;
    }
}
