package com.poppulo.lotteryapi.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
import com.poppulo.lotteryapi.entity.Ticket;
import com.poppulo.lotteryapi.entity.TicketLine;
import com.poppulo.lotteryapi.exception.TicketNotFoundException;
import com.poppulo.lotteryapi.service.LotteryService;

@WebMvcTest(StatusController.class)
class StatusControllerTest {

	@Autowired
	private MockMvc mockMvc;

	Ticket t1;
	TicketLine l1;
	List<Ticket> tickets;
	List<TicketLine> lines1;

	@BeforeEach 
	public void setup() {
		lines1=new ArrayList<>();
		l1 = new TicketLine(1, 2, 1, 0, null);
		t1 = new Ticket(1,1,true,lines1);
	}

	@MockBean
	private LotteryService service;

	@Test
	void testCheckStatus() throws Exception {

		Mockito.when(service.checkStatus(1)).thenReturn(t1);

		mockMvc.perform(put("/status/1")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.ticketId").value(1));
	}
	
	@Test
	void testCheckStatusOnBadTicketId() throws Exception {

		Mockito.when(service.checkStatus(1)).thenThrow(new TicketNotFoundException());

		mockMvc.perform(put("/status/1")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().is4xxClientError());
	}

}
