package cn.hereyou.auth.credential;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import org.apereo.cas.authentication.RememberMeUsernamePasswordCredential;

import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * @author wuhuipeng
 * @version 1.0
 * @date 2021/5/21 9:30
 */

public class RememberMeUsernamePasswordCaptchaCredential  extends RememberMeUsernamePasswordCredential {
    @Size(min=5,max=5,message="require captcha")
    private String captcha;

    public RememberMeUsernamePasswordCaptchaCredential(String captcha) {
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public RememberMeUsernamePasswordCaptchaCredential(boolean rememberMe, String captcha) {
        super(rememberMe);
        this.captcha = captcha;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), captcha);
    }
}
