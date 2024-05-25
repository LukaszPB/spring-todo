package com.zawadzkia.springtodo.task.category;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskCategoryDTO {

    private Long id;
    private String name;
    private String description;
    private String image;

}
