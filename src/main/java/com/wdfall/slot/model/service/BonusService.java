package com.wdfall.slot.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wdfall.slot.model.dao.BonusDAO;
import com.wdfall.slot.model.vo.BonusInputVO;
import com.wdfall.slot.model.vo.BonusResultVO;
import com.wdfall.slot.rungame.RunGameConstants.RunGameStage;

import lombok.extern.slf4j.Slf4j;
/**
 * BonusService
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
@Slf4j
@Service
public class BonusService implements IFSlotService<BonusInputVO, BonusResultVO> {

	@Autowired
	BonusDAO dao;
	
	public void insertInput(BonusInputVO vo) {
		dao.insertInput(vo);
	}

	public void insertResult(BonusResultVO vo) {
		dao.insertResult(vo);
	}

	public List<BonusResultVO> selectResultList(RunGameStage stage) {
		return dao.selectResultList(stage);
	}

	public BonusResultVO selectResultDetail(int seq) {
		BonusResultVO vo = dao.selectResultDetail(seq);
		BonusInputVO inputVO = dao.selectInputBySeq(vo.getInputSeq());
		vo.setInputVO(inputVO);
		return vo;
	}

	
}
