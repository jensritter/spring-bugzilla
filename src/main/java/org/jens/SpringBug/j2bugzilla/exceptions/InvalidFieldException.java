package org.jens.SpringBug.j2bugzilla.exceptions;

/**
 * A {@code InvalidFieldException} is thrown when a client attempts to file
 * a bug report with values that cannot be parsed by the installation for one
 * of the fields.
 * 
 * @author Tom
 *
 */
public class InvalidFieldException extends BugzillaException {

    /**
     * Eclipse-generated SUID
     */
    private static final long serialVersionUID = 8890334980751624989L;

    /**
     * Creates a new {@link InvalidFieldException} with the specified message
     * and wrapped exception
     * @param message A short, descriptive message of the error
     * @param cause The wrapped exception causing this to be thrown
     */
    public InvalidFieldException(String message, Throwable cause) {
        super(message, cause);
    }

}
