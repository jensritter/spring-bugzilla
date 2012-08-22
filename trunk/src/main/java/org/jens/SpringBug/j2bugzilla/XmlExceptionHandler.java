package org.jens.SpringBug.j2bugzilla;

import org.apache.xmlrpc.XmlRpcException;
import org.jens.SpringBug.j2bugzilla.exceptions.*;


/**
 * The {@code XmlExceptionHandler} provides a static utility method for translating
 * generic {@link XmlRpcException}s into subclasses of {@link BugzillaException}
 * to be handled by the client code.
 * 
 * @author Tom
 */
public final class XmlExceptionHandler {
    
    private XmlExceptionHandler() {
        // don't use this
    }

    /**
     * Translates a {@link XmlRpcException} into a subclass of {@link BugzillaException}
     * @param exception An exception wrapping a known fault code of Bugzilla's interface
     * @return A subclass of {@code BugzillaException}
     */
    public static BugzillaException handleFault(XmlRpcException exception) {
        /*
         * Yes, the cyclomatic complexity is through the roof. On the plus side,
         * at least end-users are getting meaningful exception messages instead of
         * a naked XmlRpcException from Apache.
         */
        switch(exception.code) {

        case 51 :
        {
            return new InvalidFieldException("The component you specified is not valid for this product", exception);
        }

        case 101 :
        {
            return new NoSuchIdException("The bug you attempted to retrieve does not exist.", exception);
        }

        case 102 :
        {
            return new AccessDeniedException("You do not have permission to view this bug. " 
                    + "(Are you logged in?)", exception);
        }

        case 103 :
        {
            return new InvalidFieldException("The alias you specified is invalid", exception);
        }

        case 104 :
        {
            return new InvalidFieldException("One of the fields you specified for this bug " 
                    + "is invalid.", exception);
        }

        case 105 :
        {
            return new InvalidFieldException("No component was specified", exception);
        }

        case 106 :
        {
            return new InvalidFieldException("The specified product is invalid", exception);
        }

        case 107 :
        {
            return new InvalidFieldException("No summary was specified", exception);
        }

        case 108 :
        {
            return new AccessDeniedException("You do not have permission to edit this bug", exception);
        }

        case 504 :
        {
            return new InvalidFieldException("An invalid user was specified", exception);
        }

        default:
        {
            StringBuilder result = new StringBuilder();
            result.append("Encountered a fault:");
            result.append(exception.code);
            result.append("  ");
            result.append(exception.getMessage());
            return new BugzillaException(result.toString() , exception);
        }
        //end switch
        }
    }

}
