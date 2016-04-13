package org.bip.spec.pubsub;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class InputReader {
    private CommandID id;
    private String topic;
    private String message;
    private BufferedReader input;

    public InputReader(InputStream is) {
        this.id = CommandID.NEWCLIENT;
        this.topic = null;
        this.message = null;
        this.input = new BufferedReader(new InputStreamReader(is));
    }

    public CommandID getCommandId() {
        return this.id;
    }

    public String getTopic() {
        return this.topic;
    }

    public String getMessage() {
        return this.message;
    }

    public void readCommand() throws IOException, InputFormatException {
        String line = this.input.readLine();
        if(line == null) {
            this.id = CommandID.ENDOFCLIENT;
        } else {
            String[] parts = line.split("\\{", 2);
            String[] subparts = parts[0].split("[ ]+");
            if(subparts[0].equals("subscribe")) {
                this.id = CommandID.SUBSCRIBE;
            } else if(subparts[0].equals("unsubscribe")) {
                this.id = CommandID.UNSUBSCRIBE;
            } else {
                if(!subparts[0].equals("publish")) {
                    throw new InputFormatException("Unknown Command");
                }

                this.id = CommandID.PUBLISH;
            }

            if(subparts.length != 2) {
                throw new InputFormatException("Error in Topic");
            }

            this.topic = subparts[1];
            this.message = null;
            if(this.id == CommandID.PUBLISH) {
                if(!line.contains("{")) {
                    throw new InputFormatException("Missing Message");
                }

                this.message = "{";
                if(parts.length > 1) {
                    this.message = this.message + parts[1];
                }

                while(!this.message.contains("}")) {
                    this.message = this.message + "\n";
                    this.message = this.message + this.input.readLine();
                }
            }
        }

    }
}
