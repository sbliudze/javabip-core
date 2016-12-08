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
package org.javabip.spec;

import org.javabip.annotations.ComponentType;
import org.javabip.annotations.Data;
import org.javabip.annotations.Guard;
import org.javabip.annotations.Port;
import org.javabip.annotations.Ports;
import org.javabip.annotations.Transition;
import org.javabip.api.PortType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ports({ @Port(name = "register", type = PortType.enforceable), @Port(name = "speak", type = PortType.enforceable),
		@Port(name = "listen", type = PortType.enforceable), @Port(name = "unregister", type = PortType.enforceable) })
@ComponentType(initial = "zero", name = "org.bip.spec.Peer")
public class Peer {
	Logger logger = LoggerFactory.getLogger(Peer.class);

	private int trackerId;
	private int peerId;

	public int noOfTransitions = 0;

	public Peer(int id) {
		this.trackerId = -1;
		peerId = id;
	}

	@Transition(name = "register", source = "zero", target = "one")
	public void registering(@Data(name = "trackerId") Integer id) {
		this.trackerId = id;
		// System.out.println("Peer " + peerId + " registered with tracker " + id);
		noOfTransitions++;
	}

	@Transition(name = "speak", source = "one", target = "one", guard = "canInteract")
	public void speaking() {
		// System.out.println("Peer " + peerId + " speaking to " + trackerId);
		noOfTransitions++;
	}

	@Transition(name = "listen", source = "one", target = "one", guard = "canInteract")
	public void listening() {
		// System.out.println("Peer " + peerId + " listening to " + trackerId);
		noOfTransitions++;
	}

	@Transition(name = "unregister", source = "one", target = "zero", guard = "canInteract")
	public void unregistering() {
		// System.out.println("Peer " + peerId + " unregistered with tracker "
		// + this.trackerId);
		this.trackerId = -1;
		noOfTransitions++;
	}

	@Guard(name = "canInteract")
	public boolean canInteract(@Data(name = "trackerId") Integer id) {
		// System.out.println("Peer " + peerId + " registered with " + trackerId
		// + ", interacting with " + id + ": "
		// + (trackerId >= 0 && id == trackerId));
		return (trackerId >= 0 && id == trackerId);
	}
}
