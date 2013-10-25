package org.dkhurtin.makesimulator.api;

import org.dkhurtin.makesimulator.common.Makefile;
import org.dkhurtin.makesimulator.impl.CommandExecutionException;

public interface MakefileExecutor {
    void execute(Makefile makefile, String target) throws CommandExecutionException;
}
