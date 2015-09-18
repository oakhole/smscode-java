package cn.smscode;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by oakhole on 9/18/15.
 */
public class SmsMessageTest {

    @Test
    public void testSmsMessage() throws Exception {
        String message = "您当前的登录验证码为:0000,如有问题请致电4001143721.";
        SmsMessage smsMessage = new SmsMessage(message,"0000");
        Assert.assertNotEquals("0000",smsMessage.getCaptcha().getCaptchaCode(),"未正确生成验证码.");
    }
}