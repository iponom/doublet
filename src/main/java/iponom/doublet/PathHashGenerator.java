package iponom.doublet;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.common.hash.HashingInputStream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Ilya Ponomarev.
 */
public class PathHashGenerator {

    private HashFunction hashFunction;

    public PathHashGenerator() {
        this.hashFunction = Hashing.goodFastHash(256);
    }

    public long pathHash(Path path) {
        try (HashingInputStream hashingInputStream = new HashingInputStream(hashFunction, Files.newInputStream(path))) {
            return hashingInputStream.hash().asLong();
        } catch (IOException e) {
            throw new DoubletException(e);
        }
    }


}
