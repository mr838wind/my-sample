package com.wdfall.slot.business.simulatorframework;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.wdfall.slot.rungame.RunGameConstants.RunGameProgress;
import com.wdfall.slot.rungame.RunGameConstants.RunGameStatus;

import lombok.extern.slf4j.Slf4j;
/**
 * 기본 simulator
 * 
 * @param <P>
 * @param <R>
 * @param <T>
 * @author chhan
 * 2017. 12. 28.
 *
 */
@Slf4j
public class BasicSimulator <P extends BasicProperty, R extends BasicAccResult, T extends AbstractBasicTask<P,R> > {

	P prop;
	
	Class<T> taskClazz;
	
	Class<R> totalResultClazz;
	
	R totalResult;
	
	// 진행률 보고
	protected volatile RunGameProgress progress; 
	
	public void setProgress(RunGameProgress progress) {
		this.progress = progress;
	}
	
	public R getTotalResult() {
		return totalResult;
	}
	
	public BasicSimulator(P prop, Class<R> totalResultClazz, Class<T> taskClazz) {
		super();
		this.prop = prop;
		this.totalResultClazz = totalResultClazz;
		this.taskClazz = taskClazz;
	}


	public void runTest() throws Exception {
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<<<<< runTest STARTED  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		long startTime = System.currentTimeMillis();
		
		if(progress != null) {
			progress.setStatus(RunGameStatus.RUNNING);
			progress.setTotalTask(prop.getPlayGameCount() * prop.getPlayThreadCount());
			progress.setDoneTask(0);
		}
		
		this.totalResult = totalResultClazz.newInstance();
		
		ExecutorService executorService = Executors.newFixedThreadPool(prop.getPlayThreadPoolSize());
		List<Callable<R>> taskList = new ArrayList<>();
		for(int i=0; i<prop.getPlayThreadCount(); i++) {
			T task = taskClazz.newInstance();
			task.setProperty(prop);
			task.setProgress(progress);
			taskList.add(task);
		}
		
		List<Future<R>> resultList = new ArrayList<>(); 
		for(Callable<R> task : taskList) {
			Future<R> taskResult = executorService.submit(task);
			resultList.add(taskResult);
		}
		
		checkTaskProgressUntilAllDone(resultList);
		calculateResult(resultList);
		
		executorService.shutdown();
		
		if(progress != null) {
			progress.setStatus(RunGameStatus.COMPLETE);
		}
		
		log.debug("\r\n\r\n");
		log.debug("<<<<<<<<<<<<< totalResult  >>>>>>>>>>>>>");
		totalResult.printTotalResult();
		
		long endTime = System.currentTimeMillis();
		log.debug(">>> time: {}s",(endTime - startTime) / 1000.0);
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<<<<< runTest Terminated  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
	
	private void checkTaskProgressUntilAllDone(List<Future<R>> resultList) throws Exception {
		int taskAllCount = resultList.size();
		
		while(true) {
			
			int taskCount = 0;
			for(Future<R> result : resultList) {
				if(result.isDone()) {
					taskCount++;
				}
			}
			
			if(progress != null) {
				log.debug(" <<<<<<<<<<<<<<<< task progress = {} / {} >>>>>>>>>>>>>>>>>", progress.getDoneTask(), progress.getTotalTask());
			}else {
				log.debug(" <<<<<<<<<<<<<<<< task progress = {} / {} >>>>>>>>>>>>>>>>>", taskCount, taskAllCount);
			}
			
			if(taskCount >= taskAllCount) {
				break;
			}
			
			Thread.sleep(5 * 1000); //5s
			
		}
	}

	private void calculateResult(List<Future<R>> resultList) throws InterruptedException, ExecutionException {
		for(Future<R> result : resultList) {
			R accResult = result.get();
			totalResult.append(accResult);
		}
	}
	
	
	
}
