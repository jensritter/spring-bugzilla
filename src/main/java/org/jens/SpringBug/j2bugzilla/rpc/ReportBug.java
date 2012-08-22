package org.jens.SpringBug.j2bugzilla.rpc;

import java.util.*;

import org.jens.SpringBug.j2bugzilla.*;

/**
 * This class allows clients to report a new {@link Bug} to a Bugzilla installation.
 * @author Tom
 *
 */
public class ReportBug implements BugzillaMethod {

    /**
     * The method name Bugzilla will execute via XML-RPC
     */
    private static final String METHOD_NAME = "Bug.create";

    private Map<Object, Object> params = new HashMap<Object, Object>();
    private Map<Object, Object> hash = new HashMap<Object, Object>();

    /**
     * Creates a new {@link ReportBug} object to add a newly created {@link Bug}
     * to the appropriate Bugzilla installation
     * 
     * @param bug A new {@link Bug} that should be tracked
     */
    public ReportBug(Bug bug) {
        //Ugly, but can't cast directly to the less precise Map<Object, Object>
        for(String key : bug.getInternalState().keySet()) {
            params.put(key, bug.getInternalState().get(key));
        }
    }

    /**
     * Returns the ID of the newly-reported {@link Bug}, or -1 if no such
     * ID can be determined. Reasons for this might include calling this method
     * before passing the {@link ReportBug} object to the 
     * {@link BugzillaConnector#executeMethod(BugzillaMethod)} method, or if there 
     * was an exception while adding the {@link Bug}
     * 
     * @return the ID of the new {@link Bug}
     */
    public int getID() {
        if(hash.containsKey("id")) {
            Integer i = (Integer)hash.get("id");
            return i.intValue();
        } else {
            return -1;
        }
    }

    @Override
    public void setResultMap(Map<Object, Object> hash) {
        this.hash = hash;
    }

    @Override
    public Map<Object, Object> getParameterMap() {
        return params;
    }

    @Override
    public String getMethodName() {
        return METHOD_NAME;
    }

}
