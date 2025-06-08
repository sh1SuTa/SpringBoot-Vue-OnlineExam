package com.exam.interceptor;

import com.exam.util.JwtUtil;
import com.exam.util.ThreadLocalUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //获取令牌
        String token = request.getHeader("Authorization");
        //验证token
        try {
            //new 一个template对象
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            //从redis获取token
            String redisToken = operations.get(token);
            //找不到token
            if (redisToken==null){
                //token失效
                throw new RuntimeException("未登录");
            }
            //找到的token解析出来
            Map<String, Object> claims = JwtUtil.parseToken(token);
            //把业务数据存储到ThreadLocal中
            ThreadLocalUtil.set(claims);
            //已登录放行
            return true;
        } catch (Exception e) {
            //http响应码401
            response.setStatus(401);
            //未登录不给予放行
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //清空ThreadLocal中的token数据
        ThreadLocalUtil.remove();
    }






}