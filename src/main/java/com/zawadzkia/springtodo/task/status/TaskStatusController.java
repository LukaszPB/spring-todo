package com.zawadzkia.springtodo.task.status;

import com.zawadzkia.springtodo.task.TaskDTO;
import com.zawadzkia.springtodo.task.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/task/status")
@RequiredArgsConstructor
public class TaskStatusController {

    private final TaskService taskService;
    private final TaskStatusService taskStatusService;

    @PostMapping(value = "/{id}")
    String updateTask(@PathVariable Long id, @ModelAttribute("status") TaskStatusDTO taskStatusDTO) {
        TaskDTO taskDTO = taskService.getTaskDTOById(id);
        taskDTO.setStatus(taskStatusDTO.getName());
        taskService.update(taskDTO);
        return "task/list";
    }
    @GetMapping(value = "/add")
    String addTaskStatusForm(Model model) {
        model.addAttribute("newStatus",new TaskStatusDTO());
        return "status/create";
    }
    @PostMapping(value = "/add")
    String addTaskStatus(@ModelAttribute("newStatus") TaskStatusDTO newStatus) {
        taskStatusService.addStatus(newStatus);
        return "redirect:/";
    }
}
