package com.wdfall.slot.model.service;

import java.util.List;

import com.wdfall.slot.model.vo.BasicInputVO;
import com.wdfall.slot.model.vo.BasicResultVO;
import com.wdfall.slot.rungame.RunGameConstants.RunGameStage;

/**
 * service interface
 * 
 * @param <I>
 * @param <R>
 * @author chhan
 * 2017. 12. 28.
 *
 */
public interface IFSlotService<I extends BasicInputVO,R extends BasicResultVO> {

	public void insertInput(I vo);

	public void insertResult(R vo);

	public List<R> selectResultList(RunGameStage stage);

	public R selectResultDetail(int seq);
	
}
