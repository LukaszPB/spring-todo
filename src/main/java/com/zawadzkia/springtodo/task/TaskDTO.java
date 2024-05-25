package com.zawadzkia.springtodo.task;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO implements Serializable {
    private Long id;

    private String summary;

    private String description;

    private LocalDateTime startDate;

    private LocalDateTime dueDate;

    private String attachment;

    private String status;

    //private String category
}
