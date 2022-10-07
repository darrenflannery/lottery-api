package com.poppulo.lotteryapi.helper;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.poppulo.lotteryapi.entity.TicketLine;

class TicketLineGeneratorTest {

	@Test
	public void testTicketLineGenerator() {
		TicketLine generatedTicketLine;
		for(int i=0; i<101; i++) {
			generatedTicketLine = TicketLineGenerator.generateTicketLine();
			assertNotNull(generatedTicketLine);
			assertNull(generatedTicketLine.getScore());
			assertNotNull(generatedTicketLine.getFirstNo());
			assertNotNull(generatedTicketLine.getSecondNo());
			assertNotNull(generatedTicketLine.getThirdNo());
			assertTrue(generatedTicketLine.getFirstNo()>=0&&generatedTicketLine.getFirstNo()<=2);
			assertTrue(generatedTicketLine.getSecondNo()>=0&&generatedTicketLine.getSecondNo()<=2);
			assertTrue(generatedTicketLine.getThirdNo()>=0&&generatedTicketLine.getThirdNo()<=2);
		}
	}
}
