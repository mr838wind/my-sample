package test.wdfall.slot.simulator.basic.utils;

public class MakeUtils {

	public static String makeObjectCode(String symbolcode) {
		return makeObjectCode(symbolcode, 1);
	}
	
	public static String makeObjectCode(String symbolcode, int groupSize) {
		return String.format("%s.%s", symbolcode, groupSize);
	}
	
	
}
