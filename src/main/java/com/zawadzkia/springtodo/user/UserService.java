package com.zawadzkia.springtodo.user;

import com.zawadzkia.springtodo.task.TaskModel;
import com.zawadzkia.springtodo.task.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public void deleteUser(long id){
        Optional<UserModel> userModel = userRepository.findById(id);
        if (userModel.isPresent())
        {
            List<TaskModel> ListOfUserTasks = taskRepository.findAllByOwner(userModel.get());
            taskRepository.deleteAll(ListOfUserTasks);
            userRepository.deleteById(id);
        }
    }

    public List<UserDTO> getAllUsers(){
        List<UserDTO> userDTOList = new LinkedList<UserDTO>();
        for (UserModel userModel : userRepository.findAll()) {
            userDTOList.add(convertUserToDTO(userModel));
        }
        return userDTOList;
    }


    public void addUser(UserDTO userDTO){
        UserModel userModel = UserModel.builder()
                .id(userDTO.getId())
                .username(userDTO.getUsername())
                .enabled(userDTO.isEnabled())
                .password(userDTO.getPassword())
                .build();
        userRepository.save(userModel);
    }

    private UserDTO convertUserToDTO(UserModel userModel){
        return UserDTO.builder()
                    .id(userModel.getId())
                    .username(userModel.getUsername())
                    .password(userModel.getPassword())
                    .enabled(userModel.isEnabled())
                    .taskModelSetId(Collections.singleton(userModel.getTasks().iterator().next().getId()))
                    .taskCategoryModelId(Collections.singleton(userModel.getCategories().iterator().next().getId()))
                    .taskStatusModelId(Collections.singleton(userModel.getStatuses().iterator().next().getId()))
                    .build();
    }
}
