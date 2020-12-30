package ru.ilka.leetcode.seeding;

import java.util.Set;

public interface InitialData<T> {
    Set<T> getEntities();
}
