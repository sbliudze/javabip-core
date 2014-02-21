package org.bip.impl;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

import org.bip.api.BIPComponent;
import org.bip.api.PortBase;

public class PortBaseImpl implements PortBase {

	@XmlAttribute
	protected String id;

	// TODO, Improvement, move this attribute to new class GlueSpecPort (?) and
	// put this class within glue?
	@XmlAttribute
	protected String specType;

	public String getId() {
		return id;
	}

	public String getSpecType() {
		return specType;
	}

	public BIPComponent component() {
		// TODO Auto-generated method stub
		return null;
	}

	public PortBaseImpl(String id, String specificationType) {
		if (id == null) {
			throw new IllegalArgumentException(
					"Port id cannot be null for specification type "
							+ specificationType);
		}
		if (specificationType == null) {
				throw new IllegalArgumentException(
						"Port spec type cannot be null for port id " + id);
		}
		this.id = id;
		this.specType = specificationType;
	}

	public PortBaseImpl() {
		// TODO Auto-generated constructor stub
	}
}
