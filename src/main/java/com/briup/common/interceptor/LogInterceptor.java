package com.briup.common.interceptor;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.mvc.condition.RequestConditionHolder;
import org.springframework.web.servlet.support.RequestContext;

/*
@Aspect
@Component
public class LogInterceptor  {

    private Logger logger = LoggerFactory.getLogger(LogInterceptor.class);


    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping) || @annotation(org.springframework.web.bind.annotation.PostMapping) || @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void logger(){};

    @Before("logger()")
    public void dologger(JoinPoint point) throws JsonProcessingException {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        Object[] args = point.getArgs();
        StringBuffer sb = new StringBuffer();
        ObjectMapper json = new ObjectMapper();

        for (Object arg : args){
            String s = json.writeValueAsString(arg);
            sb.append("  "+s);
        }
        logger.info(sb.toString());
    }
}
*/
