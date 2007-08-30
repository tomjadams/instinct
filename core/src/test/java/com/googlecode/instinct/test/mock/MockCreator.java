/*
 * Copyright 2006-2007 Workingmouse
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.googlecode.instinct.test.mock;

import static com.googlecode.instinct.expect.behaviour.Mocker.mock;
import java.lang.reflect.Array;

public final class MockCreator implements ActorCreator {
    private static final int NUMBER_OF_MOCKS_IN_AN_ARRAY = 3;

    @SuppressWarnings({"unchecked"})
    public <T> T create(final Class<T> type, final String roleName) {
        if (type.isArray()) {
            return (T) createArray(type.getComponentType(), roleName);
        } else {
            return mock(type, roleName);
        }
    }

    @SuppressWarnings({"StringContatenationInLoop", "unchecked"})
    private <E> Object createArray(final Class<E> componentType, final String fieldName) {
        final Object array =Array.newInstance(componentType, NUMBER_OF_MOCKS_IN_AN_ARRAY);
        for (int i = 0; i < NUMBER_OF_MOCKS_IN_AN_ARRAY; i++) {
            Array.set(array, i, mock(componentType, fieldName + "-" + i));
        }
        return array;
    }
}
