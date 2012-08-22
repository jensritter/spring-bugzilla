package org.jens.SpringBug.j2bugzilla;

import java.util.*;


/**
 * The Class BugFields.
 */
public class BugFields implements BugzillaMethod{

    /** The Constant METHOD_NAME. */
    private static final String METHOD_NAME = "Bug.fields";

    //private Map<Object, Object> params = new HashMap<Object, Object>();
    /** The hash. */
    private Map<Object, Object> hash = new HashMap<Object, Object>();

    /**
     * Gets the fields.
     *
     * @return the fields
     */
    @SuppressWarnings("unchecked")
    public List<Field> getFields() {
        Object[] wert =  (Object[]) hash.get("fields");

        ArrayList<Field> result = new ArrayList<Field>();
        for (Object it : wert) {
            result.add(new Field((Map<Object,Object>)it));
        }
        return result;
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
        //Object[] lst = new Object[] { "product"};
        //param.put("names", lst);
        return param;
    }

    /* (non-Javadoc)
     * @see de.mitegro.bugzilla.j2bugzilla.BugzillaMethod#getMethodName()
     */
    @Override
    public String getMethodName() {
        return METHOD_NAME;
    }


    /**
     * The Class Field.
     */
    public class Field {

        /** The wert. */
        final Map<Object,Object> wert;

        /**
         * Instantiates a new field.
         *
         * @param it the it
         */
        Field(Map<Object, Object> it) {
            wert = it;
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
            return (Integer)wert.get("id");
        }

        /**
         * Gets the display name.
         *
         * @return the display name
         */
        public String getDisplayName() {
            return (String) wert.get("display_name");
        }

        /**
         * Gets the type.
         *
         * @return the type
         */
        public int getType() {
            /*
            0 Unknown
            1 Free Text
            2 Drop Down
            3 Multiple-Selection Box
            4 Large Text Box
            5 Date/Time
            6 Bug Id
            7 Bug URLs ("See Also")
             */
            return (Integer) wert.get("type");
        }

        /**
         * Checks if is custom.
         *
         * @return true, if is custom
         */
        public boolean isCustom() {
            return (Boolean) wert.get("is_custom");
        }

        /**
         * Gets the name.
         *
         * @return the name
         */
        public String getName() {
            return (String) wert.get("name");
        }

        /**
         * Checks if is mandatory.
         *
         * @return true, if is mandatory
         */
        public boolean isMandatory() {
            return (Boolean) wert.get("is_mandatory");
        }

        /**
         * Checks if is on bug entry.
         *
         * @return true, if is on bug entry
         */
        public boolean isOnBugEntry() {
            return (Boolean) wert.get("is_on_bug_entry");
        }

        /**
         * Gets the visibility field.
         *
         * @return the visibility field
         */
        public String getVisibilityField() {
            return (String) wert.get("visibility_field");
        }


        /**
         * Gets the values.
         *
         * @return the values
         */
        @SuppressWarnings("unchecked")
        public List<Value> getValues() {
            Object[] werte = (Object[])wert.get("values");
            ArrayList<Value> result = new ArrayList<Value>();
            if (werte != null) {
                for(Object it : werte) {
                    result.add(new Value((Map<Object, Object>) it));
                }
            }
            return result;
        }
    }

    /**
     * The Class Value.
     */
    public class Value {

        /** The hs. */
        final Map<Object,Object> hs;

        /**
         * Instantiates a new value.
         *
         * @param hash the hash
         */
        Value(Map<Object,Object> hash) {
            this.hs = hash;
        }

        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return getName();
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
         * Gets the sortkey.
         *
         * @return the sortkey
         */
        public int getSortkey() {
            return (Integer) hs.get("sortkey");
        }

        /**
         * Checks if is open.
         *
         * @return true, if is open
         */
        public boolean isOpen() {
            Object tmp = hs.get("is_open");
            if (tmp != null) {
                return (Boolean) tmp;
            }
            return false;
        }
    }



}
