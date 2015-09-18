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

import cn.smscode.util.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Oakhole
 * @since 1.0
 */
public class PhoneTextHelper {

    // 接口所需参数
    private static final String send_url = "http://221.178.210.68:8888/sms.aspx";
    private String userid;
    private String account;
    private String password;
    private static final String send_action = "send";
    private static final String checkcontent = "0";

    private static PhoneTextHelper phoneTextHelper;

    private PhoneTextHelper() {
    }

    private PhoneTextHelper(String userid, String account, String password) {
        this.userid = userid;
        this.account = account;
        this.password = password;
    }

    /**
     * PhoneTextHelper单例
     *
     * @param userid
     * @param account
     * @param password
     * @return
     */
    public static synchronized PhoneTextHelper getInstance(String userid, String account, String password) {
         return new PhoneTextHelper(userid,account,password);
    }

    /**
     * 使用httpclient发起post请求
     *
     * @param mobile  目的号码,必须为手机号
     * @param content 短信内容信息
     * @return xml解析后的的hashMap
     */
    public Map<String, String> phoneText(String mobile, String content) {

        // 验证参数是否正确, 错误则返回为空值
        if (!checkRequestParameter() || !StringUtils.isNotEmpty(mobile) || !StringUtils.isNotEmpty(content)) {
            return null;
        }

        // 封装post请求参数
        List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        postParameters.add(new BasicNameValuePair("action", send_action));
        postParameters.add(new BasicNameValuePair("userid", userid));
        postParameters.add(new BasicNameValuePair("account", account));
        postParameters.add(new BasicNameValuePair("password", password));
        postParameters.add(new BasicNameValuePair("mobile", mobile));
        postParameters.add(new BasicNameValuePair("content", content));
        postParameters.add(new BasicNameValuePair("sendTime", ""));
        postParameters.add(new BasicNameValuePair("checkcontent", checkcontent));

        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse closeableHttpResponse = null;
        HttpPost httpPost = new HttpPost(send_url);

        UrlEncodedFormEntity urlEncodedFormEntity;
        Map<String, String> responseMap = null;

        // 发送post请求
        try {
            // 根据文档提示,使用utf-8格式编码
            urlEncodedFormEntity = new UrlEncodedFormEntity(postParameters, "utf-8");
            httpPost.setEntity(urlEncodedFormEntity);
            closeableHttpResponse = httpClient.execute(httpPost);
            if (closeableHttpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity httpEntity = closeableHttpResponse.getEntity();
                if (httpEntity != null) {
                    // 将响应返回的xml解析为HashMap
                    responseMap = parseXML(httpEntity.getContent());
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                closeableHttpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseMap;
    }

    /**
     * 使用dom4j解析xml文档
     *
     * @param inputStream
     * @return
     * @throws DocumentException
     * @throws IOException
     */
    private Map<String, String> parseXML(InputStream inputStream) throws DocumentException, IOException {

        Map<String, String> resultMap = new HashMap<String, String>();

        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(inputStream);
        Element rootElement = document.getRootElement();
        List<Element> elementsList = rootElement.elements();
        for (Element element : elementsList) {
            resultMap.put(element.getName(), element.getText());
        }
        inputStream.close();
        return resultMap;
    }

    /**
     * 检测发送短信验证码所需字段是否完整,且不能为空,符合基本规则
     *
     * @return
     */
    private boolean checkRequestParameter() {

        if (StringUtils.isNotEmpty(userid) && StringUtils.isNotEmpty(account) && StringUtils.isNotEmpty(password)) {
            return true;
        }
        return false;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
