package org.bip.spec.seal;

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

@Ports({ @Port(name = "set", type = PortType.enforceable),
		 @Port(name = "get", type = PortType.enforceable) })
@ComponentType(initial = "initial", name = "org.bip.spec.seal.SealableData")
public class SealableData<T> {

	Logger logger = LoggerFactory.getLogger(SealableData.class);
	
	private T data;
	private boolean sealed = false;
	
	public int noOfTransitions = 0;
		
	public SealableData(T data) {
		this.data = data;
	}

	public SealableData() {
	}
	
	@Transitions( { @Transition(name = "set", source = "set", target = "set", guard = "!isSealed"), 
				    @Transition(name = "set", source = "initial", target = "set") } )
	public void set(@Data(name = "input") T data) {
		
		logger.debug("Previous data {}, Current data: {}", this.data, data);
		this.data = data; 
		noOfTransitions++;
		
	}

	@Data(name = "value", accessTypePort = AccessType.allowed, ports = {"get"})
	public T get() {
		return data;
	}

	@Transitions({ @Transition(name = "get", source = "initial", target = "sealed", guard = "isSet"),
	@Transition(name = "get", source = "sealed", target = "sealed") } )
	public void getTransition() {
		if (!sealed) {
			logger.debug("First get executed thus the data {} is being sealed.", data);
			sealed = true;
		}		
	}

	@Transition(name = "", source = "initial", target="set", guard = "isSet")
	private void afterPropertiesSet() {
		logger.debug("Data has been provided by the constructor so transitioning into set state internally");
	}

	@Guard(name = "isSealed")
	public boolean isSealed() {
		return sealed;
	}

	@Guard(name = "isSet")
	public boolean isSet() {
		return data != null;
	}

}
