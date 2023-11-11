package com.example.g2_se1630_swd392.service;

import com.example.g2_se1630_swd392.dto.subject.request.ListSubjectRequest;
import com.example.g2_se1630_swd392.dto.subject.response.ListSubjectResponse;
import com.example.g2_se1630_swd392.entity.User;

public interface SubjectService extends BaseService<User, Integer>{
    ListSubjectResponse getAllSubject(ListSubjectRequest request);

}
