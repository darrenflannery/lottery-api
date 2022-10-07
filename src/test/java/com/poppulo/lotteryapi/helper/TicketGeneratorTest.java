package com.poppulo.lotteryapi.helper;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.poppulo.lotteryapi.entity.Ticket;

class TicketGeneratorTest {
	
	@Test
	public void testGenerateTicket() {
		Ticket generatedTicket = TicketGenerator.generateTicket(2);
		assertNotNull(generatedTicket);
		assertEquals(generatedTicket.getLines().size(), 2);
		assertFalse(generatedTicket.isCheckedStatus());
		assertNull(generatedTicket.getTotalScore());
		generatedTicket.getLines().stream().forEach((l) -> {
			assertNull(l.getScore());
			assertNotNull(l.getFirstNo());
			assertNotNull(l.getSecondNo());
			assertNotNull(l.getThirdNo());
			assertTrue(l.getFirstNo()>=0&&l.getFirstNo()<=2);
			assertTrue(l.getSecondNo()>=0&&l.getSecondNo()<=2);
			assertTrue(l.getThirdNo()>=0&&l.getThirdNo()<=2);
		});
	}

}
