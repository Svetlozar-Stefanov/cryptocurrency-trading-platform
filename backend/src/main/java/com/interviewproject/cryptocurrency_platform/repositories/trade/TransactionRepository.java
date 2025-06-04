package com.interviewproject.cryptocurrency_platform.repositories.trade;

import com.interviewproject.cryptocurrency_platform.models.trade.Transaction;
import com.interviewproject.cryptocurrency_platform.models.user.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TransactionRepository {
    private final JdbcTemplate jdbcTemplate;

    public TransactionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addTransaction(User user, Transaction transaction) {
        String sql = "INSERT INTO transactions (user_id, symbol, type, price, quantity, timestamp) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getId(),
                transaction.getSymbol(), transaction.getType(), transaction.getPrice(), transaction.getQuantity(),
                transaction.getTimestamp());
    }

    public List<Transaction> getAllTransactions(Long user_id) {
        String sql = "SELECT * FROM transactions WHERE user_id = ?";
        return jdbcTemplate.query(sql, new TransactionRowMapper(), user_id);
    }

    public void dropTransactions(Long user_id) {
        String sql = "DELETE FROM transactions WHERE user_id = ?";
        jdbcTemplate.update(sql, user_id);
    }

    private static class TransactionRowMapper implements RowMapper<Transaction> {
        @Override
        public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Transaction(
                    rs.getLong("id"),
                    rs.getLong("user_id"),
                    rs.getString("symbol"),
                    rs.getString("type"),
                    rs.getBigDecimal("price"),
                    rs.getBigDecimal("quantity"),
                    rs.getTimestamp("timestamp")
            );
        }
    }
}
