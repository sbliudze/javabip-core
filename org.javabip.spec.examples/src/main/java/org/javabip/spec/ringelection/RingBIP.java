package org.javabip.spec.ringelection;

import akka.actor.ActorSystem;
import org.javabip.api.BIPEngine;
import org.javabip.api.BIPGlue;
import org.javabip.engine.factory.EngineFactory;
import org.javabip.glue.TwoSynchronGlueBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

import static org.javabip.spec.ringelection.Constants.*;

public class RingBIP {
    private ArrayList<ProcessSpec> processesList = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RingBIP ring = (RingBIP) o;
        return processesList.equals(ring.processesList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(processesList);
    }

    public RingBIP(int size, BIPEngine engine) throws Exception {

        //generate a list of processes of a given size where each process is initiator
        for (int id = 0; id < size; id++) {
            ProcessSpec processSpec = new ProcessSpec(id, true, true);
            processesList.add(processSpec);
            engine.register(processSpec, PROCESS_SPEC + id, true);
        }

        //randomize the order
        Collections.shuffle(processesList);

        //connect processes together in both directions
        int processListSize = processesList.size();

        for (int i = 1; i< processListSize; i++){
            ProcessSpec left = processesList.get(i - 1);
            ProcessSpec right = processesList.get(i);

            left.setRightNeighbour(right);
            right.setLeftNeighbour(left);
        }

        ProcessSpec first = processesList.get(0);
        ProcessSpec last = processesList.get(processListSize - 1);

        first.setLeftNeighbour(last);
        last.setRightNeighbour(first);
    }

    public ArrayList<ProcessSpec> getProcessesList() {
        return processesList;
    }

    public static void main(String[] args) throws Exception {
        ActorSystem system = ActorSystem.create(RING_ACTOR_SYSTEM);
        EngineFactory engineFactory = new EngineFactory(system);

        BIPGlue glue = new TwoSynchronGlueBuilder() {
            @Override
            public void configure() {
                synchron(ProcessSpec.class, SEND).to(ProcessSpec.class, RECEIVE);
                synchron(ProcessSpec.class, SEND_DONE).to(ProcessSpec.class, RECEIVE);
                data(ProcessSpec.class, OUTGOING_PROCESS_HEADER).to(ProcessSpec.class, INCOMING_PROCESS_HEADER);
                data(ProcessSpec.class, OUTGOING_PROCESS_DATA).to(ProcessSpec.class, INCOMING_PROCESS_DATA);
            }
        }.build();

        BIPEngine engine = engineFactory.create(ENGINE, glue);

        RingBIP ring = new RingBIP(10, engine);

        engine.start();
        engine.execute();


        while (true){
            Thread.sleep(5000);
            System.out.println(ring);
            System.out.println(Arrays.toString(ring.processesList.stream().map(ProcessSpec::getRound).toArray()));
        }

        /*
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        engine.stop();
        engineFactory.destroy(engine);
        system.shutdown();
        System.out.println(ring);*/
    }


    @Override
    public String toString() {
        return "Ring{" +
                "processesList=" + processesList +
                '}';
    }
}