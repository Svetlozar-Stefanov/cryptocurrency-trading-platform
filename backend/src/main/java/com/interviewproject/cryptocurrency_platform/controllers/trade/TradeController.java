package com.interviewproject.cryptocurrency_platform.controllers.trade;

import com.interviewproject.cryptocurrency_platform.exceptions.UserWithThisEmailNotFoundException;
import com.interviewproject.cryptocurrency_platform.models.trade.BuyRequest;
import com.interviewproject.cryptocurrency_platform.models.trade.Transaction;
import com.interviewproject.cryptocurrency_platform.models.user.User;
import com.interviewproject.cryptocurrency_platform.repositories.trade.TransactionRepository;
import com.interviewproject.cryptocurrency_platform.repositories.user.UserRepository;
import com.interviewproject.cryptocurrency_platform.services.trade.TradeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class TradeController {
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final TradeService tradeService;

    public TradeController(UserRepository userRepository,
                           TransactionRepository transactionRepository,
                           TradeService tradeService) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.tradeService = tradeService;
    }

    @GetMapping("/transactions")
    public List<Transaction> getAllTransactions(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.getUserByEmail(userDetails.getUsername());

        if (user == null) {
            throw new UserWithThisEmailNotFoundException(userDetails.getUsername());
        }

        return transactionRepository.getAllTransactions(user.getId());
    }

    @PostMapping("/buy")
    public ResponseEntity<Map<String, String>> buy(@AuthenticationPrincipal UserDetails userDetails,
                                                   @RequestBody BuyRequest buyRequest){
        User user = userRepository.getUserByEmail(userDetails.getUsername());

        if (user == null) {
            throw new UserWithThisEmailNotFoundException(userDetails.getUsername());
        }

        tradeService.buy(user, buyRequest);
        return ResponseEntity.ok().body(Map.of("message", "Transaction successful"));
    }
}
