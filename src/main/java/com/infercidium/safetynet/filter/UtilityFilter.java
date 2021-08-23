package com.infercidium.safetynet.filter;

import java.util.HashSet;
import java.util.Set;

public class UtilityFilter implements UtilityFilterI {
    @Override
    public Set<String> newFilterSet(final String... strings) {
        HashSet<String> set = new HashSet<>();
        for (String s : strings) {
            set.add(s);
        }
        return set;
    }
}
