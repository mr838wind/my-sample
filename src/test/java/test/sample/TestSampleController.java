package test.sample;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import com.wind.sample.service.SampleVO;

import lombok.extern.slf4j.Slf4j;
import test.common.AbstractBasicController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/test-*.xml" })
@WebAppConfiguration
@Slf4j
@ActiveProfiles("test")
public class TestSampleController extends AbstractBasicController {

	/**
	 * ReflectionTestUtils, JdbcTestUtils 사용할수 있음.
	 */
	
	@Before
	public void setUp() {
		super.setUp(); 
	}

	@Test
	@Transactional
	public void select() throws Exception {
		log.info("==============select");

		mockMvc.perform(
					get("/SampleController/list")
						.session(mockSession)
						.param("name", "a")
				)
				.andDo(print())
				.andExpect(status().isOk())
				/* */
				.andDo(new ResultHandler() {
					@SuppressWarnings("unchecked")
					@Override
					public void handle(MvcResult mvcResult) throws Exception {
						ModelAndView modelAndView = mvcResult.getModelAndView();
						Object result = modelAndView.getModel().get(MODEL_RESULT);
						log.debug(" >>> result.size() = {}", ((List<SampleVO>)result).size()); 
					}
				})
				.andExpect(new ResultMatcher() {
					@Override
					public void match(MvcResult mvcResult) throws Exception {
						//...
					}
				})
				;
	}

}
