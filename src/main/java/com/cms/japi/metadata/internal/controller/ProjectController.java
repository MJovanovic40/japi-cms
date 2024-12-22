package com.cms.japi.metadata.internal.controller;

import com.cms.japi.metadata.internal.entity.Project;
import com.cms.japi.metadata.internal.repository.ProjectRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/project")
public class ProjectController {
    private final ProjectRepository projectRepository;

    public ProjectController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @RequestMapping(path = "/create")
    public String createProject(@RequestParam String projectName) {
        Project newProject = new Project();
        newProject.setName(projectName);

        try
        {
            projectRepository.save(newProject);
        }
        catch (Exception e)
        {
            return "Error while creating project: " + e.getMessage();
        }

        return "Schema " + projectName + " created";
    }
}
