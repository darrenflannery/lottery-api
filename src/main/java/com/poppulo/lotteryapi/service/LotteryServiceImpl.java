package com.poppulo.lotteryapi.service;

import java.util.List;
import java.util.NoSuchElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.poppulo.lotteryapi.dto.AmendTicketDto;
import com.poppulo.lotteryapi.dto.CreateTicketDto;
import com.poppulo.lotteryapi.entity.Ticket;
import com.poppulo.lotteryapi.exception.CannotAmmendException;
import com.poppulo.lotteryapi.exception.TicketNotFoundException;
import com.poppulo.lotteryapi.helper.StatusChecker;
import com.poppulo.lotteryapi.helper.TicketGenerator;
import com.poppulo.lotteryapi.helper.TicketLineGenerator;
import com.poppulo.lotteryapi.repository.TicketRepository;
import com.poppulo.lotteryapi.util.LotteryConstants;

@Service("lotteryService")
public class LotteryServiceImpl implements LotteryService {

	@Autowired
	private TicketRepository repo;

	private static final Logger LOGGER = LoggerFactory.getLogger(LotteryServiceImpl.class);

	public Ticket create(CreateTicketDto ticketDto) {
		LOGGER.info("Generating new ticket...");
		Ticket ticket = TicketGenerator.generateTicket(ticketDto.getNumLines());
		return repo.save(ticket);
	}

	public List<Ticket> getAll(){
		return repo.findAll();
	}

	public Ticket getTicket(int id) {
		try {
			return repo.findById(id).get();
		}
		catch(NoSuchElementException e) {
			LOGGER.error(LotteryConstants.ID_NOT_FOUND);
			throw new TicketNotFoundException();
		}
	}

	public Ticket amendTicket(int id, AmendTicketDto ticketDto) {
		try {
			Ticket ticket = repo.findById(id).get();
			if(!ticket.isCheckedStatus()) {
				for(int i=0; i<ticketDto.getAddLines(); i++) {
					ticket.getLines().add(TicketLineGenerator.generateTicketLine());
				}
				return repo.save(ticket);
			}
			else {
				LOGGER.error(LotteryConstants.CANNOT_AMMED);
				throw new CannotAmmendException();
			}
		}
		catch(NoSuchElementException e) {
			LOGGER.error(LotteryConstants.ID_NOT_FOUND);
			throw new TicketNotFoundException();
		}
	}

	public Ticket checkStatus(int id) {
		try {
			Ticket ticket = StatusChecker.checkStatus(repo.findById(id).get());
			ticket.setCheckedStatus(true);
			return repo.save(ticket);
		}
		catch(NoSuchElementException e) {
			LOGGER.error(LotteryConstants.ID_NOT_FOUND);
			throw new TicketNotFoundException();
		}
	}

}
