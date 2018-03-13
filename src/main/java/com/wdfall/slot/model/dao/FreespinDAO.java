package com.wdfall.slot.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.wdfall.slot.model.vo.FreespinInputVO;
import com.wdfall.slot.model.vo.FreespinResultVO;
import com.wdfall.slot.rungame.RunGameConstants.RunGameStage;
/**
 * FreespinDAO
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
@Repository
public class FreespinDAO {

	@Autowired
	SqlSession sqlSession;
	
	private final String NS = this.getClass().getSimpleName() + ".";

	public void insertInput(FreespinInputVO inputVO) {
		sqlSession.insert(NS + "insertInput", inputVO);
	}

	public FreespinInputVO selectInputBySeq(int seq) {
		Map<String, Object> param = new HashMap<>();
		param.put("seq", seq);
		return sqlSession.selectOne(NS + "selectInputBySeq", param);
	}

	public void insertResult(FreespinResultVO resultVO) {
		sqlSession.insert(NS + "insertResult", resultVO);
	}

	public List<FreespinResultVO> selectResultList(RunGameStage stage) {
		Map<String, Object> param = new HashMap<>();
		param.put("stage", stage);
		return sqlSession.selectList(NS + "selectResultList", param);
	}

	public FreespinResultVO selectResultDetail(int seq) {
		Map<String, Object> param = new HashMap<>();
		param.put("seq", seq);
		return sqlSession.selectOne(NS + "selectResultDetail", param);
	}
	
}
