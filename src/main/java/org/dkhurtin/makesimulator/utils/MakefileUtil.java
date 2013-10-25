package org.dkhurtin.makesimulator.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.dkhurtin.makesimulator.common.MakefileExecution;

import java.util.List;
import java.util.Map;

public class MakefileUtil {

    public static List<String> findRootTargets(Map<String, MakefileExecution> targetToExecution) {

        Map<String, List<String>> reversedMap = Maps.newHashMap();

        for (Map.Entry<String,MakefileExecution> entry : targetToExecution.entrySet()) {
            for (String parent : entry.getValue().getParentTargets()) {

                if (!reversedMap.containsKey(parent)) {
                    reversedMap.put(parent, Lists.<String>newArrayList());
                }

                reversedMap.get(parent).add(entry.getKey());
            }
        }


        List<String> rootTargets = Lists.newArrayList();

        for (Map.Entry<String, List<String>> entry : reversedMap.entrySet()) {
            if (entry.getValue().isEmpty()) {
                rootTargets.add(entry.getKey());
            }
        }

        return rootTargets;
    }
}
