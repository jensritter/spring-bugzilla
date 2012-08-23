package com.google.code.SpringBug;

import java.net.URL;

import org.springframework.stereotype.Service;

import com.google.code.SpringBug.exceptions.ConnectionException;


@Service
public class BugConnectionFactory {
    
    BugzillaConnector connectTo(String hostname) throws ConnectionException {
        return connectTo(hostname,null,null);
    }
    
    BugzillaConnector connectTo(String hostname, String httpUser, String httpPasswd) throws ConnectionException { 
        BugzillaConnector con = new BugzillaConnector();
        con.connectTo(hostname, httpUser, httpPasswd);
        return con;
    }
    
    BugzillaConnector connectTo(URL host, String username, String password) {
        BugzillaConnector con = new BugzillaConnector();
        con.connectTo(host, username, password);
        return con;
    }
    
    BugzillaConnector connectTo(BugzillaCredentials cred) throws ConnectionException {
        BugzillaConnector con = new BugzillaConnector();
        con.connectTo(cred.getHostname(), cred.getHttpUser(), cred.getHttpPass());
        return con;
    }
    
    
}
