# smscode-java

该repo作为<http://smscode.cn>短信接口的插件包；

使用`httpclient`发起post请求；

## 引用

    <dependency>
      <groupId>cn.smscode</groupId>
      <artifactId>smscode-java</artifactId>
      <version>1.0.0</version>
    </dependency>

使用http://maven.oschina.net的仓库;

    <repository>
        <id>oschina</id>
        <name>oschina</name>
        <url>http://maven.oschina.net/content/groups/public/</url>
    </repository>
    
## 接口文档

<http://smscode.cn/docs/api>

### 接口调用

    SmsCaptchaService.getInstance("11", "test", "test").text("验证码：0000.［smscode］").to(“13800000000”).send();
    
`[SmsCaptchaService][SmsCaptchaService]`: 提供短信服务;

 - `SmsCaptchaService getInstance(String userid, String account, String password)`: 生成实例;

 - `SmsCaptchaService text(String content)`: 内容赋值;

 - `SmsCaptchaService text(SmsMessage smsMessage)`，参考[SmsMessage][SmsMessage]: 通过`SmsMessage`用来封装模版短信和验证码生成待发送内容，如模版短信：注册验证码：0000。［smscode］,所需喜欢验证码内容："0000",指定验证码字符串，或默认规则生成的4位数字验证码,参考[Captcha][Captcha];
  
  验证码可自定义规则和使用默认规则进行生成；

`SmsCaptchaService to(String cellPhoneNumber)`: 手机号码;

`boolean send()`: 调用[PhoneTextHelper][PhoneTextHelper]进行推送;

### 短信服务工具类

    [PhoneTextHelper][PhoneTextHelper]
    
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
    
[SmsCaptchaService]: https://github.com/smscode/smscode-java/blob/master/src/main/java/cn/smscode/SmsCaptchaService.java
[SmsMessage]: https://github.com/smscode/smscode-java/blob/master/src/main/java/cn/smscode/SmsMessage.java
[Captcha]: https://github.com/smscode/smscode-java/blob/master/src/main/java/cn/smscode/Captcha.java
[PhoneTextHelper]: https://github.com/smscode/smscode-java/blob/master/src/main/java/cn/smscode/PhoneTextHelper.java