package com.example.g2_se1630_swd392.service.impl;

import com.example.g2_se1630_swd392.common.exception.NameAlreadyExistsException;
import com.example.g2_se1630_swd392.common.exception.RecordNotFoundException;
import com.example.g2_se1630_swd392.dto.systemSetting.request.SystemSettingRequest;
import com.example.g2_se1630_swd392.dto.systemSetting.request.ListSystemSettingRequest;
import com.example.g2_se1630_swd392.dto.systemSetting.response.ListSystemSettingResponse;
import com.example.g2_se1630_swd392.dto.systemSetting.response.SystemSettingResponse;
import com.example.g2_se1630_swd392.entity.SystemSetting;
import com.example.g2_se1630_swd392.mapper.SystemSettingMapper;
import com.example.g2_se1630_swd392.repository.SystemSettingRepository;
import com.example.g2_se1630_swd392.service.SystemSettingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@RequiredArgsConstructor
@CacheConfig(cacheNames = "SystemSetting")
public class SystemSettingServiceImpl implements SystemSettingService {

    private final SystemSettingRepository systemSettingRepository;
    private final SystemSettingMapper systemSettingMapper;

    @Override
    @Transactional
    public SystemSettingResponse create(Object objectRequest) {
        log.info("Creating system setting: ");
        var request = (SystemSettingRequest) objectRequest;
        var foundSystemSetting = systemSettingRepository.findByNameAndTypeAndActiveTrue(request.getName(), request.getType());
        if (foundSystemSetting != null) {
            throw new NameAlreadyExistsException("System Setting");
        }
        var saveSystemSetting = systemSettingMapper.convertCreateSystemSettingRequestToSystemSetting(request);
        systemSettingRepository.save(saveSystemSetting);
        return systemSettingMapper.convertSystemSettingToSystemSettingResponse(saveSystemSetting);
    }

    @Override
    @Transactional
    @CachePut(value = "systemSettingResponse", key = "'systemSetting' + #id")
    public SystemSettingResponse update(Integer id, Object objectRequest) {
        log.info("Updating system setting with id: " + id);
        var foundSystemSettingById = findSystemSettingById(id);
        var request = (SystemSettingRequest) objectRequest;
        var foundSystemSettingByName = systemSettingRepository.findByNameAndTypeForUpdate(request.getName(), foundSystemSettingById.getName(), request.getType());
        var saveSystemSetting = systemSettingMapper.convertUpdateSystemSettingRequestToSystemSetting(request, foundSystemSettingById);
        if (foundSystemSettingByName != null) {
            throw new NameAlreadyExistsException("System Setting");
        }
        systemSettingRepository.save(saveSystemSetting);
        return systemSettingMapper.convertSystemSettingToSystemSettingResponse(saveSystemSetting);
    }

    @Override
    @Cacheable(value = "systemSettingResponse", key = "'systemSetting' + #id")
    public SystemSettingResponse getDetail(Integer id) {
        log.info("Fetching system setting with id: " + id);
        SystemSetting foundSystemSetting = systemSettingRepository.findById(id).orElseThrow(
                () -> new RecordNotFoundException("System Setting")
        );
        return systemSettingMapper.convertSystemSettingToSystemSettingResponse(foundSystemSetting);
    }

    public SystemSetting findSystemSettingById(Integer id) {
        return systemSettingRepository.findById(id).orElseThrow(
                () -> new RecordNotFoundException("System Setting")
        );
    }

    @Override
    public void delete(Integer integer) {

    }

    @CachePut(value = "systemSettingResponse", key = "'systemSetting' + #id")
    @Override
    public SystemSettingResponse changeActive(Integer id) {
        log.info("Change status system setting with id: " + id);
        var foundSystemSetting = systemSettingRepository.findById(id).orElseThrow(
                () -> new RecordNotFoundException("System Setting")
        );
        var active = foundSystemSetting.getActive();
        foundSystemSetting.setActive(!active);
        systemSettingRepository.save(foundSystemSetting);
        return systemSettingMapper.convertSystemSettingToSystemSettingResponse(foundSystemSetting);
    }

    @Override
    public ListSystemSettingResponse getAllSystemSettings(ListSystemSettingRequest request) {
        log.info("Searching System Settings");
        request.validateInput();

        Pageable pageable;
        if (request.getOrderBy() == 1)
            pageable = PageRequest.of(request.getPageIndex(), request.getPageSize(), Sort.by(request.getSortBy()));
        else
            pageable = PageRequest.of(request.getPageIndex(), request.getPageSize(), Sort.by(request.getSortBy()).descending());

        Page<SystemSetting> semesters = systemSettingRepository.searchSystemSettings(request.getType(), request.getName(), request.getActive(), pageable);

        ListSystemSettingResponse response = new ListSystemSettingResponse();
        response.setTotalRecords(semesters.getTotalElements());
        response.setSystemSettingList(semesters.stream().map(systemSettingMapper::convertSystemSettingToSystemSettingResponse).toList());
        response.setPageIndex(request.getPageIndex());
        response.setPageSize(request.getPageSize());

        return response;
    }
}
