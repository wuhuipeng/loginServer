package cn.hereyou.auth.action;


import cn.hereyou.auth.credential.RememberMeUsernamePasswordCaptchaCredential;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.adaptive.AdaptiveAuthenticationPolicy;
import org.apereo.cas.web.flow.actions.AbstractNonInteractiveCredentialsAction;
import org.apereo.cas.web.flow.resolver.CasDelegatingWebflowEventResolver;
import org.apereo.cas.web.flow.resolver.CasWebflowEventResolver;
import org.apereo.cas.web.support.WebUtils;
import org.springframework.webflow.execution.RequestContext;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ：ouruyi
 * @version 1.0
 * @date ：Created in 2020/11/13 9:48
 * 功能描述：
 */

public class CaptchaCredentialsAction extends AbstractNonInteractiveCredentialsAction {
    public CaptchaCredentialsAction(CasDelegatingWebflowEventResolver initialAuthenticationAttemptWebflowEventResolver,
                                    CasWebflowEventResolver serviceTicketRequestWebflowEventResolver,
                                    AdaptiveAuthenticationPolicy adaptiveAuthenticationPolicy) {
        super(initialAuthenticationAttemptWebflowEventResolver, serviceTicketRequestWebflowEventResolver,
                adaptiveAuthenticationPolicy);
    }

    @Override
    protected Credential constructCredentialsFromRequest(RequestContext requestContext) {
        try {
            final HttpServletRequest request = WebUtils.getHttpServletRequestFromExternalWebflowContext(requestContext);
            RememberMeUsernamePasswordCaptchaCredential credentials = new RememberMeUsernamePasswordCaptchaCredential(false,request.getParameter("captcha"));
            credentials.setUsername(request.getParameter("username"));
            credentials.setPassword(request.getParameter("password"));
            if (credentials != null) {
                 return credentials;
            }
        } catch (final Exception e) {
             e.printStackTrace();
        }
        return null;
    }

}
