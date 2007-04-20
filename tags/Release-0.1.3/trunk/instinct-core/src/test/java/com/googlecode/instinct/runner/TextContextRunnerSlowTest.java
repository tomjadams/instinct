package com.googlecode.instinct.runner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static java.lang.System.setOut;
import com.googlecode.instinct.internal.runner.ASimpleContext;
import com.googlecode.instinct.internal.runner.ContextContainerWithSetUpAndTearDown;
import com.googlecode.instinct.internal.runner.ContextRunner;
import static com.googlecode.instinct.report.ResultFormat.BRIEF;
import static com.googlecode.instinct.runner.TextContextRunner.runContexts;
import com.googlecode.instinct.test.InstinctTestCase;

public final class TextContextRunnerSlowTest extends InstinctTestCase {
    private ContextRunner contextRunner;
    private ByteArrayOutputStream outputBuffer;

    @Override
    public void setUpTestDoubles() {
        outputBuffer = new ByteArrayOutputStream();
    }

    @Override
    public void setUpSubject() {
        contextRunner = new TextContextRunner(outputBuffer, BRIEF);
    }

    public void testSendsSpeciciationResultsToOutput() {
        checkSendsSpeciciationResultsToOutput(ASimpleContext.class);
        checkSendsSpeciciationResultsToOutput(ContextContainerWithSetUpAndTearDown.class);
    }

    public void testCanBeCalledStaticallySendingResultsToStandardOut() {
        final PrintStream defaultStdOut = System.out;
        try {
            setOut(new PrintStream(outputBuffer));
            runContexts(ASimpleContext.class, ContextContainerWithSetUpAndTearDown.class);
            checkRunnerSendsSpeciciationResultsToOutput(ASimpleContext.class);
            checkRunnerSendsSpeciciationResultsToOutput(ContextContainerWithSetUpAndTearDown.class);
        } finally {
            setOut(defaultStdOut);
        }
    }

    private <T> void checkSendsSpeciciationResultsToOutput(final Class<T> contextClass) {
        contextRunner.run(contextClass);
        checkRunnerSendsSpeciciationResultsToOutput(contextClass);
    }

    private <T> void checkRunnerSendsSpeciciationResultsToOutput(final Class<T> contextClass) {
        final String runnerOutput = new String(outputBuffer.toByteArray());
        assertTrue(runnerOutput.contains("Context: " + contextClass.getSimpleName()));
        assertTrue(runnerOutput.contains("Specifications run:"));
    }
}
