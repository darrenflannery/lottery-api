package com.poppulo.lotteryapi.helper;
import java.util.Random;
import com.poppulo.lotteryapi.entity.TicketLine;

public class TicketLineGenerator {

	public static TicketLine generateTicketLine() {
		TicketLine line = new TicketLine();
		line.setFirstNo(getRandom());
		line.setSecondNo(getRandom());
		line.setThirdNo(getRandom());
		return line;
	}

	private static int getRandom() {
		Random rand = new Random();
		return rand.nextInt(3);
	}
}
