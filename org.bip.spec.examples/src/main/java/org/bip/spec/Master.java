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
import org.bip.annotations.Guard;
import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.annotations.Transition;
import org.bip.annotations.Transitions;
import org.bip.api.DataOut.AccessType;
import org.bip.api.PortType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ports({ @Port(name = "req", type = PortType.enforceable), @Port(name = "compute", type = PortType.enforceable) })
@ComponentType(initial = "0", name = "org.bip.spec.Master")
public class Master {
	Logger logger = LoggerFactory.getLogger(Master.class);

	private String masterID;
	private String slave1;
	private String slave2;

	public Master(String id) {
		this.masterID = id;
		this.slave1 = "";
		this.slave2 = "";
	}

	@Transitions({ @Transition(name = "req", source = "0", target = "1"),
			@Transition(name = "req", source = "1", target = "2"), })
	public void request(@Data(name = "slaveID") String slaveID) {
		logger.info("Master " + masterID + " is requesting a slave " + slaveID);
		if (slave1.isEmpty()) {
			slave1 = slaveID;
		} else if (slave2.isEmpty()) {
			slave2 = slaveID;
		}
	}

	@Transition(name = "compute", source = "2", target = "0", guard = "mySlaves")
	public void compute() {
		logger.info("Master " + masterID + " is computing.");
		this.slave1 = "";
		this.slave2 = "";
	}

	@Guard(name = "mySlaves")
	public boolean mySlaves(@Data(name = "slaveID1") String slave1ID, @Data(name = "slaveID2") String slave2ID) {
		boolean guardCheck = (slave1.equals(slave1ID) && slave2.equals(slave2ID))
				|| (slave1.equals(slave2ID) && slave2.equals(slave1ID));
		logger.debug("Slave1: " + slave1 + " Slave2: " + slave2 + " ID1: " + slave1ID + " ID2: " + slave2ID + " "
				+ guardCheck);
		return (slave1.equals(slave1ID) && slave2.equals(slave2ID))
				|| (slave1.equals(slave2ID) && slave2.equals(slave1ID));
	}

	@Data(name = "ID", accessTypePort = AccessType.any)
	public String masterID() {
		return masterID;
	}
}
