package com.gnq.base.shiro;

import com.alibaba.fastjson.JSONObject;
import com.gnq.base.enums.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
public class LimitAuthenticationFilter extends AuthenticationFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        boolean allowed = super.isAccessAllowed(request, response, mappedValue);
        log.info("LimitAuthenticationFilter isAccessAllowed");
        log.info("测试过滤：{}", request);
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        String requestUrl = httpServletRequest.getRequestURI();
        String ip = getIPAddress(httpServletRequest);
        log.info("请求ip:{}", ip);
        if(!allowed) {
            //跨域问题，前端一个接口会发送两个请求。一个OPTIONS请求，一个post/get请求，需要过滤OPTIONS
            if (httpServletRequest.getMethod().toUpperCase().equals("OPTIONS")) {
                return true;
            }
            log.info("请求url: {}", requestUrl);
        }
        log.info("请求url: {}", requestUrl);
        return allowed;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        log.info("LimitAuthenticationFilter onAccessDenied");
        return false;
    }

    public static String getIPAddress(HttpServletRequest request) {
        String ip = null;    //X-Forwarded-For：Squid 服务代理
        String ipAddresses = request.getHeader("X-Forwarded-For");
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {        //Proxy-Client-IP：apache 服务代理
            ipAddresses = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {        //WL-Proxy-Client-IP：weblogic 服务代理
            ipAddresses = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {        //HTTP_CLIENT_IP：有些代理服务器
            ipAddresses = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {        //X-Real-IP：nginx服务代理
            ipAddresses = request.getHeader("X-Real-IP");
        }    //有些网络通过多层代理，那么获取到的ip就会有多个，一般都是通过逗号（,）分割开来，并且第一个ip为客户端的真实IP
        if (ipAddresses != null && ipAddresses.length() != 0) {
            ip = ipAddresses.split(",")[0];
        }    //还是不能获取到，最后再通过request.getRemoteAddr();获取
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            ip = request.getRemoteAddr();
        }
        // 解决请求和响应的IP一致且通过浏览器请求时，request.getRemoteAddr()为"0:0:0:0:0:0:0:1"
        if("0:0:0:0:0:0:0:1".equals(ip)){
            ip = getHostAddress();
        }
        return ip;
    }

    private static String getHostAddress(){
        // 根据网卡取本机配置的IP
        InetAddress inet = null;
        try {
            inet = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return inet.getHostAddress();
    }
}
