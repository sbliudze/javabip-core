package org.bip.api;

import java.io.OutputStream;
import java.util.ArrayList;

public interface BIPGlue {
	public ArrayList<Accepts> getAcceptConstraints();

	public ArrayList<Requires> getRequiresConstraints();

	public ArrayList<DataWire> getDataWires();

	public void toXML(OutputStream outputStream);

	public Accepts addAccept(Accepts accept);

	public Requires addRequire(Requires require);

	public void addDataWire(DataWire dataWire);

}
