package com.zawadzkia.springtodo.task;

import com.zawadzkia.springtodo.task.category.TaskCategoryRepository;
import com.zawadzkia.springtodo.task.status.TaskStatusRepository;
import com.zawadzkia.springtodo.user.UserModel;
import com.zawadzkia.springtodo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class TaskService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final TaskStatusRepository taskStatusRepository;
    private final TaskCategoryRepository taskCategoryRepository;

    public List<TaskDTO> getTaskList() {
        UserModel user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow();
        Set<TaskModel> tasks = user.getTasks();
        List<TaskDTO> result = new ArrayList<>();
        tasks.forEach(taskModel -> {
            TaskDTO taskDTO = new TaskDTO(taskModel.getId(), taskModel.getSummary(), taskModel.getDescription(),
                    taskModel.getStartDate(), taskModel.getDueDate(), taskModel.getDescription(), taskModel.getStatus().getName(),taskModel.getCategory().toString());
            result.add(taskDTO);
        });
        return result;
    }

    public TaskDTO getTaskDTOById(Long id) {
        TaskModel taskModel = taskRepository.getReferenceById(id);
        TaskDTO taskDTO = new TaskDTO(taskModel.getId(), taskModel.getSummary(), taskModel.getDescription(),
                taskModel.getStartDate(), taskModel.getDueDate(), taskModel.getDescription(), taskModel.getStatus().getName(),taskModel.getCategory().toString());
        return taskDTO;
    }


    public TaskModel getTaskModelById(Long id) {
        TaskModel taskModel = taskRepository.getReferenceById(id);
        return taskModel;
    }

    public void update(TaskDTO taskDTO) {
        TaskModel taskModel = taskRepository.findById(taskDTO.getId()).orElseThrow();
        taskModel.setSummary(taskDTO.getSummary());
        taskModel.setDescription(taskDTO.getDescription());
        taskModel.setStartDate(taskDTO.getStartDate());
        taskModel.setDueDate(taskDTO.getDueDate());
        taskModel.setAttachment(taskDTO.getAttachment());
        taskModel.setStatus(taskStatusRepository.findByNameAndOwner(taskDTO.getStatus()
                ,userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow()));
        taskModel.setCategory(taskCategoryRepository.findByNameAndOwner(taskDTO.getCategory(),userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow()));
        taskRepository.save(taskModel);
    }
    public void add(TaskDTO taskDTO) {
        UserModel owner = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow();
        TaskModel task = TaskModel.builder()
                .summary(taskDTO.getSummary())
                .description(taskDTO.getDescription())
                .startDate(taskDTO.getStartDate())
                .dueDate(taskDTO.getDueDate())
                .attachment(taskDTO.getAttachment())
                .category(null)
                .status(taskStatusRepository.findByNameAndOwner(taskDTO.getStatus(),owner))
                .category(taskCategoryRepository.findByNameAndOwner(taskDTO.getCategory(),owner))
                .owner(owner)
                .build();
        taskRepository.save(task);
    }
    public void delete(Long id) {
        taskRepository.delete(getTaskModelById(id));
    }
}
