/*
 * Copyright (c) 2013 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2013, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 15/07/2013
 */
package org.bip.spec.hanoi;

import org.bip.glue.GlueBuilder;

public class HanoiOptimalGlueBuilder extends GlueBuilder {

    @Override
    public void configure() {

            String add = "pieceAdd";
            String remove = "pieceRemove";

            // a1 req B, c2 + C, b2
            // here c2 and b2 are implicit from another constraint
            port(LeftHanoiPeg.class, add).requires(HanoiOptimalMonitor.class, "ac");
            port(LeftHanoiPeg.class, add).requires(HanoiOptimalMonitor.class, "ab");
            // a1 acc B, C, b2, c2
            port(LeftHanoiPeg.class, add).accepts(HanoiOptimalMonitor.class, "ac", "ab",
                    MiddleHanoiPeg.class, remove,
                    RightHanoiPeg.class, remove);
            // a2 req B + C
            port(LeftHanoiPeg.class, remove).requires(HanoiOptimalMonitor.class, "ac");
            port(LeftHanoiPeg.class, remove).requires(HanoiOptimalMonitor.class, "ab");
            // a2 acc B, C, b1, c1
            port(LeftHanoiPeg.class, remove).accepts(HanoiOptimalMonitor.class, "ac", "ab",
            		MiddleHanoiPeg.class, add,
                    RightHanoiPeg.class, add);

            // b1 req A + C
            port(MiddleHanoiPeg.class, add).requires(HanoiOptimalMonitor.class, "bc");
            port(MiddleHanoiPeg.class, add).requires(HanoiOptimalMonitor.class, "ab");
            // b1 acc A, C, a2, c2
            port(MiddleHanoiPeg.class, add).accepts(HanoiOptimalMonitor.class, "bc", "ab",
                    LeftHanoiPeg.class, remove,
                    RightHanoiPeg.class, remove);
            // b2 req A + C
            port(MiddleHanoiPeg.class, remove).requires(HanoiOptimalMonitor.class, "bc");
            port(MiddleHanoiPeg.class, remove).requires(HanoiOptimalMonitor.class, "ab");
            // b2 acc A, C, a1, c1
            port(MiddleHanoiPeg.class, remove).accepts(HanoiOptimalMonitor.class, "bc", "ab",
                    LeftHanoiPeg.class, add,
                    RightHanoiPeg.class, add);

            // c1 req A + B
            port(RightHanoiPeg.class, add).requires(HanoiOptimalMonitor.class, "bc");
            port(RightHanoiPeg.class, add).requires(HanoiOptimalMonitor.class, "ac");

            // c1 acc A, B, a2, b2
            port(RightHanoiPeg.class, add).accepts(HanoiOptimalMonitor.class, "bc", "ac",
            		LeftHanoiPeg.class, remove,
                    MiddleHanoiPeg.class, remove);

            // c2 req A + B
            port(RightHanoiPeg.class, remove).requires(HanoiOptimalMonitor.class, "bc");
            port(RightHanoiPeg.class, remove).requires(HanoiOptimalMonitor.class, "ac");

            // c2 acc A, B, a1, b1
            port(RightHanoiPeg.class, remove).accepts(HanoiOptimalMonitor.class, "bc", "ac",
                    LeftHanoiPeg.class, add,
                    MiddleHanoiPeg.class, add);

            // A acc b1, b2, c1, c2
            port(HanoiOptimalMonitor.class, "bc").accepts(MiddleHanoiPeg.class, add, remove, RightHanoiPeg.class, add, remove);

            // A req b1,c2 + b2,c1
            port(HanoiOptimalMonitor.class, "bc").requires(MiddleHanoiPeg.class, add, RightHanoiPeg.class, remove);
            port(HanoiOptimalMonitor.class, "bc").requires(MiddleHanoiPeg.class, remove, RightHanoiPeg.class, add);

            // B req a1,c2 + a2,c1

            port(HanoiOptimalMonitor.class, "ac").requires(LeftHanoiPeg.class, add, RightHanoiPeg.class, remove);
            port(HanoiOptimalMonitor.class, "ac").requires(LeftHanoiPeg.class, remove, RightHanoiPeg.class, add);

            // B acc a1, a2, c1, c2
            port(HanoiOptimalMonitor.class, "ac").accepts(LeftHanoiPeg.class, add, remove, RightHanoiPeg.class, add, remove);

          // C req a1,b2 + a2,b1
          port(HanoiOptimalMonitor.class, "ab").requires(LeftHanoiPeg.class, add, MiddleHanoiPeg.class, remove);
          port(HanoiOptimalMonitor.class, "ab").requires(LeftHanoiPeg.class, remove, MiddleHanoiPeg.class, add);

          // C acc a1, a2, b1, b2
          port(HanoiOptimalMonitor.class, "ab").accepts(LeftHanoiPeg.class, add, remove, MiddleHanoiPeg.class, add, remove);

          data(LeftHanoiPeg.class, "disksize").to(MiddleHanoiPeg.class, "addedDisk");
          data(LeftHanoiPeg.class, "disksize").to(RightHanoiPeg.class, "addedDisk");

          data(MiddleHanoiPeg.class, "disksize").to(LeftHanoiPeg.class, "addedDisk");
          data(MiddleHanoiPeg.class, "disksize").to(RightHanoiPeg.class, "addedDisk");

          data(RightHanoiPeg.class, "disksize").to(MiddleHanoiPeg.class, "addedDisk");
          data(RightHanoiPeg.class, "disksize").to(LeftHanoiPeg.class, "addedDisk");
        
    }
}
