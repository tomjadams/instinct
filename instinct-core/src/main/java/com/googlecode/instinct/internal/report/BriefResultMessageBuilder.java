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

package com.googlecode.instinct.internal.report;

import static java.lang.System.getProperty;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static java.util.regex.Pattern.MULTILINE;
import static java.util.regex.Pattern.compile;
import com.googlecode.instinct.internal.runner.ContextResult;
import com.googlecode.instinct.internal.runner.SpecificationFailureMessageBuilder;
import com.googlecode.instinct.internal.runner.SpecificationFailureMessageBuilderImpl;
import com.googlecode.instinct.internal.runner.SpecificationResult;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.report.ResultMessageBuilder;

public final class BriefResultMessageBuilder implements ResultMessageBuilder {
    private static final Pattern START_OF_LINE = compile("^", MULTILINE);
    private static final String NEW_LINE = getProperty("line.separator");
    private static final String TAB = "\t";
    private final SpecificationFailureMessageBuilder failureMessageBuilder = new SpecificationFailureMessageBuilderImpl();

    public String buildMessage(final ContextResult contextResult) {
        checkNotNull(contextResult);
        return buildContextResultMessage(contextResult);
    }

    public String buildMessage(final SpecificationResult specificationResult) {
        checkNotNull(specificationResult);
        return buildSpecificationResultMessage(specificationResult);
    }

    private String buildContextResultMessage(final ContextResult contextResult) {
        final StringBuilder builder = new StringBuilder();
        builder.append(contextResult.getContextName()).append(NEW_LINE);
        for (final Iterator<SpecificationResult> iterator = contextResult.getSpecificationResults().iterator(); iterator.hasNext();) {
            final SpecificationResult specificationResult = iterator.next();
            builder.append("- ").append(buildSpecificationResultMessage(specificationResult));
            if (iterator.hasNext()) {
                builder.append(NEW_LINE);
            }
        }
        return builder.toString();
    }

    private String buildSpecificationResultMessage(final SpecificationResult specificationResult) {
        final StringBuilder builder = new StringBuilder();
        final String status = specificationResult.completedSuccessfully() ? "" : " (FAILED)";
        builder.append(specificationResult.getSpecificationName()).append(status);
        if (!specificationResult.completedSuccessfully()) {
            builder.append(NEW_LINE).append(NEW_LINE).append(getFailureCause(specificationResult));
        }
        return builder.toString();
    }

    private String getFailureCause(final SpecificationResult specificationResult) {
        final String failureCause = failureMessageBuilder.buildMessage(specificationResult.getStatus());
        return indentEachLine(removeLastNewline(failureCause));
    }

    private String removeLastNewline(final String failureCause) {
        return failureCause.endsWith(NEW_LINE) ? failureCause.substring(0, failureCause.length() - 1) : failureCause;
    }

    @Suggest("Utility?")
    private String indentEachLine(final String failureCause) {
        final Matcher matcher = START_OF_LINE.matcher(failureCause);
        return matcher.replaceAll(TAB);
    }
}
