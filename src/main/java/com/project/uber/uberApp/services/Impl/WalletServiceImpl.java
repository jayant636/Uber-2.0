package com.project.uber.uberApp.services.Impl;

import com.project.uber.uberApp.entities.Ride;
import com.project.uber.uberApp.entities.User;
import com.project.uber.uberApp.entities.Wallet;
import com.project.uber.uberApp.entities.WalletTransaction;
import com.project.uber.uberApp.entities.enums.TransactionMethod;
import com.project.uber.uberApp.entities.enums.TransactionType;
import com.project.uber.uberApp.repositories.WalletRepository;
import com.project.uber.uberApp.services.WalletService;
import com.project.uber.uberApp.services.WalletTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {


    private final WalletTransactionService walletTransactionService;
    private final WalletRepository walletRepository;

    @Override
    @Transactional
    public Wallet addMoneyToWallet(User user, Double amount, String transactionId, Ride ride , TransactionMethod transactionMethod) {
//       fetch the wallet linked to user
        Wallet wallet = findByUser(user);
//        setting the balance in the wallet
        wallet.setBalance(wallet.getBalance()+amount);

        WalletTransaction walletTransaction = WalletTransaction.builder()
                .transactionId(transactionId)
                .ride(ride)
                .wallet(wallet)
                .transactionType(TransactionType.CREDIT)
                .transactionMethod(transactionMethod)
                .amount(amount)
                .build();

        walletTransactionService.createNewWalletTransaction(walletTransaction);

        //      saving the changes in the wallet repo
        return walletRepository.save(wallet);
    }

    @Override
    @Transactional
    public Wallet deductMoneyToWallet(User user, Double amount, String transactionId, Ride ride , TransactionMethod transactionMethod) {
        Wallet wallet = findByUser(user);
//        setting the balance in the wallet
        wallet.setBalance(wallet.getBalance() - amount);

        WalletTransaction walletTransaction = WalletTransaction.builder()
                .transactionId(transactionId)
                .ride(ride)
                .wallet(wallet)
                .transactionType(TransactionType.DEBIT)
                .transactionMethod(transactionMethod)
                .amount(amount)
                .build();

//        walletTransactionService.createNewWalletTransaction(walletTransaction);
        wallet.getTransactions().add(walletTransaction);

//      saving the changes in the wallet repo
        return walletRepository.save(wallet);
    }

    @Override
    public void withdrawAllMyMoneyFromWallet() {

    }

    @Override
    public Wallet findWalletById(Long walletId) {
        return walletRepository.findById(walletId).orElseThrow(()-> new RuntimeException("Wallet with this id does not exist"));
    }

    @Override
    public Wallet createNewWallet(User user) {
        //Created a wallet
        Wallet wallet = new Wallet();
//        Adding the wallet to user account
        wallet.setUser(user);
//        Saving the wallet updates in wallet repo
        return walletRepository.save(wallet);
    }

    @Override
    public Wallet findByUser(User user) {
        return walletRepository.findByUser(user).orElseThrow(()-> new RuntimeException("Wallet Not found for this UserId"+user.getId()));
    }
}
