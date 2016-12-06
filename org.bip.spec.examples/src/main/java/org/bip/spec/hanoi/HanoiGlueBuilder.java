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

import org.bip.glue.GlueBuilder;

public class HanoiGlueBuilder extends GlueBuilder {

	private int size;

	public HanoiGlueBuilder(int size) {
		this.size = size;
	}

	@Override
	public void configure() {

		for (int i = 1; i <= size; i++) {

			String piece = "" + i;
			String add = "piece" + piece + "Add";
			String remove = "piece" + piece + "Remove";

			// a1 req B, c2 + C, b2
			// here c2 and b2 are implicit from another constraint
			port(LeftHanoiPeg.class, add).requires(HanoiMonitor.class, "ac");
			port(LeftHanoiPeg.class, add).requires(HanoiMonitor.class, "ab");
			// a1 acc B, C, b2, c2
			port(LeftHanoiPeg.class, add).accepts(HanoiMonitor.class, "ac", "ab", MiddleHanoiPeg.class, remove,
					RightHanoiPeg.class, remove);
			// a2 req B + C
			port(LeftHanoiPeg.class, remove).requires(HanoiMonitor.class, "ac");
			port(LeftHanoiPeg.class, remove).requires(HanoiMonitor.class, "ab");
			// a2 acc B, C, b1, c1
			port(LeftHanoiPeg.class, remove).accepts(HanoiMonitor.class, "ac", "ab", MiddleHanoiPeg.class, add,
					RightHanoiPeg.class, add);

			// b1 req A + C
			port(MiddleHanoiPeg.class, add).requires(HanoiMonitor.class, "bc");
			port(MiddleHanoiPeg.class, add).requires(HanoiMonitor.class, "ab");
			// b1 acc A, C, a2, c2
			port(MiddleHanoiPeg.class, add).accepts(HanoiMonitor.class, "bc", "ab", LeftHanoiPeg.class, remove,
					RightHanoiPeg.class, remove);
			// b2 req A + C
			port(MiddleHanoiPeg.class, remove).requires(HanoiMonitor.class, "bc");
			port(MiddleHanoiPeg.class, remove).requires(HanoiMonitor.class, "ab");
			// b2 acc A, C, a1, c1
			port(MiddleHanoiPeg.class, remove).accepts(HanoiMonitor.class, "bc", "ab", LeftHanoiPeg.class, add,
					RightHanoiPeg.class, add);

			// c1 req A + B
			port(RightHanoiPeg.class, add).requires(HanoiMonitor.class, "bc");
			port(RightHanoiPeg.class, add).requires(HanoiMonitor.class, "ac");

			// c1 acc A, B, a2, b2
			port(RightHanoiPeg.class, add).accepts(HanoiMonitor.class, "bc", "ac", LeftHanoiPeg.class, remove,
					MiddleHanoiPeg.class, remove);

			// c2 req A + B
			port(RightHanoiPeg.class, remove).requires(HanoiMonitor.class, "bc");
			port(RightHanoiPeg.class, remove).requires(HanoiMonitor.class, "ac");

			// c2 acc A, B, a1, b1
			port(RightHanoiPeg.class, remove).accepts(HanoiMonitor.class, "bc", "ac", LeftHanoiPeg.class, add,
					MiddleHanoiPeg.class, add);

			// A acc b1, b2, c1, c2
			port(HanoiMonitor.class, "bc").accepts(MiddleHanoiPeg.class, add, remove, RightHanoiPeg.class, add, remove);

			// A req b1,c2 + b2,c1
			port(HanoiMonitor.class, "bc").requires(MiddleHanoiPeg.class, add, RightHanoiPeg.class, remove);
			port(HanoiMonitor.class, "bc").requires(MiddleHanoiPeg.class, remove, RightHanoiPeg.class, add);

			// B req a1,c2 + a2,c1

			port(HanoiMonitor.class, "ac").requires(LeftHanoiPeg.class, add, RightHanoiPeg.class, remove);
			port(HanoiMonitor.class, "ac").requires(LeftHanoiPeg.class, remove, RightHanoiPeg.class, add);

			// B acc a1, a2, c1, c2
			port(HanoiMonitor.class, "ac").accepts(LeftHanoiPeg.class, add, remove, RightHanoiPeg.class, add, remove);

			// C req a1,b2 + a2,b1
			port(HanoiMonitor.class, "ab").requires(LeftHanoiPeg.class, add, MiddleHanoiPeg.class, remove);
			port(HanoiMonitor.class, "ab").requires(LeftHanoiPeg.class, remove, MiddleHanoiPeg.class, add);

			// C acc a1, a2, b1, b2
			port(HanoiMonitor.class, "ab").accepts(LeftHanoiPeg.class, add, remove, MiddleHanoiPeg.class, add, remove);

		}

	}
}
