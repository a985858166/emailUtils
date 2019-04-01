package xin.inote.utils;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope(value = "prototype")
public class EmailSendUtils {
    private static ApplicationContext applicationContext;
    private Callback successCallback;
    private Callback errorCallback;
    @Autowired
    private SimpleEmail simpleEmail;
    @Autowired
    private HtmlEmail htmlEmail;
    String subject = null;
    String msg = null;
    String htmlMsg = null;
    String[] to = null;
    String[] cc = null;
    String[] bcc = null;
    List<EmailAttachment> emailAttachmentList = new ArrayList<>();

    /**
     * 发送
     */
    public void send(){
        Thread t = new Thread(new EmailThrowable());
        t.start();
    }

    /**
     * @param subject 发送标题
     * @return
     */
    public static EmailSendUtils subject(String subject){
        EmailSendUtils emailSendUtils = applicationContext.getBean(EmailSendUtils.class);
        emailSendUtils.subject = subject;
        return emailSendUtils;
    }



    /**
     * @param msg 发送内容
     * @return
     */
    public EmailSendUtils msg(String msg){
        this.msg = msg;
        return this;
    }

    /**
     * @param htmlMsg 发送内容 html
     * @return
     */
    public EmailSendUtils htmlMsg(String htmlMsg){
        this.htmlMsg = htmlMsg;
        return this;
    }

    /**
     * @param to 接收人邮箱地址
     * @return
     */
    public EmailSendUtils to(String to){
        this.to = new String[]{to};
        return this;
    }

    /**
     * @param to 接收人邮箱地址
     * @return
     */
    public EmailSendUtils to(String... to){
        this.to = to;
        return this;
    }
    /**
     * @param to 接收人邮箱地址
     * @return
     */
    public EmailSendUtils to(List<String> to){
        String[] array = to.toArray(new String[0]);
        this.to = array;
        return this;
    }

    /**
     * @param bcc 接收人邮箱地址  密送
     * @return
     */
    public EmailSendUtils bcc(String bcc){
        this.bcc = new String[]{bcc};
        return this;
    }

    /**
     * @param bcc 接收人邮箱地址   密送
     * @return
     */
    public EmailSendUtils bcc(String... bcc){
        this.bcc = bcc;
        return this;
    }
    /**
     * @param bcc 接收人邮箱地址   密送
     * @return
     */
    public EmailSendUtils bcc(List<String> bcc){
        String[] array = bcc.toArray(new String[0]);
        this.bcc = array;
        return this;
    }

    /**
     * @param cc 接收人邮箱地址  抄送
     * @return
     */
    public EmailSendUtils cc(String cc){
        this.cc = new String[]{cc};
        return this;
    }

    /**
     * @param cc 接收人邮箱地址   抄送
     * @return
     */
    public EmailSendUtils cc(String... cc){
        this.cc = cc;
        return this;
    }
    /**
     * @param cc 接收人邮箱地址   抄送
     * @return
     */
    public EmailSendUtils cc(List<String> cc){
        String[] array = cc.toArray(new String[0]);
        this.cc = array;
        return this;
    }

    /**
     * @param url 文件地址
     * @return
     */
    public EmailSendUtils attach(URL url){
        EmailAttachment emailAttachment = new EmailAttachment();
        emailAttachment.setURL(url);
        emailAttachmentList.add(emailAttachment);
        return this;
    }

    /**
     * @param urlList URL 集合
     * @return
     */
    public EmailSendUtils attachURLList(List<URL> urlList){
        for (URL url:urlList
             ) {
            EmailAttachment emailAttachment = new EmailAttachment();
            emailAttachment.setURL(url);
            emailAttachmentList.add(emailAttachment);
        }
        return this;
    }

    /**
     * @param paths 文件路径集合
     * @return
     */
    public EmailSendUtils attach(String... paths){
        for (String path:paths
             ) {
            EmailAttachment emailAttachment = new EmailAttachment();
            emailAttachment.setPath(path);
            emailAttachmentList.add(emailAttachment);
        }
        return this;
    }

    /**
     * @param file 文件
     * @return
     */
    public EmailSendUtils attach(File file){
        EmailAttachment emailAttachment = new EmailAttachment();
        emailAttachment.setPath(file.getPath());
        emailAttachmentList.add(emailAttachment);
        return this;
    }

    /**
     * @param fileList 文件集合
     * @return
     */
    public EmailSendUtils attach(List<File> fileList){
        for (File file:fileList
             ) {
            EmailAttachment emailAttachment = new EmailAttachment();
            emailAttachment.setPath(file.getPath());
            emailAttachmentList.add(emailAttachment);
        }
        return this;
    }

    /**
     * 成功是运行的回调函数
     * @param successCallback  需要自己实现
     */
    public EmailSendUtils successCallback(Callback successCallback) {
        this.successCallback = successCallback;
        return this;
    }

    /**
     * 失败时运行的回调函数
     * @param errorCallback 需要自己实现
     */
    public EmailSendUtils errorCallback(Callback errorCallback) {
        this.errorCallback = errorCallback;
        return this;
    }

    private class EmailThrowable implements Runnable {
        @Override
        public void run() {
            try {
                simpleEmail.setSubject(subject);
                htmlEmail.setSubject(subject);

                if (msg != null){
                    simpleEmail.setMsg(msg);
                }

                if (htmlMsg != null){
                    htmlEmail.setHtmlMsg(htmlMsg);
                }

                if (to != null){
                    simpleEmail.addTo(to);
                    htmlEmail.addTo(to);
                }
                if (cc != null){
                    simpleEmail.addCc(cc);
                    htmlEmail.addCc(cc);
                }
                if (bcc != null){
                    simpleEmail.addBcc(bcc);
                    htmlEmail.addBcc(bcc);
                }

                if (emailAttachmentList.size()>0){
                    for (EmailAttachment emailAttachment:emailAttachmentList
                         ) {
                        htmlEmail.attach(emailAttachment);
                    }
                    htmlEmail.send();
                }else {
                    simpleEmail.send();
                }
                if (successCallback != null){
                    successCallback.execution();
                }
            } catch (EmailException e) {
                if (errorCallback != null){
                    errorCallback.execution();
                }
                e.printStackTrace();
            }

        }
    }
    @Component
    @Lazy(value = false)
    public static class GetApplicationContext implements ApplicationContextAware {

        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            EmailSendUtils.applicationContext = applicationContext;
        }
    }
    public interface Callback{
        void execution();
    }
}
