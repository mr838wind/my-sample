package test.common;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/test-*.xml" })
@WebAppConfiguration
@Slf4j
public abstract class AbstractBasicController {

	protected static final String MODEL_RESULT = "result";

	@Autowired
	protected WebApplicationContext context;

	protected MockHttpSession mockSession;

	protected MockMvc mockMvc;

	protected void setUp() {
		//
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		
		//
		mockSession = new MockHttpSession(context.getServletContext());
		//mockSession.setAttribute(SessionKey.USER_INFO, getLoginUser());
		
		log.debug(">> setUp done");  
	}

	
	
//	protected UserModel getLoginUser() {
//		UserModel um = new UserModel();
//		um.setUserId("FIC02803");
//		um.setBiztpCode("70");
//		um.setJobCode("654");
//		//um.setStrCode("2710");
//		return um;
//	}


}
