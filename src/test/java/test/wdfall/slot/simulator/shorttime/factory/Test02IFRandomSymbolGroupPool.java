package test.wdfall.slot.simulator.shorttime.factory;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.wdfall.slot.business.simulator.factories.IFRandomSymbolGroupPool;
import com.wdfall.slot.business.simulator.factories.IFRandomSymbolGroupPool.SymbolGroup;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test02IFRandomSymbolGroupPool {

	@Test
	public void testShuffle1() {
		IFRandomSymbolGroupPool<String> pool = new IFRandomSymbolGroupPool.ShuffleRandomSymbolGroupPool<>();
		
		pool.addChance(makeSymbolGroup("A", 3) , 1);
		pool.addChance(makeSymbolGroup("B", 1) , 3);
		
		List<String> resultList = pool.get(3);
		
		log.debug("resultList={}", resultList);
		assertThat(resultList.size(), is(3));
		
		resultList = pool.get(2);
		log.debug("resultList={}", resultList);
		assertThat(resultList.size(), is(2));
		
	}

	@Test
	public void testShuffle2() {
		// AAA,B,B,B
		IFRandomSymbolGroupPool<String> pool = new IFRandomSymbolGroupPool.ShuffleRandomSymbolGroupPool<>();
		
		pool.addChance(makeSymbolGroup("A", 3) , 1);
		pool.addChance(makeSymbolGroup("B", 1) , 3);
		
		for(int i=0; i<1000*1000; i++) {
			List<String> resultList = pool.get(3);
			//BAB가 나오면 안됨
			assertThat(joinList(resultList,""), not(equalTo("BAB")));
		}
	}
	
	
	@Test
	public void testGroupSize1Pool() {
		// AAA,B,B,B
		IFRandomSymbolGroupPool<String> pool = new IFRandomSymbolGroupPool.ShuffleRandomSymbolGroupSize1Pool<>();
		try {
			pool.addChance("A", 3, 1);
			fail("illegal argumente exception to be thrown.");
		} catch(IllegalArgumentException e) {
		}
		
		pool.addChance(makeSymbolGroup("A", 1) , 1);
		List<String> list = pool.get(1);
		log.debug("{}",list); 
	}
	
	
	private String joinList(List<String> list, String delimiter) {
		return StringUtils.join(list,delimiter);
	}
	
	private SymbolGroup<String> makeSymbolGroup(String obj, int groupSize) {
		SymbolGroup<String> groupParam = new SymbolGroup<>();
		groupParam.setObj(obj);
		groupParam.setGroupSize(groupSize);
		return groupParam;
	}
	
}
