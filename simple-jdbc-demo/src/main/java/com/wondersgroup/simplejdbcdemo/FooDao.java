package com.wondersgroup.simplejdbcdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Repository
@Slf4j
public class FooDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private SimpleJdbcInsert simpleJdbcInsert;

    // 插入数据
    public void insertData() {
        // 使用jdbcTemplate 插入数据
        Arrays.asList("b", "c").forEach(c -> {
            jdbcTemplate.update("insert into foo (bar) values (?)", c);
        });
        // 使用simpleJdbcInsert插入数据
        HashMap<String, String> row = new HashMap<>();
        row.put("bar", "d");
        Number id = simpleJdbcInsert.executeAndReturnKey(row);
        log.info("ID of d: {}", id.longValue());
    }
    // 查询数据
    public  void listData(){
        log.info("Count{}",jdbcTemplate.queryForObject("select count (*) from foo",Long.class));
        List<String> list = jdbcTemplate.queryForList("select BAR from foo", String.class);
        list.forEach(c->log.info("Bar{}",c));

        List<Foo> query = jdbcTemplate.query("select * from foo", new RowMapper<Foo>() {
            @Override
            public Foo mapRow(ResultSet resultSet, int i) throws SQLException {
                return Foo.builder().id(resultSet.getLong(1)).bar(resultSet.getString(2)).build();
            }
        });
        query.forEach(c->log.info("Foo{}",c));

    }
}
