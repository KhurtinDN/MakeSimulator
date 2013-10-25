package org.dkhurtin.makesimulator.common;

import com.google.common.collect.Lists;

import java.util.List;

public class MakefileExecution {

    private List<String> commands;
    private List<String> parentTargets;

    public MakefileExecution(List<String> parentTargets) {
        this.parentTargets = parentTargets;
        this.commands = Lists.newArrayList();
    }

    public void addCommand(String command) {
        commands.add(command);
    }

    public List<String> getCommands() {
        return commands;
    }

    public List<String> getParentTargets() {
        return parentTargets;
    }
}
