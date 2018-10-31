package de.wennersonline.jms;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Sender {
	
	private static final Logger log = LoggerFactory.getLogger(Sender.class);
	
//	@Value("${activemq.broker-url}")
//	private String brokerUrl;
	
	@Value("${activemq.queue}")
	private String messageQueue;
	
	@Autowired
	ConnectionHandler connectionHandler;

	
	public void send(String text) {
		try {
            // Create a ConnectionFactory
//            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
//
//            
//            connectionFactory.getExceptionListener();
            // Create a Connection
            Connection connection = connectionHandler.getConnection();            
            connection.start();

            // Create a Session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the destination (Topic or Queue)
            Destination destination = session.createQueue(messageQueue);

            // Create a MessageProducer from the Session to the Topic or Queue
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            // Create a messages
            TextMessage message = session.createTextMessage(text);

            // Tell the producer to send the message
            log.info("Sent message: "+ message.hashCode() + " : " + Thread.currentThread().getName());
            producer.send(message);;
            // Clean up
            session.close();
            connection.close();
        }
        catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        }
	}

}
