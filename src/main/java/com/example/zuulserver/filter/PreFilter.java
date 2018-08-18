package com.example.zuulserver.filter;

import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;

import static com.netflix.zuul.context.RequestContext.getCurrentContext;

/**
 * 前置过滤器：转发请求时先校验
 */
@Component
public class PreFilter extends ZuulFilter {

    private Logger logger = LoggerFactory.getLogger(getClass());

    //filterType有四种，pre：前置过滤器 post：微服务响应返回数据后的过滤器
    @Override
    public String filterType() {
        return "pre";
    }

    //过滤器的执行顺序 数字最小的最先执行
    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return false;
    }

    /**
     * 当不满足过滤验证条件时，返回错误信息
     * @param ctx 请求上下文对象
     * @param map 返回的错误信息
     * @return
     */
    private Object setErrorMsg(RequestContext ctx, Map<String,Object> map){
        String resBody = JSONObject.toJSONString(map);
        HttpServletResponse response = ctx.getResponse();
        response.setHeader("contentType","application/json;charset=utf-8");
        logger.info("返回明文类容："+resBody);
        ctx.setResponseBody(resBody);
        ctx.setSendZuulResponse(false);
        return null;
    }

    //过滤器的主体方法
    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String regexJson = "^application/json.*";
        String contentType = request.getContentType();
        String header = request.getHeader("token");
        if(header==null|| header.equals("")){
            Map<String,Object> map = new HashMap<>();
            map.put("code",500);
            map.put("Msg","token为空");
            return this.setErrorMsg(ctx,map);
        }
        return null;
    }
}
