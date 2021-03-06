package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.TransferStatuses;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;


@Component
public class JdbcTransferStatuses implements TransferStatusesDao{
    private final JdbcTemplate jdbcTemplate ;

    public JdbcTransferStatuses (DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }
    @Override
    public TransferStatuses getTransferStatusByDesc(String description) {
        TransferStatuses transferStatuses = new TransferStatuses();
        String sql = "SELECT * FROM transfer_statuses WHERE transfer_status_desc = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, description);

        if(results.next()) {
            transferStatuses = mapRowToTransferStatuses(results);
        }

        return transferStatuses;
    }

    @Override
    public TransferStatuses getTransferStatusById(int transferStatusId) {
        String sql = "SELECT * FROM transfer_statuses WHERE transfer_status_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferStatusId);
        TransferStatuses transferStatuses = new TransferStatuses();
        if(results.next()) {
            transferStatuses = mapRowToTransferStatuses(results);
        }

        return transferStatuses;
    }

    private TransferStatuses mapRowToTransferStatuses(SqlRowSet rowSet){
        int transferStatusId = rowSet.getInt("transfer_status_id");
        String transferStatusDesc = rowSet.getString("transfer_status_desc");

        return new TransferStatuses(transferStatusId, transferStatusDesc);
    }
}
