package com.google.code.SpringBug;

import static org.junit.Assert.assertNotNull;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations= { "classpath*:com/google/code/SpringBug/**/context.xml","classpath:/context-test.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringRunner {

    @Resource(name="Bugzilla")
    BugzillaConnector con;
    
    @Test
    public void testConnection() {
        assertNotNull(con);
    }
/*
    @Resource(name="jdbc_hsql")
    DataSource ds;
    
    
    @Resource
    Main main;

    @Test
    public void testDI() {
        assertNotNull(main);
    }
    
    */

}
