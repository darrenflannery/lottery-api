package com.poppulo.lotteryapi.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.poppulo.lotteryapi.dto.AmendTicketDto;
import com.poppulo.lotteryapi.dto.CreateTicketDto;
import com.poppulo.lotteryapi.entity.Ticket;
import com.poppulo.lotteryapi.entity.TicketLine;
import com.poppulo.lotteryapi.exception.CannotAmmendException;
import com.poppulo.lotteryapi.exception.TicketNotFoundException;
import com.poppulo.lotteryapi.repository.TicketRepository;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class LotteryServiceTest {

	@Mock
	private TicketRepository repo;

	@InjectMocks
	private LotteryServiceImpl service;

	Ticket t1;
	Ticket returnedTicket;
	TicketLine l1;
	TicketLine l2;
	List<TicketLine> lines1;

	@BeforeEach 
	public void setup() {
		lines1=new ArrayList<>();

		l1 = new TicketLine(1, 2, 1, 0, null);
		l2 = new TicketLine(2, 0, 2, 0, null);

		lines1.add(l1);
		lines1.add(l2);

		returnedTicket = new Ticket(1,null,false,lines1);
	}

	@Test
	public void testSaveNewTicket() {
		CreateTicketDto ticketDto = new CreateTicketDto(4);
		Mockito.when(repo.save(any(Ticket.class))).thenReturn(returnedTicket);		
		Ticket generatedTicket = service.create(ticketDto);	

		assertNotNull(generatedTicket);
	}

	@Test
	public void testGetTicket() {
		Optional<Ticket> op = Optional.of(returnedTicket);
		Mockito.when(repo.findById(1)).thenReturn(op);		
		Ticket fetchedTicket = service.getTicket(1);	

		assertNotNull(fetchedTicket);
		assertEquals(fetchedTicket.getTicketId(),1);
	}

	@Test
	public void testGetBadTicket() {

		Mockito.when(repo.findById(122)).thenThrow(new NoSuchElementException());	

		assertThrows(
				TicketNotFoundException.class,
				() -> service.getTicket(122));

	}

	@Test
	public void testGetAll() {
		List<Ticket> tickets = new ArrayList<Ticket>();
		tickets.add(new Ticket(1,null,false,lines1));
		tickets.add(new Ticket(2,null,false,lines1));
		Mockito.when(repo.findAll()).thenReturn(tickets);		
		List<Ticket> fetchedTickets = service.getAll();	

		assertNotNull(fetchedTickets);
		assertTrue(fetchedTickets.size()>1);
	}

	@Test
	public void testGetStatus() {
		Mockito.when(repo.save(any(Ticket.class))).thenReturn(returnedTicket);	
		Optional<Ticket> op = Optional.of(returnedTicket);
		Mockito.when(repo.findById(1)).thenReturn(op);
		Ticket checkedTicket = service.checkStatus(1);	

		assertNotNull(checkedTicket);
	}

	@Test
	public void testCheckStatusBad() {
		Mockito.when(repo.findById(234)).thenThrow(new NoSuchElementException());	

		assertThrows(
				TicketNotFoundException.class,
				() -> service.checkStatus(234));
	}

	@Test
	public void testAmendTicket() {
		AmendTicketDto ticketDto = new AmendTicketDto(4);
		Optional<Ticket> op = Optional.of(returnedTicket);
		Mockito.when(repo.findById(1)).thenReturn(op);
		Mockito.when(repo.save(any(Ticket.class))).thenReturn(returnedTicket);		
		Ticket amendedTicket = service.amendTicket(1,ticketDto);	

		assertNotNull(amendedTicket);
	}

	@Test
	public void testAmendBadTicket() {
		AmendTicketDto ticketDto = new AmendTicketDto(4);
		Mockito.when(repo.findById(22)).thenThrow(new NoSuchElementException());	

		assertThrows(
				TicketNotFoundException.class,
				() -> service.amendTicket(22,ticketDto));

	}

	@Test
	public void testAmendCheckedTicket() {
		AmendTicketDto ticketDto = new AmendTicketDto(4);

		Ticket checkedTicket = new Ticket(1,null,true,lines1);
		Optional<Ticket> op = Optional.of(checkedTicket);

		Mockito.when(repo.findById(1)).thenReturn(op);		

		assertThrows(
				CannotAmmendException.class,
				() -> service.amendTicket(1,ticketDto));

	}

}
