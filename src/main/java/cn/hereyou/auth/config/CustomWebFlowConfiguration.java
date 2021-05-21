package cn.hereyou.auth.config;

import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.web.flow.CasWebflowConfigurer;
import org.apereo.cas.web.flow.config.CasWebflowContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;

/**
 * @author wuhuipeng
 * @version 1.0
 * @date 2021/5/21 17:16
 */

@Configuration
@EnableConfigurationProperties(CasConfigurationProperties.class)
@AutoConfigureBefore(value = CasWebflowContextConfiguration.class)
public class CustomWebFlowConfiguration {

    @Autowired
    @Qualifier("loginFlowRegistry")
    private FlowDefinitionRegistry loginFlowRegistry;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private CasConfigurationProperties casProperties;
    @Autowired
    @Qualifier("builder")
    private FlowBuilderServices builder;

    @Bean("captchaWebFlowConfig")
    @ConditionalOnMissingBean(name = "captchaWebFlowConfig")
    public CasWebflowConfigurer captchaWebFlowConfig() {
        CaptchaWebFlowConfig config = new CaptchaWebFlowConfig(builder, loginFlowRegistry, applicationContext, casProperties);
        config.initialize();
        return config;
    }


}
