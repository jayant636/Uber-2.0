package com.project.uber.uberApp.services.Impl;

import com.project.uber.uberApp.dtos.WalletTransactionDto;
import com.project.uber.uberApp.entities.WalletTransaction;
import com.project.uber.uberApp.repositories.WalletTransactionRepository;
import com.project.uber.uberApp.services.WalletTransactionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletTransactionServiceImpl implements WalletTransactionService {

    private final ModelMapper modelMapper;
    private final WalletTransactionRepository walletTransactionRepository;

    @Override
    public void createNewWalletTransaction(WalletTransaction walletTransaction) {
        walletTransactionRepository.save(walletTransaction);
    }


}
