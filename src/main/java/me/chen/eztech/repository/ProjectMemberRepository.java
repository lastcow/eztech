package me.chen.eztech.repository;

import me.chen.eztech.model.ProjectMembers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectMemberRepository extends JpaRepository<ProjectMembers, String> {

    public Optional<ProjectMembers> getProjectMembersByProjectIdAndMemberId(String projectId, String memberId);
    public List<ProjectMembers> findProjectMembersByActiveAndMember_Username(boolean active, String username);

}
