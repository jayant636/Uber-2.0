package com.project.uber.uberApp.strategies.Impl;

import com.project.uber.uberApp.entities.Driver;
import com.project.uber.uberApp.entities.Payment;
import com.project.uber.uberApp.entities.enums.PaymentStatus;
import com.project.uber.uberApp.entities.enums.TransactionMethod;
import com.project.uber.uberApp.repositories.PaymentRepository;
import com.project.uber.uberApp.services.WalletService;
import com.project.uber.uberApp.strategies.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

//Rider -> 100
//Deduct 30 rs from driver wallet & makes it to 100-30 = 70
//Driver - 70
//Uber Commission - 30

@Service
@RequiredArgsConstructor
public class CashPaymentStrategy implements PaymentStrategy {

    private final WalletService walletService;
    private final PaymentRepository paymentRepository;

    @Override
    public void processPayment(Payment payment) {

//        Get the driver from the payment
        Driver driver = payment.getRide().getDriver();

        double platformCommission = payment.getAmount() * PLATFORM_COMMISSION;

        walletService.deductMoneyToWallet(driver.getUser(),platformCommission,null,payment.getRide(), TransactionMethod.RIDE);

        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        paymentRepository.save(payment);
    }
}
