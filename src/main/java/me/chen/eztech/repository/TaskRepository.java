package me.chen.eztech.repository;


import me.chen.eztech.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {

    /**
     * Get tasks based on project and completed status
     * @param completed
     * @param projectId
     * @return
     */
    public List<Task> getTasksByCompletedAndProjectId(boolean completed, String projectId);
}
