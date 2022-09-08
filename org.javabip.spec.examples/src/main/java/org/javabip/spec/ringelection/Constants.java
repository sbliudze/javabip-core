package org.javabip.spec.ringelection;

public class Constants {

    //transitions (algorithm related)
    public static final String GT = "GT";
    public static final String LT = "LT";
    public static final String EQ = "EQ";
    public static final String ELECTED = "ELECTED";
    public static final String NOT_ELECTED = "!ELECTED";

    //states
    public static final String ACTIVE = "active";
    public static final String PASSIVE = "passive";
    public static final String LEADER = "leader";
    public static final String FOLLOWER = "follower";

    //spec name
    public static final String PROCESS = "Process";
    public static final String LOGGER = "Logger";

    //actor system
    public static final String RING_ACTOR_SYSTEM = "RingActorSystem";
    public static final String PROCESS_SPEC = "ProcessSpec";
    public static final String LOGGER_SPEC = "LoggerSpec";


    //transitions (communication related)
    public static final String SEND = "send";
    public static final String SEND_DONE = "sendDone";
    public static final String RECEIVE = "receive";

    //transitions (communication related)
    public static final String SEND_OWN = "sendOwn";
    public static final String SEND_LEADER = "sendLeader";
    public static final String SEND_OTHER = "sendOther";
    public static final String RECEIVE_OWN = "receiveOwn";
    public static final String RECEIVE_LEADER = "receiveLeader";
    public static final String RECEIVE_BIG = "receiveBig";
    public static final String RECEIVE_SMALL = "receiveSmall";

    public static final String LOG = "log";

    // guards
    public static final String CHECK_SENDER = "checkSender";
    public static final String GD_RECEIVED_LEADER = "receivedLeader";
    public static final String GD_RECEIVED_SMALL = "receivedSmall";
    public static final String GD_RECEIVED_BIG = "receivedBig";
    public static final String GD_RECEIVED_OWN = "receivedOwn";
    public static final String GD_ELECTED = "guardElected";
    public static final String GD_NOT_ELECTED = "!guardElected";

    //states (communication related)
    public static final String SENT = "sent";
    public static final String RECEIVED = "received";
    public static final String INITIAL = "initial";
    public static final String DONE = "done";

    //data agnostic
    public static final String OUTGOING_PROCESS_HEADER = "outgoingHeader";
    public static final String INCOMING_PROCESS_HEADER = "incomingHeader";
    public static final String OUTGOING_PROCESS_DATA = "outgoingData";
    public static final String INCOMING_PROCESS_DATA = "incomingData";

    //constants
    public static final String ENGINE = "engine";
}
