package com.sap.refactoring.input;

import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntityInput {

    private Integer id;
    private String name;
    private String email;
    @Setter
    private List<String> roles;

}
