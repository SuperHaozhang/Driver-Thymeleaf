package com.cheer.driver.web.controller;

import com.cheer.driver.web.exception.MyException1;
import com.cheer.driver.web.exception.MyException2;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
@ControllerAdvice
public class GlobalException implements HandlerExceptionResolver {
    @ExceptionHandler({
            HttpRequestMethodNotSupportedException.class,
            HttpMediaTypeNotSupportedException.class,
            HttpMediaTypeNotAcceptableException.class,
            MissingServletRequestParameterException.class,
            ServletRequestBindingException.class,
            ConversionNotSupportedException.class,
            TypeMismatchException.class,
            HttpMessageNotReadableException.class,
            HttpMessageNotWritableException.class,
            MethodArgumentNotValidException.class,
            MissingServletRequestPartException.class,
            BindException.class,
            NoHandlerFoundException.class})
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        ModelAndView mv = new ModelAndView(); //判断不同异常类型，做不同视图跳转
        if (e instanceof ArithmeticException) {
            mv.setViewName("error/error");
        }
        if (e instanceof NullPointerException) {
            mv.setViewName("error/error");
        }
        if (e instanceof MyException1) {
            mv.setViewName("error/MyException");
        }
        if (e instanceof MyException2) {
            mv.setViewName("error/MyException1");
        }
        else {
            mv.setViewName("error/404");
        }
        System.out.println("这个异常是："+e.toString());
        mv.addObject("error",e.getMessage());
        mv.addObject("cause",e.getCause());
        return mv;
    }
}
