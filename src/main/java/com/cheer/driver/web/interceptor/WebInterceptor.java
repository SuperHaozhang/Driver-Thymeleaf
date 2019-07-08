package com.cheer.driver.web.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 拦截器
@Component
public class WebInterceptor implements HandlerInterceptor {

    // 目标方法的前置处理 返回值为false表示请求到此结束，不在往下执行，
    // 反之true，继续执行（交给下一个拦截器的前置方法处理或者目标处理方法）
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        return true;
    }

    // 目标方法的后置处理
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    // 在完成之后执行 这个方法在 DispatcherServlet 完全处理完请 求后被调用，可以在该方法中进行一些资源清理的操作。
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
