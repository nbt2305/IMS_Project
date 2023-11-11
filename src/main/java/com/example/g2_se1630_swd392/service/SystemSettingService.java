package com.example.g2_se1630_swd392.service;

import com.example.g2_se1630_swd392.dto.systemSetting.request.ListSystemSettingRequest;
import com.example.g2_se1630_swd392.dto.systemSetting.response.ListSystemSettingResponse;
import com.example.g2_se1630_swd392.dto.systemSetting.response.SystemSettingResponse;
import com.example.g2_se1630_swd392.entity.SystemSetting;

/**
 * @author Trung Nguyễn Bá
 * @created 9/27/2023
 * @project IMS_G2_SWD392
 */
public interface SystemSettingService extends BaseService<SystemSetting, Integer>{
    SystemSettingResponse changeActive(Integer id);

    ListSystemSettingResponse getAllSystemSettings(ListSystemSettingRequest request);
}
