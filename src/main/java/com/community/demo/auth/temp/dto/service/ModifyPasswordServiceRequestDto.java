package com.community.demo.auth.temp.dto.service;

import lombok.Data;

@Data
public class ModifyPasswordServiceRequestDto {
    public long userId;
    public String modifiedPassword;
}
