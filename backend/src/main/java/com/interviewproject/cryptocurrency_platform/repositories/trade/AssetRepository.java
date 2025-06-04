package com.interviewproject.cryptocurrency_platform.repositories.trade;

import com.interviewproject.cryptocurrency_platform.models.trade.Asset;
import com.interviewproject.cryptocurrency_platform.models.trade.Transaction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AssetRepository {

    private final JdbcTemplate jdbcTemplate;

    public AssetRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Asset> getAllAssets(Long userId) {
        String sql = "SELECT * FROM assets WHERE user_id = ?";
        return jdbcTemplate.query(sql, new AssetRowMapper(), userId);
    }

    public Boolean assetExists(Long userId, Long asset_id) {
        String sql = "SELECT * FROM assets WHERE user_id = ? and symbol = ?";
        return jdbcTemplate.queryForObject(sql, new AssetRowMapper(), userId, asset_id) != null;
    }


    public Asset getAsset(Long user_id, String symbol) {
        String sql = "SELECT * FROM assets WHERE user_id = ? AND symbol = ?";
        return jdbcTemplate.queryForObject(sql, new AssetRowMapper(), user_id, symbol);
    }

    public void addAsset(Asset asset) {
        String sql = "INSERT INTO assets (user_id, symbol, quantity, avg_price) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, asset.getUserId(), asset.getSymbol(), asset.getQuantity(), asset.getAvg_price());
    }

    public void updateAsset(Asset asset) {
        String sql = "UPDATE assets SET quantity = ?, avg_price = ? WHERE user_id = ? AND symbol = ?";
        jdbcTemplate.update(sql, asset.getQuantity(), asset.getAvg_price(), asset.getUserId(), asset.getSymbol());
    }

    public void dropAssets(Long user_id) {
        String sql = "DELETE FROM assets WHERE user_id = ?";
        jdbcTemplate.update(sql, user_id);
    }

    private static class AssetRowMapper implements RowMapper<Asset> {
        @Override
        public Asset mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Asset(
                    rs.getLong("user_id"),
                    rs.getString("symbol"),
                    rs.getBigDecimal("quantity"),
                    rs.getBigDecimal("avg_price")
            );
        }
    }
}
