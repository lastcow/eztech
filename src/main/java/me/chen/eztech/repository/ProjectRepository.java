package me.chen.eztech.repository;


import jdk.nashorn.internal.runtime.options.Option;
import me.chen.eztech.model.Project;
import me.chen.eztech.model.Task;
import me.chen.eztech.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, String> {

    public List<Project> getProjectsByProfessorUsername(String userName);

}
