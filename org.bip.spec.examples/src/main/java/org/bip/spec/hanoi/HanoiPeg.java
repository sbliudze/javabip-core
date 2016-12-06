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

package org.bip.spec.hanoi;

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

		BehaviourBuilder behaviourBuilder = new BehaviourBuilder(this);

		behaviourBuilder.setComponentType(this.getClass().getCanonicalName());

		String currentState = "start";

		behaviourBuilder.setInitialState(currentState);

		behaviourBuilder.addState(currentState);

		for (int i = 0; i < size; i++) {

			behaviourBuilder.addPort("piece" + (i + 1) + "Add", PortType.enforceable, this.getClass());
			behaviourBuilder.addPort("piece" + (i + 1) + "Remove", PortType.enforceable, this.getClass());
			behaviourBuilder.addTransitionAndStates("piece" + (i + 1) + "Add", "start", "start", "isPiece" + (i + 1)
					+ "Addable", this.getClass().getMethod("movePiece" + (i + 1)));
			behaviourBuilder.addTransitionAndStates("piece" + (i + 1) + "Remove", "start", "start", "isPiece" + (i + 1)
					+ "Removable", this.getClass().getMethod("movePiece" + (i + 1)));
			behaviourBuilder.addGuard("isPiece" + (i + 1) + "Addable",
					this.getClass().getMethod("isPiece" + (i + 1) + "Addable"));
			behaviourBuilder.addGuard("isPiece" + (i + 1) + "Removable",
					this.getClass().getMethod("isPiece" + (i + 1) + "Removable"));
		}

		return behaviourBuilder;
	}

	public void movePiece(int no) {
		if (pieces[no - 1])
			logger.debug("Piece no. " + no + " is being removed.");
		else
			logger.debug("Piece no. " + no + " is being added.");
		pieces[no - 1] = !pieces[no - 1];
	}

	public void movePiece1() {
		movePiece(1);
	}

	public void movePiece2() {
		movePiece(2);
	}

	public void movePiece3() {
		movePiece(3);
	}

	public void movePiece4() {
		movePiece(4);
	}

	public void movePiece5() {
		movePiece(5);
	}

	public void movePiece6() {
		movePiece(6);
	}

	public void movePiece7() {
		movePiece(7);
	}

	public void movePiece8() {
		movePiece(8);
	}

	public boolean isPiece1Addable() {

		return isPieceAddable(1);

	}

	public boolean isPiece2Addable() {

		return isPieceAddable(2);

	}

	public boolean isPiece3Addable() {

		return isPieceAddable(3);

	}

	public boolean isPiece4Addable() {

		return isPieceAddable(4);

	}

	public boolean isPiece5Addable() {

		return isPieceAddable(5);

	}

	public boolean isPiece6Addable() {

		return isPieceAddable(6);

	}

	public boolean isPiece7Addable() {

		return isPieceAddable(7);

	}

	public boolean isPiece8Addable() {

		return isPieceAddable(8);

	}

	private boolean isPieceAddable(int no) {

		if (no == 1)
			return !pieces[0];

		for (int i = 0; i < no - 1; i++) {
			if (pieces[i])
				return false;
		}

		return !pieces[no - 1];

	}

	public boolean isPiece1Removable() {

		return isPieceRemovable(1);

	}

	public boolean isPiece2Removable() {

		return isPieceRemovable(2);

	}

	public boolean isPiece3Removable() {

		return isPieceRemovable(3);

	}

	public boolean isPiece4Removable() {

		return isPieceRemovable(4);

	}

	public boolean isPiece5Removable() {

		return isPieceRemovable(5);

	}

	public boolean isPiece6Removable() {

		return isPieceRemovable(6);

	}

	public boolean isPiece7Removable() {

		return isPieceRemovable(7);

	}

	public boolean isPiece8Removable() {

		return isPieceRemovable(8);

	}

	private boolean isPieceRemovable(int no) {

		if (no == 1)
			return pieces[0];

		for (int i = 0; i < no - 1; i++) {
			if (pieces[i])
				return false;
		}

		return pieces[no - 1];

	}

}
