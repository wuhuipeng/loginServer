package cn.hereyou.auth.config;

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
    public CaptchaWebFlowConfig(FlowBuilderServices flowBuilderServices, FlowDefinitionRegistry loginFlowDefinitionRegistry, ApplicationContext applicationContext, CasConfigurationProperties casProperties) {
        super(flowBuilderServices, loginFlowDefinitionRegistry, applicationContext, casProperties);
    }
    private static final String EVENT_ID_START_AUTHENTICATE_MOBILE_ID = "startAuthenticateCaptcha";

    @Override
    protected void doInitialize() {

        final Flow flow = getLoginFlow();
        if (flow != null) {
            //创建Action
            final ActionState actionState = createActionState(flow, EVENT_ID_START_AUTHENTICATE_MOBILE_ID,
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
            createTransitionForState(viewLoginState, "submitCaptcha", EVENT_ID_START_AUTHENTICATE_MOBILE_ID, true);

        }
    }
}
