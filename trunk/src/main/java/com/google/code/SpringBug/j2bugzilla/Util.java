package com.google.code.SpringBug.j2bugzilla;

import java.util.List;

import com.google.code.SpringBug.j2bugzilla.exceptions.BugzillaException;
import com.google.code.SpringBug.j2bugzilla.rpc.*;
import com.google.code.SpringBug.j2bugzilla.rpc.ProductGet.Product;

/**
 * The Class Util.
 */
public final class Util {

    /**
     * Instantiates a new util.
     */
    private Util() {

    }

    /**
     * Gets the values.
     * old and unused ., . . 
     * @param con the con
     * @return the values
     * @throws BugzillaException the bugzilla exception
     
    /*
    public static Map<String,List<String>> getValues(BugzillaConnector con) throws BugzillaException {
        Map<String,List<String>> map = new HashMap<String, List<String>>();
        BugFields fields = new BugFields();
        con.executeMethod(fields);
        for(Field field : fields.getFields()) {
            String name = field.getName();
            ArrayList<String> values = new ArrayList<String>();
            for(Value it : field.getValues()) {
                values.add(it.getName());
            }
            map.put(name,values);
        }
        map.remove("product"); // product wird anders abgefragt . ..
        ProductListIDs lst = new ProductListIDs();
        con.executeMethod(lst);
        ProductGet get = new ProductGet(lst.getAvailableProductids());
        con.executeMethod(get);
        ArrayList<String> names = new ArrayList<String>();
        for(Product it : get.getProducts()) {
            names.add(it.getName());
        }
        map.put("product",names);
        return map;
    }
    */
    
    /**
     * Get all Products ( and their Components ) from an Bugzilla-Installation.
     * @param con connection to and (logged in ) bugzilla-installation.
     * @return List of Products ( with their components )
     * @throws BugzillaException
     */
    public static List<Product> getAllProducts(BugzillaConnector con) throws BugzillaException {
        ProductListIDs productids = new ProductListIDs();
        con.executeMethod(productids);
        ProductGet productget = new ProductGet(productids.getAvailableProductids());
        con.executeMethod(productget);
        return productget.getProducts();
    }
}
