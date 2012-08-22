package com.google.code.SpringBug.j2bugzilla.exceptions;

/**
 * <code>BugzillaException</code> indicates that Bugzilla has returned a fault rather 
 * than the expected return value for a method. It wraps the <code>XmlRpcException</code>
 * which caused the error.
 * @author Tom
 *
 */
public class BugzillaException extends Exception {

    /**
     * Eclipse-generated SUID
     */
    private static final long serialVersionUID = -5427986526722263296L;

    /**
     * Public constructor which calls super()
     * @param message A customized error message describing the issue
     * @param cause The nested XmlRpcException cause
     */
    public BugzillaException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new {@link BugzillaException} with the specified summary
     * @param message A short, descriptive message of the error
     */
    public BugzillaException(String message) {
        super(message);
    }
}
