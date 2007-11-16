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

package com.googlecode.instinct.integrate.ant;

import com.googlecode.instinct.internal.locate.ClassLocator;
import com.googlecode.instinct.internal.locate.ClassLocatorImpl;
import com.googlecode.instinct.internal.locate.ClassWithContextAnnotationFileFilter;
import com.googlecode.instinct.internal.locate.ClassWithMarkedMethodsFileFilter;
import com.googlecode.instinct.internal.util.JavaClassName;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.marker.AnnotationAttribute;
import com.googlecode.instinct.marker.MarkingScheme;
import com.googlecode.instinct.marker.MarkingSchemeImpl;
import com.googlecode.instinct.marker.annotate.Context;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.naming.ContextNamingConvention;
import com.googlecode.instinct.marker.naming.SpecificationNamingConvention;
import java.io.File;
import java.io.FileFilter;
import java.util.Set;
import org.apache.tools.ant.Project;

@SuppressWarnings({"InstanceVariableOfConcreteClass"})
public final class Specifications {
    private final ClassLocator classLocator = new ClassLocatorImpl();
    private final Project project;
    private File specPackageRoot;
    private AnnotationAttribute groups;

    public Specifications(final Project project) {
        checkNotNull(project);
        this.project = project;
    }

    public void setDir(final String dir) {
        checkValidString("dir", dir);
        specPackageRoot = new File(dir);
        if (!specPackageRoot.exists()) {
            specPackageRoot = new File(project.getBaseDir(), dir);
            if (!specPackageRoot.exists()) {
                throw new IllegalArgumentException("Specifications directory '" + dir + "' does not exist");
            }
        }
    }

    public void setGroups(final String groups) {
        checkValidString("groups", groups);
        this.groups = new AnnotationAttribute("groups", groups);
    }

    @Suggest({"This should return ContextClass's, that way we don't need to instantiate them.",
            "he filters already instantiate them, do so here and re-use", "Don't return an array, use an ordered set."})
    public JavaClassName[] getContextClasses() {
        checkPreconditions();
        final Set<JavaClassName> contextClasses = findContextClasses();
        return contextClasses.toArray(new JavaClassName[contextClasses.size()]);
    }

    private Set<JavaClassName> findContextClasses() {
        final MarkingScheme contextMarking = new MarkingSchemeImpl(Context.class, new ContextNamingConvention(), groups);
        final MarkingScheme specificationMarking = new MarkingSchemeImpl(Specification.class, new SpecificationNamingConvention(), groups);
        final FileFilter annotatedClasses = new ClassWithContextAnnotationFileFilter(specPackageRoot, contextMarking);
        final FileFilter markedMethodClasses = new ClassWithMarkedMethodsFileFilter(specPackageRoot, specificationMarking);
        return classLocator.locate(specPackageRoot, annotatedClasses, markedMethodClasses);
    }

    private void checkPreconditions() {
        if (specPackageRoot == null) {
            throw new IllegalStateException("Specification package root must be specified");
        }
    }

    private void checkValidString(final String attributeName, final String attribute) {
        if (attribute == null || attribute.trim().length() == 0) {
            throw new IllegalArgumentException("Attribute '" + attributeName + "' can not be null, the empty string or exclusively whitespace");
        }
    }
}
