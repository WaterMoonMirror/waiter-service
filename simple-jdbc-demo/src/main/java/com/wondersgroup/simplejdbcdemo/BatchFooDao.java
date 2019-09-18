package com.wondersgroup.simplejdbcdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BatchFooDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void batchInsert(){
        jdbcTemplate.batchUpdate("insert into foo(bar) values (?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                            preparedStatement.setString(1,"x-"+i);
                    }

                    @Override
                    public int getBatchSize() {
                        return 3;  // 批处理数据量
                    }
                });
        List<Foo> fooList=new ArrayList<>();
        fooList.add(Foo.builder().id(100L).bar("x-100").build());
        fooList.add(Foo.builder().id(101L).bar("x-101").build());
        namedParameterJdbcTemplate.batchUpdate("insert into foo values(:id,:bar)", SqlParameterSourceUtils.createBatch(fooList));

    }

}
