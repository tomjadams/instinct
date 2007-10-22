/*
 * Copyright 2006-2007 Tom Adams
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

package com.googlecode.instinct.internal.util;

import java.lang.reflect.Constructor;
import au.net.netstorm.boost.edge.java.lang.reflect.DefaultEdgeConstructor;
import au.net.netstorm.boost.edge.java.lang.reflect.EdgeConstructor;
import com.googlecode.instinct.internal.edge.java.lang.reflect.ClassEdge;
import com.googlecode.instinct.internal.edge.java.lang.reflect.ClassEdgeImpl;

public final class ConstructorInvokerImpl implements ConstructorInvoker {
    private final ClassEdge classEdge = new ClassEdgeImpl();
    private final EdgeConstructor edgeConstructor = new DefaultEdgeConstructor();

    public <T> Object invokeNullaryConstructor(final Class<T> cls) {
        final Constructor<T> constructor = classEdge.getDeclaredConstructor(cls);
        constructor.setAccessible(true);
        return edgeConstructor.newInstance(constructor, new Object[]{});
    }
}
