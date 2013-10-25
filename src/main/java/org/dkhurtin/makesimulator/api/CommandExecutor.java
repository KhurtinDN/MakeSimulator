package org.dkhurtin.makesimulator.api;

import org.dkhurtin.makesimulator.impl.CommandExecutionException;

public interface CommandExecutor {
    void execute(String command) throws CommandExecutionException;
}
