/*
 * Copyright (c) 2013 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2013, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 15/07/2013
 */

package org.bip.spec.hanoi;

import org.bip.annotations.Data;
import org.bip.annotations.ExecutableBehaviour;
import org.bip.api.PortType;
import org.bip.executor.BehaviourBuilder;
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

	@ExecutableBehaviour
	public BehaviourBuilder initializeBehavior() throws NoSuchMethodException {

		BehaviourBuilder behaviourBuilder = new BehaviourBuilder();
		
		behaviourBuilder.setComponentType( this.getClass().getCanonicalName() );
		
		String initialState = "start";
		
		behaviourBuilder.setInitialState(initialState);
		
		behaviourBuilder.addState(initialState);
				
		//TRANSITIONS

		behaviourBuilder.addTransition("pieceAdd", initialState, initialState, "isPieceAddable", 
									   this.getClass().getMethod("addPiece", int.class) );
		
		behaviourBuilder.addTransition("pieceRemove", initialState, initialState, "isPieceRemovable", 
				   					   this.getClass().getMethod("removePiece"));
		

		//PORTS
		
		behaviourBuilder.addPort("pieceAdd", PortType.enforceable.toString(), this.getClass());
		behaviourBuilder.addPort("pieceRemove", PortType.enforceable.toString(), this.getClass());
				
		//GUARDS
		
		// TODO, It looks like guard does not have to specify the names of BIP data? It should also required Array.asList( "addedDisk" ); in its definition.
		// TODO, add addGuard function that has no guardName parameter since it is inferred from the name of the method.
		behaviourBuilder.addGuard("isPieceAddable", this.getClass().getMethod("isPieceAddable", int.class) );
		behaviourBuilder.addGuard("isPieceRemovable", this.getClass().getMethod("isPieceRemovable"));
		
		//DATA OUT
		
		// TODO, There is mismatch between what is defined here and in BIP annotation.
		// TODO, check todo in Data annotation, maybe addDataOut should only accept Method parameter and 
		// read other things from the method?
		//behaviourBuilder.addDataOut("disksize", this.getClass().getMethod("diskSizeOnTop"), "any" );
		
		behaviourBuilder.addDataOut(this.getClass().getMethod("diskSizeOnTop") );
		//BUILD
		
		behaviourBuilder.setComponent(this);
		

		return behaviourBuilder;
	}
	
	public void addPiece(@Data(name = "addedDisk") int no) {
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

	// TODO, autoboxing between for example int and Integer may be a good feature to help wire data from multiple components.
	public boolean isPieceAddable(@Data(name = "addedDisk") int no) {

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

	@Data(name = "disksize", accessTypePort = "allowed", ports = { "pieceAdd", "pieceRemove" })
	public int diskSizeOnTop() {

		for (int i = 0; i < size; i++) {
			if (pieces[i]) {
				return i + 1;
			}
		}
		return -1;

	}

}
