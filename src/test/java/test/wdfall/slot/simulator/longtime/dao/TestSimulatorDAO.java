package test.wdfall.slot.simulator.longtime.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wdfall.slot.model.dao.SimulatorDAO;
import com.wdfall.slot.model.vo.SimulatorInputVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/test-root-context.xml"})
public class TestSimulatorDAO {

	@Autowired
	protected SimulatorDAO dao;


	@Before
	public void setUp() {
	}
	
	@Test
	//@Transactional
	public void testSimulator(){
		log.debug(">>> test" );
		assertThat(dao, is(notNullValue()));
		
		SimulatorInputVO vo = dao.selectInputBySeq(8);
		assertThat(vo.getSeq(), is(8));
	}

}
