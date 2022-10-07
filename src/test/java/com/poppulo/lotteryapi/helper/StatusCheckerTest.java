package com.poppulo.lotteryapi.helper;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import com.poppulo.lotteryapi.entity.Ticket;
import com.poppulo.lotteryapi.entity.TicketLine;

class StatusCheckerTest {
	
	Ticket ticketToBeChecked;
	Ticket checkedTicket;
	List<TicketLine> lines;

	@Test
	public void testSumOfAllValuesIsTwoGetsScoreOfTen() {
		//Rule 1 - Score of 10
		TicketLine l1 = new TicketLine(1, 1 , 1, 0, null);
		TicketLine l2 = new TicketLine(2, 1 , 0, 1, null);
		TicketLine l3 = new TicketLine(3, 0 , 1, 1, null);
		TicketLine l4 = new TicketLine(4, 2 , 0, 0, null);
		TicketLine l5 = new TicketLine(5, 0 , 0, 2, null);
		TicketLine l6 = new TicketLine(6, 0 , 2, 0, null);
		
		//negative case
		TicketLine l7 = new TicketLine(6, 1 , 2, 0, null);
		
		lines = new ArrayList<TicketLine>();
		
		lines.add(l1);
		lines.add(l2);
		lines.add(l3);
		lines.add(l4);
		lines.add(l5);
		lines.add(l6);
		
		ticketToBeChecked = new Ticket(1, null,false,lines);
		
		checkedTicket = StatusChecker.checkStatus(ticketToBeChecked);
		
		checkedTicket.getLines().stream().forEach((l) -> {
			assertTrue(l.getFirstNo()+l.getSecondNo()+l.getThirdNo()==2);
			assertEquals(l.getScore(), 10);
		});
		assertEquals(checkedTicket.getTotalScore(), 60);
		
		assertNotEquals(l7.getScore(), 10);
	}
	
	@Test
	public void testAllEqualValuesGetScoreOfFive() {
		//Rule 2 - Score of 5
		TicketLine l1 = new TicketLine(1, 1 , 1, 1, null);
		TicketLine l2 = new TicketLine(2, 2 , 2, 2, null);
		TicketLine l3 = new TicketLine(3, 0 , 0, 0, null);
		
		//negative case
		TicketLine l4 = new TicketLine(4, 2 , 0, 1, null);

		lines = new ArrayList<TicketLine>();
		
		lines.add(l1);
		lines.add(l2);
		lines.add(l3);
		
		ticketToBeChecked = new Ticket(1, null,false,lines);
		
		checkedTicket = StatusChecker.checkStatus(ticketToBeChecked);
		
		checkedTicket.getLines().stream().forEach((l) -> {
			assertTrue(l.getFirstNo()==l.getSecondNo() && l.getFirstNo()==l.getThirdNo());
			assertEquals(l.getScore(), 5);
		});
		assertEquals(checkedTicket.getTotalScore(), 15);
		assertNotEquals(l4.getScore(), 5);
	}
	
	@Test
	public void testFirstDifferentToOthersGetScoreOfOne() {
		//Rule 3 - score of 1
		TicketLine l1 = new TicketLine(1, 0, 1, 2, null);
		TicketLine l2 = new TicketLine(2, 0, 2, 2, null);
		TicketLine l3 = new TicketLine(3, 0, 2, 1, null);
		TicketLine l4 = new TicketLine(4, 1, 0, 2, null);
		TicketLine l5 = new TicketLine(5, 1, 2, 0, null);
		TicketLine l6 = new TicketLine(6, 1, 0, 0, null);
		TicketLine l7 = new TicketLine(7, 2, 0, 1, null);
		
		//negative case
		TicketLine l10 = new TicketLine(4, 2 , 1, 1, null);

		lines = new ArrayList<TicketLine>();
		
		lines.add(l1);
		lines.add(l2);
		lines.add(l3);
		lines.add(l4);
		lines.add(l5);
		lines.add(l6);
		lines.add(l7);
		
		ticketToBeChecked = new Ticket(1, null,false,lines);
		
		checkedTicket = StatusChecker.checkStatus(ticketToBeChecked);
		
		checkedTicket.getLines().stream().forEach((l) -> {
			assertTrue(l.getFirstNo()!=l.getSecondNo() && l.getFirstNo()!=l.getThirdNo());
			assertEquals(l.getScore(), 1);
		});
		assertEquals(checkedTicket.getTotalScore(), 7);
		assertNotEquals(l10.getScore(), 1);
	}
	
	@Test
	public void testDoublePositiveLinesGetScoreOfTen() {
		//Lines that follow rules 1 & 3 but should get a score of 10
		TicketLine l1 = new TicketLine(1, 0, 1, 1, null);
		TicketLine l2 = new TicketLine(2, 2, 0, 0, null);
		
		lines = new ArrayList<TicketLine>();
		
		lines.add(l1);
		lines.add(l2);
		
		ticketToBeChecked = new Ticket(1, null,false,lines);
		
		checkedTicket = StatusChecker.checkStatus(ticketToBeChecked);
		
		checkedTicket.getLines().stream().forEach((l) -> {
			assertTrue(l.getFirstNo()!=l.getSecondNo() && l.getFirstNo()!=l.getThirdNo());
			assertTrue(l.getFirstNo()+l.getSecondNo()+l.getThirdNo()==2);
			assertEquals(l.getScore(), 10);
		});
		assertEquals(checkedTicket.getTotalScore(), 20);
	}
	
	@Test
	public void testScoreOfZero() {
		//Lines that get a score of 0
		TicketLine l1 = new TicketLine(1, 2, 1, 2, null);
		TicketLine l2 = new TicketLine(2, 1, 2, 1, null);
		TicketLine l3 = new TicketLine(3, 0, 0, 1, null);
		TicketLine l4 = new TicketLine(4, 0, 1, 0, null);
		TicketLine l5 = new TicketLine(5, 2, 0, 2, null);
		TicketLine l6 = new TicketLine(6, 2, 2, 0, null);
		
		lines = new ArrayList<TicketLine>();
		
		lines.add(l1);
		lines.add(l2);
		lines.add(l3);
		lines.add(l4);
		lines.add(l5);
		lines.add(l6);
		
		ticketToBeChecked = new Ticket(1, null,false,lines);
		
		checkedTicket = StatusChecker.checkStatus(ticketToBeChecked);
		
		checkedTicket.getLines().stream().forEach((l) -> {
			assertFalse(l.getFirstNo()!=l.getSecondNo() && l.getFirstNo()!=l.getThirdNo());
			assertFalse(l.getFirstNo()+l.getSecondNo()+l.getThirdNo()==2);
			assertFalse(l.getFirstNo()==l.getSecondNo() && l.getFirstNo()==l.getThirdNo());
			assertEquals(l.getScore(), 0);
		});
		assertEquals(checkedTicket.getTotalScore(), 0);
	}
	

}
