package com.google.code.SpringBug.rpc;

import java.io.*;
import java.util.*;

import org.apache.commons.codec.binary.Base64OutputStream;
import org.jens.Shorthand.Swallow;

import com.google.code.SpringBug.*;

/**
 * The Class AttachmentBug.
 */
public class AttachmentBug implements BugzillaMethod {

    /**
     * The method Bugzilla will execute via XML-RPC
     * http://www.bugzilla.org/docs/4.0/en/html/api/Bugzilla/WebService/Bug.html#add_attachment
     */
    private static final String METHOD_NAME = "Bug.add_attachment";

    /** The params. */
    private Map<Object, Object> params = new HashMap<Object, Object>();
    //private Map<Object, Object> hash = new HashMap<Object, Object>();


    /**
     * Instantiates a new attachment bug.
     *
     * @param bug the bug
     * @param filename the filename
     * @param comment the comment
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public AttachmentBug(Bug bug, String filename, String comment) throws IOException {
        this(bug.getID(),filename,comment);
    }

    /**
     * Instantiates a new attachment bug.
     *
     * @param id the id
     * @param filename the filename
     * @param comment the comment
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public AttachmentBug(int id, String filename, String comment) throws IOException {
        params.put("ids",id);
        if (filename.startsWith("http")) {
            params.put("is_url", true);
            params.put("data",filename);
            //params.put("content_type","text/plain");
        } else {
            params.put("data",base64_encodeFile(filename));
            params.put("content_type",guessMime(filename));
        }

        params.put("file_name",filename);
        params.put("summary",comment);

    }


    /*
Params
ids
Required array An array of ints and/or strings--the ids or aliases of bugs that you want to add this attachment to. The same attachment and comment will be added to all these bugs.

data
Required base64 The content of the attachment.

file_name
Required string The "file name" that will be displayed in the UI for this attachment.

summary
Required string A short string describing the attachment.

content_type
Required string The MIME type of the attachment, like text/plain or image/png.

comment
string A comment to add along with this attachment.

is_patch
boolean True if Bugzilla should treat this attachment as a patch. If you specify this, you do not need to specify a content_type. The content_type of the attachment will be forced to text/plain.

Defaults to False if not specified.

is_private
boolean True if the attachment should be private (restricted to the "insidergroup"), False if the attachment should be public.

Defaults to False if not specified.

is_url
boolean True if the attachment is just a URL, pointing to data elsewhere. If so, the data item should just contain the URL.

Defaults to False if not specified.
     */

    /**
     * Base64_encode file.
     *
     * @param filename the filename
     * @return the string
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private String base64_encodeFile(String filename) throws IOException {
        FileInputStream in = null;
        Base64OutputStream out = null;
        try {
            File file = new File(filename);
            long size=file.length();
            if (size > Integer.MAX_VALUE) {
                throw new IOException("Filesize too big. ("+Integer.MAX_VALUE+")");
            }
            final ByteArrayOutputStream buf = new ByteArrayOutputStream();
            out = new Base64OutputStream(buf);
            in = new FileInputStream(file);

            byte[] cache = new byte[4096];
            int len = -1;
            while ((len = in.read(cache)) != -1) {
                out.write(cache, 0, len);
            }
            return buf.toString();
        } finally {
            Swallow.close(in);
            Swallow.close(out);
        }
    }

    /**
     * Guess mime.
     *
     * @param filename the filename
     * @return the string
     */
    String guessMime(String filename) {
        String type = "application/octet-stream";
        if (filename.length()<4) {
            return "application/octet-stream"; // unknown
        }
        if (filename.endsWith("txt")) {
            return "text/plain";
        }
        if (filename.endsWith("xml")) {
            return "application/xml";
        }
        return type;
    }


    /* (non-Javadoc)
     * @see de.mitegro.bugzilla.j2bugzilla.BugzillaMethod#setResultMap(java.util.Map)
     */
    @Override
    public void setResultMap(Map<Object, Object> hash) {
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
