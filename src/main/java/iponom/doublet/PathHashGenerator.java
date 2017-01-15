package iponom.doublet;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.common.hash.HashingInputStream;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Ilya Ponomarev.
 */
@Component
public class PathHashGenerator {

    private static final int MINIMUM_BITS = 256;

    private HashFunction hashFunction;

    public PathHashGenerator() {
        this.hashFunction = Hashing.goodFastHash(MINIMUM_BITS);
    }

    public long pathHash(Path path) {
        try (HashingInputStream hashingInputStream = new HashingInputStream(hashFunction, Files.newInputStream(path))) {
            return hashingInputStream.hash().asLong();
        } catch (IOException e) {
            throw new DoubletException(e);
        }
    }

}
