package com.example.g2_se1630_swd392.controller;

import com.example.g2_se1630_swd392.dto.HttpResponse;
import com.example.g2_se1630_swd392.dto.project.request.CreateProjectRequest;
import com.example.g2_se1630_swd392.dto.project.request.SearchProjectRequest;
import com.example.g2_se1630_swd392.dto.project.request.UpdateProjectRequest;
import com.example.g2_se1630_swd392.dto.project.response.SearchProjectResponse;
import com.example.g2_se1630_swd392.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000/")
public class ProjectController {
    private final ProjectService projectService;
    @PostMapping("/search")
    public HttpResponse<?> searchProject(@RequestBody SearchProjectRequest request) {
        SearchProjectResponse response = projectService.searchProject(request);
        return HttpResponse.ok(response);
    }
    @PostMapping("/create")
    public HttpResponse<?> createProject(@RequestBody CreateProjectRequest request){
        return HttpResponse.ok(projectService.create(request));
    }

    @PutMapping("/update/{id}")
    public HttpResponse<?> updateProject(@PathVariable("id") Integer id, @RequestBody UpdateProjectRequest request) {
        return HttpResponse.ok(projectService.update(id, request));
    }

    @GetMapping("/get-detail/{id}")
    public HttpResponse<?> getDetail(@PathVariable("id") Integer id){
        return HttpResponse.ok(projectService.getDetail(id));
    }
}
