package com.example.g2_se1630_swd392.controller;

import com.example.g2_se1630_swd392.dto.HttpResponse;
import com.example.g2_se1630_swd392.dto.systemSetting.request.SystemSettingRequest;
import com.example.g2_se1630_swd392.dto.systemSetting.request.ListSystemSettingRequest;
import com.example.g2_se1630_swd392.service.SystemSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/system-setting")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000/")
public class SystemSettingController {
    private final SystemSettingService systemSettingService;

//    @AccessPermission(permissionRequired = Constants.Permission.MANAGE_SYSTEM_SETTINGS)
    @PostMapping("/search")
    public HttpResponse<?> getAllSemesters(@RequestBody ListSystemSettingRequest request) {
        return HttpResponse.ok(systemSettingService.getAllSystemSettings(request));
    }

//    @AccessPermission(permissionRequired = Constants.Permission.MANAGE_SYSTEM_SETTINGS)
    @PostMapping("/create")
    public HttpResponse<?> create(@RequestBody SystemSettingRequest request) {
        return HttpResponse.ok(systemSettingService.create(request));
    }

//    @AccessPermission(permissionRequired = Constants.Permission.MANAGE_SYSTEM_SETTINGS)
    @PutMapping("/update/{id}")
    public HttpResponse<?> update(@PathVariable("id") Integer id, @RequestBody SystemSettingRequest request) {
        return HttpResponse.ok(systemSettingService.update(id, request));
    }

//    @AccessPermission(permissionRequired = Constants.Permission.MANAGE_SYSTEM_SETTINGS)
    @PutMapping("/change-active/{id}")
    public HttpResponse<?> changeActive(@PathVariable("id") Integer id) {
        return HttpResponse.ok(systemSettingService.changeActive(id));
    }

//    @AccessPermission(permissionRequired = Constants.Permission.MANAGE_SYSTEM_SETTINGS)
    @GetMapping("/get-detail/{id}")
    public HttpResponse<?> getDetail(@PathVariable("id") Integer id){
        return HttpResponse.ok(systemSettingService.getDetail(id));
    }

}
