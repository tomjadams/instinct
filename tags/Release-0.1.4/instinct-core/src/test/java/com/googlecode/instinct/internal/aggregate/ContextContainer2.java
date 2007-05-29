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

package com.googlecode.instinct.internal.aggregate;

import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Context;

@SuppressWarnings({"EmptyClass", "PackageVisibleInnerClass", "UnusedDeclaration"})
public final class ContextContainer2 {
    @Context
    public static class AnEmbeddedPublicContext {
        public void whoCares() {
        }
    }

    @Context
    static class AnEmbeddedPackageLocalContext {
        public void whoCares() {
        }
    }

    @Context
    private static class AnEmbeddedPrivateContext {
        @BeforeSpecification
        public void aSetUpMethod() {
        }
    }
}
