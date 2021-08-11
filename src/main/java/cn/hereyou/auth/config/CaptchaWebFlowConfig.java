package cn.hereyou.auth.config;

import cn.hereyou.auth.credential.AllFieldsCredential;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.web.flow.CasWebflowConstants;
import org.apereo.cas.web.flow.configurer.AbstractCasWebflowConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.ActionState;
import org.springframework.webflow.engine.Flow;
import org.springframework.webflow.engine.ViewState;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;

/**
 * @author wuhuipeng
 * @version 1.0
 * @date 2021/5/21 16:28
 */
public class CaptchaWebFlowConfig extends AbstractCasWebflowConfigurer {

    /**
     * Instantiates a new Default webflow configurer.
     *
     * @param flowBuilderServices    the flow builder services
     * @param flowDefinitionRegistry the flow definition registry
     * @param applicationContext     the application context
     * @param casProperties          the cas properties
     */
    public CaptchaWebFlowConfig(FlowBuilderServices flowBuilderServices, FlowDefinitionRegistry flowDefinitionRegistry, ApplicationContext applicationContext, CasConfigurationProperties casProperties) {
        super(flowBuilderServices, flowDefinitionRegistry, applicationContext, casProperties);
    }

    private static final String EVENT_ID_START_AUTHENTICATE_CAPTCHA = "startAuthenticateCaptcha";
    @Override
    protected void doInitialize() {

        final Flow flow = getLoginFlow();
        if (flow != null) {
            createFlowVariable(flow, CasWebflowConstants.VAR_ID_CREDENTIAL, AllFieldsCredential.class);
            //创建Action
            final ActionState actionState = createActionState(flow, EVENT_ID_START_AUTHENTICATE_CAPTCHA,
            //自定义Action
            createEvaluateAction("captchaCredentialsAction"));
            //添加成功后的Transition
            actionState.getTransitionSet().add(createTransition(CasWebflowConstants.TRANSITION_ID_SUCCESS,
                    CasWebflowConstants.STATE_ID_CREATE_TICKET_GRANTING_TICKET));
            //添加警告的Transition
            actionState.getTransitionSet()
                    .add(createTransition(CasWebflowConstants.TRANSITION_ID_WARN, CasWebflowConstants.TRANSITION_ID_WARN));
            //添加错误的Transition跳转到login
            actionState.getTransitionSet()
                    .add(createTransition(CasWebflowConstants.TRANSITION_ID_ERROR, CasWebflowConstants.STATE_ID_VIEW_LOGIN_FORM));
            //添加认证错误的的Transition跳转到login并显示错误信息
            actionState.getTransitionSet().add(createTransition(CasWebflowConstants.TRANSITION_ID_AUTHENTICATION_FAILURE,
                    CasWebflowConstants.STATE_ID_HANDLE_AUTHN_FAILURE));

            actionState.getExitActionList().add(createEvaluateAction("clearWebflowCredentialsAction"));
            registerMultifactorProvidersStateTransitionsIntoWebflow(actionState);
            ViewState viewLoginState = (ViewState) flow.getState(CasWebflowConstants.STATE_ID_VIEW_LOGIN_FORM);

            createTransitionForState(viewLoginState, "submitCaptcha", EVENT_ID_START_AUTHENTICATE_CAPTCHA, true);
        }
    }

}
