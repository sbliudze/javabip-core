package org.bip.executor;

import java.io.IOException;
import java.net.ServerSocket;

import org.bip.spec.pubsub.nativ.CommandBuffer3;
import org.bip.spec.pubsub.nativ.CommandHandler3;
import org.bip.spec.pubsub.nativ.TCPReader3;
import org.bip.spec.pubsub.nativ.TopicManager3;
import org.junit.Test;


public class TCPAcceptorNative
{
	private static final int BUFFER_SIZE=10;
	private static final int HANDLER_POOL_SIZE=5;
	
	
	@Test
    public static void main(String [ ] args)
    {
        ServerSocket tcpacceptor =null;
        try {
            tcpacceptor = new ServerSocket(7676);
        } catch (IOException e) {
            System.err.println("Fail to listen on port 7676");
            System.exit(-1);
        }
        
		Thread tr1 = new Thread(new TestPubSub(true));
		tr1.start();

		// Thread tr2 = new Thread(new TestPubSub(true));
		// tr2.start();
		//
		// Thread tr3 = new Thread(new TestPubSub(true));
		// tr3.start();

        CommandBuffer3 buff = new CommandBuffer3(BUFFER_SIZE);        
        TopicManager3 top_manage= new TopicManager3();

        // starting the command handlers
        for(int i =0; i<HANDLER_POOL_SIZE; i++){
        	new Thread(new CommandHandler3(buff,top_manage)).start();
        }
        
        long id=0;
        
        while(true){
            try{
                Thread tr=new Thread(new TCPReader3(tcpacceptor.accept(),id++,buff));
                tr.start();
            } catch (IOException e) {
                System.err.println("Fail to accept client connection");
                System.exit(-1);
            }
        }
    }
    
}