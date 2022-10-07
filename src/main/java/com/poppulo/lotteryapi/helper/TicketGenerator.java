package com.poppulo.lotteryapi.helper;
import java.util.ArrayList;
import java.util.List;
import com.poppulo.lotteryapi.entity.Ticket;
import com.poppulo.lotteryapi.entity.TicketLine;

public class TicketGenerator {
	
	public static Ticket generateTicket(int numLines) {
		
		Ticket ticket = new Ticket();
		List<TicketLine> lines = new ArrayList<>();
				
		for(int i=0; i<numLines; i++) {
			lines.add(TicketLineGenerator.generateTicketLine());
		}
		ticket.setLines(lines);
		
		return ticket;
	}
}