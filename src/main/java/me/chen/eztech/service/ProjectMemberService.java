package me.chen.eztech.service;


import me.chen.eztech.model.Project;
import me.chen.eztech.model.ProjectMembers;
import me.chen.eztech.model.User;
import me.chen.eztech.repository.ProjectMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectMemberService {

    @Autowired
    ProjectService projectService;
    @Autowired
    UserService userService;
    @Autowired
    ProjectMemberRepository projectMemberRepository;

    public ProjectMembers create(String projectId, String memberId){

        Optional<ProjectMembers> projectMember = projectMemberRepository.getProjectMembersByProjectIdAndMemberId(projectId, memberId);
        if(projectMember.isPresent()){
            ProjectMembers projectMembers = projectMember.get();
            projectMembers.setActive(true);
            projectMemberRepository.save(projectMembers);
        }else {

            Optional<Project> project = projectService.getProjectById(projectId);
            Optional<User> member = userService.getUserById(memberId);

            if (project.isPresent() && member.isPresent()) {
                ProjectMembers projectMembers = new ProjectMembers();
                projectMembers.setProject(project.get());
                projectMembers.setMember(member.get());
                projectMembers.setActive(true);
                projectMembers.setJoined(new Timestamp(System.currentTimeMillis()));

                return projectMemberRepository.save(projectMembers);
            }
        }

        return null;
    }


    /**
     * Get Projects based on username
     * @param username
     * @return
     */
    public List<ProjectMembers> getProjectByActiveAndUserName(boolean active, String username){
        return projectMemberRepository.findProjectMembersByActiveAndMember_Username(active, username);
    }
}
