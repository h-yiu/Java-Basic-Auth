package io.hyao.custombasicauth.repository;

import io.hyao.custombasicauth.entity.UserInfo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class JdbcUserRepository implements UserRepository{
    private final JdbcTemplate jdbcTemplate;

    public JdbcUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public UserInfo checkUserByUserName(String userName) {
        String sql = String.format("SELECT * FROM check_user_func('%s')", userName);
        List<Map<String,Object>> dataResult = jdbcTemplate.queryForList(sql);
        if (!dataResult.isEmpty()) {
            String userId = (String) dataResult.get(0).get("id");
            String password = (String) dataResult.get(0).get("password");
            String email = (String) dataResult.get(0).get("email");
            return new UserInfo(userId, userName, password, email);
        }
        return null;
    }

    @Override
    public String saveNewUser(UserInfo userInfo) {
        String sql = String.format("SELECT * FROM save_new_user_func('%s','%s','%s')", userInfo.getUserName(), userInfo.getPassword(), userInfo.getEmail());
        Map<String, Object> data = jdbcTemplate.queryForMap(sql);
        if (!data.isEmpty()) {
            return (String) data.get("user_id");
        }
        return null;
    }
}
