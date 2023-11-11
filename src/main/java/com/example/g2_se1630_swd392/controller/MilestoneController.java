package com.example.g2_se1630_swd392.controller;

import com.example.g2_se1630_swd392.dto.HttpResponse;
import com.example.g2_se1630_swd392.dto.milestone.request.CreateMilestoneRequest;
import com.example.g2_se1630_swd392.dto.milestone.request.SearchMilestoneRequest;
import com.example.g2_se1630_swd392.dto.milestone.request.UpdateMilestoneRequest;
import com.example.g2_se1630_swd392.service.MilestoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/milestone")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000/")
public class MilestoneController {
    private final MilestoneService milestoneService;

    @PostMapping("/search")
    public HttpResponse<?> getAllMilestones(@RequestBody SearchMilestoneRequest request) {
        return HttpResponse.ok(milestoneService.getAllMilestones(request));
    }

    @PostMapping("/create")
    public HttpResponse<?> create(@RequestBody CreateMilestoneRequest request) {
        return HttpResponse.ok(milestoneService.create(request));
    }

    @PutMapping("/update/{id}")
    public HttpResponse<?> update(@PathVariable("id") Integer id, @RequestBody UpdateMilestoneRequest request) {
        return HttpResponse.ok(milestoneService.update(id, request));
    }
    

    @GetMapping("/get-detail/{id}")
    public HttpResponse<?> getDetail(@PathVariable("id") Integer id){
        return HttpResponse.ok(milestoneService.getDetail(id));
    }

}
