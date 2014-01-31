package io.xtech.bip.activator;

/**
 * User: radoslaw szymanek
 * Date: 5/31/12
 * Copyright 2012 Crossing-Tech
 */

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class ServiceActivator implements BundleActivator {

    public void start(BundleContext ctx) throws Exception {

        System.out.println("Bundle providing BIP Admin commands for Simple usecase is started.");

    }

    public void stop(BundleContext ctx) {

        System.out.println("Bundle providing BIP Admin commands for Simple usecase is stopped.");

    }

}