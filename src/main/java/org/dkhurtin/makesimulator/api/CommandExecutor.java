package org.dkhurtin.makesimulator.api;

import org.dkhurtin.makesimulator.impl.CommandExecutionException;

public interface CommandExecutor {

    /**
     * Executes command
     * @param command the command line that should be executed
     * @throws CommandExecutionException if error occur
     */
    void execute(String command) throws CommandExecutionException;
}
