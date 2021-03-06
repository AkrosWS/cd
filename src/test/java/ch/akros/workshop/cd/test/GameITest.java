/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.akros.workshop.cd.test;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.akros.workshop.cd.util.Log4jGameLogger;

//@formatter:off
/**
 * To run this test in embedded wildfly you got to set
 * -Djava.util.logging.manager=org.jboss.logmanager.LogManager 
 * in run runtime configuration VM Arguments
 */
//@formatter:on

@RunWith(Arquillian.class)
public class GameITest {

	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap
				.create(WebArchive.class, "test.war")
				.addPackages(true, "ch.akros.workshop.cd.util", "ch.akros.workshop.cd.service", "ch.akros.workshop.cd.exception",
						"ch.akros.workshop.cd.domain").addClass(ch.akros.workshop.cd.test.MockGameLogger.class).deleteClass(Log4jGameLogger.class)
				.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml").addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				// Deploy our test datasource
				.addAsWebInfResource("test-ds.xml");
	}

	@Inject
	private MockGameLogger gameLogger;

	@Test()
	public void testAutomatedStartOfGame() throws InterruptedException {
		Thread.sleep(10000L);
		Assert.assertTrue("Game has not been tried to start", gameLogger.isTimerTriggered());
	}

}
