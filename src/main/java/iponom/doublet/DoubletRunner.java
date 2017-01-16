package iponom.doublet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

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
@Component
@Slf4j
public class DoubletRunner implements ApplicationRunner {

    @Value("${doublet.result.file.name}")
    private String resultFileName;

    @Autowired
    private DuplicatesFinder duplicatesFinder;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        List<String> args = applicationArguments.getNonOptionArgs();
        if (args.size() == 0) {
            log.error("Set path to a directory as the first prameter");
            return;
        }
        try {
            Path path = Paths.get(args.get(0));
            if (Files.isDirectory(path)) {
                log.warn("Start searching");
                Stream<List<String>> stream = duplicatesFinder.search(path);
                List<String> result = stream
                        .flatMap((list) -> Stream.concat(list.stream(), Stream.of("")))
                        .collect(Collectors.toList());
                Files.write(Paths.get(resultFileName), result);
                log.info("Searching has been finished. See " + resultFileName);
            } else {
                log.info(args.get(0) + " is not a directory");
            }
        } catch (InvalidPathException e) {
            log.error(args.get(0) + " is an invalid path");
        } catch (Exception e) {
            log.error("Searching hasn't finished. ", e);
        }
    }

}
