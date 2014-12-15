package org.bip.spec.pubsub.nativ;


import lsr.concurrence.provided.server.CommandID;

public class Command3
{
    private CommandID id;
    private String topic;
    private String message;
    private ClientProxy3 cproxy;
   
    public Command3(ClientProxy3 cproxy, CommandID id, String topic, String message)
    {
        this.cproxy=cproxy;
        this.id=id;
        this.topic=topic;
        this.message=message;
    }

    public ClientProxy3 getClientProxy()
    {
        return this.cproxy;
    }

    public CommandID getId()
    {
        return this.id;
    }

    public String getTopic()
    {
        return this.topic;
    }
   
    public String getMessage()
    {
        return this.message;
    }    

}
