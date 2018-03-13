package com.wdfall.slot.business.simulatorframework;

import java.util.concurrent.Callable;

import com.wdfall.slot.business.utils.CloneUtils;
import com.wdfall.slot.rungame.RunGameConstants.RunGameProgress;
/**
 * AbstractBasicTask
 * 
 * @param <P>
 * @param <R>
 * @author chhan
 * 2017. 12. 28.
 *
 */
public abstract class AbstractBasicTask<P extends BasicProperty, R extends BasicAccResult> implements Callable<R> {
	
	protected P property;
	
	public void setProperty(P property) {
		this.property = CloneUtils.deepCopyByLib(property);
	}
	
	// 진행률 보고
	protected volatile RunGameProgress progress; 
	
	public void setProgress(RunGameProgress progress) {
		this.progress = progress;
	}
	
}
