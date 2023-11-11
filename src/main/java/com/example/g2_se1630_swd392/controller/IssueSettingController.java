package com.example.g2_se1630_swd392.controller;

import com.example.g2_se1630_swd392.dto.HttpResponse;
import com.example.g2_se1630_swd392.dto.issue_setting.request.CreateIssueSettingRequest;
import com.example.g2_se1630_swd392.dto.issue_setting.request.SearchIssueSettingRequest;
import com.example.g2_se1630_swd392.dto.issue_setting.request.UpdateIssueSettingRequest;
import com.example.g2_se1630_swd392.dto.issue_setting.response.SearchIssueSettingResponse;
import com.example.g2_se1630_swd392.service.IssueSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/issue-types")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000/")
public class IssueSettingController {
    private final IssueSettingService issueTypeService;
    @PostMapping("/search")
    public HttpResponse<?> searchIssueType(@RequestBody SearchIssueSettingRequest request) {
        SearchIssueSettingResponse response = issueTypeService.searchIssueType(request);
        return HttpResponse.ok(response);
    }
    @PostMapping("/create")
    public HttpResponse<?> createIssueType(@RequestBody CreateIssueSettingRequest request){
        return HttpResponse.ok(issueTypeService.create(request));
    }

    @PutMapping("/update/{id}")
    public HttpResponse<?> updateIssueType(@PathVariable("id") Integer id, @RequestBody UpdateIssueSettingRequest request) {
        return HttpResponse.ok(issueTypeService.update(id, request));
    }

    @GetMapping("/get-detail/{id}")
    public HttpResponse<?> getDetail(@PathVariable("id") Integer id){
        return HttpResponse.ok(issueTypeService.getDetail(id));
    }
    @PutMapping("/change-active/{id}")
    public HttpResponse<?> changeActive(@PathVariable("id") Integer id) {
        return HttpResponse.ok(issueTypeService.changeActive(id));
    }
}
