import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by shawnritchie on 28/04/15.
 */
public class Consumer {

    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(AmqpConfig.class);
    }

}
