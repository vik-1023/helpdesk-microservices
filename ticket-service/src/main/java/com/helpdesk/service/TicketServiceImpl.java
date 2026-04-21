package com.helpdesk.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.helpdesk.client.UserClient;
import com.helpdesk.dto.TicketRequestDTO;
import com.helpdesk.dto.TicketResponseDTO;
import com.helpdesk.dto.UserResponseDTO;
import com.helpdesk.entity.Priority;
import com.helpdesk.entity.Status;
import com.helpdesk.entity.Ticket;
import com.helpdesk.exception.ResourceNotFoundException;
import com.helpdesk.mapper.TicketMapper;
import com.helpdesk.repository.TicketRepository;

import feign.FeignException;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final UserClient userClient;

    public TicketServiceImpl(
            TicketRepository ticketRepository,
            UserClient userClient
    ) {
        this.ticketRepository = ticketRepository;
        this.userClient = userClient;
    }


    // ===============================
    // USER SERVICE CALL METHOD
    // ===============================

    public UserResponseDTO getUserFromService(Long userId) {

        try {

            return userClient.getUserById(userId);

        } catch (FeignException ex) {

            if (ex.status() == 404) {

                throw new ResourceNotFoundException(
                        "User not found with ID: " + userId
                );

            }

            if (ex.status() == 503) {

                throw new RuntimeException(
                        "User service is currently down"
                );

            }

            throw new RuntimeException(
                    "User service is unavailable. Please try again later."
            );
        }
    }


    // ===============================
    // CREATE TICKET
    // ===============================

    @Override
    public TicketResponseDTO createTicket(
            TicketRequestDTO ticketRequestDTO
    ) {

        // Validate User Exists
        UserResponseDTO user =
                getUserFromService(
                        ticketRequestDTO.getUserId()
                );


        // Validate Summary
        if (ticketRequestDTO.getSummary() == null ||
                ticketRequestDTO.getSummary().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Summary cannot be empty"
            );
        }


        // Validate Username
        if (ticketRequestDTO.getUsername() == null ||
                ticketRequestDTO.getUsername().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Username cannot be empty"
            );
        }


        // Convert DTO → Entity
        Ticket ticket =
                TicketMapper.toEntity(ticketRequestDTO);


        // Save Ticket
        Ticket savedTicket =
                ticketRepository.save(ticket);


        // Convert Entity → DTO
        return TicketMapper.toResponseDTO(savedTicket);
    }


    // ===============================
    // GET ALL TICKETS (PAGINATION)
    // ===============================

    @Override
    public Page<TicketResponseDTO> getAllTicket(
            Pageable pageable
    ) {

        return ticketRepository
                .findAll(pageable)
                .map(TicketMapper::toResponseDTO);
    }


    // ===============================
    // GET TICKET BY ID
    // ===============================

    @Override
    public TicketResponseDTO getTicketById(Long id) {

        Ticket ticket =
                ticketRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Ticket not found with ID: " + id
                                )
                        );

        return TicketMapper.toResponseDTO(ticket);
    }


    // ===============================
    // GET TICKET BY USERNAME
    // ===============================

    @Override
    public List<TicketResponseDTO> getTicketByUsername(
            String username
    ) {

        List<Ticket> tickets =
                ticketRepository.findByUsername(username);

        if (tickets.isEmpty()) {

            throw new ResourceNotFoundException(
                    "Ticket not found with username: " + username
            );
        }

        return tickets.stream()
                .map(TicketMapper::toResponseDTO)
                .toList();
    }


    // ===============================
    // UPDATE TICKET
    // ===============================

    @Override
    public TicketResponseDTO updateTicket(
            Long id,
            TicketRequestDTO ticketRequestDTO
    ) {

        Ticket ticket =
                ticketRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Ticket not found with ID: " + id
                                )
                        );

        ticket.setSummary(ticketRequestDTO.getSummary());
        ticket.setPriority(ticketRequestDTO.getPriority());
        ticket.setStatus(ticketRequestDTO.getStatus());
        ticket.setUsername(ticketRequestDTO.getUsername());

        Ticket updatedTicket =
                ticketRepository.save(ticket);

        return TicketMapper.toResponseDTO(updatedTicket);
    }


    // ===============================
    // DELETE TICKET
    // ===============================

    @Override
    public void deleteTicket(Long id) {

        ticketRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Ticket not found with ID: " + id
                        )
                );

        ticketRepository.deleteById(id);
    }


    // ===============================
    // FIND BY STATUS
    // ===============================

    @Override
    public List<TicketResponseDTO> findTicketByStatus(
            Status status
    ) {

        List<Ticket> tickets =
                ticketRepository.findByStatus(status);

        if (tickets.isEmpty()) {

            throw new ResourceNotFoundException(
                    "Ticket not found with status: " + status
            );
        }

        return tickets.stream()
                .map(TicketMapper::toResponseDTO)
                .toList();
    }


    // ===============================
    // FIND BY PRIORITY
    // ===============================

    @Override
    public List<TicketResponseDTO> findTicketByPriority(
            Priority priority
    ) {

        List<Ticket> tickets =
                ticketRepository.findByPriority(priority);

        if (tickets.isEmpty()) {

            throw new ResourceNotFoundException(
                    "Ticket not found with priority: " + priority
            );
        }

        return tickets.stream()
                .map(TicketMapper::toResponseDTO)
                .toList();
    }

}