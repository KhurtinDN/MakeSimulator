package org.dkhurtin.makesimulator.impl;

import org.dkhurtin.makesimulator.api.CommandExecutor;
import org.dkhurtin.makesimulator.api.MakefileExecutor;
import org.dkhurtin.makesimulator.common.Makefile;
import org.dkhurtin.makesimulator.common.MakefileExecution;

public class MakefileExecutorImpl implements MakefileExecutor {

    private CommandExecutor commandExecutor = new LinuxCommandExecutor();

    @Override
    public void execute(Makefile makefile, String target) throws CommandExecutionException {
        execute(makefile, makefile.getExecution(target));
    }

    private void execute(Makefile makefile, MakefileExecution execution) throws CommandExecutionException {
        for (String parent : execution.getParentTargets()) {
            execute(makefile, makefile.getExecution(parent));
        }

        for (String command : execution.getCommands()) {
            commandExecutor.execute(command);
        }
    }
}
