package org.dkhurtin.makesimulator.api;

import org.dkhurtin.makesimulator.common.Makefile;

public interface MakefileExecutor {
    void execute(Makefile makefile, String target);
}
