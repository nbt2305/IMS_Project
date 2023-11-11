package com.example.g2_se1630_swd392.mapper;

import com.example.g2_se1630_swd392.dto.subject.request.CreateSubjectRequest;
import com.example.g2_se1630_swd392.dto.subject.request.UpdateSubjectRequest;
import com.example.g2_se1630_swd392.dto.subject.response.CreateSubjectResponse;
import com.example.g2_se1630_swd392.dto.subject.response.SubjectResponse;
import com.example.g2_se1630_swd392.dto.subject.response.UpdateSubjectResponse;
import com.example.g2_se1630_swd392.entity.Subject;
import com.example.g2_se1630_swd392.entity.SystemSetting;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

@Data
@AllArgsConstructor
@Component
public class SubjectMapper {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    private static final ModelMapper modelMapper = new ModelMapper();
    public Subject convertCreateSubjectDtoToSubject(CreateSubjectRequest request) {
        return modelMapper.map(request, Subject.class);
    }
    public Subject convertUpdateSubjectRequestToSubject(UpdateSubjectRequest request, Integer id) {
        Subject response = modelMapper.map(request, Subject.class);
        response.setId(id);
        return response;
    }
    public CreateSubjectResponse convertSubjectToCreateSubjectResponse(Subject request) {
        CreateSubjectResponse response = new CreateSubjectResponse();
        response.setId(request.getId());
        response.setName(request.getName());
        response.setDescription(request.getDescription());
        return response;
    }
    public UpdateSubjectResponse convertSubjectToUpdateSubjectResponse(Subject request) {
        UpdateSubjectResponse response = new UpdateSubjectResponse();
        response.setName(request.getName());
        response.setDescription(request.getDescription());
        return response;
    }

    public SubjectResponse convertSubjectToSubjectResponse(Subject request) {
        SubjectResponse response = new SubjectResponse();
        response.setId(request.getId());
        response.setName(request.getName());
        response.setDescription(request.getDescription());
        response.setCreatedDate(request.getCreatedDate());
        response.setUpdatedDate(request.getUpdatedDate());
        return response;
    }
    public <T> List<T> convertList(List<?> sourceList, Class<T> targetType) {
        if(sourceList == null)
            return null;
        Type listType = new TypeToken<List<T>>() {}.getType();
        return modelMapper.map(sourceList, listType);
    }
}
