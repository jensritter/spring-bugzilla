package com.google.code.SpringBug.j2bugzilla.rpc;

import java.util.*;

import com.google.code.SpringBug.j2bugzilla.BugzillaMethod;


/**
 * 
 */
public class ProductGet implements BugzillaMethod{

    /** The Constant METHOD_NAME. */
    private static final String METHOD_NAME = "Product.get";

    /** The hash. */
    private Map<Object, Object> hash = new HashMap<Object, Object>();

    /** The product ids. */
    List<Integer> productIds;

    /**
     * Instantiates a new product get.
     *
     * @param lst the lst
     */
    public ProductGet(List<Integer> lst) {
        productIds = lst;
    }

    /* (non-Javadoc)
     * @see de.mitegro.bugzilla.j2bugzilla.BugzillaMethod#setResultMap(java.util.Map)
     */
    @Override
    public void setResultMap(Map<Object, Object> hash) {
        this.hash = hash;
    }

    /* (non-Javadoc)
     * @see de.mitegro.bugzilla.j2bugzilla.BugzillaMethod#getMethodName()
     */
    @Override
    public String getMethodName() {
        return METHOD_NAME;
    }

    /* (non-Javadoc)
     * @see de.mitegro.bugzilla.j2bugzilla.BugzillaMethod#getParameterMap()
     */
    @Override
    public Map<Object, Object> getParameterMap() {
        Map<Object,Object> param = new HashMap<Object,Object>();
        param.put("ids", productIds.toArray());
        return param;
    }

    /**
     * Gets the product names.
     *
     * @return the product names
     */
    @SuppressWarnings("unchecked")
    public List<Product> getProducts() {
        ArrayList<Product> result = new ArrayList<Product>();
        Object[] arr = (Object[]) hash.get("products");
        for(Object it : arr) {
            Map<Object,Object> item = (Map<Object, Object>) it;
            Product p = new Product(item);
            result.add(p);
        }
        return result;
    }

    /**
     * The Class Product.
     */
    public class Product {

        /** The hs. */
        final Map<Object,Object> hs;

        /**
         * Instantiates a new product.
         *
         * @param hash the hash
         */
        Product(Map<Object,Object> hash) {
            hs = hash;
        }

        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return getName();
        }

        /**
         * Gets the id.
         *
         * @return the id
         */
        public int getId() {
            return (Integer) hs.get("id");
        }

        /**
         * Gets the name.
         *
         * @return the name
         */
        public String getName() {
            return (String) hs.get("name");
        }

        /**
         * Gets the description.
         *
         * @return the description
         */
        public String getDescription() {
            return (String) hs.get("description");
        }
        
        @SuppressWarnings("unchecked")
        public List<Component> getComponents() {
            Object[] arr = (Object[])hs.get("components");
            ArrayList<Component> result = new ArrayList<Component>();
            if (arr == null || arr.length == 0) {
                return result;
            }
            for(Object it : arr) {
                
                Component c = new Component((Map<Object, Object>) it);
                result.add(c);
            }
            return result;
        }
    }
    
    public class Component {
        
        // {id=25, default_assigned_to=ritter@mitegro.de, is_active=true, description=Kalog, sort_key=0, name=Kalog, default_qa_contact=}
        final Map<Object,Object> data;
        
        Component(Map<Object,Object> data) {
            this.data = data;
        }
        
        public int getId() {
            return (Integer) data.get("id"); 
        }
        
        public String getDefaultAssignedTo() {
            return (String) data.get("default_assigned_to");
        }
        
        public boolean isActive() {
            String tx = (String) data.get("is_active");
            return "true".equals(tx);
        }
        
        public String getDescription() {
            return (String) data.get("description");
        }
        
        public String getName() {
            return (String) data.get("name");
        }
        
        public String getDefaultQAContact() {
            return (String) data.get("default_qa_contact");
        }
        
        
        
    }

}
