package com.bhub.challenge.integration;

import com.bhub.challenge.integration.dto.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class DomainServerIntegration {

    public UserInfo getUserInfo(UUID userId){
        UserInfo.Address address = new UserInfo.Address("AB street", "N 5", "Suzano", "SP");
        var userInfo = new UserInfo(userId, "User Name", address );
        log.info("User information retrieved from Domain Service, user: {}", userInfo);
        return userInfo;
    }
}
