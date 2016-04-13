package org.bip.spec.pubsub;

public enum CommandID {
    SUBSCRIBE,
    UNSUBSCRIBE,
    PUBLISH,
    NEWCLIENT,
    ENDOFCLIENT;

    CommandID() {
    }
}
