package com.googlecode.instinct.expect.state;

import java.util.Collection;

public interface CollectionChecker<E, T extends Collection<E>> extends IterableChecker<E, T> {
    void isEmpty();

    void notEmpty();

    void hasSize(int size);
}
