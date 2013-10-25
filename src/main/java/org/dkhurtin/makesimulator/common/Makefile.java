package org.dkhurtin.makesimulator.common;

import com.google.common.collect.Maps;
import org.dkhurtin.makesimulator.utils.MakefileUtil;

import java.util.List;
import java.util.Map;

public class Makefile {

    private Map<String, MakefileExecution> targetToExecution = Maps.newHashMap();

    public Makefile(Map<String, MakefileExecution> targetToExecution) {
        this.targetToExecution = targetToExecution;
    }

    public boolean exists(String target) {
        return targetToExecution.containsKey(target);
    }

    public MakefileExecution getExecution(String target) {
        if (!exists(target)) {
            throw new IllegalArgumentException("Target '" + target + "' not found in makefile.");
        }

        return targetToExecution.get(target);
    }

    public List<String> findRootTargets() {
        return MakefileUtil.findRootTargets(targetToExecution);
    }
}
