package com.example.g2_se1630_swd392.controller;

import com.example.g2_se1630_swd392.dto.HttpResponse;
import com.example.g2_se1630_swd392.dto.assignment.request.CreateAssignmentRequest;
import com.example.g2_se1630_swd392.dto.assignment.request.ListAssignmentRequest;
import com.example.g2_se1630_swd392.dto.assignment.request.UpdateAssignmentRequest;
import com.example.g2_se1630_swd392.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/assignment")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000/")
public class AssignmentController {
    private final AssignmentService assignmentService;

    @PostMapping("/search")
    public HttpResponse<?> getAllAssignments(@RequestBody ListAssignmentRequest request) {
        return HttpResponse.ok(assignmentService.getAllAssignments(request));
    }

    @PostMapping("/create")
    public HttpResponse<?> create(@RequestBody CreateAssignmentRequest request) {
        return HttpResponse.ok(assignmentService.create(request));
    }

    @PutMapping("/update/{id}")
    public HttpResponse<?> update(@PathVariable("id") Integer id, @RequestBody UpdateAssignmentRequest request) {
        return HttpResponse.ok(assignmentService.update(id, request));
    }

    @GetMapping("/get-detail/{id}")
    public HttpResponse<?> getDetail(@PathVariable("id") Integer id) {
        return HttpResponse.ok(assignmentService.getDetail(id));
    }

}
