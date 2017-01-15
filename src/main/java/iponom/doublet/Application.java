package iponom.doublet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Ilya Ponomarev.
 */
public class Application {

    public static final String RESULT_FILE_NAME = "result.txt";

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Set path to a directory as the first prameter");
            return;
        }
        try {
            Path path = Paths.get(args[0]);
            if (Files.isDirectory(path)) {
                DuplicatesFinder duplicatesFinder =  new ParallelStreamDuplicatesFinder();
                Stream<List<String>> stream = duplicatesFinder.search(path);
                List<String> result = stream
                        //.filter((list) -> list != null && list.size() > 0)
                        .flatMap((list) -> Stream.concat(list.stream(), Stream.of("")))
                        .collect(Collectors.toList());
                Files.write(Paths.get(RESULT_FILE_NAME), result);
                System.out.println("The search has been finished. See " + RESULT_FILE_NAME);
            } else {
                System.err.println(args[0] + " is not a directory");
            }
        } catch (InvalidPathException e) {
            System.err.println(args[0] + " is an invalid path");
        } catch (IOException e) {
            System.err.println("Can't write result to file " + RESULT_FILE_NAME);
        }
    }


}
