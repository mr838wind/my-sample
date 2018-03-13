package com.wdfall.slot.business.simulator.factories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * random symbol 생성 class, standing symbol 효과 지원(같은 심블 여러개가 한 group으로 묶인 상황)
 * 
 * @param <T>
 * @author chhan
 * 2017. 12. 28.
 *
 */
public interface IFRandomSymbolGroupPool<T> {
	
	public void addChance(SymbolGroup<T> groupParam, int chance);
	public void addChance(T obj, int groupSize, int chance);
	public void addGroupSize1Chance(T obj, int chance);
	
	public List<T> get(int amount);
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class SymbolGroup<T> {
		private T obj;
		private int groupSize;
	}
	
	public static class ShuffleRandomSymbolGroupPool<T> implements IFRandomSymbolGroupPool<T> {

		protected List<SymbolGroup<T>> pool = new ArrayList<>();
		
		@Override
		public void addChance(SymbolGroup<T> groupParam, int chance) {
			if(chance <= 0) return;
			for(int i=0; i<chance; i++) {
				pool.add(groupParam);
			}
		}
		
		@Override
		public void addChance(T obj, int groupSize, int chance) {
			SymbolGroup<T> groupParam = new SymbolGroup<>(obj, groupSize);
			this.addChance(groupParam, chance);
		}
		
		@Override
		public void addGroupSize1Chance(T obj, int chance) {
			this.addChance(obj, 1, chance);
		}
		
		/*
		 * << Symbol n개 붙어다니는 형식으로 뽑는다. >>
		 * 1. pool을 shuffle하고 줄 세운다.
		 * 2. n>1일 경우 Symbol을 n개로 늘린다.
		 * 3. 중간의 amount개를 가져온다.
		 */
		@Override
		public List<T> get(int amount) {
			
			Collections.shuffle(pool);
			
			List<T> tmpList = processMultiplyToPool();
			
			List<T> resultList = getSymbolFromMiddle(tmpList, amount);
			
			return resultList;
		}

		private List<T> processMultiplyToPool() {
			List<T> resultList = new ArrayList<>();
			
			for(SymbolGroup<T> item : pool) {
				for(int i=0; i<item.getGroupSize(); i++) {
					resultList.add(item.getObj());
				}
			}
			
			return resultList;
		}
		
		private List<T> getSymbolFromMiddle(List<T> tmpList, int amount) {
			List<T> resultList = new ArrayList<>();
			
			int middle = (tmpList.size() - amount) / 2;
			int last = middle + amount;
			
			if(last > tmpList.size()) {
				// reel구성 symbol이 훨씬 많아 이 상황은 error로 본다. 
				throw new IllegalArgumentException("too little symbol in reel.");
			}
			
			for(int i=middle; i<last; i++) {
				T item = tmpList.get(i);
				resultList.add(item);
			}
			
			return resultList;
		}

	}
	
	/**
	 * group size equal 1 
	 * @author chhan
	 */
	public static class ShuffleRandomSymbolGroupSize1Pool<T> extends ShuffleRandomSymbolGroupPool<T> {
		
		@Override
		public void addChance(SymbolGroup<T> groupParam, int chance) {
			if(groupParam.getGroupSize() != 1) {
				throw new IllegalArgumentException("group size should be 1.");
			}
			super.addChance(groupParam, chance);
		}
		
		@Override
		public void addChance(T obj, int groupSize, int chance) {
			if(groupSize != 1) {
				throw new IllegalArgumentException("group size should be 1.");
			}
			super.addChance(obj, groupSize, chance);
		}
		
	}
	
	
	
	
}
