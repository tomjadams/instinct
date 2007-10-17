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

import au.net.netstorm.boost.nursery.instance.InstanceProvider;
import com.googlecode.instinct.internal.util.instance.ConcreteInstanceProvider;
import com.googlecode.instinct.internal.testdouble.SpecificationDoubleCreator;

public class StubCreator implements SpecificationDoubleCreator {
    private final InstanceProvider instanceProvider = new ConcreteInstanceProvider();

    @SuppressWarnings({"unchecked"})
    public <T> T createDouble(final Class<T> doubleType, final String roleName) {
        return (T) instanceProvider.newInstance(doubleType);
    }
}
