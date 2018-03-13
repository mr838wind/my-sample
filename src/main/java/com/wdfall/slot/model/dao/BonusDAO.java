package com.wdfall.slot.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.wdfall.slot.model.vo.BonusInputVO;
import com.wdfall.slot.model.vo.BonusResultVO;
import com.wdfall.slot.rungame.RunGameConstants.RunGameStage;
/**
 * BonusDAO
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
@Repository
public class BonusDAO {

	@Autowired
	SqlSession sqlSession;
	
	private final String NS = this.getClass().getSimpleName() + ".";

	public void insertInput(BonusInputVO vo) {
		sqlSession.insert(NS + "insertInput", vo);
	}

	public BonusInputVO selectInputBySeq(int seq) {
		Map<String, Object> param = new HashMap<>();
		param.put("seq", seq);
		return sqlSession.selectOne(NS + "selectInputBySeq", param);
	}

	public void insertResult(BonusResultVO vo) {
		sqlSession.insert(NS + "insertResult", vo);
	}

	public List<BonusResultVO> selectResultList(RunGameStage stage) {
		Map<String, Object> param = new HashMap<>();
		param.put("stage", stage);
		return sqlSession.selectList(NS + "selectResultList", param);
	}

	public BonusResultVO selectResultDetail(int seq) {
		Map<String, Object> param = new HashMap<>();
		param.put("seq", seq);
		return sqlSession.selectOne(NS + "selectResultDetail", param);
	}
	
}
