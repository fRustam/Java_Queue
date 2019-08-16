import javax.jms.*;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;



public class Message {

        // DEFAULT_BROKER_URL - это tcp://localhost:61616
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    public static void main(String[] args) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = connectionFactory.createConnection();
        connection.start();
        MessageConsumer messageConsumer;

        // JMS сообщения посылаются и получаются используя Session.
        // создаём non-transactional session объект.
        // если нудно использовать трансакции то ставим первое значение 'true'
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //Topic topic = session.createTopic("QueueTopic"); - если нужно послать сообщение в Топик
        Destination destination = session.createQueue("Queue_1");

        MessageProducer producer = session.createProducer(destination); // *= session.createProducer(topic); -
                                                                        // если хотим посылать сообщение в топик

        // Посылаем сообщение победителя "YOU DID IT!!!"
        TextMessage message = session.createTextMessage();
        message.setText("YOU DID IT!!!");

        // Выводим сообщение в консоль
        producer.send(message);
        System.out.println("Sent message '" + message.getText() + "'");

        connection.close();
    }
}
