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

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.maven.plugin.Mojo;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.testing.MojoRule;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.ExecTask;
import static org.assertj.core.api.Assertions.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import static org.mockito.Mockito.*;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class AuthRealmMojoTest {

    private Path root;

    @Rule
    public MojoRule mojoRule = new MojoRule();

    @Before
    public void before() {
        ExecTask task = getAsAdminExecutable();
        task.createArg().setLine("start-domain");
        task.execute();
    }

    @After
    public void after() {
        ExecTask task = getAsAdminExecutable();
        task.createArg().setLine("stop-domain");
        task.execute();
    }

    private ExecTask getAsAdminExecutable() {
        ExecTask execTask = new ExecTask();
        Path base = getBasedir();
        Project project = new Project();
        project.setBaseDir(base.toFile());
        execTask.setProject(project);
        Path asadmin = base.resolve("target")
            .resolve("glassfish-3.1.2.2")
            .resolve("glassfish3")
            .resolve("bin")
            .resolve("asadmin");
        execTask.setExecutable(asadmin.toString());
        return execTask;
    }

    private Path getMavenTestClasses() {
        return Paths.get("target", "test-classes");
    }

    private Path getBasedir() {
        if (root == null) {
            String separator = System.getProperty("file.separator");
            String pack = AuthRealmMojoTest.class.getPackage().getName().replace(".", separator);
            URL thisDir = AuthRealmMojoTest.class.getResource(".");
            String path = thisDir.getPath();
            String rootStr = path.replace(getMavenTestClasses() + separator, "")
                .replace(pack + separator, "");
            root = Paths.get(rootStr);
        }
        return root;
    }

    @Test
    public void testExecute() throws Exception {
        URL fileUrl = this.getClass().getResource("test-pom.xml");
        File pom = new File(fileUrl.getFile());
        Mojo mojo = mojoRule.lookupMojo(AuthRealmMojo.name, pom);
        assertThat(mojo).isNotNull();
        assertThat(mojo).isInstanceOf(AuthRealmMojo.class);
        AuthRealmMojo instance = AuthRealmMojo.class.cast(mojo);
        Log original = instance.getLog();
        Log log = spy(original);
        instance.setLog(log);
        instance.execute();
        verify(log).info("CREATE of security realm `JDBC_myproject`: done");
        instance.setLog(original);
    }

}
