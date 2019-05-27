package me.chen.eztech.service;

import me.chen.eztech.model.Task;
import me.chen.eztech.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;

    public List<Task> getTaskByProjectAndStatus(String projectId, boolean completed){
        return taskRepository.getTasksByCompletedAndProjectId(completed, projectId);
    }
}
