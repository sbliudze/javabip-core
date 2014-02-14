package org.bip.glue;

/*
 * Copyright (c) 2012 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2012, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 10/15/12
 */

import java.io.OutputStream;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.bip.api.Accepts;
import org.bip.api.BIPGlue;
import org.bip.api.DataWire;
import org.bip.api.Requires;

@XmlRootElement(name = "glue")
class BIPGlueImpl implements BIPGlue {

	@XmlElementWrapper(name = "accepts")
	@XmlElement(name = "accept")
	private ArrayList<AcceptsImpl> acceptConstraints;

	@XmlElementWrapper(name = "requires")
	@XmlElement(name = "require")
	private ArrayList<RequiresImpl> requiresConstraints;

	@XmlElementWrapper(name = "data")
	@XmlElement(name = "wire")
	private ArrayList<DataWireImpl> dataWires;

	public BIPGlueImpl() {
		this.acceptConstraints = new ArrayList<AcceptsImpl>();
		this.requiresConstraints = new ArrayList<RequiresImpl>();
		this.dataWires = new ArrayList<DataWireImpl>();
	}

	public BIPGlueImpl(ArrayList<Accepts> acceptConstraints, ArrayList<Requires> requiresConstraints,
			ArrayList<DataWire> dataWires) {
		this.acceptConstraints = new ArrayList<AcceptsImpl>();
		for (Accepts acc : acceptConstraints) {
			this.acceptConstraints.add(new AcceptsImpl(acc.getEffect(), acc.getCauses()));
		}

		this.requiresConstraints = new ArrayList<RequiresImpl>();
		for (Requires req : requiresConstraints) {
			this.requiresConstraints.add(new RequiresImpl(req.getEffect(), req.getCauses()));
		}

		this.dataWires = new ArrayList<DataWireImpl>();
		for (DataWire dataWire : dataWires) {
			this.dataWires.add(new DataWireImpl(dataWire.getFrom(), dataWire.getTo()));
		}

	}

	public ArrayList<Accepts> getAcceptConstraints() {
		ArrayList<Accepts> acceptConstraintsInterface = new ArrayList<Accepts>();
		acceptConstraintsInterface.addAll(acceptConstraints);
		return acceptConstraintsInterface;
	}

	public ArrayList<Requires> getRequiresConstraints() {
		ArrayList<Requires> requiresConstraintsInterface = new ArrayList<Requires>();
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

	public AcceptsImpl addAccept(AcceptsImpl accept) {
		this.acceptConstraints.add(accept);
		return accept;
	}

	public RequiresImpl addRequire(RequiresImpl require) {
		this.requiresConstraints.add(require);
		return require;
	}

	public void addDataWire(DataWireImpl dataWire) {
		this.dataWires.add(dataWire);
	}

}
