package cn.hereyou.auth.service;

import cn.hereyou.auth.credential.RememberMeUsernamePasswordCaptchaCredential;
import cn.hereyou.auth.exception.CaptchaErrorException;
import cn.hutool.core.util.StrUtil;
import org.apereo.cas.authentication.AuthenticationHandlerExecutionResult;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.PreventedException;
import org.apereo.cas.authentication.handler.support.AbstractPreAndPostProcessingAuthenticationHandler;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.services.ServicesManager;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.security.auth.login.FailedLoginException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wuhuipeng
 * @version 1.0
 * @date 2021/5/21 16:07
 */
public class CaptchaAuthenticationHandler extends AbstractPreAndPostProcessingAuthenticationHandler  {


    public CaptchaAuthenticationHandler(String name, ServicesManager servicesManager, PrincipalFactory principalFactory, Integer order) {
        super(name, servicesManager, principalFactory, order);
    }
    @Override
    protected AuthenticationHandlerExecutionResult doAuthentication(Credential credential) throws GeneralSecurityException, PreventedException {
        RememberMeUsernamePasswordCaptchaCredential usernamePasswordCredential = (RememberMeUsernamePasswordCaptchaCredential) credential;
        //密码用户校验逻辑
        String requestCaptcha = usernamePasswordCredential.getCaptcha();
        RequestContextHolder.getRequestAttributes().getSessionMutex();
        Object attribute = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession().getAttribute("captcha");
        String realCaptcha = attribute == null ? null : attribute.toString();
        final boolean correct = compareCaptcha(requestCaptcha, realCaptcha);
        if (!correct) {
             throw new CaptchaErrorException("验证码错误");
        }

       //TODO
        String username=usernamePasswordCredential.getUsername();
        Map<String, Object> map=new HashMap<>();

        map.put("email", "2252436103@qq.com");
        Principal principal = principalFactory.createPrincipal(username, map);
        return createHandlerResult(credential, principal);

    }


    @Override
    public boolean supports(final Credential credential) {
        if (!RememberMeUsernamePasswordCaptchaCredential.class.isInstance(credential)) {
            return false;
        }
        if (this.credentialSelectionPredicate == null) {
            return true;
        }
        final boolean result = this.credentialSelectionPredicate.test(credential);
        return result;
    }


    /**
     * 验证码比较
     *
     * @param requestCaptcha 用户输入
     * @param realCaptcha    系统生成
     * @return
     */
    public boolean compareCaptcha(String requestCaptcha, String realCaptcha) {
        if (StrUtil.isBlank(requestCaptcha)) {
            return false;
        }
        // O和0均视为0
        String str1 = requestCaptcha.replace("O", "0").replace("o", "0");
        String str2 = realCaptcha.replace("O", "0").replace("o", "0");
        // 忽略大小写
        return str1.equalsIgnoreCase(str2);
    }
}
