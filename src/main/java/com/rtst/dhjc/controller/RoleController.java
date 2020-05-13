package com.rtst.dhjc.controller;

import com.rtst.dhjc.bean.BaseResult;
import com.rtst.dhjc.entity.systemInfo.Permission;
import com.rtst.dhjc.entity.systemInfo.Role;
import com.rtst.dhjc.entity.systemInfo.RolePermission;
import com.rtst.dhjc.service.serviceImpl.systemInfo.AuthServiceImpl;
import com.rtst.dhjc.service.serviceImpl.systemInfo.RoleServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 功能模块(角色控制器)
 *
 * @Author white Liu
 * @Date 2020/4/29 11:16
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/role")
@Api(tags = "角色信息管理")
public class RoleController {
    @Autowired
    RoleServiceImpl roleService;
    @Autowired
    AuthServiceImpl authService;
    /**
     * 查询角色列表
     * @return
     */
    @GetMapping("/roleList")
    @ApiOperation(value="查询所有角色列表")
    public BaseResult roleList(){
        List<Role> roles = roleService.roleList();
        return BaseResult.ok().put("data",roles);
    }

    /**
     * 根据角色ID查询页面权限
     * @return
     */
    @PostMapping("/findPermissionByRoleId")
    @ApiOperation(value="根据角色ID查询当前角色的页面权限")
    public BaseResult findPermissionByRoleId(@RequestBody @ApiParam(name="角色ID",value="roleId",required = true) Role role){
        List<Permission>  permissions = authService.findAuthByRoleId(role);
        List<Permission> permissionsAll = authService.findAuthAll();
        //先查询出所有的页面权限，再查询出角色拥有的权限，利用for循环判断该角色是否拥有该权限，有就给一个状态值state = 1
        for(int i=0;i<permissionsAll.size();i++){
            for(int j=i;j<permissions.size();j++){
                if(permissionsAll.get(i).getPermissionId()==permissions.get(j).getPermissionId()){
                    permissionsAll.get(i).setState(1);
                }
            }
        }
        permissionsAll.remove(0);
        return BaseResult.ok().put("data",permissionsAll);
    }

    /**
     * 修改角色权限
     * @param rolePermissions
     * @return
     */
    @PostMapping("/updatePermission")
    @ApiOperation(value="修改用户权限")
    public BaseResult updatePermission(@RequestBody List<RolePermission> rolePermissions){
        int refNum = authService.updatePermission(rolePermissions);
        if(refNum>0){
            return BaseResult.ok("修改成功!");
        }
        return BaseResult.error(400,"修改失败！");
    }
}
