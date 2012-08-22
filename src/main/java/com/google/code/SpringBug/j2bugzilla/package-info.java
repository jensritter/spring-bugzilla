/**
 * <p>
 * This package contains the base classes required by the library to communicate with a
 * Bugzilla installation. For example, the {@link de.mitegro.bugzilla.j2bugzilla.Bug} class is defined here, to provide
 * a useful data object for encapsulating the pertinent values of a bug report. The
 * entry point for accessing a remote Bugzilla installation, {@link de.mitegro.bugzilla.j2bugzilla.BugzillaConnector},
 * is also provided here. This package further includes the {@link de.mitegro.bugzilla.j2bugzilla.BugzillaMethod}
 * interface, which allows implementing classes to execute methods defined
 * in the Bugzilla XML-RPC API. Finally, this package contains the {@link jbugz.base.XmlExceptionHandler}
 * for translating the Apache {@link XmlRpcException} objects to library-appropriate
 * exceptions.
 * </p>
 * <p>
 * To begin using this library, a {@link de.mitegro.bugzilla.j2bugzilla.BugzillaConnector} object must be created and
 * used to connect to a remote instance. For example, one would use the following
 * code snippet to connect to the sample Landfill installation:
 * <br />
 * <br />
 * <code>
 * BugzillaConnector conn = new BugzillaConnector();<br />
 * conn.connectTo("https://landfill.bugzilla.org/bugzilla-tip/");<br />
 * </code>
 * <br />
 * The connectTo method will attempt to connect to the /xmlrpc.cgi endpoint of the
 * provided URL; this can also be directly appended to the URL string. If a connection
 * cannot be established, a {@link jbugz.exceptions.ConnectionException} is thrown
 * with an appropriate message.
 * </p>
 * <p>
 * Once a connection has been established, classes which implement {@link de.mitegro.bugzilla.j2bugzilla.BugzillaMethod}
 * may be passed to the {@link de.mitegro.bugzilla.j2bugzilla.BugzillaConnector#executeMethod(BugzillaMethod)} method.
 * For example, to authenticate with the server, one would write the following:
 * <br />
 * <br />
 * <code>
 * BugzillaMethod logIn = new LogIn("user@sample.com", "somepass");<br />
 * try {<br />
 *   conn.executeMethod(logIn);<br />
 * } catch(BugzillaException e) {<br />
 *   e.printStackTrace();<br />
 * }<br />
 * </code>
 * <br />
 * If the returned XML document includes a fault code, the appropriate subclass of
 * {@link jbugz.exceptions.BugzillaException} will be thrown from {@code executeMethod}.
 * </p>
 */
package com.google.code.SpringBug.j2bugzilla;