package com.google.code.SpringBug.j2bugzilla.rpc;

import java.util.*;

import com.google.code.SpringBug.j2bugzilla.BugzillaMethod;

/**
 * The <code>BugzillaVersion</code> class allows clients to query a particular
 * Bugzilla installation as to its version.
 * 
 * @author Tom
 *
 */
public class BugzillaVersion implements BugzillaMethod {

    /**
     * The method Bugzilla will execute via XML-RPC
     */
    private static final String METHOD_NAME = "Bugzilla.version";

    private Map<Object, Object> params = new HashMap<Object, Object>();
    private Map<Object, Object> hash = new HashMap<Object, Object>();

    /**
     * Constructs a new {@link BugzillaVersion} object for querying an installation
     * as to the version of the Bugzilla software it is running
     */
    public BugzillaVersion() { }

    /**
     * Returns a <code>String</code> representing the Bugzilla software version
     * @return A <code>String</code> representing the Bugzilla software version
     */
    public String getVersion() {
        if(hash.containsKey("version")) {
            return (String)hash.get("version");
        } else {
            return "";
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
