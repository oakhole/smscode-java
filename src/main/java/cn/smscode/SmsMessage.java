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

/**
 * 两种方式赋值短信内容
 * <p>
 * 1. 直接赋值;
 * <p>
 * 2. 间接赋值,分别赋值模板内容和验证码,并使用赋值的验证码替换模板内容中的验证码;
 * <p>
 * Created by oakhole on 9/15/15.
 */
public class SmsMessage {

    private String content;

    private Captcha captcha;

    /**
     * 指定内容
     *
     * @param content
     */
    public SmsMessage(String content) {
        this.captcha = new Captcha();
    }

    public SmsMessage(String content, String regex) {
        this.captcha = new Captcha();
        this.content = content.replaceAll(regex, captcha.getCaptchaCode());
    }

    /**
     * 使用验证码替换短信内容
     *
     * @param content
     * @param regex
     * @param captchaCode
     */
    public SmsMessage(String content, String regex, String captchaCode) {
        this.content = content.replaceAll(regex, captchaCode);
    }

    /**
     * 自定义规则生成验证码
     *
     * @param content
     * @param captcha
     */
    public SmsMessage(String content, String regex, Captcha captcha) {
        this.content = content.replaceAll(regex, captcha.getCaptchaCode());
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Captcha getCaptcha() {
        return captcha;
    }

    public void setCaptcha(Captcha captcha) {
        this.captcha = captcha;
    }
}
