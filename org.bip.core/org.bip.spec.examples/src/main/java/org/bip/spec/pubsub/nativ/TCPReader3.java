package org.bip.spec.pubsub.nativ;

import java.io.IOException;
import java.net.Socket;

import lsr.concurrence.provided.server.CommandID;
import lsr.concurrence.provided.server.InputFormatException;
import lsr.concurrence.provided.server.InputReader;


public class TCPReader3 implements Runnable{
	
	private ClientProxy3 cproxy;
	private Socket client_sock;
	private CommandBuffer3 command_buff;
	private InputReader reader;
 	
	public TCPReader3(Socket sock, long id, CommandBuffer3 buff) throws IOException
    {
		this.cproxy= new ClientProxy3(id, sock.getOutputStream());
		this.client_sock=sock;
		this.command_buff=buff;
		this.reader = new InputReader(this.client_sock.getInputStream());
    }
	
	public void clean(){
		try{
            this.client_sock.close();
        }
        catch (IOException e) {
            System.err.println("IOException: "+e.getMessage());
        }

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
        boolean connected=true;
		// System.out.println("New client with id " + this.cproxy.getId());
        
		// this.cproxy.write("connection_ack "+this.cproxy.getId());

        while(connected==true){
            try{
                reader.readCommand();
                this.command_buff.putCommand(new Command3(this.cproxy,reader.getCommandId(), reader.getTopic(), reader.getMessage()));
                if(reader.getCommandId() == CommandID.ENDOFCLIENT){
                    connected=false;
                }
            } catch (IOException e) {
                System.err.println("IOException: "+e.getMessage());
                connected=false;
            } catch (InputFormatException e) {
                System.err.println(e+ " : "+e.getMessage());
            }
        }

        this.clean();
        
	}

}
