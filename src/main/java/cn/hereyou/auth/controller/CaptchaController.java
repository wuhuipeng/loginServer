package cn.hereyou.auth.controller;


import cn.hutool.captcha.AbstractCaptcha;
import cn.hutool.captcha.CaptchaUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.io.IOException;


/**
 * @author ouruyi
 */
@Controller
public class CaptchaController {

    public static final String KEY_CAPTCHA = "captcha";

    public static final String KEY_RESET_CAPTCHA = "resetCaptcha";


    @RequestMapping("/getCaptcha")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) {
        // 设置相应类型,告诉浏览器输出的内容为图片
        response.setContentType("image/png");
        // 不缓存此内容
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        ServletOutputStream out = null;
        try {

            AbstractCaptcha captcha = CaptchaUtil.createLineCaptcha(200, 100, 5, 25);
            captcha.setBackground(new Color(15, 70, 140, 255));
            HttpSession session = request.getSession();
            session.removeAttribute(KEY_CAPTCHA);
            session.setAttribute(KEY_CAPTCHA, captcha.getCode());
            out = response.getOutputStream();
            captcha.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @RequestMapping("/getCaptcha4Reset.do")
    public void getCaptcha4Reset(HttpServletRequest request, HttpServletResponse response) {
        // 设置相应类型,告诉浏览器输出的内容为图片
        response.setContentType("image/png");
        // 不缓存此内容
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        ServletOutputStream out = null;
        try {

            AbstractCaptcha captcha = CaptchaUtil.createLineCaptcha(200, 100, 5, 25);
            captcha.setBackground(new Color(255, 255, 255, 255));
            HttpSession session = request.getSession();
            session.removeAttribute(KEY_RESET_CAPTCHA);
            session.setAttribute(KEY_RESET_CAPTCHA, captcha.getCode());
            out = response.getOutputStream();
            captcha.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
