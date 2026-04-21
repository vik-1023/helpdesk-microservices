package com.helpdesk.dto;

import com.helpdesk.entity.Priority;
import com.helpdesk.entity.Status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TicketRequestDTO {
	
	private Long userId;
	private String summary;
    private Priority priority;
    private String username;
    private Status status;
    
}
