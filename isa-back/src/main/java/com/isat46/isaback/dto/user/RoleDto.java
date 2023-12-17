package com.isat46.isaback.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.isat46.isaback.model.Role;
import com.isat46.isaback.model.User;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class RoleDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    public RoleDto(){}

    public RoleDto(Role role){
        this(role.getId(), role.getName());
    }

    public RoleDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
