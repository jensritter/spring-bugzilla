package org.jens.SpringBug.j2bugzilla.exceptions;

/**
 * An {@code AccessDeniedException} is thrown when client code attempts to access
 * a bug which is not available under Bugzilla's permission scheme. The user
 * may not have logged in with {@link org.jens.SpringBug.j2bugzilla.rpc.LogIn} or their account
 * may simply lack the necessary permissions.
 * 
 * @author Tom
 *
 */
public class AccessDeniedException extends BugzillaException {

    /**
     * Eclipse-generated SUID
     */
    private static final long serialVersionUID = 5677589535670349908L;

    /**
     * Creates a new instance of {@link AccessDeniedException} with the
     * specified message and wrapped exception.
     * @param message A short, descriptive message of the error
     * @param cause A wrapped exception which caused this to be thrown
     */
    public AccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }

}
