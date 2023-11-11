package com.example.g2_se1630_swd392.service;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Trung Nguyễn Bá
 * @created 9/27/2023
 * @project IMS_G2_SWD392
 */
@Service
public interface BaseService<T, ID> {
    Object create(Object request);

    Object update(ID id, Object request);

    Object getDetail(ID id);

    void delete(ID id);

}
