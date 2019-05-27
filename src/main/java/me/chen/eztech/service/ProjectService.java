package me.chen.eztech.service;

import me.chen.eztech.dto.ProjectDto;
import me.chen.eztech.model.Project;
import me.chen.eztech.model.Task;
import me.chen.eztech.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    public Project newProjectWithDto(ProjectDto projectDto){
        Project project = new Project();
        project.setName(projectDto.getProjectName());
        project.setDuedate(projectDto.getDeadLine());
        project.setDescription(projectDto.getProjectDesc());
        project.setStatus("In progress");

        return projectRepository.save(project);
    }

    /**
     * Update project
     * @param projectId
     * @param projectDto
     * @return
     */
    public Project updateProjectWithDto(String projectId, ProjectDto projectDto){
        // Get Project
        Optional<Project> project = projectRepository.findById(projectId);
        if(!project.isPresent()){
            return null;
        }

        // Do update
        Project selectedProject = project.get();
        if(projectDto.getProjectName() != null) {
            selectedProject.setName(projectDto.getProjectName());
        }
        if(projectDto.getDeadLine() != null) {
            selectedProject.setDuedate(projectDto.getDeadLine());
        }
        if(projectDto.getProjectDesc()!= null) {
            selectedProject.setDescription(projectDto.getProjectDesc());
        }

        return projectRepository.save(selectedProject);

    }

    public List<Project> getProjects(){
        return projectRepository.findAll();
    }
}
