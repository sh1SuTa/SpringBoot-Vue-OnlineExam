package com.exam;

import com.exam.controller.LoginController;
import com.exam.entity.Admin;
import com.exam.entity.Login;
import com.exam.serviceimpl.LoginServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;



import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoginController.class)
@RunWith(SpringRunner.class)

public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoginServiceImpl loginService;
    @Test
    public void testAdminLoginSuccess() throws Exception {
        Login login = new Login(9527, "123456");
        Admin admin = new Admin();
        admin.setAdminId(9527);
        admin.setPwd("123456");
        admin.setAdminName("超级管理员");

        Mockito.when(loginService.adminLogin(9527, "123456")).thenReturn(admin);

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(login)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("请求成功"))
                .andExpect(jsonPath("$.data.adminName").value("超级管理员"));
    }



    @Test
    public void testLoginFailure() throws Exception {
        Login login = new Login(99999, "wrong");

        Mockito.when(loginService.adminLogin(99999, "wrong")).thenReturn(null);
        Mockito.when(loginService.teacherLogin(99999, "wrong")).thenReturn(null);
        Mockito.when(loginService.studentLogin(99999, "wrong")).thenReturn(null);

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(login)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("登录失败"))
                .andExpect(jsonPath("$.data").doesNotExist());
    }
    @Test
    public void testEmptyPassword() throws Exception {
        Login login = new Login(9527, "");

        Mockito.when(loginService.adminLogin(9527, "")).thenReturn(null);

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(login)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("登录失败"));
        System.out.println("登录失败");
    }
    @Test
    public void testNullUsername() throws Exception {
        Login login = new Login(null, "123456");

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(login)))
                .andExpect(status().isBadRequest()); // 或抛出异常
    }


}

