package cn.hereyou.auth.config;

import cn.hereyou.auth.action.CaptchaCredentialsAction;
import cn.hereyou.auth.service.UserPasswordLoginHandler;
import org.apereo.cas.authentication.AuthenticationEventExecutionPlan;
import org.apereo.cas.authentication.AuthenticationEventExecutionPlanConfigurer;
import org.apereo.cas.authentication.AuthenticationHandler;
import org.apereo.cas.authentication.adaptive.AdaptiveAuthenticationPolicy;
import org.apereo.cas.authentication.principal.DefaultPrincipalFactory;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.web.flow.resolver.CasDelegatingWebflowEventResolver;
import org.apereo.cas.web.flow.resolver.CasWebflowEventResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.webflow.execution.Action;


/**
 * @author ouruyi
 */
@Configuration("captchaAuthenticationConfiguration")
@EnableConfigurationProperties(CasConfigurationProperties.class)
public class CaptchaAuthenticationConfiguration implements AuthenticationEventExecutionPlanConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(CaptchaAuthenticationConfiguration.class);

    @Autowired
    @Qualifier("servicesManager")
    private ServicesManager servicesManager;


    @Autowired
    @Qualifier("adaptiveAuthenticationPolicy")
    private AdaptiveAuthenticationPolicy adaptiveAuthenticationPolicy;

    @Autowired
    @Qualifier("serviceTicketRequestWebflowEventResolver")
    private CasWebflowEventResolver serviceTicketRequestWebflowEventResolver;

    @Autowired
    @Qualifier("initialAuthenticationAttemptWebflowEventResolver")
    private CasDelegatingWebflowEventResolver initialAuthenticationAttemptWebflowEventResolver;

    @Bean
    public Action captchaCredentialsAction() {
        return new CaptchaCredentialsAction(initialAuthenticationAttemptWebflowEventResolver,
                serviceTicketRequestWebflowEventResolver, adaptiveAuthenticationPolicy);
    }

    /**
     * 用户名、密码、验证码
     */
    @Bean
    public AuthenticationHandler captchaAuthenticationHandler() {
        return new UserPasswordLoginHandler(UserPasswordLoginHandler.class.getSimpleName(),
                servicesManager, new DefaultPrincipalFactory(), 1);
    }


    @Override
    public void configureAuthenticationExecutionPlan(final AuthenticationEventExecutionPlan plan) {

        final AuthenticationHandler customAuthenticationHandler = captchaAuthenticationHandler();
        plan.registerAuthenticationHandler(customAuthenticationHandler);
     }
}
