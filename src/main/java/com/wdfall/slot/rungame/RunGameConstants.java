package com.wdfall.slot.rungame;

/**
 * simulator 실행 관련 constants
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
public class RunGameConstants {

	public static enum RunGameStage {
		BASIC, STAGE01;
	}
	
	public static enum RunGameType {
		SIMULATOR, BONUS, FREESPIN;
	}
	
	public static enum RunGameStatus {
		INIT, RUNNING, COMPLETE;
	}
	
	public static class RunGameProgress {
		private volatile RunGameStatus status = RunGameStatus.INIT;
		private volatile long doneTask = 0;
		private volatile long totalTask = 1;
		
		public synchronized void init() {
			status = RunGameStatus.INIT;
			doneTask = 0;
			totalTask = 1;
		}
		
		public synchronized void clear() {
			init();
		}
		
		public synchronized void increaseDoneTask() {
			this.doneTask++;
		}

		// get && set
		public synchronized RunGameStatus getStatus() {
			return status;
		}

		public synchronized void setStatus(RunGameStatus status) {
			this.status = status;
		}

		public synchronized long getDoneTask() {
			return doneTask;
		}

		public synchronized void setDoneTask(long doneTask) {
			this.doneTask = doneTask;
		}

		public synchronized long getTotalTask() {
			return totalTask;
		}

		public synchronized void setTotalTask(long totalTask) {
			this.totalTask = totalTask;
		}
		
	}
	
	
}
