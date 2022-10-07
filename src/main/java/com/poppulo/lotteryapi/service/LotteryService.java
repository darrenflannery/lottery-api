package com.poppulo.lotteryapi.service;

import java.util.List;
import com.poppulo.lotteryapi.dto.AmendTicketDto;
import com.poppulo.lotteryapi.dto.CreateTicketDto;
import com.poppulo.lotteryapi.entity.Ticket;

public interface LotteryService {

	public Ticket create (CreateTicketDto ticketDto);

	public List<Ticket> getAll();

	public Ticket getTicket(int id);

	public Ticket amendTicket(int id, AmendTicketDto ticketDto);

	public Ticket checkStatus(int id);
}
