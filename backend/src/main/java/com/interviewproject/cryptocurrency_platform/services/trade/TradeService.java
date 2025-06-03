package com.interviewproject.cryptocurrency_platform.services.trade;

import com.interviewproject.cryptocurrency_platform.exceptions.InsufficientFundsException;
import com.interviewproject.cryptocurrency_platform.models.trade.BuyRequest;
import com.interviewproject.cryptocurrency_platform.models.trade.Transaction;
import com.interviewproject.cryptocurrency_platform.models.user.User;
import com.interviewproject.cryptocurrency_platform.repositories.trade.TransactionRepository;
import com.interviewproject.cryptocurrency_platform.repositories.user.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

@Service
public class TradeService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public TradeService(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    public void buy(User user, BuyRequest buyRequest) {
        BigDecimal finalPrice = buyRequest.price().multiply(buyRequest.quantity());
        if (user.getBalance().compareTo(finalPrice) >= 0) {
            user.withdrawFromBalance(finalPrice);

            userRepository.updateUserBalance(user);

            Transaction newTransaction = new Transaction(
                user.getId(),
                    buyRequest.symbol(),
                    "BUY",
                    buyRequest.price(),
                    buyRequest.quantity(),
                    Timestamp.from(Instant.now())
            );

            transactionRepository.addTransaction(user, newTransaction);
        } else {
            throw new InsufficientFundsException("");
        }
    }
}
