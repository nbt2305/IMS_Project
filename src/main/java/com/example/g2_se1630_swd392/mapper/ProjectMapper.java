package com.example.g2_se1630_swd392.mapper;

import com.example.g2_se1630_swd392.dto.project.request.CreateProjectRequest;
import com.example.g2_se1630_swd392.dto.project.request.UpdateProjectRequest;
import com.example.g2_se1630_swd392.dto.project.response.ProjectResponse;
import com.example.g2_se1630_swd392.entity.Project;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

@Data
@Component
public class ProjectMapper {
    private static final ModelMapper modelMapper = new ModelMapper();
    public ProjectMapper() {
        modelMapper.addMappings(new PropertyMap<CreateProjectRequest, Project>() {
            protected void configure() {
                map().setId(null);
            }
        });
    }

    public Project convertCreateProjectRequestToProject(CreateProjectRequest request, Type targetType){
        if(request == null)
            return  null;

        return modelMapper.map(request, targetType);
    }

    public Project convertUpdateProjectRequestToProject(UpdateProjectRequest request, Type targetType) {
        if(request == null)
            return  null;

        return modelMapper.map(request, targetType);
    }

    public ProjectResponse convertProjectToCreateProjectResponse(Project project, Type targetType) {
        if(project == null)
            return  null;

        return modelMapper.map(project, targetType);
    }

    public ProjectResponse convertProjectToProjectResponse(Project project, Type targetType) {
        if(project == null)
            return  null;

        return modelMapper.map(project, targetType);
    }

    public <T> List<T> convertList(List<?> sourceList, Class<T> targetType) {
        if(sourceList == null)
            return null;
        Type listType = new TypeToken<List<T>>() {}.getType();
        return modelMapper.map(sourceList, listType);
    }
}
