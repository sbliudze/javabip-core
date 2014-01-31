package org.bip.glue;

/*
* Copyright (c) 2012 Crossing-Tech TM Switzerland. All right reserved.
* Copyright (c) 2012, RiSD Laboratory, EPFL, Switzerland.
*
* Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
* Date: 10/15/12
*/
public abstract class TwoSynchronGlueBuilder extends GlueBuilder {

    public TwoSynchronGlueBuilder() {
        super();
    }

    public SynchronBuilder synchron(Class<?> spec, String portId) {

        if (spec == null)
            throw new IllegalArgumentException("Spec type can not be null");
        if (portId == null)
            throw new IllegalArgumentException("PortId can not be null.");

        return new SynchronBuilder(portId, spec);

    }

    public class SynchronBuilder {

        private String fromPortId;
        private Class<?> fromSpec;

        public SynchronBuilder(String portId, Class<?> spec) {
            this.fromPortId = portId;
            this.fromSpec = spec;
        }

        public void to(Class<?> spec, String portId) {

            if (spec == null)
                throw new IllegalArgumentException("Spec type can not be null");
            if (portId == null)
                throw new IllegalArgumentException("DataId can not be null.");
            if (portId.equals(""))
                throw new IllegalArgumentException("PortId can not be an empty string.");

            port(fromSpec, fromPortId).requires(spec, portId);

            port(fromSpec, fromPortId).accepts(spec, portId);

            port(spec, portId).requires(fromSpec, fromPortId);

            port(spec, portId).accepts(fromSpec, fromPortId);

        }

    }


}
