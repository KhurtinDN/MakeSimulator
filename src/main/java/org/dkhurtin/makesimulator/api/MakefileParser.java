package org.dkhurtin.makesimulator.api;

import org.dkhurtin.makesimulator.common.Makefile;

import java.io.IOException;

public interface MakefileParser {

    /**
     * Parses makefile
     * @param path the local path to makefile
     * @return the makefile object
     * @throws IOException if io error occur
     */
    Makefile parse(String path) throws IOException;
}
