package com.poppulo.lotteryapi.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="TICKET")
public class Ticket {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)  
	@Column(name = "ticket_id")
	private Integer ticketId;
	private Integer totalScore;
	private boolean checkedStatus;

	@OneToMany(cascade=CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name="fk_line_id",referencedColumnName = "ticket_id")
	private List<TicketLine> lines;

	public Ticket(Integer totalScore, boolean checkedStatus, List<TicketLine> lines) {
		this.totalScore = totalScore;
		this.checkedStatus = checkedStatus;
		this.lines = lines;
	}
}
