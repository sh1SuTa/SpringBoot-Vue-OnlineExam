package com.exam.controller;

import com.exam.entity.Admin;
import com.exam.entity.ApiResult;
import com.exam.serviceimpl.AdminServiceImpl;
import com.exam.util.ApiResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController {
    @Autowired
    private AdminServiceImpl adminService;

    //查询管理员
    @GetMapping("/admins")
    public ApiResult findAll(){
        System.out.println("查询全部");
        return ApiResultHandler.success(adminService.findAll());
    }

    @GetMapping("/admin/{adminId}")
    public ApiResult findById(@PathVariable("adminId") Integer adminId){
        System.out.println("根据ID查找");
        return ApiResultHandler.success(adminService.findById(adminId));
    }
    //删除管理员
    @DeleteMapping("/admin/{adminId}")
    public ApiResult deleteById(@PathVariable("adminId") Integer adminId){
        adminService.deleteById(adminId);
        return ApiResultHandler.success();
    }
    //更新管理员
    @PutMapping("/admin/{adminId}")
    public ApiResult update(@PathVariable("adminId") Integer adminId, Admin admin){
        return ApiResultHandler.success(adminService.update(admin));
    }
    //TODO添加管理员
    @PostMapping("/admin")
    public ApiResult add(Admin admin){
        return ApiResultHandler.success(adminService.add(admin));
    }
}
