package de.wennersonline.jms;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

@Configuration
@EnableJms
public class Receiver {
	
	private static final Logger log = LoggerFactory.getLogger(Receiver.class);
	
	@Value("${activemq.queue}")
	private String messageQueue;
	
	@Autowired
	ConnectionHandler connectionHandler;
	
	String text;

	public void recieve() {
		try {

			Connection connection = connectionHandler.getConnection();
			connection.start();


            // Create a Session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the destination (Topic or Queue)
            Destination destination = session.createQueue(messageQueue);

            // Create a MessageConsumer from the Session to the Topic or Queue
            MessageConsumer consumer = session.createConsumer(destination);

            // Wait for a message
            Message message = consumer.receive(1000);

            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                text = textMessage.getText();
                log.info("Received: " + text);
            } else {
            	log.info("Received: " + message);
            }

            consumer.close();
            session.close();
            connection.close();
            
		} catch (JMSException e) {
			System.out.println("Keine Verbindung");
		}
	}
	
	public String recieveMessage() {
		recieve();
		return text;
	}
}
