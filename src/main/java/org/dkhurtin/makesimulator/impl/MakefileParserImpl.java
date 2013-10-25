package org.dkhurtin.makesimulator.impl;

import org.apache.commons.lang3.StringUtils;
import org.dkhurtin.makesimulator.api.MakefileParser;
import org.dkhurtin.makesimulator.common.Makefile;
import org.dkhurtin.makesimulator.common.MakefileBuilder;
import org.dkhurtin.makesimulator.common.MakefileExecution;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MakefileParserImpl implements MakefileParser {

    @Override
    public Makefile parse(String makefilePath) throws IOException {
        MakefileBuilder makefileBuilder = new MakefileBuilder();

        String currentTarget = null;
        MakefileExecution currentExecution = null;

        BufferedReader lineReader = new BufferedReader(new FileReader(makefilePath));

        for (String line = lineReader.readLine(); line != null; line = lineReader.readLine()) {
            if (StringUtils.isBlank(line) || line.startsWith("#")) {
                continue;
            }

            if (isTarget(line)) {
                if (currentTarget != null) {
                    makefileBuilder.addTarget(currentTarget, currentExecution);
                }

                TargetAndParents targetAndParents = parseTargetLine(line);
                currentTarget = targetAndParents.target;
                currentExecution = new MakefileExecution(targetAndParents.parents);

            } else if (isCommand(line)) {
                if (currentExecution == null) {
                    throw new IllegalStateException("Command without target: " + line);
                }
                currentExecution.addCommand(parseCommand(line));
            }
        }

        if (currentTarget != null) {
            makefileBuilder.addTarget(currentTarget, currentExecution);
        }

        return makefileBuilder.build();
    }

    private boolean isTarget(String line) {
        return Character.isAlphabetic(line.charAt(0));
    }

    private boolean isCommand(String line) {
        return line.startsWith(" ") || line.startsWith("\t");
    }

    private boolean isValidTargetName(String target) {
        return target != null && target.matches("[-\\w]+");
    }

    private TargetAndParents parseTargetLine(String line) {
        String[] targetAndParents = line.trim().split(":", 2);

        String target = targetAndParents[0].trim();
        String parentLine = targetAndParents.length > 1 ? targetAndParents[1].trim() : "";

        String[] parents;
        if (parentLine.isEmpty()) {
            parents = new String[0];
        } else {
            parents = parentLine.split("[ ]+");
        }

        if (!isValidTargetName(target)) {
            throw new IllegalStateException("Invalid target name: " + target);
        }

        for (String parentTarget : parents) {
            if (!isValidTargetName(parentTarget)) {
                throw new IllegalStateException("Invalid parent target name: " + parentTarget +
                        " of target: " + target);
            }
        }

        return new TargetAndParents(target, Arrays.asList(parents));
    }

    private String parseCommand(String line) {
        if (!Character.isAlphabetic(line.charAt(1))) {
            throw new IllegalStateException("Command format mismatch! Invalid command: " + line);
        }

        return line.trim();
    }

    private static class TargetAndParents {

        public String target;
        public List<String> parents;

        private TargetAndParents(String target, List<String> parents) {
            this.target = target;
            this.parents = parents;
        }
    }
}
