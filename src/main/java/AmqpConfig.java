import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * Created by shawnritchie on 28/04/15.
 */
@Configuration
public class AmqpConfig {

    protected final String genericExchangeName = "generic.exchange";

    protected final String replyRoutingKey = "reply.routing.key";
    protected final String replyQueueName = "reply.queue";

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        connectionFactory.setUsername("user");
        connectionFactory.setPassword("user");
        connectionFactory.setPublisherConfirms(true);
        return connectionFactory;
    }

    @Bean
    public RabbitTransactionManager rabbitTransactionManager() {
        return new RabbitTransactionManager(connectionFactory());
    }

    @Bean(name = "responseTemplate")
    public RabbitTemplate responseTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        template.setExchange(genericExchangeName);
        return template;
    }

    protected Queue createQueue(String name) {
        return new Queue(name, false, false, false, new HashMap<String, Object>() {{
            put("x-message-ttl", 500);
        }});
    }

    @Bean
    public Exchange genericExchange() {
        return new DirectExchange(this.genericExchangeName, false, false);
    }

    @Bean(name = "replyQueue")
    public Queue replyQueue() {
        return createQueue(this.replyQueueName);
    }

    @Bean(name = "replyBinding")
    @Autowired
    public Binding replyBinding(Exchange genericExchange,  @Qualifier("replyQueue") Queue replyQueue) {
        Binding binding =
                BindingBuilder
                        .bind(replyQueue)
                        .to(genericExchange)
                        .with(this.replyRoutingKey)
                        .noargs();

        return binding;
    }

    @Bean
    @Autowired
    public SimpleMessageListenerContainer listenerContainer(@Qualifier("replyQueue") Queue replyQueue, @Qualifier("responseTemplate") RabbitTemplate responseTemplate) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setQueues(replyQueue);
        container.setMessageListener(new MessageListenerAdapter(new SpittleHandler(responseTemplate)));
        return container;
    }

}
