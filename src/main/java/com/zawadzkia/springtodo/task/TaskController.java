package com.zawadzkia.springtodo.task;

import com.zawadzkia.springtodo.task.status.TaskStatusDTO;
import com.zawadzkia.springtodo.task.status.TaskStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping({ "/task" })
@Slf4j
class TaskController {

    private final TaskService taskService;

    private final TaskStatusService taskStatusService;

    @GetMapping
    String getTaskList(Model model) {
        List<TaskDTO> taskList = taskService.getTaskList();
        List<TaskStatusDTO> userTaskStatusList = taskStatusService.getUserTaskStatusList();
        model.addAttribute("tasks", taskList);
        model.addAttribute("statusList", userTaskStatusList);
        return "task/list";

    }
    @GetMapping("/add")
    String addTaskForm(Model model) {
        model.addAttribute("newTask", new TaskDTO());
        List<TaskStatusDTO> userTaskStatusList = taskStatusService.getUserTaskStatusList();
        model.addAttribute("statusList", userTaskStatusList);
        return "task/create";
    }
    @PostMapping("/add")
    String addTask(@ModelAttribute("newTask") TaskDTO newTask) {
        taskService.add(newTask);
        return "redirect:/";
    }
    @GetMapping("/update/{id}")
    String updateTaskForm(Model model, @PathVariable("id") Long id) {
        model.addAttribute("updateTask", taskService.getTaskDTOById(id));
        List<TaskStatusDTO> userTaskStatusList = taskStatusService.getUserTaskStatusList();
        model.addAttribute("statusList", userTaskStatusList);
        return "task/update";
    }
    @PostMapping("/update")
    String updateTask(@ModelAttribute("updateTask") TaskDTO updateTask) {
        taskService.update(updateTask);
        return "redirect:/";
    }
    @PostMapping("/delete/{id}")
    String deleteTask(@PathVariable("id") Long id) {
        taskService.delete(id);
        return "redirect:/";
    }
}
