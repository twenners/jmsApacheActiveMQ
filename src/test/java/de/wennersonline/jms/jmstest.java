package de.wennersonline.jms;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.wennersonline.jms.Receiver;
import de.wennersonline.jms.Sender;
import org.junit.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest
public class jmstest {

	@Autowired
	private Sender sender;
	
	@Autowired
	private Receiver receiver;
	
	@Test
	public void testSendReceiver() {
		sender.send("Hallo Welt");
		Assert.assertEquals("Hallo Welt", receiver.recieveMessage());

	}

}
