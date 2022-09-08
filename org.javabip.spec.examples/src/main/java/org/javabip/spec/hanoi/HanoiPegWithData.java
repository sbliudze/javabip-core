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
 * Date: 15/07/2013
 */

package org.javabip.spec.hanoi;

import org.javabip.annotations.Data;
import org.javabip.annotations.ExecutableBehaviour;
import org.javabip.api.PortType;
import org.javabip.api.DataOut.AccessType;
import org.javabip.executor.BehaviourBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HanoiPegWithData {

	Logger logger = LoggerFactory.getLogger(HanoiPegWithData.class);

	private int size;

	public int noOfTransitions = 0;

	final int maxPieces = 8;

	// pieces[0] equals true means that the first smallest piece is actually present in this peg.
	boolean[] pieces;

	public HanoiPegWithData(int size, boolean isEmpty) {

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

		BehaviourBuilder behaviourBuilder = new BehaviourBuilder(this);

		behaviourBuilder.setComponentType(this.getClass().getCanonicalName());

		String initialState = "start";

		behaviourBuilder.setInitialState(initialState);

		behaviourBuilder.addState(initialState);

		// PORTS

		behaviourBuilder.addPort("pieceAdd", PortType.enforceable, this.getClass());
		behaviourBuilder.addPort("pieceRemove", PortType.enforceable, this.getClass());

		// TRANSITIONS

		behaviourBuilder.addTransitionAndStates("pieceAdd", initialState, initialState, "isPieceAddable", "", "", this
				.getClass().getMethod("addPiece", int.class));

		behaviourBuilder.addTransitionAndStates("pieceRemove", initialState, initialState, "isPieceRemovable", "", "", this
				.getClass().getMethod("removePiece"));

		// GUARDS

		behaviourBuilder.addGuard(this.getClass().getMethod("isPieceAddable", int.class));
		behaviourBuilder.addGuard(this.getClass().getMethod("isPieceRemovable"));

		// DATA OUT

		behaviourBuilder.addDataOut(this.getClass().getMethod("diskSizeOnTop"));
		// BUILD

		return behaviourBuilder;
	}

	public void addPiece(@Data(name = "addedDisk") int no) {
		logger.debug("EXECUTION: component " + this.getClass().getName() + " has added a piece.");
		pieces[no - 1] = true;
		noOfTransitions++;
		logger.debug("Piece no. " + no + " is being added.");
	}

	public void removePiece() {
		logger.debug("EXECUTION: component " + this.getClass().getName() + " has removed a piece.");
		for (int i = 0; i < size; i++)
			if (pieces[i]) {
				logger.debug("Piece no. " + (i + 1) + " is being removed.");
				pieces[i] = false;
				noOfTransitions++;
				return;
			}

	}

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

	@Data(name = "disksize", accessTypePort = AccessType.allowed, ports = { "pieceAdd", "pieceRemove" })
	public int diskSizeOnTop() {

		for (int i = 0; i < size; i++) {
			if (pieces[i]) {
				return i + 1;
			}
		}
		return -1;

	}

}
