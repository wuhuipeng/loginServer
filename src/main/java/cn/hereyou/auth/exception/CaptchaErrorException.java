package cn.hereyou.auth.exception;


import javax.security.auth.login.AccountException;

/**
 * @author ouruyi
 */
public class CaptchaErrorException extends AccountException {
    public CaptchaErrorException() {
        super();
    }

    public CaptchaErrorException(String msg) {
        super(msg);
    }
}
