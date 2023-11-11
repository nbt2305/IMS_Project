package com.example.g2_se1630_swd392.annotation;

import com.example.g2_se1630_swd392.common.exception.PermissionDeniedException;
import com.example.g2_se1630_swd392.entity.Permission;
import com.example.g2_se1630_swd392.repository.PermissionRepository;
import com.example.g2_se1630_swd392.service.UserService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;


@Aspect
@Component
@RequiredArgsConstructor
public class AccessPermissionInterceptor {
//    private final UserServiceImpl userServiceImpl;
    private final UserService userService;
    private final PermissionRepository permissionRepository;

//    public AccessPermissionInterceptor(UserServiceImpl userServiceImpl, PermissionRepository permissionRepository) {
//        this.userServiceImpl = userServiceImpl;
//        this.permissionRepository = permissionRepository;
//    }

    @Before("@annotation(accessPermission)")
    public void checkAccess(AccessPermission accessPermission) {
        var currentUser = userService.getCurrentUser();
        var permissions = permissionRepository.getAllByRoleId(currentUser.getRoleId()).stream().map(Permission::getName).toList();
        String permissionRequired = accessPermission.permissionRequired();
        if (!permissions.contains(permissionRequired)) {
            throw new PermissionDeniedException();
        }
    }
}
