package org.gfoo.textlearnstore;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LearnReceiver {

	public final static String RECEIVE_METHOD = "receiveMessage";

	public void receiveMessage(String message) {
		log.info(message);
	}

}