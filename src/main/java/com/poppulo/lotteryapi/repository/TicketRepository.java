package com.poppulo.lotteryapi.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.poppulo.lotteryapi.entity.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {

}
