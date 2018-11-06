package AppProject.AppProject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;


//@ContextConfiguration(locations = { "classpath:orbit-config.xml" })
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)
public class AbstractServiceForTesting extends	AbstractTransactionalJUnit4SpringContextTests {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
/*	@BeforeClass
    public static void testGetCurrentLogin() {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken("xxforx", "welcome"));
        SecurityContextHolder.setContext(securityContext);
        String login = SecurityUtils.getCurrentLogin();
        //assertThat(login).isEqualTo("admin");
       // assert(login).equals("xxforx");
        
    }*/
}



