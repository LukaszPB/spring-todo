package com.zawadzkia.springtodo.user;

import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {

    private Long id;
    private String username;
    private String password;
    private boolean enabled;
    private Set<Long> taskModelSetId;
    private Set<Long> taskCategoryModelId;
    private Set<Long> taskStatusModelId;

}
