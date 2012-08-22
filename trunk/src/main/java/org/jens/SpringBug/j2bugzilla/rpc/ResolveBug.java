package org.jens.SpringBug.j2bugzilla.rpc;

import java.util.*;

import org.jens.SpringBug.j2bugzilla.*;

/**
 * The Class ResolveBug.
 */
public class ResolveBug implements BugzillaMethod {

    /** The method name Bugzilla will execute via XML-RPC. */
    private static final String METHOD_NAME = "Bug.update";

    /** The params. */
    private Map<Object, Object> params = new HashMap<Object, Object>();

    //@edu.umd.cs.findbugs.annotations.SuppressWarnings("URF_UNREAD_FIELD")
    //private Map<Object, Object> hash = new HashMap<Object, Object>();

    /**
     * Creates a new {@link CommentBug} object to add a comment to an
     * existing {@link Bug} in the client's installation.
     *
     * @param bug A <code>Bug</code> to comment on
     * @param comment The comment to append
     * @param status the status
     * @param resolution the resolution
     */
    public ResolveBug(Bug bug, String comment, String status, String resolution) {
        this(bug.getID(), comment, status, resolution);
    }

    /**
     * Creates a new {@link CommentBug} object to add a comment to an
     * existing {@link Bug} in the client's installation.
     *
     * @param id The integer ID of an existing <code>Bug</code>
     * @param comment The comment to append
     * @param status the status
     * @param resolution the resolution
     */
    public ResolveBug(int id, String comment, String status, String resolution) {
        params.put("ids", id);

        /*
        comment
        hash. A comment on the change. The hash may contain the following fields:

        body string The actual text of the comment. Note: For compatibility with the parameters to "add_comment", you can also call this field comment, if you want.
        is_private boolean Whether the comment is private or not. If you try to make a comment private and you don't have the permission to, an error will be thrown.

         */
        Map<String,Object> tmp = new HashMap<String,Object>();
        tmp.put("body_string",comment);
        params.put("comment", tmp);

        /*
        status
        string The status you want to change the bug to. Note that if a bug is changing from open to closed, you should also specify a resolution.

         */
        params.put("status",status);

        /*
        resolution
        string The current resolution. May only be set if you are closing a bug or if you are modifying an already-closed bug. Attempting to set the resolution to any value (even an empty or null string) on an open bug will cause an error to be thrown.

        If you change the status field to an open status, the resolution field will automatically be cleared, so you don't have to clear it manually.
         */
        params.put("resolution",resolution);


    }

    /* (non-Javadoc)
     * @see de.mitegro.bugzilla.j2bugzilla.BugzillaMethod#setResultMap(java.util.Map)
     */
    @Override
    public void setResultMap(Map<Object, Object> hash) {
        // liefert den Diff zurück. Ignoriere ich einfach mal . . .
        //this.hash = hash;
    }

    /* (non-Javadoc)
     * @see de.mitegro.bugzilla.j2bugzilla.BugzillaMethod#getParameterMap()
     */
    @Override
    public Map<Object, Object> getParameterMap() {
        return params;
    }

    /* (non-Javadoc)
     * @see de.mitegro.bugzilla.j2bugzilla.BugzillaMethod#getMethodName()
     */
    @Override
    public String getMethodName() {
        return METHOD_NAME;
    }
}
