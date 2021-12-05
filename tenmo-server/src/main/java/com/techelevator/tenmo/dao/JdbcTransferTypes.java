package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.TransferStatuses;
import com.techelevator.tenmo.model.TransferTypes;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JdbcTransferTypes implements TransferTypesDao {
    private JdbcTemplate jdbcTemplate;

    @Override
    public TransferTypes getTransferTypeByDesc(String description) {
        String sql = "SELECT transfer_type_id, transfer_type_desc FROM transfer_types WHERE transfer_type_desc = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, description);
        TransferTypes transferTypes = null;
        if(results.next()) {
            transferTypes = mapRowToTransferTypes(results);
        }

        return transferTypes;
    }

    @Override
    public TransferTypes getTransferTypeById(int transferId) {
        String sql = "SELECT transfer_type_id, transfer_type_desc FROM transfer_types WHERE transfer_type_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
        TransferTypes transferTypes = null;
        if(results.next()) {
            transferTypes = mapRowToTransferTypes(results);
        }

        return transferTypes;
    }


    private TransferTypes mapRowToTransferTypes(SqlRowSet rowSet){
        int transferTypeId = rowSet.getInt("transferTypeId");
        String transferTypeDesc = rowSet.getString("transferTypeDesc");

        return new TransferTypes(transferTypeId, transferTypeDesc);
    }
}
