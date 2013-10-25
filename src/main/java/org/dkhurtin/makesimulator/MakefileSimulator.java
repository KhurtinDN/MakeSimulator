package org.dkhurtin.makesimulator;

import org.dkhurtin.makesimulator.api.MakefileExecutor;
import org.dkhurtin.makesimulator.api.MakefileParser;
import org.dkhurtin.makesimulator.common.Makefile;
import org.dkhurtin.makesimulator.impl.CommandExecutionException;
import org.dkhurtin.makesimulator.impl.MakefileExecutorImpl;
import org.dkhurtin.makesimulator.impl.MakefileParserImpl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Hello world!
 */
public class MakefileSimulator {

    public static void main(String[] args) {

        if (args.length < 1) {
            throw new IllegalArgumentException("You should provide a path to makefile");
        }

        String makefilePath = args[0];
        List<String> targets = Arrays.asList(args).subList(1, args.length);

        MakefileParser parser = new MakefileParserImpl();

        Makefile makefile;
        try {
            makefile = parser.parse(makefilePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found: " + makefilePath, e);
        } catch (IOException e) {
            throw new RuntimeException("Unable to read makefile: " + makefilePath, e);
        }

        MakefileExecutor executor = new MakefileExecutorImpl();

        if (targets.isEmpty()) {
            targets = makefile.findRootTargets();
            System.out.println("Default is root targets: " + targets);
        }

        try {
            for (String target : targets) {
                executor.execute(makefile, target);
            }
        } catch (CommandExecutionException e) {
            e.printStackTrace();
        }
    }
}
