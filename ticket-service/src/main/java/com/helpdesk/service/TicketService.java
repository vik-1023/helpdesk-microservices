package com.helpdesk.service;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.helpdesk.dto.TicketRequestDTO;
import com.helpdesk.dto.TicketResponseDTO;
import com.helpdesk.entity.Priority;
import com.helpdesk.entity.Status;

public interface TicketService {

    TicketResponseDTO createTicket(TicketRequestDTO ticket);

    Page<TicketResponseDTO> getAllTicket(Pageable pageable);

    TicketResponseDTO getTicketById(Long id);

    List<TicketResponseDTO> getTicketByUsername(String username);

    TicketResponseDTO updateTicket(Long id, TicketRequestDTO ticket);

    void deleteTicket(Long id);

    List<TicketResponseDTO> findTicketByStatus(Status status);

    List<TicketResponseDTO> findTicketByPriority(Priority priority);
}