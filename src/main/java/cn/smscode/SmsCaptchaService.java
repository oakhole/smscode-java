/*
 * Copyright (c) 2015 , Nanjing Huanjie Information Science & Technology Co., Ltd
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package cn.smscode;

import java.util.Map;

/**
 * 配置服务参数,如用户ID,用户名,密码,发送所需参数
 * <p/>
 * Created by oakhole on 9/15/15.
 */
public class SmsCaptchaService {

    private String cellPhoneNubmer;
    private String content;

    private static SmsCaptchaService smsCaptchaService = new SmsCaptchaService();

    private static PhoneTextHelper phoneTextHelper;

    /**
     * 初始化单例
     */
    private SmsCaptchaService() {
    }

    /**
     * 初始化用户鉴权信息
     *
     * @param userid
     * @param account
     * @param password
     */
    public static synchronized SmsCaptchaService getInstance(String userid, String account, String password) {
        phoneTextHelper = PhoneTextHelper.getInstance(userid, account, password);
        return smsCaptchaService;
    }

    /**
     * 直接赋值目的号码和短息内容
     *
     * @param content
     * @return
     */
    public SmsCaptchaService text(String content) {
        smsCaptchaService.setContent(content);
        return smsCaptchaService;
    }

    /**
     * 参照smsMessage构造方法封装短信内容
     *
     * @param smsMessage
     * @return
     */
    public SmsCaptchaService text(SmsMessage smsMessage) {
        smsCaptchaService.setContent(smsMessage.getContent());
        return smsCaptchaService;
    }

    /**
     * 赋值cellPhoneNumber
     *
     * @param cellPhoneNumber
     * @return
     */
    public SmsCaptchaService to(String cellPhoneNumber) {
        smsCaptchaService.setCellPhoneNubmer(cellPhoneNumber);
        return smsCaptchaService;
    }

    /**
     * 推送短信
     *
     * @return
     */
    public boolean send() {
        Map<String, String> responseMap = phoneTextHelper.phoneText(smsCaptchaService.getCellPhoneNubmer(),
                smsCaptchaService.getContent());
        return responseMap != null && "ok".equals(responseMap.get("message"));
    }

    public String getCellPhoneNubmer() {
        return cellPhoneNubmer;
    }

    public void setCellPhoneNubmer(String cellPhoneNubmer) {
        this.cellPhoneNubmer = cellPhoneNubmer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public PhoneTextHelper getPhoneTextHelper() {
        return phoneTextHelper;
    }

    public void setPhoneTextHelper(PhoneTextHelper phoneTextHelper) {
        this.phoneTextHelper = phoneTextHelper;
    }
}
