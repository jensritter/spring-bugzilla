package com.google.code.SpringBug.j2bugzilla.rpc;

import java.util.*;

import org.slf4j.*;

import com.google.code.SpringBug.j2bugzilla.*;
import com.google.code.SpringBug.j2bugzilla.exceptions.InvalidDescriptionException;

/**
 * This class provides convenience methods for searching for {@link Bug}s on your installation.
 * @author Tom
 *
 */
public class BugSearch implements BugzillaMethod {

    /**
     * Private logging instance
     */
    private static final Logger LOG = LoggerFactory.getLogger(BugSearch.class);

    /**
     * The method Bugzilla will execute via XML-RPC
     */
    private static final String METHOD_NAME = "Bug.search";

    private Map<Object, Object> hash = new HashMap<Object, Object>();
    private Map<Object, Object> params = new HashMap<Object, Object>();

    /**
     * Defines a limit to a search for particular {@link Bug}s.
     * 
     * @author Tom
     *
     */
    public enum SearchLimiter {

        /**
         * The email of the assignee
         */
        OWNER("assigned_to"),

        /**
         * The email of the reporting user
         */
        REPORTER("reporter"),

        /**
         * The Status field value
         */
        STATUS("status"),

        /**
         * The resolution field, if the bug's status is closed. You can search
         * for all open bugs by searching for a blank resolution.
         */
        RESOLUTION("resolution"),

        /**
         * The {@link com.google.code.SpringBug.j2bugzilla.Bug.Priority} field value
         */
        PRIORITY("priority"),

        /**
         * The product affected by this bug
         */
        PRODUCT("product"),

        /**
         * The component affected by this bug
         */
        COMPONENT("component"),

        /**
         * The operating system affected by this bug
         */
        OPERATING_SYSTEM("op_sys"),

        /**
         * The hardware affected by this bug
         */
        PLATFORM("platform"),

        /**
         * The initial summary comment
         */
        SUMMARY("summary"),

        /**
         * The version affected by this bug
         */
        VERSION("version"),

        /**
         * A particular Bug ID
         */
        ID("id");

        private final String name;
        /**
         * Creates a new {@link SearchLimiter} with the
         * designated name
         * @param name The name Bugzilla expects for this search limiter
         */
        SearchLimiter(String name) {
            this.name = name;
        }
        /**
         * Get the name Bugzilla expects for this search limiter
         * @return A <code>String</code> representing the search limiter
         */
        String getName() {
            return this.name;
        }
    }

    /**
     * Creates a new {@link BugSearch} object with the appropriate search limit
     * and query string.
     * @param limit What dimension to search {@link Bug}s by in the Bugzilla installation
     * @param query What to match fields against
     */
    public BugSearch(SearchLimiter limit, String query) {
        params.put(limit.getName(), query);
    }

    /**
     * Adds the query param.
     *
     * @param limit the limit
     * @param query the query
     */
    public void addQueryParam(SearchLimiter limit, Object query) {
        params.put(limit.getName(), query);
    }

    /**
     * Instantiates a new bug search.
     */
    public BugSearch() {
        // for all bugs !!
    }

    /**
     * Returns the {@link Bug}s found by the query as a <code>List</code>
     * @return a {@link List} of {@link Bug}s that match the query and limit
     */
    public List<Bug> getSearchResults() {
        List<Bug> results = new ArrayList<Bug>();
        /*
         * The following is messy, but necessary due to how the returned XML document nests
         * Maps.
         */

        if(hash.containsKey("bugs")) {

            //Map<String, Object>[] bugList = (Map<String, Object>[])hash.get("bugs");
            Object[] bugs = (Object[])hash.get("bugs");
            if(bugs.length == 0) {
                return results; //early return if map is empty
            }

            for(Object o : bugs) {
                @SuppressWarnings("unchecked")
                Map<String, Object> bugMap = (HashMap<String, Object>)o;
                try {
                    results.add(new Bug(bugMap));
                } catch(InvalidDescriptionException e) {
                    //If this happens with a bug retrieved via search, there's
                    //nothing the API or user can do, short of modifying it
                    //via the web interface, so we'll log it and continue
                    LOG.debug("A bug retrieved via search was invalid", e);
                }
            }
        }
        return results;
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
