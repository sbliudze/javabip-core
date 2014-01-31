package org.bip.glue;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import org.bip.impl.PortImpl;

class ListType<T extends List<PortImpl>> {

	private List<ListElementType<T>> list = new ArrayList<ListElementType<T>>();

	public ListType() {
	}

	public ListType(List<T> map) {
		for (T e : map) {
			list.add(new ListElementType<T>(e));
		}
	}

	@XmlElement(name = "option")
	public List<ListElementType<T>> getList() {
		return list;
	}

	public void setList(List<ListElementType<T>> list) {
		this.list = list;
	}
}
