package me.chen.eztech.repository;


import me.chen.eztech.model.Project;
import me.chen.eztech.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, String> {

}
