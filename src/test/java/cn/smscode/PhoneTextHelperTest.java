package cn.smscode;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by oakhole on 9/18/15.
 */
public class PhoneTextHelperTest {

    @Test
    public void testPhoneText() throws Exception {

        String userid = "11";
        String account = "test";
        String password = "test";

        String cellPhoneNumber = "13800000000";
        String content = "您当前登录验证码为0000,如有问题请致电4001143721.";

        Assert.assertNotNull(PhoneTextHelper.getInstance(userid, account, password).phoneText(cellPhoneNumber, content));
    }
}