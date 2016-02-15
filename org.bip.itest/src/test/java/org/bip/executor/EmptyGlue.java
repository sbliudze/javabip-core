package org.bip.executor;
/*
 * Copyright (c) 2011 Crossing-Tech TM Switzerland. All right reserved.
 * User: radoslaw
 * Date: 11/27/15
 *   ___                      _   _             ___         _
 *  / __|___ _ _  _ _  ___ __| |_(_)__ ___  _  | __|__ _ __| |_ ___ _ _ _  _
 * | (__/ _ \ ' \| ' \/ -_) _|  _| |\ V / || | | _|/ _` / _|  _/ _ \ '_| || |
 *  \___\___/_||_|_||_\___\__|\__|_| \_/ \_, | |_| \__,_\__|\__\___/_|  \_, |
 *                                       |__/                           |__/
 */

import org.bip.api.Accept;
import org.bip.api.BIPGlue;
import org.bip.api.DataWire;
import org.bip.api.Require;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class EmptyGlue implements BIPGlue {
    @Override
    public List<Accept> getAcceptConstraints() {
        return new ArrayList<Accept>();
    }

    @Override
    public List<Require> getRequiresConstraints() {
        return new ArrayList<Require>();
    }

    @Override
    public List<DataWire> getDataWires() {
        return new ArrayList<DataWire>();
    }

    @Override
    public void toXML(OutputStream outputStream) {
    }

}
