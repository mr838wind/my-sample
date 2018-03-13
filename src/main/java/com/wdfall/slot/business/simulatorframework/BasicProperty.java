package com.wdfall.slot.business.simulatorframework;

import lombok.Data;
/**
 * 기본 property
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
@Data
public class BasicProperty {

	// 스레드수
	protected int playThreadCount = 1;
	// 스레드 풀 size
	protected int playThreadPoolSize = 1;
	// thread당 game 진행 수
	protected long playGameCount = 1000*1000;
	
}
