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

import com.github.wavesoftware.glassfish.ant.tasks.AuthRealmTask;
import com.github.wavesoftware.glassfish.ant.tasks.enums.ConsoleActions;
import static java.lang.String.format;
import java.lang.reflect.Field;
import org.apache.maven.plugin.logging.Log;
import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.BuildListener;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class AntBuildListener implements BuildListener {

    private final Log log;

    public AntBuildListener(Log log) {
        this.log = log;
    }

    @Override
    public void buildStarted(BuildEvent event) {
        // do nothing here
    }

    @Override
    public void buildFinished(BuildEvent event) {
        // do nothing here
    }

    @Override
    public void targetStarted(BuildEvent event) {
        // do nothing here
    }

    @Override
    public void targetFinished(BuildEvent event) {
        // do nothing here
    }

    @Override
    public void taskStarted(BuildEvent event) {
        // do nothing here
    }

    @Override
    public void taskFinished(BuildEvent event) {
    }

    private boolean isStringEmpty(String input) {
        return input == null || input.isEmpty();
    }

    @Override
    public void messageLogged(BuildEvent event) {
        int priority = event.getPriority();
        Throwable throwable = event.getException();
        String message = getMessage(event);
        if (isStringEmpty(message) && throwable != null) {
            message = throwable.getLocalizedMessage();
        }
        switch (priority) {
            case Project.MSG_DEBUG:
                if (throwable == null) {
                    log.debug(message);
                } else {
                    log.debug(message, throwable);
                }
                break;
            case Project.MSG_INFO:
                if (throwable == null) {
                    log.info(message);
                } else {
                    log.info(message, throwable);
                }
                break;
            case Project.MSG_VERBOSE:
                if (throwable == null) {
                    log.debug(message);
                } else {
                    log.debug(message, throwable);
                }
                break;
            case Project.MSG_WARN:
                if (throwable == null) {
                    log.warn(message);
                } else {
                    log.warn(message, throwable);
                }
                break;
            case Project.MSG_ERR:
                if (throwable == null) {
                    log.error(message);
                } else {
                    log.error(message, throwable);
                }
                break;
            default:
                throw new UnsupportedOperationException(
                    format("Message priority `%d` is not known", priority)
                );
        }
    }

    private String getMessage(BuildEvent event) {
        String base = event.getMessage();
        Task task = event.getTask();
        String message;
        if (task instanceof AuthRealmTask) {
            AuthRealmTask auth = AuthRealmTask.class.cast(task);
            String name = auth.getProject().getProperty("glassfish-console.security.realm.name");
            ConsoleActions action = getPrivate(auth, "action");
            message = format("%s of security realm `%s`: %s", action, name, base);
        } else {
            message = base;
        }
        return message;
    }

    private <T> T getPrivate(AuthRealmTask auth, String name) {
        try {
            Field field = AuthRealmTask.class.getDeclaredField(name);
            field.setAccessible(true);
            @SuppressWarnings("unchecked")
            T value = (T) field.get(auth);
            field.setAccessible(false);
            return value;
        } catch (NoSuchFieldException ex) {
            throw new IllegalStateException(ex);
        } catch (SecurityException ex) {
            throw new IllegalStateException(ex);
        } catch (IllegalArgumentException ex) {
            throw new IllegalStateException(ex);
        } catch (IllegalAccessException ex) {
            throw new IllegalStateException(ex);
        }
    }

}
