package com.interviewproject.cryptocurrency_platform.services.user;

import com.interviewproject.cryptocurrency_platform.models.user.User;
import com.interviewproject.cryptocurrency_platform.repositories.trade.AssetRepository;
import com.interviewproject.cryptocurrency_platform.repositories.trade.TransactionRepository;
import com.interviewproject.cryptocurrency_platform.repositories.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final TransactionRepository transactionRepository;
    private final AssetRepository  assetRepository;
    private final UserRepository userRepository;

    public UserService(TransactionRepository transactionRepository,
                       AssetRepository assetRepository,
                       UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.assetRepository = assetRepository;
        this.userRepository = userRepository;
    }

    public void reset(User user) {
        user.reset();
        userRepository.updateUserBalance(user);

        transactionRepository.dropTransactions(user.getId());
        assetRepository.dropAssets(user.getId());
    }
}
