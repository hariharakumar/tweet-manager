package com.hari.tweetmanager.service.impl;

import com.hari.tweetmanager.Exception.TweetManagerException;
import com.hari.tweetmanager.dto.Url;
import com.hari.tweetmanager.dto.User;
import com.hari.tweetmanager.mapper.UserRecordMapper;
import com.hari.tweetmanager.service.UrlDao;
import com.hari.tweetmanager.service.UserDao;
import com.hari.tweetmanager.utils.MySqlQueries;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Service
public class UserDaoImpl implements UserDao {

    static Logger logger = Logger.getLogger(UserDaoImpl.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    UrlDao urlDao;

    @Override
    public long storeUser(User user) throws TweetManagerException {

        // After storing user in DB - call storeURL to store the URL associated to the user - get Id and store it in user table

        // Check if user already exists
        User existingUser = getUserById(user.getUserId());

        if(existingUser != null) {
            return existingUser.getId();
        }

        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {

                    PreparedStatement ps = connection.prepareStatement(MySqlQueries.SQL_USER_CREATE,
                            Statement.RETURN_GENERATED_KEYS);
                    ps.setLong(1, user.getUserId());
                    ps.setString(2, user.getName());
                    ps.setString(3, user.getScreenName());
                    ps.setString(4, user.getLocation());
                    ps.setString(5, user.getDescription());
                    ps.setString(6, user.getUserCreatedAt());
                    ps.setLong(7, user.getFavoritesCount());
                    ps.setLong(8, user.getStatusesCount());
                    ps.setLong(9, user.getFollowersCount());
                    ps.setLong(10, user.getFriendsCount());
                    ps.setString(11, user.getLanguage());
                    return ps;
                }
            }, keyHolder);
        }
        catch (Exception dae) {
            throw new TweetManagerException("Error while storing user data ", dae);
        }

        Long userId = keyHolder.getKey().longValue();

        List<Url> userUrls = user.getUrls();

        urlDao.storeUrls(userUrls, userId, null);

        return userId;
    }

    @Override
    public User getUserById(Long userId) {

        try {
            User userObject = jdbcTemplate.queryForObject(
                    MySqlQueries.SQL_USER_GET_BY_ID,
                    new Object[]{userId},
                    new UserRecordMapper());

            return userObject;
        } catch (EmptyResultDataAccessException e) {
            logger.info("No user found with a userId : " + userId); // This is ok - it means user will be created next
            return null;
        }
    }

}
