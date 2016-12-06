/*
 * Copyright 2012-2016 École polytechnique fédérale de Lausanne (EPFL), Switzerland
 * Copyright 2012-2016 Crossing-Tech SA, Switzerland
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Author: Simon Bliudze, Anastasia Mavridou, Radoslaw Szymanek and Alina Zolotukhina
 */
package org.bip.spec;

import org.bip.annotations.ComponentType;
import org.bip.annotations.Data;
import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.annotations.Transition;
import org.bip.api.DataOut.AccessType;
import org.bip.api.PortType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ports({ @Port(name = "get", type = PortType.enforceable), @Port(name = "work", type = PortType.enforceable) })
@ComponentType(initial = "0", name = "org.bip.spec.Slave")
public class Slave {

	Logger logger = LoggerFactory.getLogger(Slave.class);

	private String slaveID;
	private String currentMaster;

	public Slave(String id) {
		this.slaveID = id;
		this.currentMaster = "";
	}

	@Transition(name = "get", source = "0", target = "1")
	// public void get(@Data(name = "ID") String masterID) {
	public void get() {
		logger.info("Slave " + slaveID + " is getting the orders from master ");
	}

	@Transition(name = "work", source = "1", target = "0")
	public void work() {
		logger.info("Slave " + slaveID + " is working.");
		this.currentMaster = "";
	}

	// @Guard(name = "isMyMaster")
	// public boolean isMyMaster(@Data(name = "ID") String masterID) {
	// return currentMaster.equals(masterID);
	// }

	@Data(name = "ID", accessTypePort = AccessType.any)
	public String slaveID() {
		return slaveID;
	}
}
