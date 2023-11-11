package com.example.g2_se1630_swd392.mapper;

import com.example.g2_se1630_swd392.dto.issue_setting.request.CreateIssueSettingRequest;
import com.example.g2_se1630_swd392.dto.issue_setting.request.UpdateIssueSettingRequest;
import com.example.g2_se1630_swd392.dto.issue_setting.response.IssueSettingResponse;
import com.example.g2_se1630_swd392.entity.IssueSetting;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class IssueSettingMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    public IssueSettingMapper() {
        modelMapper.addMappings(new PropertyMap<CreateIssueSettingRequest, IssueSetting>() {
            protected void configure() {
                map().setId(null);
            }
        });
    }

    public IssueSetting convertCreateIssueTypeRequestToIssueType(CreateIssueSettingRequest request, Type targetType){
        if(request == null)
            return  null;

        return modelMapper.map(request, targetType);
    }

    public IssueSetting convertUpdateIssueTypeRequestToIssueType(UpdateIssueSettingRequest request, Type targetType) {
        if(request == null)
            return  null;

        return modelMapper.map(request, targetType);
    }

    public IssueSettingResponse convertIssueTypeToIssueTypeResponse(IssueSetting issueType, Type targetType) {
        if(issueType == null)
            return  null;

        return modelMapper.map(issueType, targetType);
    }

    public List<IssueSettingResponse> convertList(List<IssueSetting> sourceList) {
        if (sourceList == null)
            return null;

        List<IssueSettingResponse> responseList = new ArrayList<>();

        for (IssueSetting issueSetting : sourceList) {
            IssueSettingResponse response = modelMapper.map(issueSetting, IssueSettingResponse.class);
            responseList.add(response);
        }

        return responseList;
    }

    public static void main(String[] args) {
        IssueSettingMapper map = new IssueSettingMapper();
        IssueSetting a = new IssueSetting();
        a.setName("aasx");
        map.convertIssueTypeToIssueTypeResponse(a, IssueSettingResponse.class);
    }
}
