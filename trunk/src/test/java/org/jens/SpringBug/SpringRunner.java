package org.jens.SpringBug;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations= { "classpath*:org/jens/**/context.xml","classpath:/context-test.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringRunner {

    
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
