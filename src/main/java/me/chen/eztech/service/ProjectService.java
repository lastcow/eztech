package me.chen.eztech.service;

import me.chen.eztech.dto.ProjectDto;
import me.chen.eztech.form.MilestoneForm;
import me.chen.eztech.model.Project;
import me.chen.eztech.model.ProjectMembers;
import me.chen.eztech.model.Task;
import me.chen.eztech.model.User;
import me.chen.eztech.repository.ProjectMemberRepository;
import me.chen.eztech.repository.ProjectRepository;
import me.chen.eztech.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    ProjectMemberRepository projectMemberRepository;
    @Autowired
    UserService userService;


    /**
     * Get project by ID
     * @param id
     * @return
     */
    public Optional<Project> getProjectById(String id){
        return projectRepository.findById(id);
    }

    /**
     * Get my projects as professor
     * @param userName
     * @return
     */
    public List<Project> getMyProject(String userName){
        return projectRepository.getProjectsByProfessorUsername(userName);
    }

    public Project newProjectWithDto(ProjectDto projectDto, String username){
        // Get owner
        Optional<User> owner = userService.getUserByUsername(username);
        if(owner.isPresent()) {

            Project project = new Project();
            project.setName(projectDto.getProjectName());
            project.setDuedate(projectDto.getDeadLine());
            project.setDescription(projectDto.getProjectDesc());
            project.setStatus("In progress");
            project.setProfessor(owner.get());

            return projectRepository.save(project);
        }

        return null;
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


    /**
     * Add milestone to project
     * @param milestoneForm
     */
    public void addMilestone(MilestoneForm milestoneForm){

        // Get project
        Optional<Project> project = projectRepository.findById(milestoneForm.getProjectId());

        // Add milestone
        if(project.isPresent()){

            Task task = new Task();
            task.setMilestone(true);
            task.setDeadline(milestoneForm.getDeadline());
            task.setDescription(milestoneForm.getDesc());

            task.setProject(project.get());
            taskRepository.save(task);

        }

    }

    public void inactivateMembers(String projectId){
        Optional<Project> projectOpt = projectRepository.findById(projectId);
        if(projectOpt.isPresent()){
            Project project = projectOpt.get();

            List<ProjectMembers> members = project.getMembers();
            members.forEach(member -> {
                member.setActive(false);
            });

            // Save
            projectMemberRepository.saveAll(members);
        }

    }
}
