package com.hari.tweetmanager.service.impl;

import com.hari.tweetmanager.Exception.TweetManagerException;
import com.hari.tweetmanager.dto.Url;
import com.hari.tweetmanager.service.UrlDao;
import com.hari.tweetmanager.utils.MySqlQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Service
public class UrlDaoImpl implements UrlDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void storeUrls(List<Url> urlList, Long userId, Long tweetId) throws TweetManagerException {

        for (Url url : urlList) {
            try {
                jdbcTemplate.update(new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {

                        PreparedStatement ps = connection.prepareStatement(MySqlQueries.SQL_URL_CREATE);

                        ps.setString(1, url.getUrl());
                        ps.setString(2, url.getExpandedUrl());
                        ps.setString(3, url.getDisplayUrl());

                        if(tweetId != null) {
                            ps.setNull(4, Types.NULL);
                            ps.setLong(5, tweetId);
                        }
                        else {
                            ps.setLong(4, userId);
                            ps.setNull(5, Types.NULL);
                        }

                        return ps;
                    }
                });
            } catch (Exception ue) {
                throw new TweetManagerException("Error while storing url data ", ue);
            }
        }
    }
}
