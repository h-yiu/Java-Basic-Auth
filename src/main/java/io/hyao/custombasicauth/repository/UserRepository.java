package io.hyao.custombasicauth.repository;

import io.hyao.custombasicauth.entity.UserInfo;

public interface UserRepository {
    UserInfo checkUserByUserName(String userName);

    String saveNewUser(UserInfo userInfo);
}
