package com.example.g2_se1630_swd392.mapper;

import com.example.g2_se1630_swd392.dto.issue.request.CreateIssueRequest;
import com.example.g2_se1630_swd392.dto.issue.request.UpdateIssueRequest;
import com.example.g2_se1630_swd392.dto.issue.response.IssueResponse;
import com.example.g2_se1630_swd392.entity.Issue;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;
import java.lang.reflect.Type;
import java.util.List;

@Data
@Component
public class IssueMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    public IssueMapper(){
        modelMapper.addMappings(new PropertyMap<CreateIssueRequest, Issue>() {
            protected void configure() {
                map().setId(null);
            }
        });
    }

    public Issue convertCreateIssueRequestToIssue(CreateIssueRequest request, Type targetType) {
        if(request == null)
            return  null;

        return modelMapper.map(request, targetType);
    }

    public Issue convertUpdateIssueRequestToIssue(UpdateIssueRequest request, Type targetType) {
        if(request == null)
            return  null;

        return modelMapper.map(request, targetType);
    }

    public IssueResponse convertIssueToIssueResponse(Issue issue, Type targetType) {
        if(issue == null)
            return  null;

        return modelMapper.map(issue, targetType);
    }

    public <T> List<T> convertList(List<?> sourceList, Class<T> targetType) {
        if(sourceList == null)
            return null;
        Type listType = new TypeToken<List<T>>() {}.getType();
        return modelMapper.map(sourceList, listType);
    }

    public static void main(String[] args) {
        IssueMapper a = new IssueMapper();

//        Issue i = new Issue();
//        i.setId(1);
//        i.setTitle("aa");
//        i.setProject(new Project());
//        i.getProject().setId(1);
//        i.setIssueType(new IssueSetting());
//        i.getIssueType().setId(1);
//        i.setIssueStatus(new IssueSetting());
//        i.getIssueStatus().setId(2);
//        System.out.println(a.convertIssueToCreateIssueResponse(i, CreateIssueResponse.class));
        CreateIssueRequest r = new CreateIssueRequest();
        r.setTitle("a");
        r.setIssueTypeId(1);
        r.setMilestoneId(1);
        r.setProjectId(1);
        r.setProcessId(1);
        System.out.println(a.convertCreateIssueRequestToIssue(r, Issue.class).getProcess());
//        UpdateIssueRequest request = new UpdateIssueRequest();
//        request.setTitle("issue 2");
//        request.setId(1);
//        request.setProjectId(1);
//        request.setIssueTypeId(1);
//        request.setMilestoneId(1);
//        Issue i = a.convertUpdateIssueRequestToIssue(request, Issue.class);
//        System.out.println(i.getProject()+"\n"+i.getIssueType()+"\n"+i.getMilestone()+"\n"+i.getLabel());
    }
}
