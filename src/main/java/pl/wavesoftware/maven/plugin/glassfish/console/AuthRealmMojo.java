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
import com.github.wavesoftware.glassfish.ant.tasks.enums.RealmTypes;
import java.io.File;
import static java.lang.String.format;
import java.util.Map;
import java.util.Properties;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.XmlProperty;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
@Mojo(name = AuthRealmMojo.name, threadSafe = true)
public class AuthRealmMojo extends AbstractMojo {

    protected static final String name = "auth-realm";

    @Component
    private MavenProject project;

    /**
     * A action to do with Glassfish Auth Realm. Posible values are: AUTO, CREATE, DELETE, LIST
     */
    @Parameter(defaultValue = "AUTO")
    private ConsoleActions action = ConsoleActions.AUTO;

    @Parameter(defaultValue = "false")
    private boolean recreateOnAutoMode = false;

    /**
     * A Glassfish Auth Realm type. Posible values are: FILE, CERTIFICATE, JDBC, LDAP, PAM, SOLARIS, CUSTOM
     */
    @Parameter(required = true)
    private RealmTypes type = RealmTypes.JDBC;

    /**
     * A Glassfish installation directory
     */
    @Parameter(required = true)
    private File installDir;

    /**
     * A properties for a realm
     */
    @Parameter(required = false)
    private Properties properties = new Properties();

    /**
     * A file containing a properties of a realm. Schema is:
     * https://raw.github.com/wavesoftware/glassfish-ant-console/master/docs/glassfish-console.xsd
     */
    @Parameter(required = false)
    private File propertiesFile;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Running a Glassfish console - auth realm...");
        Project antProject = new Project();
        antProject.setBaseDir(project.getBasedir());
        antProject.addBuildListener(new AntBuildListener(this.getLog()));
        AuthRealmTask task = new AuthRealmTask();
        task.setProject(antProject);
        task.setAction(action.toString());
        task.setType(type.toString());
        task.setInstallDir(installDir.getPath());
        if (propertiesFile != null && propertiesFile.canRead()) {
            loadAsAntProperties(task, propertiesFile);
        }
        setReCreate(task);
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            String key = format("glassfish-console.security.realm.%s", getValue(entry.getKey(), ""));
            antProject.setProperty(key, getValue(entry.getValue()));
        }
        task.execute();
    }

    private String getValue(Object obj, String defaultValue) {
        return obj == null ? defaultValue : obj.toString();
    }

    private String getValue(Object obj) {
        return getValue(obj, null);
    }

    private void loadAsAntProperties(AuthRealmTask task, File propertiesFile) {
        XmlProperty xmlProperty = new XmlProperty();
        xmlProperty.setFile(propertiesFile);
        Project antProject = task.getProject();
        xmlProperty.setProject(antProject);
        xmlProperty.execute();
    }

    private void setReCreate(AuthRealmTask task) {
        if (recreateOnAutoMode) {
            task.getProject().setProperty("glassfish-console.security.realm.re-create", "");
        }
    }

}
