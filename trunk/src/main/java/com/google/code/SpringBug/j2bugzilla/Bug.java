package com.google.code.SpringBug.j2bugzilla;

import java.util.*;

import org.joda.time.LocalDate;
import org.joda.time.format.*;
import org.slf4j.*;

import com.google.code.SpringBug.j2bugzilla.exceptions.InvalidDescriptionException;


/**
 * The Class Bug.
 */
public class Bug {

    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(Bug.class);
    /*
    private String product = "Mitegro";
    private String op_sys = "All";
    private String platform = "All";
    private String version = "unspecified";
    private String priority = "P3";
     */
    /*
    private String product;
    private String op_sys;
    private String platform;
    private String version;
    private String priority;

    private String component;
    private String summary;
    private String severity = "";
    private String description;
    private String deadline;
    private String estimated;
    private List<String> cc = new ArrayList<String>();
    private String assigned_to;
     */
    /** The keys which <em>must</em> have non-blank values for a bug to be properly submitted. */
    private static String[] requiredKeys = {}; // "product", "component", "summary"

    /**
     * Keys which will default if not included. It is legal to leave them blank, but a warning may be
     * raised if in debug mode.
     */
    private static String[] defaultKeys = {"description", "op_sys", "platform", "priority", "severity"};

    /**
     * Enum describing the legitimate values for a Bug's priority rank.
     *
     * @author Tom
     */
    public enum Priority {
        /** The P1. */
        P1,
        /** The P2. */
        P2,
        /** The P3. */
        P3,
        /** The P4. */
        P4,
        /** The P5. */
        P5}

    /**
     * HashMap representing fields for each Bug. The Value for each Key is a <code>String</code>
     * <em>except</em> for the CC: field, which is an array of <code>Strings</code>.
     */
    private Map<String, Object> internalState;

    /**
     * Only for Compatibility with tom's bug
     * 
     * Constructor for creating a new {@link Bug} to submit to an installation.
     * The constructor checks for required values, and throws an
     *
     * @param state A <code>Map</code> pairing required keys to values
     * @throws InvalidDescriptionException if the state you provide excludes a required key/value pair.
     * {@link InvalidDescriptionException} if one or more required values are missing.
     */
    public Bug(Map<String, Object> state) throws InvalidDescriptionException{
        checkRequiredFields(state);
        //If an exception is thrown from the above, the below will not execute.
        //Therefore, all subsequent calls to methods on this Bug will fail with NPEs.
        internalState = state;
    }

    /**
     * Plain Constructor. Fills no fields !
     */
    public Bug() {
        internalState = new HashMap<String,Object>();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(getID()).append(" ").append(getPriority()).append(" ").append(getSummary());
        return result.toString();
    }

    /**
     * Internal method for determining whether a given <code>HashMap</code> is a valid
     * representation of a {@link Bug} or not.
     * @param state a collection of String keys and String values in a <code>HashMap</code>
     * @throws InvalidDescriptionException if a required key/value pair is not provided
     */
    private static void checkRequiredFields(Map<String, Object> state) throws InvalidDescriptionException {
        for(String str : requiredKeys) {
            if(!state.containsKey(str)) {
                throw new InvalidDescriptionException("Missing key/value pair: " + str);
            }
        }
        for(String str : defaultKeys) {
            if(!state.containsKey(str)) {
                LOG.debug("Bug did not contain field " + str + "; a default will be used.");
            }
        }

    }



    /**
     * Checks if is 3.
     *
     * @return true, if is 3
     */
    private boolean is3() {
        return internalState.containsKey("internals");
    }

    /**
     * Gets the iD.
     *
     * @return the iD
     */
    public int getID() {
        Integer value = (Integer)internalState.get("id");
        if (value == null) {
            return -1;
        }
        return value;
    }

    /**
     * Gets the internal state.
     *
     * @return the internal state
     */
    public Map<String, Object> getInternalState() {
        return internalState;
    }

    /**
     * Returns how highly this bug is ranked.
     *
     * @return the priority
     */
    public Priority getPriority() {
        String priority = (String) internalState.get("priority");
        if (priority == null) {
            throw new RuntimeException("Unknown Priority: NULL");
        }
        int level = Integer.valueOf(priority.substring(1,2));
        switch(level) {
        case 1: return Priority.P1;
        case 2: return Priority.P2;
        case 3: return Priority.P3;
        case 4: return Priority.P4;
        case 5: return Priority.P5;
        default: throw new RuntimeException("Unknown Priority:" + priority);
        }
    }


    /** The Constant FMTBUGZILLA. */
    public static final DateTimeFormatter FMTBUGZILLA = new DateTimeFormatterBuilder().appendYear(4, 4).appendLiteral('-').appendMonthOfYear(2).appendLiteral('-').appendDayOfMonth(2).toFormatter();

    /**
     * Gets the deadline txt.
     *
     * @return the deadline txt
     */
    @SuppressWarnings("unchecked")
    public String getDeadlineTXT() {
        Object has =internalState.get("deadline");
        if (has != null) {
            return has.toString();
        }
        if (internalState.containsKey("internals")) {
            Map<String,Object> values = (Map<String,Object>) internalState.get("internals");
            // old bugzilla-version stores this in subkey ( internals )
            has = values.get("deadline");
            if (has != null) {
                return has.toString();
            }
        }
        return "";
    }

    /**
     * Gets the deadline.
     *
     * @return the deadline
     */
    public LocalDate getDeadline() {
        String time = getDeadlineTXT();
        if (time == null || time.equals("")) {
            return null;
        }
        String year = time.substring(0,4);
        String month = time.substring(5,7);
        String day = time.substring(8,10);
        return new LocalDate(Integer.valueOf(year), Integer.valueOf(month), Integer.valueOf(day));
    }

    /**
     * Sets the deadline.
     *
     * @param value the new deadline
     */
    public void setDeadline(String value) {
        internalState.put("deadline", value);
    }

    /** The fmt bugzilla. */
    private final DateTimeFormatter fmtBugzilla = new DateTimeFormatterBuilder().appendYear(4, 4).appendLiteral('-').appendMonthOfYear(2).appendLiteral('-').appendDayOfMonth(2).toFormatter();

    /** The fmt. */
    private final DateTimeFormatter fmt = new DateTimeFormatterBuilder()
    .appendDayOfMonth(2)
    .appendLiteral(".")
    .appendMonthOfYear(2)
    .appendLiteral(".")
    .appendYear(2, 4)
    .toFormatter();

    /**
     * Sets the deadline.
     *
     * @param value the new deadline
     */
    public void setDeadline(LocalDate value) {
        setDeadline(fmtBugzilla.print(value));
    }

    /**
     * Sets the deadline.
     *
     * @param value the new deadline
     */
    public void setDeadline(Date value) {
        setDeadline(new LocalDate(value));
    }

    /**
     * Gets the deadline fmt.
     *
     * @return the deadline fmt
     */
    public String getDeadlineFmt() {
        LocalDate dl = getDeadline();
        if (dl != null) {
            return fmt.print(dl);
        }
        return "";
    }

    /**
     * Sets the deadline today.
     */
    public void setDeadlineToday() {
        // egal bei welcher version. wenn ich eine bug submitte, muss deadline im main-map stehen.
        internalState.put("deadline", FMTBUGZILLA.print(new LocalDate()));
    }

    /**
     * Removes the deadline.
     */
    @SuppressWarnings("unchecked")
    public void removeDeadline() {
        internalState.remove("deadline");
        if (is3()) {
            Map<String,Object> values = (Map<String,Object>) internalState.get("internals");
            values.remove("deadline");
        }
    }

    // Properties :
    /**
     * Gets the ccs.
     *
     * @return the ccs
     */
    public List<String> getCcs() {
        if (internalState.containsKey("cc")) { // only while bug-creating !!
            Object[] cc = (Object[]) internalState.get("cc");
            ArrayList<String> result = new ArrayList<String>();
            for(Object it : cc) {
                result.add(it.toString());
            }
            return result;
        }
        return new ArrayList<String>();
    }

    /**
     * Adds the cc.
     *
     * @param member the member
     */
    public void addCc(String member) {
        List<String> cur = getCcs();
        cur.add(member);
        Object[] what = new Object[cur.size()];
        for(int i=0; i<cur.size(); i++) {
            what[i] = cur.get(i);
        }
        internalState.put("cc", what);
    }

    /**
     * Removes the cc.
     *
     * @param member the member
     * @return true, if successful
     */
    public boolean removeCC(String member) {
        List<String> cur = getCcs();
        boolean result = cur.remove(member);
        if (result) {
            Object[] what = new Object[cur.size()];
            for(int i=0; i<cur.size(); i++) {
                what[i] = cur.get(i);
            }
            internalState.put("cc", what);
        }
        return result;
    }

    /**
     * Sets the cc.
     *
     * @param values the new cc
     */
    public void setCc(String[] values) {
        Object[] what = new Object[values.length];
        for(int i=0; i<values.length; i++) {
            what[i] = values[i];
        }
        internalState.put("cc", what);
    }

    /**
     * Clear cc.
     */
    public void clearCc() {
        internalState.remove("cc");
    }

    /**
     * Gets the product.
     *
     * @return the product
     */
    @SuppressWarnings("unchecked")
    public String getProduct() {
        String value= (String)internalState.get("product");
        if (value == null && is3()) {
            Map<String,Object> submap = (Map<String, Object>) internalState.get("internals");
            value = (String) submap.get("product");
        }
        return value;
    }

    /**
     * Sets the product.
     *
     * @param value the new product
     */
    public void setProduct(String value) {
        internalState.put("product", value);
    }

    /**
     * Gets the op_sys.
     *
     * @return the op_sys
     */
    @SuppressWarnings("unchecked")
    public String getOp_sys() {
        Object value = internalState.get("op_sys");
        if (value == null && is3()) { // wenn ich einen bug erzeuge, ist dieses nicht(!) in internals !
            Map<String,Object> submap = (Map<String, Object>) internalState.get("internals");
            value = submap.get("op_sys");
        }
        return (String)value;
    }

    /**
     * Sets the op_sys.
     *
     * @param op_sys the new op_sys
     */
    public void setOp_sys(String op_sys) {
        internalState.put("op_sys", op_sys);
    }

    /**
     * Gets the platform.
     *
     * @return the platform
     */
    @SuppressWarnings("unchecked")
    public String getPlatform() {
        String result =  (String)internalState.get("platform");
        if (result == null && is3()) {
            Map<String,Object> submap = (Map<String, Object>) internalState.get("internals");
            return (String)submap.get("rep_platform");
        }
        return result;
    }

    /**
     * Sets the platform.
     *
     * @param platform the new platform
     */
    public void setPlatform(String platform) {
        internalState.put("platform", platform);
    }

    /**
     * Gets the version.
     *
     * @return the version
     */
    @SuppressWarnings("unchecked")
    public String getVersion() {
        String value= (String)internalState.get("version");
        if (value == null && is3()) {
            Map<String,Object> submap = (Map<String, Object>) internalState.get("internals");
            return (String)submap.get("version");
        }
        return value;
    }

    /**
     * Sets the version.
     *
     * @param version the new version
     */
    public void setVersion(String version) {
        internalState.put("version", version);
    }

    /**
     * Gets the priority string.
     *
     * @return the priority string
     */
    public String getPriorityString() {
        return (String)internalState.get("priority");
    }

    /**
     * Sets the priority.
     *
     * @param priority the new priority
     */
    public void setPriority(String priority) {
        internalState.put("priority", priority);
    }

    /**
     * Gets the component.
     *
     * @return the component
     */
    public String getComponent() {
        return (String)internalState.get("component");
    }

    /**
     * Sets the component.
     *
     * @param component the new component
     */
    public void setComponent(String component) {
        internalState.put("component", component);
    }

    /**
     * Gets the summary.
     *
     * @return the summary
     */
    public String getSummary() {
        return (String)internalState.get("summary");
    }

    /**
     * Sets the summary.
     *
     * @param summary the new summary
     */
    public void setSummary(String summary) {
        internalState.put("summary", summary);
    }

    /**
     * Gets the severity.
     *
     * @return the severity
     */
    public String getSeverity() {
        return (String)internalState.get("severity");
    }

    /**
     * Sets the severity.
     *
     * @param severity the new severity
     */
    public void setSeverity(String severity) {
        internalState.put("severity", severity);
    }
    /*
    public String getDescription() {
        throw new RuntimeException("This should only be used while creating a bug");
        //return (String)internalState.get("description");
    }
     */
    /**
     * Sets the description.
     *
     * @param description the new description
     */
    public void setDescription(String description) {
        internalState.put("description", description);
    }

    /**
     * Gets the estimated.
     *
     * @return the estimated
     */
    @SuppressWarnings("unchecked")
    public Double getEstimated() {
        Double value = (Double)internalState.get("estimated_time");
        if (value == null && is3()) {
            Map<String,Object> subkey = (Map<String, Object>) internalState.get("internals");
            value = (Double) subkey.get("estimated_time");
        }
        if (value == null) {
            return Double.valueOf(0.0);
        }
        return value;
    }

    /**
     * Sets the estimated.
     *
     * @param estimated the new estimated
     */
    public void setEstimated(Double estimated) {
        internalState.put("estimated_time", estimated);
    }

    /**
     * Gets the assigned_to.
     *
     * @return the assigned_to
     */
    public String getAssigned_to() {
        return (String)internalState.get("assigned_to");
    }

    /**
     * Sets the assigned_to.
     *
     * @param assigned_to the new assigned_to
     */
    public void setAssigned_to(String assigned_to) {
        internalState.put("assigned_to", assigned_to);
    }

    /**
     * Gets the BugID's for Bugs which this Bug blocks.
     *
     * @return the blocks
     */
    public List<Integer> getBlocks() {
        return getBugIdsFromFeld("blocks");

    }

    /**
     * Helper-Methode für getBlocks und getDepends
     * @param feldname
     * @return
     */
    private List<Integer> getBugIdsFromFeld(String feldname) {
        Object entry  = internalState.get(feldname);
        ArrayList<Integer> result = new ArrayList<Integer>();
        if (entry == null) {
            return result;
        }
        Object[] items = (Object[])entry;
        for(Object it : items) {
            result.add((Integer)it);
        }
        return result;
    }

    /**
     * Gets the BugID's for Bugs which this Bug depends on.
     *
     * @return the depends
     */
    public List<Integer> getDepends() {
        return getBugIdsFromFeld("depends_on");
    }
}
