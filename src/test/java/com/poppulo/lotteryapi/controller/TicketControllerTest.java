package com.poppulo.lotteryapi.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.poppulo.lotteryapi.dto.AmendTicketDto;
import com.poppulo.lotteryapi.dto.CreateTicketDto;
import com.poppulo.lotteryapi.entity.Ticket;
import com.poppulo.lotteryapi.entity.TicketLine;
import com.poppulo.lotteryapi.exception.CannotAmmendException;
import com.poppulo.lotteryapi.exception.TicketNotFoundException;
import com.poppulo.lotteryapi.service.LotteryService;
import static org.hamcrest.Matchers.*;

@WebMvcTest(TicketController.class)
class TicketControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private LotteryService service;

	Ticket t1;
	Ticket t2;
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

		lines2.add(l1);
		lines2.add(l2);
		lines2.add(l3);
		lines2.add(l4);

		tickets = new ArrayList<>();
		t1 = new Ticket(1,null,false,lines1);
		t2 = new Ticket(2,null,false,lines2);
		tickets.add(t1);
		tickets.add(t2);
	}

	@Test
	public void testFindAll() throws Exception {

		Mockito.when(service.getAll()).thenReturn(tickets);
		String url = "/ticket";

		mockMvc.perform(get(url))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(2)));
	}

	@Test
	public void testCreateTicket() throws Exception 
	{
		CreateTicketDto createTicketDto = new CreateTicketDto(2);

		Mockito.when(service.create(createTicketDto)).thenReturn(t1);

		mockMvc.perform(post("/ticket")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"numLines\": 2 }") 
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.ticketId").exists()) ; 
	}

	@Test
	public void testAmendTicket() throws Exception 
	{
		AmendTicketDto amendTicketDto = new AmendTicketDto(2);
		
		Mockito.when(service.amendTicket(2, amendTicketDto)).thenReturn(t2);
				
		mockMvc.perform(put("/ticket/2")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"addLines\": 2 }")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.lines", hasSize(4)));
	}
	
	@Test
	public void testCannotAmendCheckedTicket() throws Exception 
	{
		AmendTicketDto amendTicketDto = new AmendTicketDto(2);
		
		Mockito.when(service.amendTicket(2, amendTicketDto)).thenThrow(new CannotAmmendException());
		
		mockMvc.perform(put("/ticket/2")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"addLines\": 2 }")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().is4xxClientError());
	}
	
	
	@Test
	public void testGetTicketById() throws Exception 
	{
		Mockito.when(service.getTicket(1)).thenReturn(t1);
		
		mockMvc.perform(get("/ticket/1")
	      .accept(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk())
	      .andExpect(jsonPath("$.ticketId").value(1));
	}
	
	@Test
	public void testGetTicketOnBadTicketId() throws Exception 
	{
		Mockito.when(service.getTicket(20)).thenThrow(new TicketNotFoundException());
		
		mockMvc.perform(get("/ticket/20")
	      .accept(MediaType.APPLICATION_JSON))
	      .andExpect(status().is4xxClientError());
	}

}
