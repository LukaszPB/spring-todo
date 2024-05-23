package com.zawadzkia.springtodo.task.status;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskStatusDTO implements Serializable {

    private Long id;

    private String name;

    private String displayName;

}
