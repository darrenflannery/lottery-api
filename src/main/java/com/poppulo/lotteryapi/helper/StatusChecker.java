package com.poppulo.lotteryapi.helper;

import java.util.List;
import com.poppulo.lotteryapi.entity.Ticket;
import com.poppulo.lotteryapi.entity.TicketLine;

public class StatusChecker {

	public static Ticket checkStatus(Ticket ticket) {
		
		int totalScore=0;
		
		List<TicketLine> lines = ticket.getLines();
		
		for(int i=0; i<lines.size(); i++) {
			
			TicketLine l = lines.get(i);
			int firstNo =  l.getFirstNo();
			int secondNo =  l.getSecondNo();
			int thirdNo =  l.getThirdNo();

			if(firstNo+secondNo+thirdNo==2) {
				l.setScore(10);
			}
			else if(firstNo==secondNo && secondNo==thirdNo) {
				l.setScore(5);
			}
			else if(firstNo!=secondNo && firstNo!=thirdNo){
				l.setScore(1);
			}
			else {
				l.setScore(0);
			}
			totalScore += l.getScore();
		}
		ticket.setLines(lines);
		ticket.setTotalScore(totalScore);
		return ticket;
	}
}
