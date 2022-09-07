package org.javabip.spec.ringelection;

import org.javabip.annotations.*;
import org.javabip.annotations.Port;
import org.javabip.api.PortType;
import org.javabip.annotations.Data;

import static org.javabip.spec.ringelection.Constants.*;

@Ports({
        @Port(name = SEND, type = PortType.enforceable),
        @Port(name = SEND_DONE, type = PortType.enforceable),
        @Port(name = RECEIVE, type = PortType.enforceable)
})

@ComponentType(initial = INITIAL, name = PROCESS)
@Invariant("isLeader == false || processID == 0")
public class ProcessSpec {
    private int processID;
    private boolean isActive;
    private boolean isLeader;
    private boolean isInitiator;
    private boolean isElected = false;
    private ProcessSpec leftNeighbour;
    private ProcessSpec rightNeighbour;
    private int round = 0;

    ProcessMessage processMessage;

    public ProcessSpec(int processID, boolean isActive, boolean isInitiator) {
        this.processID = processID;
        this.isActive = isActive;
        this.isInitiator = isInitiator;
        this.isLeader = false;
        processMessage = new ProcessMessage(ProcessMessage.MessageType.ELECTION, round, processID);
    }


    @Transitions({
        @Transition(name = SEND, source = INITIAL, target = SENT, pre = "isLeader == true", post = "isActive == false"),
        @Transition(name = SEND, source = RECEIVED, target = SENT, guard = GD_NOT_ELECTED),
        @Transition(name = SEND_DONE, source = RECEIVED, target = DONE, guard = GD_ELECTED)
    })
    public void send() {
        System.out.println("Process " + processID + " has sent #" + processMessage.processID);
    }

    @Transitions({
            @Transition(name = RECEIVE, source = SENT, target = RECEIVED, guard = CHECK_SENDER),
            @Transition(name = RECEIVE, source = INITIAL, target = RECEIVED, guard = CHECK_SENDER),
            @Transition(name = RECEIVE, source = DONE, target = DONE, guard = CHECK_SENDER)
    })
    public void receive(@Data(name = INCOMING_PROCESS_DATA) ProcessMessage processMessage, @Data(name = INCOMING_PROCESS_HEADER) int header) {
        System.out.println("Process " + processID + " has received #" + processMessage.processID);
        compute(processMessage);
    }

    //@Transition(name = COMPUTE, source = RECEIVED, target = DONE)
    public void compute(ProcessMessage processMessage) {

        if (!isElected) {
            //check if this is a notification message of elected leader
            if (processMessage.messageType.equals(ProcessMessage.MessageType.LEADER)) {
                this.processMessage = processMessage;
                this.isElected = true;
            }

            // if the process is in active state, compare its id with the incoming id
            if (this.isActive) {
                int leftProcessID = processMessage.processID;

                //if the incoming id is smaller, dismiss it and send own id to the next neighbour
                if (leftProcessID < this.processID) {
                    processMessage.round++;
                    //this.processMessage = new ProcessMessage(ProcessMessage.MessageType.ELECTION, round++, this.processID);
                }

                //if the incoming id is bigger, this process becomes passive and translates incoming id to the next neighbour
                else if (leftProcessID > this.processID) {
                    this.isActive = false;
                    this.processMessage = processMessage;
                    System.out.println("Process " + processID + " is now passive");
                }

                //if the incoming id is equal to this.id, this process becomes the leader
                else {
                    this.isLeader = true;
                    this.isElected = true;
                    this.processMessage = new ProcessMessage(ProcessMessage.MessageType.LEADER, round++, this.processID);
                    System.out.println("Process " + processID + ": I am the leader!");
                }
            }

            //otherwise, if the process is already passive, just pass the message further
            else {
                this.processMessage = processMessage;
                System.out.println("Process " + processID + ": Passing the highest id (" + processMessage.processID + ")");
            }
        }

    }

    public boolean isInitiator() {
        return isInitiator;
    }

    @Guard(name = GD_ELECTED)
    public boolean isElected() {
        return isElected;
    }

    public int getRound() {
        return round;
    }

    private boolean areNeighboursSet() {
        return leftNeighbour != null && rightNeighbour != null;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isLeader() {
        return isLeader;
    }

    public void setLeftNeighbour(ProcessSpec leftNeighbour) {
        this.leftNeighbour = leftNeighbour;
    }

    public void setRightNeighbour(ProcessSpec rightNeighbour) {
        this.rightNeighbour = rightNeighbour;
    }

    @Data(name = OUTGOING_PROCESS_HEADER)
    public int getProcessID() {
        return processID;
    }

    @Data(name = OUTGOING_PROCESS_DATA)
    public ProcessMessage getProcessMessage() throws InterruptedException {
        return processMessage;
    }

    @Guard(name = CHECK_SENDER)
    public boolean checkSender(@Data(name = INCOMING_PROCESS_HEADER) int header) {
        return header == leftNeighbour.processID;
    }

    @Override
    public String toString() {
        return "\nProcess{" +
                "processID=" + processID +
                //", isActive=" + isActive +
                ", isLeader=" + isLeader +
                //", isInitiator=" + isInitiator +
                ", isElected=" + isElected +
                ", leftNeighbour=" + (leftNeighbour == null ? "null" : leftNeighbour.processID) +
                ", rightNeighbour=" + (rightNeighbour == null ? "null" : rightNeighbour.processID) +
                "}";
    }
}
