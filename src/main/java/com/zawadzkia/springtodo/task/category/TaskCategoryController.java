package com.zawadzkia.springtodo.task.category;

import com.zawadzkia.springtodo.task.TaskDTO;
import com.zawadzkia.springtodo.task.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping({ "/task/category" })
@Slf4j
public class TaskCategoryController {

    private final TaskService taskService;
    private final TaskCategoryService taskCategoryService;

    @PostMapping(value = "/{id}")
    String updateTask(@PathVariable Long id, @RequestParam("category") String categoryName) {
        log.info("Received category: {}", categoryName);
        TaskDTO taskDTO = taskService.getTaskDTOById(id);
        if (taskDTO != null) {
            taskDTO.setCategory(categoryName);
            // Ensure other fields remain unchanged
            taskDTO.setStatus(taskService.getTaskModelById(id).getStatus().getName());
            taskService.update(taskDTO);
        } else {
            log.warn("Task with ID {} not found", id);
        }
        return "redirect:/";
    }

    @GetMapping(value = "/add")
    String addTaskCategoryForm(Model model) {
        model.addAttribute("newCategory",new TaskCategoryDTO());
        return "category/create";
    }

    @PostMapping(value = "/add")
    String addTaskCategory(@ModelAttribute("newCategory") TaskCategoryDTO newCategory) {
        taskCategoryService.addCategory(newCategory);
        return "redirect:/";
    }
}
