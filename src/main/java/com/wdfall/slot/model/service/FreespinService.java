package com.wdfall.slot.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wdfall.slot.model.dao.FreespinDAO;
import com.wdfall.slot.model.vo.FreespinInputVO;
import com.wdfall.slot.model.vo.FreespinResultVO;
import com.wdfall.slot.rungame.RunGameConstants.RunGameStage;

import lombok.extern.slf4j.Slf4j;
/**
 * FreespinService
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
@Slf4j
@Service
public class FreespinService implements IFSlotService<FreespinInputVO, FreespinResultVO> {
	
	@Autowired
	FreespinDAO dao;

	public void insertInput(FreespinInputVO inputVO) {
		dao.insertInput(inputVO);
	}

	public void insertResult(FreespinResultVO resultVO) {
		dao.insertResult(resultVO);
	}

	public List<FreespinResultVO> selectResultList(RunGameStage stage) {
		return dao.selectResultList(stage);
	}

	public FreespinResultVO selectResultDetail(int seq) {
		FreespinResultVO vo = dao.selectResultDetail(seq);
		FreespinInputVO inputVO = dao.selectInputBySeq(vo.getInputSeq());
		vo.setInputVO(inputVO);
		return vo;
	}
	
}
