package org.bip.spec.pubsub.nativ;



public class CommandHandler3 implements Runnable{
	private CommandBuffer3 command_buff;
	private TopicManager3 topic_manager;
	
	public CommandHandler3(CommandBuffer3 buff, TopicManager3 manager){
		this.command_buff=buff;
		this.topic_manager=manager;
	}

	@Override
	public void run() {
		while(true){
			// System.out.println("Command Handler: waiting for a command");
            topic_manager.executeCommand(command_buff.getCommand());
        }
		
	}
	
}
