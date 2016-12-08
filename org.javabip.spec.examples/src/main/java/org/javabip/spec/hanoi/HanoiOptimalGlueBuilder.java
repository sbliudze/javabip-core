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

import org.javabip.glue.GlueBuilder;

public class HanoiOptimalGlueBuilder extends GlueBuilder {

	@Override
	public void configure() {

		String add = "pieceAdd";
		String remove = "pieceRemove";

		// a1 req B, c2 + C, b2
		// here c2 and b2 are implicit from another constraint
		port(LeftHanoiPegWithData.class, add).requires(HanoiOptimalMonitor.class, "ac");
		port(LeftHanoiPegWithData.class, add).requires(HanoiOptimalMonitor.class, "ab");
		// a1 acc B, C, b2, c2
		port(LeftHanoiPegWithData.class, add).accepts(HanoiOptimalMonitor.class, "ac", "ab", MiddleHanoiPegWithData.class, remove,
				RightHanoiPegWithData.class, remove);
		// a2 req B + C
		port(LeftHanoiPegWithData.class, remove).requires(HanoiOptimalMonitor.class, "ac");
		port(LeftHanoiPegWithData.class, remove).requires(HanoiOptimalMonitor.class, "ab");
		// a2 acc B, C, b1, c1
		port(LeftHanoiPegWithData.class, remove).accepts(HanoiOptimalMonitor.class, "ac", "ab", MiddleHanoiPegWithData.class, add,
				RightHanoiPegWithData.class, add);

		// b1 req A + C
		port(MiddleHanoiPegWithData.class, add).requires(HanoiOptimalMonitor.class, "bc");
		port(MiddleHanoiPegWithData.class, add).requires(HanoiOptimalMonitor.class, "ab");
		// b1 acc A, C, a2, c2
		port(MiddleHanoiPegWithData.class, add).accepts(HanoiOptimalMonitor.class, "bc", "ab", LeftHanoiPegWithData.class, remove,
				RightHanoiPegWithData.class, remove);
		// b2 req A + C
		port(MiddleHanoiPegWithData.class, remove).requires(HanoiOptimalMonitor.class, "bc");
		port(MiddleHanoiPegWithData.class, remove).requires(HanoiOptimalMonitor.class, "ab");
		// b2 acc A, C, a1, c1
		port(MiddleHanoiPegWithData.class, remove).accepts(HanoiOptimalMonitor.class, "bc", "ab", LeftHanoiPegWithData.class, add,
				RightHanoiPegWithData.class, add);

		// c1 req A + B
		port(RightHanoiPegWithData.class, add).requires(HanoiOptimalMonitor.class, "bc");
		port(RightHanoiPegWithData.class, add).requires(HanoiOptimalMonitor.class, "ac");

		// c1 acc A, B, a2, b2
		port(RightHanoiPegWithData.class, add).accepts(HanoiOptimalMonitor.class, "bc", "ac", LeftHanoiPegWithData.class, remove,
				MiddleHanoiPegWithData.class, remove);

		// c2 req A + B
		port(RightHanoiPegWithData.class, remove).requires(HanoiOptimalMonitor.class, "bc");
		port(RightHanoiPegWithData.class, remove).requires(HanoiOptimalMonitor.class, "ac");

		// c2 acc A, B, a1, b1
		port(RightHanoiPegWithData.class, remove).accepts(HanoiOptimalMonitor.class, "bc", "ac", LeftHanoiPegWithData.class, add,
				MiddleHanoiPegWithData.class, add);

		// A acc b1, b2, c1, c2
		port(HanoiOptimalMonitor.class, "bc").accepts(MiddleHanoiPegWithData.class, add, remove, RightHanoiPegWithData.class, add,
				remove);

		// A req b1,c2 + b2,c1
		port(HanoiOptimalMonitor.class, "bc").requires(MiddleHanoiPegWithData.class, add, RightHanoiPegWithData.class, remove);
		port(HanoiOptimalMonitor.class, "bc").requires(MiddleHanoiPegWithData.class, remove, RightHanoiPegWithData.class, add);

		// B req a1,c2 + a2,c1

		port(HanoiOptimalMonitor.class, "ac").requires(LeftHanoiPegWithData.class, add, RightHanoiPegWithData.class, remove);
		port(HanoiOptimalMonitor.class, "ac").requires(LeftHanoiPegWithData.class, remove, RightHanoiPegWithData.class, add);

		// B acc a1, a2, c1, c2
		port(HanoiOptimalMonitor.class, "ac")
				.accepts(LeftHanoiPegWithData.class, add, remove, RightHanoiPegWithData.class, add, remove);

		// C req a1,b2 + a2,b1
		port(HanoiOptimalMonitor.class, "ab").requires(LeftHanoiPegWithData.class, add, MiddleHanoiPegWithData.class, remove);
		port(HanoiOptimalMonitor.class, "ab").requires(LeftHanoiPegWithData.class, remove, MiddleHanoiPegWithData.class, add);

		// C acc a1, a2, b1, b2
		port(HanoiOptimalMonitor.class, "ab").accepts(LeftHanoiPegWithData.class, add, remove, MiddleHanoiPegWithData.class, add,
				remove);

		data(LeftHanoiPegWithData.class, "disksize").to(MiddleHanoiPegWithData.class, "addedDisk");
		data(LeftHanoiPegWithData.class, "disksize").to(RightHanoiPegWithData.class, "addedDisk");

		data(MiddleHanoiPegWithData.class, "disksize").to(LeftHanoiPegWithData.class, "addedDisk");
		data(MiddleHanoiPegWithData.class, "disksize").to(RightHanoiPegWithData.class, "addedDisk");

		data(RightHanoiPegWithData.class, "disksize").to(MiddleHanoiPegWithData.class, "addedDisk");
		data(RightHanoiPegWithData.class, "disksize").to(LeftHanoiPegWithData.class, "addedDisk");

	}
}
