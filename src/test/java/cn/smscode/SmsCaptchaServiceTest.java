package cn.smscode;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by oakhole on 9/18/15.
 */
public class SmsCaptchaServiceTest {

    @Test
    public void testSend() throws Exception {

        // 初始化短信服务
        String userid = "11";
        String account = "test";
        String password = "test";

        String cellPhoneNumber = "13800000000";
        String content = "您当前的登录验证码为0000,如有问题请致电4001143721.";

        SmsCaptchaService smsCaptchaService = SmsCaptchaService.getInstance(userid, account, password);

        // 两种方式发送短信
        // 1. 直接发送短信内容到目的号码
        Assert.assertFalse(smsCaptchaService.text(content).to(cellPhoneNumber).send());

        // 2. 按规则替换验证码'0000'
        // 2.1. 默认验证码生成规则
        Assert.assertFalse(smsCaptchaService.text(new SmsMessage(content, "0000")).to(cellPhoneNumber).send());

        // 2.2. 自定义验证码生成规则
        Assert.assertFalse(smsCaptchaService.text(new SmsMessage(content, "0000", new Captcha(4, new char[]{'a', 'b', 'c', 'd'})))
                .to(cellPhoneNumber).send());

    }
}