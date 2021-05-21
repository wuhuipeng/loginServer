package cn.hereyou.auth.service;

import cn.hereyou.auth.credential.RememberMeUsernamePasswordCaptchaCredential;
import org.apereo.cas.authentication.AuthenticationHandlerExecutionResult;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.PreventedException;
import org.apereo.cas.authentication.handler.support.AbstractPreAndPostProcessingAuthenticationHandler;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.services.ServicesManager;

import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wuhuipeng
 * @version 1.0
 * @date 2021/5/21 16:07
 */
public class UserPasswordLoginHandler  extends AbstractPreAndPostProcessingAuthenticationHandler {
    @Override
    protected AuthenticationHandlerExecutionResult doAuthentication(Credential credential) throws GeneralSecurityException, PreventedException {
        RememberMeUsernamePasswordCaptchaCredential myCaptchaCredential = (RememberMeUsernamePasswordCaptchaCredential) credential;
        //密码用户校验逻辑
       //TODO
        String username=myCaptchaCredential.getUsername();
        Map<String, Object> map=new HashMap<>();
        map.put("email", "2252436103@qq.com");
        Principal principal = principalFactory.createPrincipal(username, map);
        return createHandlerResult(credential, principal);

    }

    public UserPasswordLoginHandler(String name, ServicesManager servicesManager, PrincipalFactory principalFactory, Integer order) {
        super(name, servicesManager, principalFactory, order);
    }

    @Override
    public boolean supports(Credential credential) {
        return false;
    }
}
