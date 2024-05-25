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
    String updateTask(@PathVariable Long id, @ModelAttribute("category") String statusName) {
        TaskDTO taskDTO = taskService.getTaskDTOById(id);
        taskDTO.setStatus(statusName);
        taskService.update(taskDTO);
        return "redirect:/";
    }

    @GetMapping(value = "/add")
    String addTaskCategoryForm(Model model) {
        model.addAttribute("newCategory",new TaskCategoryDTO());
        return "category/create";
    }

    @PostMapping(value = "/add")
    String addTaskCategory(@ModelAttribute("newCategory") TaskCategoryDTO newCategory) {
        System.out.println(newCategory);
        taskCategoryService.addCategory(newCategory);
        return "redirect:/";
    }
}
