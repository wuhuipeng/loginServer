package cn.hereyou.auth.exception;


import javax.security.auth.login.AccountException;

/**
 * @author ouruyi
 */
public class MobileErrorException extends AccountException {
    public MobileErrorException() {
        super();
    }

    public MobileErrorException(String msg) {
        super(msg);
    }
}
