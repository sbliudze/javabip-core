/*
 * Copyright (c) 2013 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2013, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 15/07/2013
 */
package org.bip.spec.hanoi;

import org.bip.glue.GlueBuilder;

public class HanoiRandomGlueBuilder extends GlueBuilder {

    @Override
    public void configure() {

            String add = "pieceAdd";
            String remove = "pieceRemove";

            port(HanoiPeg.class, add).requires(HanoiPeg.class, remove);
            port(HanoiPeg.class, remove).requires(HanoiPeg.class, add);

            port(HanoiPeg.class, add).accepts(HanoiPeg.class, remove);
            port(HanoiPeg.class, remove).accepts(HanoiPeg.class, add);

            data(HanoiPeg.class, "disksize").to(HanoiPeg.class, "addedDisk");
        
    }
}
