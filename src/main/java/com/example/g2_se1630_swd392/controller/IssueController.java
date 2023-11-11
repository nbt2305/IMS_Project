package com.example.g2_se1630_swd392.controller;

import com.example.g2_se1630_swd392.dto.HttpResponse;
import com.example.g2_se1630_swd392.dto.issue.request.BulkEditIssueRequest;
import com.example.g2_se1630_swd392.dto.issue.request.CreateIssueRequest;
import com.example.g2_se1630_swd392.dto.issue.request.SearchIssueRequest;
import com.example.g2_se1630_swd392.dto.issue.request.UpdateIssueRequest;
import com.example.g2_se1630_swd392.dto.issue.response.SearchIssueResponse;
import com.example.g2_se1630_swd392.service.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/issues")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000/")
public class IssueController {

    private final IssueService issueService;
    @PostMapping("/search")
    public HttpResponse<?> searchIssues(@RequestBody SearchIssueRequest request) {
        SearchIssueResponse response = issueService.searchIssues(request);
        return HttpResponse.ok(response);
    }

    @PostMapping("/create")
    public HttpResponse<?> createIssue(@RequestBody CreateIssueRequest request){
        return HttpResponse.ok(issueService.create(request));
    }

    @PutMapping("/update/{id}")
    public HttpResponse<?> update(@PathVariable("id") Integer id, @RequestBody UpdateIssueRequest request) {
        return HttpResponse.ok(issueService.update(id, request));
    }

    @GetMapping("/get-detail/{id}")
    public HttpResponse<?> getDetail(@PathVariable("id") Integer id){
        return HttpResponse.ok(issueService.getDetail(id));
    }

    @PostMapping("/bulk-edit")
    public HttpResponse<?> bulkEditIssue(@RequestBody BulkEditIssueRequest request){
        issueService.bulkEditIssue(request);
        return HttpResponse.ok(null);
    }

    @DeleteMapping("/delete/{id}")
    public HttpResponse<?> deleteIssue(@PathVariable("id") Integer id){
        issueService.delete(id);
        return HttpResponse.ok(null);
    }
}
