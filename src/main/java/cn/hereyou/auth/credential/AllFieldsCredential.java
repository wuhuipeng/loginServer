package cn.hereyou.auth.credential;

import lombok.*;
import org.apereo.cas.authentication.RememberMeUsernamePasswordCredential;

/**
 * @author ouruyi
 */
@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AllFieldsCredential extends RememberMeUsernamePasswordCredential {

    private String captcha;
    private String phoneNumber;
    private String validateCode;

}
