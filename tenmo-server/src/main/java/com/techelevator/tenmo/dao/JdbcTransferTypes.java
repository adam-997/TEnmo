package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.TransferTypes;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;


@Component
public class JdbcTransferTypes implements TransferTypesDao {
    private final JdbcTemplate jdbcTemplate ;

    public JdbcTransferTypes (DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }
    @Override
    public TransferTypes getTransferTypeByDesc(String description) {

        String sql = "SELECT * FROM transfer_types WHERE transfer_type_desc = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, description);
        TransferTypes transferTypes = null;
        if(results.next()) {
            transferTypes = mapRowToTransferTypes(results);
        }

        return transferTypes;
    }

    @Override
    public TransferTypes getTransferTypeById(int transferTypeId) {
        String sql = "SELECT * FROM transfer_types WHERE transfer_type_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferTypeId);
        TransferTypes transferTypes = null;
        if(results.next()) {
            transferTypes = mapRowToTransferTypes(results);
        }

        return transferTypes;
    }


    private TransferTypes mapRowToTransferTypes(SqlRowSet rowSet){
        int transferTypeId = rowSet.getInt("transfer_type_id");
        String transferTypeDesc = rowSet.getString("transfer_type_desc");

        return new TransferTypes(transferTypeId, transferTypeDesc);
    }
}
