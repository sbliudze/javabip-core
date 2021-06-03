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
package org.javabip.glue;

import org.javabip.api.Accept;
import org.javabip.api.BIPGlue;
import org.javabip.api.DataWire;
import org.javabip.api.Require;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Class implementing the functionality of the BIP Glue.
 * 
 */
@XmlRootElement(name = "glue")
class BIPGlueImpl implements BIPGlue {

	@XmlElementWrapper(name = "accepts")
	@XmlElement(name = "accept")
	private ArrayList<AcceptImpl> acceptConstraints;

	@XmlElementWrapper(name = "requires")
	@XmlElement(name = "require")
	private ArrayList<RequireImpl> requiresConstraints;

	@XmlElementWrapper(name = "data")
	@XmlElement(name = "wire")
	private ArrayList<DataWireImpl> dataWires;

	public BIPGlueImpl() {
		this.acceptConstraints = new ArrayList<AcceptImpl>();
		this.requiresConstraints = new ArrayList<RequireImpl>();
		this.dataWires = new ArrayList<DataWireImpl>();
	}

	public BIPGlueImpl(ArrayList<Accept> acceptConstraints, ArrayList<Require> requiresConstraints,
			ArrayList<DataWire> dataWires) {
		this.acceptConstraints = new ArrayList<AcceptImpl>();
		for (Accept acc : acceptConstraints) {
			this.acceptConstraints.add(new AcceptImpl(acc.getEffect(), acc.getCauses()));
		}

		this.requiresConstraints = new ArrayList<RequireImpl>();
		for (Require req : requiresConstraints) {
			this.requiresConstraints.add(new RequireImpl(req.getEffect(), req.getCauses()));
		}

		this.dataWires = new ArrayList<DataWireImpl>();
		for (DataWire dataWire : dataWires) {
			this.dataWires.add(new DataWireImpl(dataWire.getFrom(), dataWire.getTo(), dataWire.isCopy()));
		}

	}

	public ArrayList<Accept> getAcceptConstraints() {
		ArrayList<Accept> acceptConstraintsInterface = new ArrayList<Accept>();
		acceptConstraintsInterface.addAll(acceptConstraints);
		return acceptConstraintsInterface;
	}

	public ArrayList<Require> getRequiresConstraints() {
		ArrayList<Require> requiresConstraintsInterface = new ArrayList<Require>();
		requiresConstraintsInterface.addAll(requiresConstraints);
		return requiresConstraintsInterface;
	}

	public ArrayList<DataWire> getDataWires() {
		ArrayList<DataWire> dataWiresInterface = new ArrayList<DataWire>();
		dataWiresInterface.addAll(dataWires);
		return dataWiresInterface;
	}

	public void toXML(OutputStream outputStream) {

		// create JAXB context and instantiate marshaller
		JAXBContext context;
		try {
			context = JAXBContext.newInstance(BIPGlueImpl.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			// Write to outputStream
			m.marshal(this, outputStream);
		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}

	public AcceptImpl addAccept(AcceptImpl accept) {
		this.acceptConstraints.add(accept);
		return accept;
	}

	public RequireImpl addRequire(RequireImpl require) {
		this.requiresConstraints.add(require);
		return require;
	}

	public void addDataWire(DataWireImpl dataWire) {
		this.dataWires.add(dataWire);
	}

}
