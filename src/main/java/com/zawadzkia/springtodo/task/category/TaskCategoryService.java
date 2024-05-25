package com.zawadzkia.springtodo.task.category;

import com.zawadzkia.springtodo.auth.AppUserDetails;
import com.zawadzkia.springtodo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TaskCategoryService {

    private final TaskCategoryRepository taskCategoryRepository;
    private final UserRepository userRepository;

    public List<TaskCategoryDTO> getUserTaskCategoryList() {
        ArrayList<TaskCategoryDTO> result = new ArrayList<>();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof AppUserDetails userDetails) {
            List<TaskCategoryModel> allByOwner = taskCategoryRepository.findAllByOwner(userDetails.getUser());
            allByOwner.forEach(category -> result.add(new TaskCategoryDTO(category.getId(), category.getName(),
                    category.getDescription(), category.getImage())));
        }
        return result;
    }

    public void addCategory(TaskCategoryDTO categoryDTO) {
        TaskCategoryModel category = TaskCategoryModel.builder()
                .name(categoryDTO.getName())
                .description((categoryDTO.getDescription()))
                .image((categoryDTO.getImage()))
                .owner(userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow())
                .build();
        taskCategoryRepository.save(category);
    }

}
