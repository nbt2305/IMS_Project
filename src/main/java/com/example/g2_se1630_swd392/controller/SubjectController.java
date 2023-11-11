package com.example.g2_se1630_swd392.controller;

import com.example.g2_se1630_swd392.dto.HttpResponse;
import com.example.g2_se1630_swd392.dto.subject.request.CreateSubjectRequest;
import com.example.g2_se1630_swd392.dto.subject.request.ListSubjectRequest;
import com.example.g2_se1630_swd392.dto.subject.request.UpdateSubjectRequest;
import com.example.g2_se1630_swd392.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/subject")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000/")
public class SubjectController {
    private final SubjectService subjectService;

    @PostMapping("/search")
    public HttpResponse<?> getAllSubject(@RequestBody ListSubjectRequest request) {
        return HttpResponse.ok(subjectService.getAllSubject(request));
    }

    @PostMapping("/create")
    public HttpResponse<?> create(@RequestBody CreateSubjectRequest request) {
        return HttpResponse.ok(subjectService.create(request));
    }

    @PutMapping("/update/{id}")
    public HttpResponse<?> update(@PathVariable("id") Integer id, @RequestBody UpdateSubjectRequest request) {
        return HttpResponse.ok(subjectService.update(id, request));
    }

    @GetMapping("/get-detail/{id}")
    public HttpResponse<?> getDetail(@PathVariable("id") Integer id) {
        return HttpResponse.ok(subjectService.getDetail(id));
    }
}
