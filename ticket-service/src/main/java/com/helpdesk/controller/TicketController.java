package com.helpdesk.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.helpdesk.dto.TicketRequestDTO;
import com.helpdesk.dto.TicketResponseDTO;
import com.helpdesk.entity.Priority;
import com.helpdesk.entity.Status;
import com.helpdesk.service.TicketService;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

	private TicketService ticketService;

	public TicketController(TicketService ticketService) {
		this.ticketService = ticketService;
	}

	
	@PostMapping("/createTicket")
	public ResponseEntity<TicketResponseDTO> createTicket(@RequestBody TicketRequestDTO ticketRequestDTO) {

		TicketResponseDTO createdTicket = ticketService.createTicket(ticketRequestDTO);

		return ResponseEntity.status(HttpStatus.CREATED).body(createdTicket);
	}

	@GetMapping
	public ResponseEntity<Page<TicketResponseDTO>> getAllTickets(Pageable pageable) {

		return ResponseEntity.ok(ticketService.getAllTicket(pageable));
	}

	@GetMapping("/getTicketById/{id}")
	public ResponseEntity<TicketResponseDTO> getTicketById(@PathVariable Long id) {

		return ResponseEntity.ok(ticketService.getTicketById(id));
	}

	@GetMapping("/getTicketByUsername/{username}")
	public ResponseEntity<List<TicketResponseDTO>> getTicketsByUsername(@PathVariable String username) {

		return ResponseEntity.ok(ticketService.getTicketByUsername(username));
	}

	@PutMapping("/updateTicketById/{id}")
	public ResponseEntity<TicketResponseDTO> updateTicket(@PathVariable Long id,
			@RequestBody TicketRequestDTO ticketRequestDTO) {

		return ResponseEntity.ok(ticketService.updateTicket(id, ticketRequestDTO));
	}

	@DeleteMapping("/deleteTicketById/{id}")
	public ResponseEntity<String> deleteTicket(@PathVariable Long id) {

		ticketService.deleteTicket(id);

		return ResponseEntity.ok("Ticket deleted successfully: " + id);
	}

	@GetMapping("/getTicketByStatus/{status}")
	public ResponseEntity<List<TicketResponseDTO>> findTicketByStatus(@PathVariable Status status) {

		return ResponseEntity.ok(ticketService.findTicketByStatus(status));
	}

	@GetMapping("/getTicketByPriority/{priority}")
	public ResponseEntity<List<TicketResponseDTO>> findTicketByPriority(@PathVariable Priority priority) {

		return ResponseEntity.ok(ticketService.findTicketByPriority(priority));
	}
}