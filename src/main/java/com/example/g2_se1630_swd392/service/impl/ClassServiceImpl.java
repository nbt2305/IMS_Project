package com.example.g2_se1630_swd392.service.impl;

import com.example.g2_se1630_swd392.common.exception.NameAlreadyExistsException;
import com.example.g2_se1630_swd392.common.exception.RecordNotFoundException;
import com.example.g2_se1630_swd392.dto._class.request.CreateClassRequest;
import com.example.g2_se1630_swd392.dto._class.request.CreateClassStudentRequest;
import com.example.g2_se1630_swd392.dto._class.request.ListClassRequest;
import com.example.g2_se1630_swd392.dto._class.request.UpdateClassRequest;
import com.example.g2_se1630_swd392.dto._class.response.ClassResponse;
import com.example.g2_se1630_swd392.dto._class.response.ClassStudentResponse;
import com.example.g2_se1630_swd392.dto._class.response.ListClassResponse;
import com.example.g2_se1630_swd392.dto.user.response.UserResponse;
import com.example.g2_se1630_swd392.entity.Class;
import com.example.g2_se1630_swd392.entity.ClassStudent;
import com.example.g2_se1630_swd392.entity.User;
import com.example.g2_se1630_swd392.mapper.ClassMapper;
import com.example.g2_se1630_swd392.mapper.UserMapper;
import com.example.g2_se1630_swd392.repository.*;
import com.example.g2_se1630_swd392.service.ClassService;
import com.example.g2_se1630_swd392.common.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassServiceImpl implements ClassService {
    private final ClassRepository classRepository;
    private final ClassMapper classMapper;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ClassStudentRepository classStudentRepository;
    private final SubjectRepository subjectRepository;
    private final SystemSettingRepository systemSettingRepository;

    // TODO: 10/18/2023 Thêm phần semesterId và subjectId khi tạo class 
    @Override
    public ClassResponse create(Object objectRequest) {
        var request = (CreateClassRequest) objectRequest;

        var subjectIdRequest = request.getSubjectId();
        var semesterIdRequest = request.getSemesterId();

        var foundSubject = subjectRepository.findById(subjectIdRequest).orElseThrow(
                () -> new RecordNotFoundException("Subject")
        );
        var foundSemester = systemSettingRepository.findByIdAndTypeAndActiveTrue(
                semesterIdRequest, Constants.SystemConfig.SEMESTER
        ).orElseThrow(() -> new RecordNotFoundException("System Setting"));
        var foundClass = classRepository.findByNameAndSubjectIdAndSemesterId(
                request.getName(), subjectIdRequest, semesterIdRequest
        );
        if (foundClass != null) {
            throw new NameAlreadyExistsException("Class");
        }


        var teacher = userRepository.findByIdAndRoleId(request.getTeacherId(), Constants.Role.TEACHER);
        if (teacher != null) {
            var saveClass = classMapper.convertCreateClassRequestToClass(
                    request,
                    teacher,
                    foundSubject,
                    foundSemester);
            classRepository.save(saveClass);
            var projects = projectRepository.findAllBy_class(saveClass);
            return classMapper.convertClassToClassResponse(saveClass, projects);
        }
        throw new RecordNotFoundException("Teacher");
    }

    @Override
    public ClassResponse update(Integer id, Object objectRequest) {
        Class foundClassById = findClassById(id);
        var request = (UpdateClassRequest) objectRequest;
        var teacher = userRepository.findByIdAndRoleId(request.getTeacherId(), Constants.Role.TEACHER);

        var subjectIdRequest = request.getSubjectId();
        var semesterIdRequest = request.getSemesterId();

        var foundSubject = subjectRepository.findById(subjectIdRequest).orElseThrow(
                () -> new RecordNotFoundException("Subject")
        );
        var foundSemester = systemSettingRepository.findByIdAndTypeAndActiveTrue(
                semesterIdRequest, Constants.SystemConfig.SEMESTER
        ).orElseThrow(() -> new RecordNotFoundException("System Setting"));

        if (!foundClassById.getName().equals(request.getName())) {
            var foundClass = classRepository.findByNameAndSubjectIdAndSemesterId(
                    request.getName(), subjectIdRequest, semesterIdRequest
            );
            if (foundClass != null) {
                throw new NameAlreadyExistsException("Class");
            }
        }

        var saveClass = classMapper.convertUpdateClassRequestToClass(
                request,
                teacher,
                foundSubject,
                foundSemester);
        saveClass.setId(id);
        var foundSemesterByName = classRepository.findByNameForUpdate(saveClass.getName(), foundClassById.getName());
        if (foundSemesterByName != null) {
            throw new NameAlreadyExistsException("Class");
        }
        classRepository.save(saveClass);
        var projects = projectRepository.findAllBy_class(saveClass);
        return classMapper.convertClassToClassResponse(saveClass, projects);
    }

    @Override
    public ClassResponse getDetail(Integer id) {
        var foundClass = findClassById(id);
        var projects = projectRepository.findAllBy_class(foundClass);
        return classMapper.convertClassToClassResponse(foundClass, projects);
    }

    @Override
    public void delete(Integer integer) {

    }

    public Class findClassById(Integer id) {
        Class foundClass = classRepository.findByIdAndActiveTrue(id);
        if (foundClass != null) {
            return foundClass;
        }
        throw new RecordNotFoundException("Class");
    }

    @Override
    public ListClassResponse getAllClasses(ListClassRequest request) {
        request.validateInput();

        Pageable pageable;
        if (request.getOrderBy() == 1)
            pageable = PageRequest.of(request.getPageIndex(), request.getPageSize(), Sort.by(request.getSortBy()));
        else
            pageable = PageRequest.of(request.getPageIndex(), request.getPageSize(), Sort.by(request.getSortBy()).descending());

        Page<Class> classes = classRepository.findAllClasses(
                request.getName(),
                request.getTeacherId(),
                request.getSubjectId(),
                request.getSemesterId(),
                pageable);

        var response = new ListClassResponse();
        List<ClassResponse> classResponses = new ArrayList<>();
        response.setTotalRecords(classes.getTotalElements());
        for (Class _class : classes) {
            var projects = projectRepository.findAllBy_class(_class);
            var classResponse = classMapper.convertClassToClassResponse(_class, projects);
            classResponses.add(classResponse);
        }
        response.setClassList(classResponses);
        response.setPageIndex(request.getPageIndex());
        response.setPageSize(request.getPageSize());

        return response;
    }

    @Override
    @Transactional
    public ClassStudentResponse createClassStudent(CreateClassStudentRequest request) {
        var classId = request.getClassId();
        var studentIds = request.getStudents();
        var projectId = request.getProjectId();

        Class foundClass = findClassById(classId);

        if (projectId != null) {
            var foundProject = projectRepository.findByIdAndClassId(projectId, classId);
            if (foundProject == null) {
                throw new RecordNotFoundException("Project");
            }
        }

        classStudentRepository.deleteAllByClassIdAndStudentIdIn(
                classId,
                studentIds);
        int count = userRepository.countByIdInAndRoleId(studentIds, Constants.Role.STUDENT);
        if (count != studentIds.size()) {
            throw new RecordNotFoundException("Student");
        }
        for (Integer studentId : studentIds) {
            ClassStudent classStudent = new ClassStudent();
            classStudent.setClassId(classId);
            classStudent.setStudentId(studentId);
            classStudent.setProjectId(projectId);
            classStudentRepository.save(classStudent);
        }
        List<User> students = userRepository.findAllByIdIn(studentIds);
        return classMapper.convertClassStudentToClassStudentResponse(foundClass, students);
    }

    @Override
    public ClassStudentResponse getAllStudentsOfClass(Integer id) {
        Class foundClass = findClassById(id);
        List<Integer> studentIds = userRepository.getAllStudentsByClassId(id)
                .stream()
                .map(User::getId
                ).toList();
        List<User> students = userRepository.findAllByIdIn(studentIds);
        return classMapper.convertClassStudentToClassStudentResponse(foundClass, students);
    }

    @Override
    public List<UserResponse> getAllStudentHaveNoProject(Integer classId) {
        List<Integer> studentIds = classStudentRepository.findByClassIdAndProjectIdNull(classId)
                .stream().map(ClassStudent::getStudentId).toList();
        List<User> users = userRepository.findAllByIdIn(studentIds);
        return users.stream().map(userMapper::convertUserToUserResponse).toList();
    }
}
