package cn.hereyou.auth.credential;

import lombok.*;
import org.apereo.cas.authentication.RememberMeUsernamePasswordCredential;

import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * @author wuhuipeng
 * @version 1.0
 * @date 2021/5/21 9:30
 */

@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RememberMeUsernamePasswordCaptchaCredential  extends RememberMeUsernamePasswordCredential {
     private String captcha;
}
