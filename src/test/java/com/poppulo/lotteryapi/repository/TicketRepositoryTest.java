package com.poppulo.lotteryapi.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.poppulo.lotteryapi.entity.Ticket;
import com.poppulo.lotteryapi.entity.TicketLine;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
class TicketRepositoryTest {

	@Autowired
	private TicketRepository repo;

	TicketLine l1;
	TicketLine l2;
	TicketLine l3;
	TicketLine l4;
	List<Ticket> tickets;
	List<TicketLine> lines1;
	List<TicketLine> lines2;

	@BeforeEach 
	public void setup() {
		lines1=new ArrayList<>();
		lines2=new ArrayList<>();

		l1 = new TicketLine(1, 2, 1, 0, null);
		l2 = new TicketLine(2, 0, 2, 0, null);
		l3 = new TicketLine(3, 2, 2, 1, null);
		l4 = new TicketLine(4, 0, 1, 1, null);

		lines1.add(l1);
		lines1.add(l2);

		lines2.add(l3);
		lines2.add(l4);
	}
	
	@Test
	void testSaveAndRetrieveTicket() {
		Ticket t = new Ticket(1, null,false,lines1);
		repo.save(t);
		Ticket fetchedTicket = repo.findById(t.getTicketId()).get();
		assertNotNull(fetchedTicket.getTicketId());
		assertEquals(1, fetchedTicket.getTicketId());
	}
	
	@Test
	void testFindAllTickets() {
		repo.saveAndFlush(new Ticket(101,null,false,lines1));
		repo.saveAndFlush(new Ticket(201,null,false,lines1));
		
		List<Ticket> fetchedTickets = repo.findAll();
		System.out.println(fetchedTickets.size());
		assertTrue(fetchedTickets.size()>1);
	}
	
	

}
