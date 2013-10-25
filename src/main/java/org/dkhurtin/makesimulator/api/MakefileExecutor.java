package org.dkhurtin.makesimulator.api;

import org.dkhurtin.makesimulator.common.Makefile;
import org.dkhurtin.makesimulator.impl.CommandExecutionException;

public interface MakefileExecutor {

    /**
     * Executes target of makefile
     * @param makefile the makefile
     * @param target the target name
     * @throws CommandExecutionException if error occur
     */
    void execute(Makefile makefile, String target) throws CommandExecutionException;
}
