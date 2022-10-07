package com.poppulo.lotteryapi.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.poppulo.lotteryapi.dto.AmendTicketDto;
import com.poppulo.lotteryapi.dto.CreateTicketDto;
import com.poppulo.lotteryapi.entity.Ticket;
import com.poppulo.lotteryapi.service.LotteryService;

@RestController
@RequestMapping("/ticket")
public class TicketController {

	@Autowired
	private LotteryService service;

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Ticket create(@RequestBody CreateTicketDto ticketDto) {
		return service.create(ticketDto);
	}
	
	@PutMapping("/{id}")
	Ticket amend(@PathVariable int id, @RequestBody AmendTicketDto ticketDto) {
		return service.amendTicket(id, ticketDto);
	}

	@RequestMapping
	public List<Ticket> getAll() {
		return service.getAll();
	}
	
	@RequestMapping("/{id}")
	public Ticket getTicket(@PathVariable int id) {
		return service.getTicket(id);
	}
}
