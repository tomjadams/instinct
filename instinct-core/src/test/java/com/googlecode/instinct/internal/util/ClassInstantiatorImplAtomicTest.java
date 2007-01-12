package com.googlecode.instinct.internal.util;

import java.io.File;
import au.net.netstorm.boost.edge.java.lang.EdgeClass;
import static com.googlecode.instinct.mock.Mocker.eq;
import static com.googlecode.instinct.mock.Mocker.expects;
import static com.googlecode.instinct.mock.Mocker.mock;
import static com.googlecode.instinct.mock.Mocker.returnValue;
import static com.googlecode.instinct.mock.Mocker.same;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import static com.googlecode.instinct.test.reflect.ReflectUtil.insertFieldValue;

public final class ClassInstantiatorImplAtomicTest extends InstinctTestCase {
    @Suggest("This string becomes a dummy.")
    private static final String FULLY_QUALIFIED_CLASS_NAME = "FQN";
    private ClassInstantiator instantiator;
    private File packageRoot;
    private File classFile;
    private EdgeClass edgeClass;
    private JavaClassNameFactory classNameFactory;
    private JavaClassName className;

    public void testProperties() {
        checkClass(ClassInstantiatorImpl.class, ClassInstantiator.class);
    }

    public void testInstantiateClass() {
        expects(classNameFactory).method("create").with(same(packageRoot), same(classFile)).will(returnValue(className));
        expects(className).method("getFullyQualifiedName").will(returnValue(FULLY_QUALIFIED_CLASS_NAME));
        expects(edgeClass).method("forName").with(eq(FULLY_QUALIFIED_CLASS_NAME)).will(returnValue(Class.class));
        final Class<?> actualClass = instantiator.instantiateClass(classFile);
        assertSame(Class.class, actualClass);
    }

    @Override
    public void setUpTestDoubles() {
        packageRoot = mock(File.class, "packageRoot");
        classFile = mock(File.class);
        edgeClass = mock(EdgeClass.class);
        classNameFactory = mock(JavaClassNameFactory.class);
        className = mock(JavaClassName.class);
    }

    @Override
    public void setUpSubject() {
        instantiator = new ClassInstantiatorImpl(packageRoot);
        insertFieldValue(instantiator, "classNameFactory", classNameFactory);
        insertFieldValue(instantiator, "edgeClass", edgeClass);
    }
}
