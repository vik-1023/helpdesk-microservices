package com.helpdesk.mapper;

import com.helpdesk.dto.TicketRequestDTO;
import com.helpdesk.dto.TicketResponseDTO;
import com.helpdesk.entity.Ticket;



public class TicketMapper {

    // RequestDTO → Entity
    public static Ticket toEntity(TicketRequestDTO ticketRequestDto) {

        Ticket ticket = new Ticket();

        ticket.setUserId(ticketRequestDto.getUserId()); // IMPORTANT FIX
        ticket.setPriority(ticketRequestDto.getPriority());
        ticket.setStatus(ticketRequestDto.getStatus());
        ticket.setSummary(ticketRequestDto.getSummary());
        ticket.setUsername(ticketRequestDto.getUsername());

        return ticket;
    }

    // Entity → ResponseDTO
    public static TicketResponseDTO toResponseDTO(Ticket ticket) {

        return new TicketResponseDTO(
                ticket.getId(),
                ticket.getSummary(),
                ticket.getPriority(),
                ticket.getUsername(),
                ticket.getStatus(),
                ticket.getCreatedOn(),
                ticket.getUpdatedOn(),
                ticket.getUserId()
        );
    }

}