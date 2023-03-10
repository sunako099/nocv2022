package com.suanko.graduationdesign.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.suanko.graduationdesign.entity.User;
import com.suanko.graduationdesign.service.UserService;
import com.suanko.graduationdesign.vo.DataView;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }

    /**
     * 验证码
     * @param response
     * @param session
     * @throws IOException
     */
    @RequestMapping("/login/getCode")
    public void getCode(HttpServletResponse response, HttpSession session) throws IOException {
        //1.验证码对象
        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(116, 36, 4, 10);
        //2.放入到Session
        session.setAttribute("code",captcha.getCode());
        //3.输出验证码
        ServletOutputStream outputStream = response.getOutputStream();
        captcha.write(outputStream);
        //4.关闭输出流
        outputStream.close();
    }

    /**
     * 具体登录逻辑
     */
    @RequestMapping("/login/login")
    @ResponseBody
    public DataView login(String username,String password,String code,HttpSession session){
        DataView dataView=new DataView();
        //1.判断验证码是否正确
        String sessionCode=(String) session.getAttribute("code");
        if (code!=null && sessionCode.equals(code)) {
            //2.session普通登录逻辑
//            User user=userService.login(username,password);
            //shiro登录
            try {
                Subject subject = SecurityUtils.getSubject();
                UsernamePasswordToken token = new UsernamePasswordToken(username, password);
                subject.login(token);
                User user = (User) subject.getPrincipal();
                //3.判断
                if (user != null) {
                    dataView.setCode(200);
                    dataView.setMsg("登陆成功！");
                    session.setAttribute("user", user);
                    return dataView;
                } }
                catch (Exception ex)  {
                    dataView.setCode(100);
                    dataView.setMsg("用户名或密码错误！");
                    return dataView;
                }
            }
        dataView.setCode(100);
        dataView.setMsg("验证码错误！");
        return dataView;
    }


    /**
     * 退出登录
     */
    @RequestMapping("/login/logout")
    public String logout(){
        Subject subject=SecurityUtils.getSubject();
        subject.logout();
        return "login";
    }
}
