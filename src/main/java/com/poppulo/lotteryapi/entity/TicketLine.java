package com.poppulo.lotteryapi.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="TICKET_LINE")
public class TicketLine {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)  
	private Integer lineId;
	private Integer firstNo;
	private Integer secondNo;
	private Integer thirdNo;
	private Integer score;
	
}
