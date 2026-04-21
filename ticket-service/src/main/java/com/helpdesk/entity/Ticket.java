package com.helpdesk.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String summary;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Priority priority;

    private String username; // optional display field

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = false)
    private Long userId; // main relation with USER service

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    @PrePersist
    protected void createdOn() {

        createdOn = LocalDateTime.now();
        updatedOn = LocalDateTime.now();

        if (status == null)
            status = Status.OPEN;

        if (priority == null)
            priority = Priority.MEDIUM;
    }

    @PreUpdate
    protected void updatedOn() {
        updatedOn = LocalDateTime.now();
    }

}