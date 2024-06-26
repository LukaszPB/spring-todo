package com.zawadzkia.springtodo.task.status;

import com.zawadzkia.springtodo.auth.AppUserDetails;
import com.zawadzkia.springtodo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskStatusService {

    private final TaskStatusRepository taskStatusRepository;
    private final UserRepository userRepository;

    public List<TaskStatusDTO> getUserTaskStatusList() {
        ArrayList<TaskStatusDTO> result = new ArrayList<>();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof AppUserDetails userDetails) {
            List<TaskStatusModel> allByOwner = taskStatusRepository.findAllByOwner(userDetails.getUser());
            allByOwner.forEach(status -> result.add(new TaskStatusDTO(status.getId(), status.getName(),
                    status.getDisplayName())));
        }
        return result;
    }
    public void addStatus(TaskStatusDTO statusDTO) {
        TaskStatusModel status = TaskStatusModel.builder()
                .name(statusDTO.getName())
                .displayName((statusDTO.getDisplayName()))
                .owner(userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow())
                .build();
        taskStatusRepository.save(status);
    }
}
