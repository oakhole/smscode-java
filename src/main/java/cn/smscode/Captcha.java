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
 * 若无指定验证码则自动按规则生成验证码generateCaptchaCode();
 * <p>
 * 默认生成验证码规则为:4位0-9的数字;
 * <p>
 * Created by oakhole on 9/15/15.
 */
public class Captcha {

    private String captchaCode;

    // 默认生成验证码的位数;
    public static final int DEFAULT_DIGIT = 4;

    // 默认验证码的随机取值范围,可为任意字符;
    public static final char[] THRESHOLD_CODES = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    // 若无指定验证码,则按默认规则随机生成
    public Captcha() {
        this.captchaCode = generateCaptchaCode(DEFAULT_DIGIT, THRESHOLD_CODES);
    }

    /**
     * 指定验证码生成规则
     *
     * @param digit
     * @param threshold_codes
     */
    public Captcha(int digit, char[] threshold_codes) {
        this.captchaCode = generateCaptchaCode(digit, threshold_codes);
    }

    /**
     * 指定验证码
     *
     * @param captchaCode
     */
    public Captcha(String captchaCode) {
        this.captchaCode = captchaCode;
    }

    /**
     * 指定位数和区间,生成随机验证码
     *
     * @param digit length of captcha code
     * @param codes array of character, usually as "0-9","a-z","A-Z"
     * @return
     */
    private String generateCaptchaCode(int digit, char[] codes) {

        StringBuffer stringBuffer = new StringBuffer();

        for (int i = 0; i < digit; i++) {
            int index = (int) (Math.random() * codes.length);
            stringBuffer.append(codes[index]);
        }

        return stringBuffer.toString();
    }

    public String getCaptchaCode() {
        return captchaCode;
    }

    public void setCaptchaCode(String captchaCode) {
        this.captchaCode = captchaCode;
    }

}
