package com.example.g2_se1630_swd392.controller;

import com.example.g2_se1630_swd392.dto.HttpResponse;
import com.example.g2_se1630_swd392.dto._class.request.CreateClassRequest;
import com.example.g2_se1630_swd392.dto._class.request.CreateClassStudentRequest;
import com.example.g2_se1630_swd392.dto._class.request.ListClassRequest;
import com.example.g2_se1630_swd392.dto._class.request.UpdateClassRequest;
import com.example.g2_se1630_swd392.service.ClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/class")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000/")
public class ClassController {
    private final ClassService classService;

    @PostMapping("/search")
    public HttpResponse<?> getAllClasses(@RequestBody ListClassRequest request) {
        return HttpResponse.ok(classService.getAllClasses(request));
    }

    @PostMapping("/create")
    public HttpResponse<?> create(@RequestBody CreateClassRequest request) {
        return HttpResponse.ok(classService.create(request));
    }

    @PutMapping("/update/{id}")
    public HttpResponse<?> update(@PathVariable("id") Integer id, @RequestBody UpdateClassRequest request) {
        return HttpResponse.ok(classService.update(id, request));
    }

    @GetMapping("/get-detail/{id}")
    public HttpResponse<?> getDetail(@PathVariable("id") Integer id) {
        return HttpResponse.ok(classService.getDetail(id));
    }

    // Class - Student
    @PostMapping("/create/class-student")
    public HttpResponse<?> createClassStudent(@RequestBody CreateClassStudentRequest request) {
        return HttpResponse.ok(classService.createClassStudent(request));
    }

    @GetMapping("/get-detail/{id}/students")
    public HttpResponse<?> getStudents(@PathVariable("id") Integer id) {
        return HttpResponse.ok(classService.getAllStudentsOfClass(id));
    }

    @GetMapping("/get-students-have-no-project/{id}")
    public HttpResponse<?> getStudentsHaveNoProject(@PathVariable("id") Integer id) {
        return HttpResponse.ok(classService.getAllStudentHaveNoProject(id));
    }
}
