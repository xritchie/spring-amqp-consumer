import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.SerializationUtils;
import spittr.Spittle;

/**
 * Created by shawnritchie on 28/04/15.
 */
public class SpittleHandler implements MessageListener {

    private RabbitTemplate rabbitTemplate;

    public SpittleHandler(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void onMessage(Message message) {
        try {
            System.out.println("Recieved Message");

            String uuid = (String) message.getMessageProperties().getHeaders().get("uuidrequest");
            String correlationId = new String(message.getMessageProperties().getCorrelationId());
            Spittle spittle = (Spittle)SerializationUtils.deserialize(message.getBody());

            System.out.println("uuid: " + uuid);
            System.out.println("correlationId: " + correlationId);
            System.out.println(spittle.getMessage());

            Message responseMessage = MessageBuilder
                .withBody(org.springframework.amqp.utils.SerializationUtils.serialize(spittle))
                .setHeader("uuidrequest", uuid)
                .build();

            rabbitTemplate.convertAndSend(correlationId, responseMessage);
        }
        catch(Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

    }

}
