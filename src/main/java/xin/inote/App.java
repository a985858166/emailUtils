package xin.inote;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import xin.inote.utils.EmailSendUtils;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
       ApplicationContext ac = new ClassPathXmlApplicationContext("spring-context-email.xml");
        for (int i = 0; i < 1; i++) {
            EmailSendUtils.subject("申请书").msg("你的验证码是11").htmlMsg("<h1>你的验证码是</h1>").attach("/Users/zhenying/Downloads/18D1824C8A83A5BADAE96BF58D5050E5.jpg").to(new String[]{"985858166@qq.com","qq985858166@gmail.com"}).successCallback(new EmailSendUtils.Callback() {
                @Override
                public void execution() {
                    System.out.println("发送成功!");
                }
            }).errorCallback(new EmailSendUtils.Callback() {
                @Override
                public void execution() {
                    System.out.println("发送失败!");
                }
            }).send();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}
