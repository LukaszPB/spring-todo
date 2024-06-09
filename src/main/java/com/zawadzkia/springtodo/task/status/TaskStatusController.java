package com.zawadzkia.springtodo.task.status;

import com.zawadzkia.springtodo.task.TaskDTO;
import com.zawadzkia.springtodo.task.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/task/status")
@RequiredArgsConstructor
@Slf4j
public class TaskStatusController {

    private final TaskService taskService;
    private final TaskStatusService taskStatusService;

    @PostMapping(value = "/{id}")
    String updateTask(@PathVariable Long id, @RequestParam("status") String statusName) {
        log.info("Received status: {}", statusName);
        TaskDTO taskDTO = taskService.getTaskDTOById(id);
        if (taskDTO != null) {
            System.out.println(taskDTO.getStatus());
            System.out.println(taskDTO.getCategory());
            taskDTO.setStatus(statusName);
            // Ensure other fields remain unchanged
            //taskDTO.setStatus(taskService.getTaskModelById(id).getStatus().getName());
            taskService.update(taskDTO);
        } else {
            log.warn("Task with ID {} not found", id);
        }
        return "redirect:/";
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
