package test.wdfall.slot.simulator.shorttime;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static test.wdfall.slot.simulator.basic.utils.MakeUtils.makeObjectCode;

import org.junit.Test;

import com.wdfall.slot.business.simulator.pattern.WinLinePattern;
import com.wdfall.slot.business.simulator.property.EmulatorReelProperty;
import com.wdfall.slot.business.simulator.property.GameProperty;
import com.wdfall.slot.business.simulator.property.InvalidSimulatorProperty;
import com.wdfall.slot.business.simulator.property.SimulatorPayoutPropertyTable;
import com.wdfall.slot.business.simulator.property.SymbolDefine;

public class Test01PropertiesDefine {

	static int firstPayoutSymbolCount = 5;

	/**
	 * 심벌 및 Payout 정의
	 */
	@Test
	public void makeSymbolDefine() {
		
		/** Wild Symbol **/
		SymbolDefine symbol = new SymbolDefine("w", 1, makeObjectCode("w"), SymbolDefine.TYPE_WILD, firstPayoutSymbolCount).setPayout(5000, 500, 50 ) ;
		assertEquals( "w",  symbol.getSymbolCode() );
		assertEquals( SymbolDefine.TYPE_WILD,  symbol.getType()  );
		assertEquals( 5000,  symbol.getPayout(5) );
		assertEquals( 500,  symbol.getPayout(4) );
		assertEquals( 50,  symbol.getPayout(3) );
		assertEquals( 0,  symbol.getPayout(2) );
		assertEquals( 0,  symbol.getPayout(1) );
		
		/** 2 match Symbol **/
		SymbolDefine symbolK = new SymbolDefine("K", 1, makeObjectCode("K"), SymbolDefine.TYPE_NORMAL, firstPayoutSymbolCount).setPayout( 1000, 100, 5, 3 ) ;
		assertEquals( "K",  symbolK.getSymbolCode() );
		assertEquals( SymbolDefine.TYPE_NORMAL,  symbolK.getType()  );
		assertEquals( 1000,  symbolK.getPayout(5) );
		assertEquals( 100,  symbolK.getPayout(4) );
		assertEquals( 5,  symbolK.getPayout(3) );
		assertEquals( 3,  symbolK.getPayout(2) );
		assertEquals( 0,  symbolK.getPayout(1) );
		
	}
	
	@Test
	public void makeWinLines() {
		
		/** Line을 정의하기 전에 스테이지 Scale이 정의 되어야 한다. **/
		try {
			GameProperty gameProperties = new GameProperty(); 
			gameProperties.addWinLine( new WinLinePattern(new int[] {0,0,0,0,0}));
	        fail("Expected an InvalidSimulatorProperty to be thrown");
	    } catch (InvalidSimulatorProperty e) {	        
	    }		

		/** 스테이지 Scale을 벗어나는 Line정의 **/
		try {
			GameProperty gameProperties = new GameProperty();
			gameProperties.setGroundScale(3,3,3,3,3);			
			gameProperties.addWinLine( new WinLinePattern(new int[] {3,4,4,4,3}));
	        fail("Expected an InvalidSimulatorProperty to be thrown");
	    } catch (InvalidSimulatorProperty e) {	        
	    }
		
		/** 3 * 5 슬롯, 라인3개 등록 **/
		GameProperty gameProperties = new GameProperty();
		gameProperties.setGroundScale(3,3,3,3,3);
		gameProperties.addWinLine( new WinLinePattern(new int[] {0,0,0,0,0}));
		gameProperties.addWinLine( new WinLinePattern(new int[] {1,1,1,1,1}));
		gameProperties.addWinLine( new WinLinePattern(new int[] {2,2,2,2,2}));		
		
		assertEquals(3,  gameProperties.countWinLines());
		
	}



	
	@SuppressWarnings("unchecked")
	@Test 
	public void defineReel(){
		
		/** total symbol count **/
		EmulatorReelProperty reel1 = new EmulatorReelProperty ();
		reel1.addSymbolAmount("w", 1 );
		reel1.addSymbolAmount("a", 1 );
		reel1.addSymbolAmount("k", 1 );
		reel1.addSymbolAmount("q", 1 );
		reel1.addSymbolAmount("j", 1 );		
		assertEquals(5, reel1.count() );

		/** total symbol count **/
		EmulatorReelProperty reel2 = new EmulatorReelProperty ();
		reel2.addSymbolAmount("w", 1 ).addSymbolAmount("a", 2 )
			.addSymbolAmount("k", 3 ).addSymbolAmount("q", 4 ).addSymbolAmount("j", 5 );		
		assertEquals(15, reel2.count() );	
				

		/** 스테이지 Scale이 먼저 선언되어야 함 **/
		try {
			GameProperty gameProperties = new GameProperty();
			gameProperties.setReelProperty(0, reel1);
	        fail("Expected an InvalidSimulatorProperty to be thrown");
	    } catch (InvalidSimulatorProperty e) {	        
	    }

		/** 릴속성(각심벌빈도)을 게임 속성에 바인딩 하기 전에, 심벌이 정의되어 있어야 한다. **/
		try {
			GameProperty gameProperties = new GameProperty();
			gameProperties.setGroundScale(3,3,3,3,3);			

			EmulatorReelProperty reel = new EmulatorReelProperty ();
			reel.addSymbolAmount("w", 1 ).addSymbolAmount("k", 2);
			gameProperties.setReelProperty(0, reel);
	        fail("Expected an InvalidSimulatorProperty to be thrown");
	    } catch (InvalidSimulatorProperty e) {	        
	    }

	}
	

	
	@Test
	public void makePayoutProperties(){
		SimulatorPayoutPropertyTable table = new SimulatorPayoutPropertyTable();
		table.put( new SymbolDefine ("w", 1, makeObjectCode("w"), SymbolDefine.TYPE_WILD, firstPayoutSymbolCount).setPayout( 1000, 100, 20) );
		table.put( new SymbolDefine ("a", 1, makeObjectCode("a"), SymbolDefine.TYPE_NORMAL, firstPayoutSymbolCount).setPayout( 100, 50, 15) );
		table.put( new SymbolDefine ("b", 1, makeObjectCode("b"), SymbolDefine.TYPE_NORMAL, firstPayoutSymbolCount).setPayout( 50, 30, 10) );
		table.put( new SymbolDefine ("c", 1, makeObjectCode("c"), SymbolDefine.TYPE_NORMAL, firstPayoutSymbolCount).setPayout( 30, 20, 5) );
		
		assertEquals( 1000, table.get("w").getPayout(5) );
		assertEquals( 100, table.get("w").getPayout(4) );
		assertEquals( 100, table.get("a").getPayout(5) );
		assertEquals( 0, table.get("a").getPayout(2) );
		
		assertEquals(4, table.getSymbolCount()  );
		
	}
	
	
	@Test
	public void makeGamePropertiy3v3() {
		
		GameProperty gameProperties = new GameProperty();
		
		gameProperties.setPlayThreadCount(10);
		gameProperties.setPlayThreadPoolSize(10); 
		gameProperties.setPlayGameCount(1000 * 1000);
		
		// 게임 socket 정의 (스테이지 정의)
		gameProperties.setGroundScale( 3, 3, 3);	//릴 별 소켓수
		
		int firstPayoutSymbolCount = 3;
		// 게임에 사용되는  심벌 및 Payout 정의
		gameProperties.addSymbolDefine(new SymbolDefine("WW", 1, "WW.1", SymbolDefine.TYPE_WILD, firstPayoutSymbolCount).setPayout(100));
		gameProperties.addSymbolDefine(new SymbolDefine("N1", 1, "N1.1", SymbolDefine.TYPE_NORMAL, firstPayoutSymbolCount).setPayout(5));
		gameProperties.addSymbolDefine(new SymbolDefine("N2", 1, "N2.1", SymbolDefine.TYPE_NORMAL, firstPayoutSymbolCount).setPayout(10));
		gameProperties.addSymbolDefine(new SymbolDefine("N3", 1, "N3.1", SymbolDefine.TYPE_NORMAL, firstPayoutSymbolCount).setPayout(20));
				
		// Reel 구성
		EmulatorReelProperty reel0 = new EmulatorReelProperty ();
		reel0.addSymbolAmount("WW.1", 1).addSymbolAmount("N1.1", 8)
			.addSymbolAmount("N2.1", 12).addSymbolAmount("N3.1", 10);
		assertThat(reel0.count(), is(31));
		gameProperties.setReelProperty(0, reel0);
		
		EmulatorReelProperty reel1 = new EmulatorReelProperty ();
		reel1.addSymbolAmount("WW.1", 1).addSymbolAmount("N1.1", 10)
			.addSymbolAmount("N2.1", 10).addSymbolAmount("N3.1", 10);
		assertThat(reel1.count(), is(31));
		gameProperties.setReelProperty(1, reel1);
		
		EmulatorReelProperty reel2 = new EmulatorReelProperty ();
		reel2.addSymbolAmount("WW.1", 1).addSymbolAmount("N1.1", 14)
			.addSymbolAmount("N2.1", 8).addSymbolAmount("N3.1", 8);
		assertThat(reel2.count(), is(31));
		gameProperties.setReelProperty(2, reel2);
		
		
		// Win Line 구성
		int[][] wininLines= {
				new int[]{0,0,0}
		};
		for( int[] line : wininLines ){
			gameProperties.addWinLine( new WinLinePattern( line  ));
		}
		
	}

}
