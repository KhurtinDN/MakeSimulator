package org.dkhurtin.makesimulator.common;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.dkhurtin.makesimulator.utils.MakefileUtil;

import java.util.*;

public class MakefileBuilder {

    private Map<String, MakefileExecution> targetToExecution = Maps.newHashMap();

    public void addTarget(String target, MakefileExecution execution) {
        targetToExecution.put(target, execution);
    }

    public void validate() {
        checkParentTargetExists();
        checkLoopNotExists();
    }

    public Makefile build() {
        validate();

        return new Makefile(targetToExecution);
    }

    private void checkParentTargetExists() {
        for (MakefileExecution execution : targetToExecution.values()) {
            for (String parentTarget : execution.getParentTargets()) {
                if (!targetToExecution.containsKey(parentTarget)) {
                    throw new IllegalStateException("Invalid makefile! Parent target not found: " + parentTarget);
                }
            }
        }
    }

    private void checkLoopNotExists() {

        List<String> rootTargets = MakefileUtil.findRootTargets(targetToExecution);

        Set<String> allVisited = Sets.newHashSet();

        for (String rootTarget : rootTargets) {
            checkLoopNotExists(rootTarget, allVisited);
        }

        Set<String> notVisitedTargets = Sets.newHashSet(targetToExecution.keySet());
        notVisitedTargets.removeAll(allVisited);

        // detect loop from not root
        for (String notVisitedTarget : notVisitedTargets) {
            checkLoopNotExists(notVisitedTarget, allVisited);
        }
    }

    private void checkLoopNotExists(String fromTarget, Set<String> allVisited) {
        Queue<String> queue = new LinkedList<String>();
        Set<String> viewed = new HashSet<String>();

        queue.add(fromTarget);

        while (!queue.isEmpty()) {
            String target = queue.remove();

            if (viewed.contains(target)) {
                throw new IllegalStateException("Target " + fromTarget + " contains loop!");
            }

            viewed.add(target);

            for (String parentTarget : targetToExecution.get(target).getParentTargets()) {
                queue.add(parentTarget);
            }
        }

        allVisited.addAll(viewed);
    }
}
