/*
 * The MIT License
 *
 * Copyright 2014 Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package pl.wavesoftware.maven.plugin.glassfish.console;

import org.apache.maven.plugin.logging.Log;
import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.Project;
import org.assertj.core.api.Assertions;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import static org.mockito.Mockito.*;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class AntBuildListenerTest {

    @Test
    public void testBuildStarted() {
        Project project = new Project();
        BuildEvent event = new BuildEvent(project);
        Log log = mock(Log.class);
        AntBuildListener instance = new AntBuildListener(log);
        instance.buildStarted(event);
        assertThat(instance).isNotNull();
    }

    @Test
    public void testBuildFinished() {
        Project project = new Project();
        BuildEvent event = new BuildEvent(project);
        Log log = mock(Log.class);
        AntBuildListener instance = new AntBuildListener(log);
        instance.buildFinished(event);
        assertThat(instance).isNotNull();
    }

    @Test
    public void testTargetStarted() {
        Project project = new Project();
        BuildEvent event = new BuildEvent(project);
        Log log = mock(Log.class);
        AntBuildListener instance = new AntBuildListener(log);
        instance.targetStarted(event);
        assertThat(instance).isNotNull();
    }

    @Test
    public void testTargetFinished() {
        Project project = new Project();
        BuildEvent event = new BuildEvent(project);
        Log log = mock(Log.class);
        AntBuildListener instance = new AntBuildListener(log);
        instance.targetFinished(event);
        assertThat(instance).isNotNull();
    }

    @Test
    public void testTaskStarted() {
        Project project = new Project();
        BuildEvent event = new BuildEvent(project);
        Log log = mock(Log.class);
        AntBuildListener instance = new AntBuildListener(log);
        instance.taskStarted(event);
        assertThat(instance).isNotNull();
    }

    @Test
    public void testTaskFinished() {
        Project project = new Project();
        BuildEvent event = new BuildEvent(project);
        Log log = mock(Log.class);
        AntBuildListener instance = new AntBuildListener(log);
        instance.taskFinished(event);
        assertThat(instance).isNotNull();
    }

    @Test
    public void testMessageLogged() {
        Project project = new Project();
        BuildEvent event = new BuildEvent(project);
        event.setMessage("This is debug message", Project.MSG_DEBUG);
        Log log = mock(Log.class);
        AntBuildListener instance = new AntBuildListener(log);
        instance.messageLogged(event);
        assertThat(instance).isNotNull();
        verify(log).debug("This is debug message");
    }

    @Test
    public void testMessageLoggedDebugThowable() {
        Project project = new Project();
        BuildEvent event = new BuildEvent(project);
        event.setMessage("This is debug message", Project.MSG_DEBUG);
        Exception ex = new Exception("ex");
        event.setException(ex);
        Log log = mock(Log.class);
        AntBuildListener instance = new AntBuildListener(log);
        instance.messageLogged(event);
        assertThat(instance).isNotNull();
        verify(log).debug("This is debug message", ex);
    }

    @Test
    public void testMessageLoggedWarning() {
        Project project = new Project();
        BuildEvent event = new BuildEvent(project);
        event.setMessage("This is warn message", Project.MSG_WARN);
        Log log = mock(Log.class);
        AntBuildListener instance = new AntBuildListener(log);
        instance.messageLogged(event);
        assertThat(instance).isNotNull();
        verify(log).warn("This is warn message");
    }

    @Test
    public void testMessageLoggedWarningThrowable() {
        Project project = new Project();
        BuildEvent event = new BuildEvent(project);
        event.setMessage("This is warn message", Project.MSG_WARN);
        Exception ex = new Exception("ex");
        event.setException(ex);
        Log log = mock(Log.class);
        AntBuildListener instance = new AntBuildListener(log);
        instance.messageLogged(event);
        assertThat(instance).isNotNull();
        verify(log).warn("This is warn message", ex);
    }

    @Test
    public void testMessageLoggedError() {
        Project project = new Project();
        BuildEvent event = new BuildEvent(project);
        event.setMessage("This is error message", Project.MSG_ERR);
        Log log = mock(Log.class);
        AntBuildListener instance = new AntBuildListener(log);
        instance.messageLogged(event);
        assertThat(instance).isNotNull();
        verify(log).error("This is error message");
    }

    @Test
    public void testMessageLoggedErrorThrowable() {
        Project project = new Project();
        BuildEvent event = new BuildEvent(project);
        event.setMessage("This is error message", Project.MSG_ERR);
        Exception ex = new Exception("ex");
        event.setException(ex);
        Log log = mock(Log.class);
        AntBuildListener instance = new AntBuildListener(log);
        instance.messageLogged(event);
        assertThat(instance).isNotNull();
        verify(log).error("This is error message", ex);
    }

    @Test
    public void testMessageLoggedInfo() {
        Project project = new Project();
        BuildEvent event = new BuildEvent(project);
        event.setMessage("This is info message", Project.MSG_INFO);
        Log log = mock(Log.class);
        AntBuildListener instance = new AntBuildListener(log);
        instance.messageLogged(event);
        assertThat(instance).isNotNull();
        verify(log).info("This is info message");
    }

    @Test
    public void testMessageLoggedInfoThrowable() {
        Project project = new Project();
        BuildEvent event = new BuildEvent(project);
        event.setMessage("This is info message", Project.MSG_INFO);
        Exception ex = new Exception("ex");
        event.setException(ex);
        Log log = mock(Log.class);
        AntBuildListener instance = new AntBuildListener(log);
        instance.messageLogged(event);
        assertThat(instance).isNotNull();
        verify(log).info("This is info message", ex);
    }

    @Test
    public void testMessageLoggedVerbose() {
        Project project = new Project();
        BuildEvent event = new BuildEvent(project);
        event.setMessage("This is verbose message", Project.MSG_VERBOSE);
        Log log = mock(Log.class);
        AntBuildListener instance = new AntBuildListener(log);
        instance.messageLogged(event);
        assertThat(instance).isNotNull();
        verify(log).debug("This is verbose message");
    }

    @Test
    public void testMessageLoggedVerboseThrowable() {
        Project project = new Project();
        BuildEvent event = new BuildEvent(project);
        event.setMessage("This is verbose message", Project.MSG_VERBOSE);
        Exception ex = new Exception("ex");
        event.setException(ex);
        Log log = mock(Log.class);
        AntBuildListener instance = new AntBuildListener(log);
        instance.messageLogged(event);
        assertThat(instance).isNotNull();
        verify(log).debug("This is verbose message", ex);
    }

    @Test
    public void testMessageLoggedFromException() {
        Project project = new Project();
        BuildEvent event = new BuildEvent(project);
        Exception ex = new Exception("ex");
        event.setException(ex);
        Log log = mock(Log.class);
        AntBuildListener instance = new AntBuildListener(log);
        instance.messageLogged(event);
        assertThat(instance).isNotNull();
        verify(log).error("ex", ex);
    }

    @Test
    public void testMessageLoggedInvalid() {
        try {
            Project project = new Project();
            BuildEvent event = new BuildEvent(project);
            event.setMessage("This is invalid message", 71830);
            Log log = mock(Log.class);
            AntBuildListener instance = new AntBuildListener(log);
            instance.messageLogged(event);
            Assertions.failBecauseExceptionWasNotThrown(UnsupportedOperationException.class);
        } catch (UnsupportedOperationException uoe) {
            assertThat(uoe).isNotNull();
            assertThat(uoe).hasMessage("Message priority `71830` is not known");
        }
    }

}
