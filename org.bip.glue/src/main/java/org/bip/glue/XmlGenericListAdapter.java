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
package org.bip.glue;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.adapters.XmlAdapter;

class XmlGenericListAdapter<T extends List<PortBaseImpl>> extends XmlAdapter<ListType<T>, List<T>> {

	@Override
	public List<T> unmarshal(ListType<T> v) throws Exception {
		List<T> list = new ArrayList<T>();

		for (ListElementType<T> listElementType : v.getList()) {
			list.add(listElementType.getValue());
		}
		return list;
	}

	@Override
	public ListType<T> marshal(List<T> v) throws Exception {
		ListType<T> listType = new ListType<T>();

		for (T entry : v) {
			ListElementType<T> listElementType = new ListElementType<T>();
			listElementType.setValue(entry);
			listType.getList().add(listElementType);
		}
		return listType;
	}
}
