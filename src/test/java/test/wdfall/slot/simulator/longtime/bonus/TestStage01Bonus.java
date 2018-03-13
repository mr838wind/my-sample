package test.wdfall.slot.simulator.longtime.bonus;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.wdfall.slot.business.bonus.Stage01BonusProperty;
import com.wdfall.slot.business.bonus.Stage01BonusProperty.WheelColor;
import com.wdfall.slot.business.bonus.Stage01BonusProperty.WheelPiece;
import com.wdfall.slot.business.bonus.Stage01BonusTask;
import com.wdfall.slot.business.simulatorframework.BasicSimulator;
import com.wdfall.slot.business.simulatorframework.Stage01BonusAccResult;

import lombok.extern.slf4j.Slf4j;

/**
 * result = 16.99
 * @author chhan
 *
 */
@Slf4j
public class TestStage01Bonus {

	@Test
	public void testStage1BonusGame() throws Exception {
		Stage01BonusProperty property = makeStage1BonusProperty();
		
		BasicSimulator<Stage01BonusProperty, Stage01BonusAccResult, Stage01BonusTask> basicSimulator = new BasicSimulator<>(property, Stage01BonusAccResult.class, Stage01BonusTask.class);
		basicSimulator.runTest();
	}

	private static Stage01BonusProperty makeStage1BonusProperty() {
		Stage01BonusProperty property = new Stage01BonusProperty();
		List<WheelPiece> wheelPieceList = new ArrayList<>();
		wheelPieceList.add(new WheelPiece(2, 10, WheelColor.RED));
		wheelPieceList.add(new WheelPiece(2, 10, WheelColor.BLACK));
		wheelPieceList.add(new WheelPiece(4, 10, WheelColor.RED));
		wheelPieceList.add(new WheelPiece(4, 10, WheelColor.BLACK));
		wheelPieceList.add(new WheelPiece(8, 10, WheelColor.RED));
		wheelPieceList.add(new WheelPiece(8, 10, WheelColor.BLACK));
		wheelPieceList.add(new WheelPiece(20, 10, WheelColor.RED));
		wheelPieceList.add(new WheelPiece(20, 10, WheelColor.BLACK));
		property.setWheelPieceList(wheelPieceList);
		return property;
	}
}
