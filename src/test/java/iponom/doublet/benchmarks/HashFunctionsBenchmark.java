package iponom.doublet.benchmarks;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.common.hash.HashingInputStream;
import iponom.doublet.FileUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Warmup;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**

 http://openjdk.java.net/projects/code-tools/jmh/
 idea plugin - https://github.com/artyushov/idea-jmh-plugin/blob/master/README.md
 To avoid error with access to C:\Windows use TMP variable
 http://stackoverflow.com/questions/37720692/exception-while-trying-to-acquire-a-jmh-lock

 Benchmark                                        Mode  Cnt      Score      Error  Units
 HashFunctionsBenchmark.measureApacheCommonsMd5  thrpt   50      0,125 ±    0,001  ops/s
 HashFunctionsBenchmark.measureGetSize           thrpt   50  75775,306 ±  616,851  ops/s
 HashFunctionsBenchmark.measureGuavaFastHash     thrpt   50  52614,099 ± 1204,642  ops/s
 HashFunctionsBenchmark.measureGuavaFastHash64   thrpt   50  51265,305 ± 2048,213  ops/s
 HashFunctionsBenchmark.measureCompare           thrpt   20      0,089 ±    0,001  ops/s
 HashFunctionsBenchmark.measureLoop              thrpt   20      1,591 ±    0,067  ops/s
 HashFunctionsBenchmark.measureCustomCompare     thrpt   20      0,489 ±    0,036  ops/s

 *
 * @author Ilya Ponomarev.
 */
public class HashFunctionsBenchmark {

    private final static String FILE_1 = "c:/Temp/test1.avi";
    private final static String FILE_2 = "c:/Temp/test2.avi";

   @Benchmark
    @Warmup(iterations = 3)
    @Measurement(iterations = 5)
    public void measureGuavaFastHash64() throws IOException {
       HashFunction hashFunction = Hashing.goodFastHash(64);
       try (HashingInputStream hashingInputStream = new HashingInputStream(hashFunction, inputStream())) {
           hashingInputStream.hash().asInt();
       }
    }

    @Benchmark
    @Warmup(iterations = 3)
    @Measurement(iterations = 5)
    public void measureGuavaFastHash32() throws IOException {
        HashFunction hashFunction = Hashing.goodFastHash(32);
        try (HashingInputStream hashingInputStream = new HashingInputStream(hashFunction, inputStream())) {
            hashingInputStream.hash().asInt();
        }
    }

    @Benchmark
    @Warmup(iterations = 2)
    @Measurement(iterations = 2)
    public void measureCompare() throws IOException {
        try (InputStream is1 = inputStream(); InputStream is2 = anotherInputStream()) {
            IOUtils.contentEquals(is1, is2);
        }
    }

    @Benchmark
    @Warmup(iterations = 3)
    @Measurement(iterations = 5)
    public void measureApacheCommonsMd5() throws IOException {
        DigestUtils.md5Hex(inputStream());
    }

    @Benchmark
    @Warmup(iterations = 3)
    @Measurement(iterations = 5)
    public void measureGetSize() throws  IOException{
        Files.size(Paths.get(FILE_1));
    }

    @Benchmark
    @Warmup(iterations = 2)
    @Measurement(iterations = 2)
    public void measureLoop() throws IOException {
        long someNumber = 3412;
        long size = Files.size(Paths.get(FILE_1));
        for (long i = 0; i < size; i++) {
            if (i == someNumber) callMe();
        }
    }

    @Benchmark
    @Warmup(iterations = 2)
    @Measurement(iterations = 2)
    public void measureCustomCompare() throws IOException {
        FileUtils.equals(Paths.get(FILE_1), Paths.get(FILE_2));
    }

    private InputStream inputStream() throws IOException {
        return Files.newInputStream(Paths.get(FILE_1));
    }

    private InputStream anotherInputStream() throws IOException {
        return Files.newInputStream(Paths.get(FILE_2));
    }

    private void callMe() {};
}
