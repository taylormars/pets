package com.pet.provider.utils;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by liyut on 2017-12-20.
 */
@Service
public class SqlUtils {

    @Resource
    public JdbcTemplate jdbcTemplate;
    /**
     * xs 新增一条记录且返回主键id
     * @param sql 新增待执行sql
     * @param param 参数
     * @return int 主键ID
     */
    public int insertSqlAndReturnKeyId(final String sql, final Object[] param) {
        final String innersql = sql;
        final Object[] innerO = param;
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(final Connection con) throws SQLException {
                    PreparedStatement ps = con.prepareStatement(innersql, Statement.RETURN_GENERATED_KEYS);
                    for (int i = 0; i < innerO.length; i++) {
                        ps.setObject(i + 1, innerO[i]);
                    }
                    return ps;
                }
            }, keyHolder);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        Number key = keyHolder.getKey();
        return keyHolder.getKey().intValue();
    }
}
