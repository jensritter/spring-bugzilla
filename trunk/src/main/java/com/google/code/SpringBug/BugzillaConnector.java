package com.google.code.SpringBug;

import java.io.IOException;
import java.net.*;
import java.util.*;

import org.apache.xmlrpc.*;
import org.apache.xmlrpc.client.*;
import org.slf4j.*;

import com.google.code.SpringBug.j2bugzilla.exceptions.*;
import com.google.code.SpringBug.rpc.*;
import com.google.code.SpringBug.rpc.ProductGet.Product;

/**
 * The <code>BugzillaConnector</code> class handles all access to a given Bugzilla installation.
 * The Bugzilla API uses XML-RPC, implemented via the Apache XML-RPC library in this instance.
 * @author Tom
 * @author jens.ritter@gmail.com
 * @see <a href="http://www.bugzilla.org/docs/tip/en/html/api/Bugzilla/WebService.html">WebService</a>
 * @see <a href="http://www.bugzilla.org/docs/tip/en/html/api/Bugzilla/WebService/Server/XMLRPC.html">XML-RPC</a>
 */
public class BugzillaConnector {
    
    private static Logger logger = LoggerFactory.getLogger(BugzillaConnector.class);
    /**
     * The {@link XmlRpcClient} handles all requests to Bugzilla by transforming method names and
     * parameters into properly formatted XML documents, which it then transmits to the host.
     */
    private XmlRpcClient client;
    
    /**
     * A <code>List</code> of cookies received from the installation, used for authentication
     */
    private ArrayList<String> cookies = new ArrayList<String>();
    
    /**
     * Use this method to designate a host to connect to. You must call this method
     * before executing any other methods of this object.
     * 
     * @param host A string pointing to the domain of the Bugzilla installation
     * @throws ConnectionException if a connection cannot be established
     */
    public void connectTo(String host) throws ConnectionException {
        connectTo(host,null,null);
    }


    public void connectTo(String host, String httpUser, String httpPasswd) throws ConnectionException {

        String newHost = host;
        if(!newHost.endsWith("xmlrpc.cgi")) {
            if(newHost.endsWith("/")) {
                newHost += "xmlrpc.cgi";
            } else {
                newHost += "/xmlrpc.cgi";
            }
        }
        
        if (!newHost.startsWith("http")) {
            newHost = "http://" + newHost;
        }

        URL hostURL;
        try {
            hostURL = new URL(newHost);
        } catch (MalformedURLException e) {
            logger.error("URL parameter for host is improperly formed; cannot connect", e);
            throw new ConnectionException("Host URL is malformed; URL supplied was " + newHost, e);
        }
        connectTo(hostURL,httpUser,httpPasswd);
        
    }


    public void connectTo(URL host, String username, String password) {
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        if (username != null) {
            config.setBasicUserName(username);
            config.setBasicPassword(password);
        }
        config.setServerURL(host);

        client = new XmlRpcClient();
        client.setConfig(config);

        /**
         * Here, we override the default behavior of the transport factory to properly
         * handle cookies for authentication
         */
        XmlRpcTransportFactory factory = new XmlRpcSunHttpTransportFactory(client) {
            /**
             * We override another internal class to properly define cookie behavior
             */
            @Override
            public XmlRpcTransport getTransport() {
                return new XmlRpcSunHttpTransport(client) {

                    private URLConnection conn;

                    @Override
                    protected URLConnection newURLConnection(URL pURL) throws IOException {
                        conn = super.newURLConnection(pURL);
                        return conn;
                    }

                    /**
                     * This is the meat of these two overrides -- the HTTP header data now includes the
                     * cookies received from the Bugzilla installation on login and will pass them every
                     * time a connection is made to transmit or receive data.
                     */
                    @Override
                    protected void initHttpHeaders(XmlRpcRequest request) throws XmlRpcClientException {
                        super.initHttpHeaders(request);
                        if(cookies.size()>0) {
                            StringBuilder commaSep = new StringBuilder();

                            for(String str : cookies) {
                                commaSep.append(str);
                                commaSep.append(",");
                            }
                            setRequestHeader("Cookie", commaSep.toString());
                        }
                    }

                    @Override
                    protected void close() throws XmlRpcClientException {
                        getCookies(conn);
                    }

                    /**
                     * Retrieves cookie values from the HTTP header of Bugzilla responses
                     * @param value
                     */
                    private void getCookies(URLConnection value) {
                        if(cookies.size()==0) {
                            Map<String, List<String>> headers = value.getHeaderFields();
                            if(headers.containsKey("Set-Cookie")) {//avoid NPE
                                List<String> vals = headers.get("Set-Cookie");
                                for(String str : vals) {
                                    cookies.add(str);
                                }
                            }
                        }

                    }
                };//end XmlRpcSunHttpTransport
            }
        };//end XmlRpcSunHttpTransportFactory
        //after all that, tell our client to use our custom Factory
        client.setTransportFactory(factory);
        
    }

    /**
     * Allows the API to execute any properly encoded XML-RPC method.
     * If the method completes properly, the {@link BugzillaMethod#setResultMap(Map)}
     * method will be called, and the implementation class will provide
     * methods to access any data returned.
     * 
     * @param method A {@link BugzillaMethod} to call on the connected installation
     * @throws BugzillaException
     */
    @SuppressWarnings("unchecked")
    public void executeMethod(BugzillaMethod method) throws BugzillaException {
        if(client == null) { return; }//We are not currently connected to an installation; prevent NPE

        Object[] obj = {method.getParameterMap()};
        try {
            Object results = client.execute(method.getMethodName(), obj);
            method.setResultMap((Map<Object,Object>)results);
        } catch (XmlRpcException e) {
            BugzillaException wrapperException = XmlExceptionHandler.handleFault(e);
            throw wrapperException;
        }
    }
    
    public List<Product> getAllProducts(BugzillaConnector con) throws BugzillaException {
        ProductListIDs productids = new ProductListIDs();
        executeMethod(productids);
        ProductGet productget = new ProductGet(productids.getAvailableProductids());
        executeMethod(productget);
        return productget.getProducts();
    }
}
