package com.helpdesk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.helpdesk.entity.Priority;
import com.helpdesk.entity.Status;
import com.helpdesk.entity.Ticket;


@Repository
public interface TicketRepository  extends JpaRepository<Ticket, Long>{

	List<Ticket>findByUsername(String string);
	List<Ticket>findByStatus(Status status);
	List<Ticket>findByPriority(Priority priority);
}
