package org.dkhurtin.makesimulator.api;

import org.dkhurtin.makesimulator.common.Makefile;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface MakefileParser {
    Makefile parse(String path) throws IOException;
}
