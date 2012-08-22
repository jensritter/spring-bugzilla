package com.google.code.SpringBug.j2bugzilla.rpc;

import java.util.*;

import com.google.code.SpringBug.j2bugzilla.*;


/**
 * This class allows clients to request a list of all public comments made on a 
 * specific {@link Bug} in a Bugzilla installation. The {@link Bug} must already
 * exist in the installation. 
 * 
 * @author Tom
 *
 */
public class BugComments implements BugzillaMethod {

    /**
     * The XML-RPC method Bugzilla will use
     */
    private static final String METHOD_NAME = "Bug.comments";

    private Map<Object, Object> params = new HashMap<Object, Object>();
    private Map<Object, Object> hash = new HashMap<Object, Object>();

    /**
     * The ID of the {@link Bug}
     */
    private final int id;

    /**
     * Creates a new {@link BugComments} object for the specified
     * {@link Bug}
     * 
     * @param bug A {@link Bug} to retrieve comments for
     */
    public BugComments(Bug bug) {
        this(bug.getID());
    }

    /**
     * Creates a new {@link BugComments} object for the specified
     * {@link Bug} id.
     * @param id An integer specifying which {@link Bug} to retrieve
     * comments for
     */
    public BugComments(int id) {
        this.id = id;
        params.put("ids", id);
    }

    /**
     * Returns a <code>List</code> of all public comments made on the
     * {@link Bug} requested from the installation
     * 
     * @return A List of Strings representing user comments
     */
    @SuppressWarnings("unchecked")
    public List<String> getComments() {
        List<String> commentList = new ArrayList<String>();

        if(hash.containsKey("bugs")) {
            /*
             * Hideous, but it's the structure of the XML that
             * Bugzilla returns. Since it's designed to return comments for
             * multiple bugs at a time, there's extra nesting we don't need.
             * TODO Allow requests for lists of Bugs
             */
            
            Map<String, Map<String, Map<Object, Object>[]>> m = 
                (Map<String, Map<String, Map<Object, Object>[]>>)hash.get("bugs");

            Map<String, Map<Object, Object>[]> weird = m.get(String.valueOf(id));
            Object[] comments = weird.get("comments");

            if(comments.length == 0) { return commentList; } //Early return to prevent ClassCast

            for(Object o : comments) {
                Map<Object, Object> comment = (Map<Object, Object>)o;
                commentList.add((String)comment.get("text"));
            }
        }

        return commentList;
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
