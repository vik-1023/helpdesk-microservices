package com.helpdesk.dto;

import java.time.LocalDateTime;

import com.helpdesk.entity.Priority;
import com.helpdesk.entity.Status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TicketResponseDTO {

    private Long id;
    private String summary;
    private Priority priority;
    private String username;
    private Status status;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private Long userId;

}
