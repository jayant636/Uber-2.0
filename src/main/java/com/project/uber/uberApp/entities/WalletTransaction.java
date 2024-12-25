package com.project.uber.uberApp.entities;

import com.project.uber.uberApp.entities.enums.TransactionMethod;
import com.project.uber.uberApp.entities.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "walletTransaction")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WalletTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;

    private TransactionType transactionType;

    private TransactionMethod transactionMethod;

    @ManyToOne
    private Ride ride;

    private String transactionId;

    //Many walletTransaction can have one Wallet
    @ManyToOne
    private Wallet wallet;

    @CreationTimestamp
    private LocalDateTime timestamp;


}
