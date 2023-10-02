package com.phoenix.authservice.transform;

import com.phoenix.authservice.model.RetrieveRoleRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RetrieveRoleRequestBuilder {

    public static RetrieveRoleRequest build(String name) {
        return RetrieveRoleRequest.builder()
                .name(name)
                .build();
    }
}
