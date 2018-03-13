package com.wdfall.slot.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.wdfall.slot.model.vo.SimulatorInputVO;
import com.wdfall.slot.model.vo.SimulatorResultVO;
import com.wdfall.slot.rungame.RunGameConstants.RunGameStage;
/**
 * SimulatorDAO
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
@Repository
public class SimulatorDAO {

	@Autowired
	SqlSession sqlSession;
	
	private final String NS = this.getClass().getSimpleName() + ".";

	public void insertInput(SimulatorInputVO vo) {
		sqlSession.insert(NS + "insertInput", vo);
	}

	public SimulatorInputVO selectInputBySeq(int seq) {
		Map<String, Object> param = new HashMap<>();
		param.put("seq", seq);
		return sqlSession.selectOne(NS + "selectInputBySeq", param);
	}

	public void insertResult(SimulatorResultVO vo) {
		sqlSession.insert(NS + "insertResult", vo);
	}

	public List<SimulatorResultVO> selectResultList(RunGameStage stage) {
		Map<String, Object> param = new HashMap<>();
		param.put("stage", stage);
		return sqlSession.selectList(NS + "selectResultList", param);
	}

	public SimulatorResultVO selectResultDetail(int seq) {
		Map<String, Object> param = new HashMap<>();
		param.put("seq", seq);
		return sqlSession.selectOne(NS + "selectResultDetail", param);
	}
	
}
