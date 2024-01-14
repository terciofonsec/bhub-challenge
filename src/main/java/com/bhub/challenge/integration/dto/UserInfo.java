package com.bhub.challenge.integration.dto;

import java.util.UUID;

public record UserInfo(UUID userId, String userName, Address address ) {

    public record Address(String street, String complement, String city, String state){}
}
