package com.wdfall.slot.business.bonus;

import java.util.List;

import com.wdfall.slot.business.simulatorframework.BasicProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Stage01BonusProperty
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
@Data
public class Stage01BonusProperty extends BasicProperty{
	
	protected List<WheelPiece> wheelPieceList;
	
	
	public static enum WheelColor {
		RED, BLACK;
		
		private static final WheelColor[] allValues = WheelColor.values();
		public static WheelColor fromInt(int order) {
			return allValues[order];
		}
	}
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class WheelPiece {
		int multiple;
		int frequency;
		WheelColor color;
	}
	
	public static void main(String[] args) {
		System.out.println(WheelColor.fromInt(0));
	}
	
}
