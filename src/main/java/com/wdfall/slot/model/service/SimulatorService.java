package com.wdfall.slot.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wdfall.slot.model.dao.SimulatorDAO;
import com.wdfall.slot.model.vo.SimulatorInputVO;
import com.wdfall.slot.model.vo.SimulatorResultVO;
import com.wdfall.slot.rungame.RunGameConstants.RunGameStage;

import lombok.extern.slf4j.Slf4j;
/**
 * SimulatorService
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
@Slf4j
@Service
public class SimulatorService implements IFSlotService<SimulatorInputVO, SimulatorResultVO>{

	@Autowired
	SimulatorDAO dao;
	
	public void insertInput(SimulatorInputVO vo) {
		dao.insertInput(vo);
	}
	
	public SimulatorInputVO selectInputBySeq(int seq) {
		return dao.selectInputBySeq(seq);
	}

	public void insertResult(SimulatorResultVO vo) {
		dao.insertResult(vo);
	}

	public List<SimulatorResultVO> selectResultList(RunGameStage stage) {
		return dao.selectResultList(stage);
	}

	public SimulatorResultVO selectResultDetail(int seq) {
		SimulatorResultVO vo = dao.selectResultDetail(seq);
		SimulatorInputVO inputVO = dao.selectInputBySeq(vo.getInputSeq());
		vo.setInputVO(inputVO);
		return vo;
	}
	
}
