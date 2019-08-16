import javax.jms.*;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import java.io.IOException;

public class ConsumerMessage {

        private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;

        public static void main(String[] args) throws JMSException {

            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
            Connection connection = connectionFactory.createConnection();
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue("Queue_1");
            MessageConsumer consumer = session.createConsumer(destination);

            MessageListener listner = message -> {
                try {
                    if (message instanceof TextMessage) {
                        TextMessage textMessage = (TextMessage) message;
                        System.out.println("Received message" + " " + textMessage.getText() + "'");
                    }
                } catch (JMSException e) {
                    System.out.println("Caught:" + e);
                    e.printStackTrace();
                }
            };
            Destination destination1 = session.createQueue("Queue_2");

            MessageProducer producer = session.createProducer(destination1); // *= session.createProducer(topic); -
                                                                             // если хотим посылать сообщение в топик

            // Посылаем сообщение полученное в listner из Queue_1
            TextMessage message = session.createTextMessage();
            message.setText(String.valueOf(listner)); //не могу понять, как вытащить значение из listner

            // Выводим сообщение в консоль
            producer.send(message);
            System.out.println("Sent message '" + message.getText() + "'");

            consumer.setMessageListener(listner);
                try {
                    System.in.read();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            connection.close();
        }
   }
