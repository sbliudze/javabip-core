package org.bip.glue;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import org.bip.impl.PortBaseImpl;

class ListElementType<T extends List<PortBaseImpl>> {

	private T value;

	public ListElementType() {
	}

	public ListElementType(T value) {
		this.value = value;
	}

	@XmlElementWrapper(name = "causes")
	@XmlElement(name = "port")
	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

}
