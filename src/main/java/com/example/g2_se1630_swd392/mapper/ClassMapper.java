package com.example.g2_se1630_swd392.mapper;

import com.example.g2_se1630_swd392.dto._class.request.CreateClassRequest;
import com.example.g2_se1630_swd392.dto._class.request.UpdateClassRequest;
import com.example.g2_se1630_swd392.dto._class.response.ClassResponse;
import com.example.g2_se1630_swd392.dto._class.response.ClassStudentResponse;
import com.example.g2_se1630_swd392.entity.*;
import com.example.g2_se1630_swd392.entity.Class;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@AllArgsConstructor
@Component
public class ClassMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    public Class convertCreateClassRequestToClass(CreateClassRequest request,
                                                  User teacher,
                                                  Subject subject,
                                                  SystemSetting semester) {
        Class response = new Class();
        response.setName(request.getName());
        response.setDescription(request.getDescription());
        response.setTeacher(teacher);
        response.setSubject(subject);
        response.setSemester(semester);
        return response;
    }

    public Class convertUpdateClassRequestToClass(UpdateClassRequest request,
                                                  User teacher,
                                                  Subject subject,
                                                  SystemSetting semester) {
        Class response = new Class();
        response.setName(request.getName());
        response.setDescription(request.getDescription());
        response.setTeacher(teacher);
        response.setSubject(subject);
        response.setSemester(semester);
        return response;
    }

    public ClassResponse convertClassToClassResponse(Class request, List<Project> projects) {
        ClassResponse response = new ClassResponse();
        response.setId(request.getId());
        response.setName(request.getName());
        response.setDescription(request.getDescription());
        response.setProjects(projects);
        response.setTeacher(request.getTeacher());
        response.setSubject(request.getSubject());
        response.setSemester(request.getSemester());
        return response;
    }

    public ClassStudentResponse convertClassStudentToClassStudentResponse(Class request, List<User> users) {
        ClassStudentResponse response = new ClassStudentResponse();
        response.setId(request.getId());
        response.setName(request.getName());
        response.setStudents(users);
        return response;
    }


}
