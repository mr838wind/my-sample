package com.wdfall.slot.spring.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wdfall.slot.spring.SpringBootstrap;
import com.wdfall.slot.spring.test.sample.SampleDAO;
import com.wdfall.slot.spring.test.sample.SampleVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TestDAO {
	
	@Autowired
	private SampleDAO sampleDAO;
	
	public void testDAO() {
		List<SampleVO> sampleList = sampleDAO.selectSampleList();
		log.info(" sampleList: {}", sampleList);
	}
	
	public static void main(String[] args) {
		SpringBootstrap.getInstance().getContext().getBean(TestDAO.class).testDAO();
		SpringBootstrap.getInstance().close();
	}
	
}

