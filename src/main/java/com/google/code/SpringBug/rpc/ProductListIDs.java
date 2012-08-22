package com.google.code.SpringBug.rpc;

import java.util.*;

import com.google.code.SpringBug.BugzillaMethod;


/**
 * Holt alle (installierten) Product-ID's aus der Bugzilla-Installation.
 * ( liefert nur die id's - nicht die namen, etc. )
 * @see ProductGet damit weitere informationen zu diesem product geholt werden 
 */
public class ProductListIDs implements BugzillaMethod{
    // Product.get_accessible_products

    /** The Constant METHOD_NAME. */
    private static final String METHOD_NAME = "Product.get_accessible_products";

    /** The hash. */
    private Map<Object, Object> hash = new HashMap<Object, Object>();


    /* (non-Javadoc)
     * @see de.mitegro.bugzilla.j2bugzilla.BugzillaMethod#getMethodName()
     */
    @Override
    public String getMethodName() {
        return METHOD_NAME;
    }

    /* (non-Javadoc)
     * @see de.mitegro.bugzilla.j2bugzilla.BugzillaMethod#setResultMap(java.util.Map)
     */
    @Override
    public void setResultMap(Map<Object, Object> hash) {
        this.hash = hash;
    }

    /* (non-Javadoc)
     * @see de.mitegro.bugzilla.j2bugzilla.BugzillaMethod#getParameterMap()
     */
    @Override
    public Map<Object, Object> getParameterMap() {
        Map<Object,Object> param = new HashMap<Object,Object>();
        return param;
    }

    /**
     * Gets the available productids.
     *
     * @return the available productids
     */
    public List<Integer> getAvailableProductids() {
        Object[] lst = (Object[]) hash.get("ids");
        ArrayList<Integer> result = new ArrayList<Integer>();
        for(Object it : lst) {
            result.add((Integer)it);
        }
        return result;
    }
}
