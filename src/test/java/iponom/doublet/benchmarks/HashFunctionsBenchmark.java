package iponom.doublet.benchmarks;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.common.hash.HashingInputStream;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Warmup;

import java.io.*;

/**
 * @author Ilya Ponomarev.
 */
public class HashFunctionsBenchmark {

    @Benchmark
    @Warmup(iterations = 3)
    @Measurement(iterations = 5)
    public void measureGuavaFastHash() throws IOException {
        HashFunction hashFunction = Hashing.goodFastHash(32);
        try (HashingInputStream hashingInputStream = new HashingInputStream(hashFunction, inputStream())) {
            long hashCode = hashingInputStream.hash().asLong();
        }
    }


    private InputStream inputStream() throws IOException {
        //TODO
        return new FileInputStream("c:/Temp/test.avi");
    }

}
