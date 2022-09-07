package org.javabip.spec.ringelection;

public class ProcessMessage {
    MessageType messageType;
    int round;
    int processID;

    public ProcessMessage(MessageType messageType, int round, int processID) {
        this.messageType = messageType;
        this.round = round;
        this.processID = processID;
    }

    enum MessageType {
        ELECTION, LEADER;
    }

    public ProcessMessage copy(){
        return new ProcessMessage(messageType, round, processID);
    }
}
