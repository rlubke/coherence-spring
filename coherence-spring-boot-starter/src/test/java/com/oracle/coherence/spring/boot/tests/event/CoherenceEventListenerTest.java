/*
 * Copyright (c) 2013, 2021, Oracle and/or its affiliates.
 *
 * Licensed under the Universal Permissive License v 1.0 as shown at
 * https://oss.oracle.com/licenses/upl.
 */
package com.oracle.coherence.spring.boot.tests.event;

import com.oracle.coherence.spring.configuration.annotation.EnableCoherence;
import com.oracle.coherence.spring.event.CoherenceEventListener;
import com.tangosol.net.Coherence;
import com.tangosol.net.NamedMap;
import com.tangosol.net.events.application.LifecycleEvent;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.web.WebAppConfiguration;

/**
*
* @author Gunnar Hillert
*
*/
@WebAppConfiguration
@SpringBootTest(classes = {
		ServletWebServerFactoryAutoConfiguration.class,
		CoherenceEventListenerTest.Config.class
})
public class CoherenceEventListenerTest {

	@Autowired
	private Coherence coherence;

	@Autowired
	private TestService testService;

	@Test
	@RepeatedTest(2)
	@DirtiesContext
	public void testCoherenceEventListener() {
		final NamedMap<String, String> namedMap = this.coherence.getSession().getMap("tasks");
		namedMap.put("foo1", "bar1");
		namedMap.put("foo2", "bar2");
		namedMap.remove("foo1");

//		await().atMost(5, TimeUnit.SECONDS).untilAsserted(() ->
//			assertThat(this.testService.getEventNames()).hasSize(3));
//		assertThat(this.testService.getEventNames()).contains("insert", "insert", "delete");

	}

	@CoherenceEventListener
	public void handleCoherenceEvent(LifecycleEvent event) {

	}

	@Configuration
	@EnableCoherence
	@EnableAutoConfiguration
	@ComponentScan({ "com.oracle.coherence.spring.boot.tests.event" })
	static class Config {
	}

}
