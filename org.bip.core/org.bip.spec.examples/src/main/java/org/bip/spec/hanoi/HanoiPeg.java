/*
 * Copyright (c) 2013 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2013, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 15/07/2013
 */

package org.bip.spec.hanoi;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Hashtable;

import org.bip.annotations.bipData;
import org.bip.annotations.bipExecutableBehaviour;
import org.bip.api.Data;
import org.bip.api.DataOut;
import org.bip.api.Guard;
import org.bip.api.Port;
import org.bip.api.PortBase;
import org.bip.executor.BehaviourBuilder;
import org.bip.impl.DataImpl;
import org.bip.impl.GuardImpl;
import org.bip.impl.PortImpl;
import org.bip.impl.TransitionImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HanoiPeg {

	Logger logger = LoggerFactory.getLogger(HanoiPeg.class);

	private int size;

	int numberOfMoves = 0;

	final int maxPieces = 8;

	// pieces[0] equals true means that the first smallest piece is actually present in this peg.
	boolean[] pieces;

	public HanoiPeg(int size, boolean isEmpty) {

		if (size > maxPieces)
			throw new IllegalArgumentException("Max number of pieces is " + maxPieces);

		this.size = size;

		pieces = new boolean[size];

		if (!isEmpty)
			for (int i = 0; i < size; i++)
				pieces[i] = true;

	}

	@bipExecutableBehaviour
	public BehaviourBuilder initializeBehavior() throws NoSuchMethodException {

		String componentType = this.getClass().getCanonicalName();
		ArrayList<String> states = new ArrayList<String>();

		ArrayList<TransitionImpl> allTransitions = new ArrayList<TransitionImpl>();
		ArrayList<Port> allPorts = new ArrayList<Port>();
		ArrayList<Guard> guards = new ArrayList<Guard>();

		String currentState = "start";

		states.add(currentState);

		//DATA IN
		
		Data<Integer> dataAdd = new DataImpl<Integer>("addedDisk", Integer.class);
		ArrayList<Data<?>> trData = new ArrayList<Data<?>>();
		trData.add(dataAdd);
		
		//TRANSITIONS
		
		allTransitions.add(new TransitionImpl("pieceAdd", "start", "start", "isPieceAddable", this.getClass().getMethod("addPiece", int.class), trData));
		allTransitions.add(new TransitionImpl("pieceRemove", "start", "start", "isPieceRemovable", this.getClass().getMethod("removePiece")));

		//PORTS
		
		Port add = new PortImpl("pieceAdd", Port.Type.enforceable.toString(), this.getClass());
		Port rm = new PortImpl("pieceRemove", Port.Type.enforceable.toString(), this.getClass());
		allPorts.add(rm);
		allPorts.add(add);
		
		//GUARDS
		
		guards.add(new GuardImpl("isPieceAddable", this.getClass().getMethod("isPieceAddable", Integer.class)));
		guards.add(new GuardImpl("isPieceRemovable", this.getClass().getMethod("isPieceRemovable")));

		//DATA OUT
		
		DataOut<?> outData = createData("disksize", int.class, "any");
		ArrayList<DataOut<?>> dataOut = new ArrayList<DataOut<?>>();
		dataOut.add(outData);
		Hashtable<String, Method> dataOutName = new Hashtable<String, Method>();
		dataOutName.put("disksize", this.getClass().getMethod("diskSizeOnTop"));

		//BUILD
		
		BehaviourBuilder behaviourBuilder = new BehaviourBuilder(componentType, currentState, allTransitions, allPorts, states, guards, dataOutName, dataOut, this);

		return behaviourBuilder;
	}
	
	//TODO find a way not to copy this method among classes
	<T> DataOut<T> createData(String dataName, Class<T> type, String accessType) {
		DataOut<T> toReturn = new DataImpl<T>(dataName, type, accessType);
		return toReturn;
	}

	public void addPiece(int no) {
		logger.debug("EXECUTION: component " + this.getClass().getName() + " has added a piece.");
		pieces[no - 1] = true;
		logger.debug("Piece no. " + no + " is being added.");
	}

	public void removePiece() {
		logger.debug("EXECUTION: component " + this.getClass().getName() + " has removed a piece.");
		for (int i = 0; i < size; i++)
			if (pieces[i]) {
				logger.debug("Piece no. " + (i + 1) + " is being removed.");
				pieces[i] = false;
				return;
			}

	}

	public boolean isPieceAddable(@bipData(name = "addedDisk") Integer no) {

		if (no == -1)
			return false;

		if (no == 1)
			return !pieces[0];

		for (int i = 0; i < no - 1; i++) {
			if (pieces[i])
				return false;
		}

		return !pieces[no - 1];

	}

	public boolean isPieceRemovable() {

		for (int i = 0; i < size; i++) {
			if (pieces[i])
				return true;
		}

		return false;
	}

	@bipData(name = "disksize", accessTypePort = "allowedPorts", ports = { "pieceAdd", "pieceRemove" })
	public int diskSizeOnTop() {

		for (int i = 0; i < size; i++) {
			if (pieces[i]) {
				return i + 1;
			}
		}
		return -1;

	}

}
