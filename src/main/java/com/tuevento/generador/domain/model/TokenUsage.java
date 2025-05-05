package com.tuevento.generador.domain.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "token_usages")
public class TokenUsage {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "UUID")
    private UUID tokenUsageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Users user;

    @Column(nullable = false)
    private String service;

    @Column(nullable = false)
    private int tokenConsumed;

    @Column(nullable = false)
    private LocalDateTime timeStamp = LocalDateTime.now();

    public TokenUsage(Users user, String service, int tokenConsumed) {
        this.user = user;
        this.service = service;
        this.tokenConsumed = tokenConsumed;
    }

    
}
