package org.bip.spec.seal;

import org.bip.annotations.ComponentType;
import org.bip.annotations.Data;
import org.bip.annotations.Port;
import org.bip.annotations.Ports;
import org.bip.annotations.Transition;
import org.bip.annotations.Transitions;
import org.bip.api.PortType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ports({ @Port(name = "write", type = PortType.enforceable) })
@ComponentType(initial = "initial", name = "org.bip.spec.seal.SealableDataWriter")
public class SealableDataWriter<T> {

	Logger logger = LoggerFactory.getLogger(SealableData.class);

	private T data;
	public int noOfTransitions = 0;

	public SealableDataWriter(T data) {
		this.data = data;
	}

	@Data(name = "value")
	public T getData() {
		return data;
	}

	// TODO: change to only one transition when dynamicity is added. And remove component when it
	// reaches its final state.
	@Transitions({
 @Transition(name = "write", source = "written", target = "written"),
	@Transition(name = "write", source = "initial", target = "written")})
	public void write() {
		logger.debug("Writing data {}", this.data);
		noOfTransitions++;
	}
	
}
