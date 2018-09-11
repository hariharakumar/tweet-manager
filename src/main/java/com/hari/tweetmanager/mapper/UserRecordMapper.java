package com.hari.tweetmanager.mapper;

import com.hari.tweetmanager.dto.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRecordMapper  implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("u.id"));
        user.setUserId(rs.getLong("u.userId"));
        user.setName(rs.getString("u.name"));
        user.setScreenName(rs.getString("u.screenName"));
        user.setLocation(rs.getString("u.location"));
        user.setDescription(rs.getString("u.description"));
        user.setUserCreatedAt(rs.getString("u.userCreatedAt"));

        user.setFavoritesCount(rs.getLong("u.favoritesCount"));
        user.setStatusesCount(rs.getLong("u.statusesCount"));
        user.setFollowersCount(rs.getLong("u.followersCount"));
        user.setFriendsCount(rs.getLong("u.friendsCount"));
        user.setLanguage(rs.getString("u.language"));

        return user;
    }

}
