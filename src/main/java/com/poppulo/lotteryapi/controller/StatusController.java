package com.poppulo.lotteryapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poppulo.lotteryapi.entity.Ticket;
import com.poppulo.lotteryapi.service.LotteryService;

@RestController
@RequestMapping("/status")
public class StatusController {
	
	@Autowired
	private LotteryService service;
	
	@PutMapping("/{id}")
	Ticket checkStatus(@PathVariable int id) {
		return service.checkStatus(id);
	}

}
