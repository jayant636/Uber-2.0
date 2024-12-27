package com.project.uber.uberApp.strategies.Impl;

import com.project.uber.uberApp.entities.Driver;
import com.project.uber.uberApp.entities.Payment;
import com.project.uber.uberApp.entities.Rider;
import com.project.uber.uberApp.entities.enums.PaymentStatus;
import com.project.uber.uberApp.entities.enums.TransactionMethod;
import com.project.uber.uberApp.repositories.PaymentRepository;
import com.project.uber.uberApp.services.WalletService;
import com.project.uber.uberApp.strategies.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//Costs
//Rider - 232 , Driver - 500
//Ride - 100 , commission - 30
//Rider -> 232 -100 = 132
//Driver - 500+100-30 = 570


@Service
@RequiredArgsConstructor
public class WalletPaymentStrategy implements PaymentStrategy {

    private final WalletService walletService;
    private final PaymentRepository paymentRepository;

    @Override
    @Transactional
    public void processPayment(Payment payment) {

//        From payment get the rider & driver
        Driver driver = payment.getRide().getDriver();
        Rider rider = payment.getRide().getRider();

//        Deduct money from Rider wallet
        walletService.deductMoneyToWallet(rider.getUser(),payment.getAmount(),null,payment.getRide(), TransactionMethod.RIDE);


//        0.3 of amount will be go as commission & 0.7 will be go to driver of total money
        double driversCut = payment.getAmount() * (1- PLATFORM_COMMISSION);

        //        Adding money to Driver wallet
        walletService.addMoneyToWallet(driver.getUser(),driversCut,null,payment.getRide(),TransactionMethod.RIDE);

        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        paymentRepository.save(payment);
    }
}
