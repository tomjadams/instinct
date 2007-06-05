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

package com.googlecode.instinct.internal.mock;

import static com.googlecode.instinct.expect.Mocker12.anything;
import static com.googlecode.instinct.expect.Mocker12.eq;
import static com.googlecode.instinct.expect.Mocker12.expects;
import static com.googlecode.instinct.expect.Mocker12.mock;
import static com.googlecode.instinct.expect.Mocker12.returnValue;
import static com.googlecode.instinct.expect.Mocker12.same;
import com.googlecode.instinct.internal.aggregate.locate.MarkedFieldLocator;
import com.googlecode.instinct.internal.util.ObjectFactory;
import com.googlecode.instinct.marker.annotate.Dummy;
import com.googlecode.instinct.marker.naming.DummyNamingConvention;
import com.googlecode.instinct.marker.naming.NamingConvention;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import static com.googlecode.instinct.test.reflect.Reflector.insertFieldValue;

public final class TestDoubleAutoWirerImplAtomicTest extends InstinctTestCase {
    private TestDoubleAutoWirer wirer;
    private MarkedFieldLocator fieldLocator;
    private ObjectFactory objectFactory;
    private NamingConvention dummyNamingConvention;

    public void testConformsToClassTraits() {
        checkClass(TestDoubleAutoWirerImpl.class, TestDoubleAutoWirer.class);
    }

    public void testWire() {
        expects(objectFactory).method("create").with(same(DummyNamingConvention.class), eq(new Object[]{})).will(returnValue(dummyNamingConvention));
        expects(fieldLocator).method("locateAll").with(anything(), same(Dummy.class), same(dummyNamingConvention));
        wirer.wire(mock(Object.class));
    }

    @Override
    public void setUpTestDoubles() {
        fieldLocator = mock(MarkedFieldLocator.class);
        objectFactory = mock(ObjectFactory.class);
        dummyNamingConvention = mock(NamingConvention.class, "mockDummyNamingConvention");
    }

    @Override
    public void setUpSubject() {
        wirer = new TestDoubleAutoWirerImpl();
        insertFieldValue(wirer, "objectFactory", objectFactory);
        insertFieldValue(wirer, "fieldLocator", fieldLocator);
    }
}
