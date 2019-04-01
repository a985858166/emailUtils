# 发送邮件工具类
## 使用教程

### 导入包

```xml
<dependency>
     <groupId>org.apache.commons</groupId>
     <artifactId>commons-email</artifactId>
     <version>1.5</version>
</dependency>
```

### 导入配置文件
#### spring-context-email.xml
#### 修改配置文件的发送账号信息

### 演示


```java
public static void main( String[] args )
    {
       ApplicationContext ac = new ClassPathXmlApplicationContext("spring-context-email.xml");
        EmailSendUtils.subject("验证码").htmlMsg("<h1>你的验证码是45722</h1>").attach("/Users/zhenying/Downloads/18D1824C8A83A5BADAE96BF58D5050E5.jpg").to(new String[]{"123456@qq.com","123456@gmail.com"}).successCallback(new EmailSendUtils.Callback() {
            @Override
            public void execution() {
                System.out.println("发送成功!");
                System.out.println("这是成功的回调--可选");
            }
        }).errorCallback(new EmailSendUtils.Callback() {
            @Override
            public void execution() {
                System.out.println("发送失败!");
                System.out.println("这是发送失败的回调--可选");
            }
        }).send();


    }
```



     
