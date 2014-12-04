/*
 * Copyright (c) 2013 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2013, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 15/07/2013
 */

package org.bip.spec.pubsub;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.bip.annotations.Data;
import org.bip.annotations.ExecutableBehaviour;
import org.bip.api.DataOut.AccessType;
import org.bip.api.PortType;
import org.bip.executor.BehaviourBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Client {

	Logger logger = LoggerFactory.getLogger(Client.class);

	final private MessageActuator actuator;

	final List<Message> messages;
	final List<Subject> messagesSubjects;	
	
	boolean connected;	
	boolean connecting;
	
	final List<Message> messageToBePublished;
	final List<Subject> subjectOfMessageToBePublished;
	
	final List<Subject> subscriptionInterest;
	
	final Set<Subject> currentSubscriptions;

	private boolean status;
	
	private String clientId;
	
	public Client(MessageActuator actuator) {

		this.actuator = actuator;
		this.messages = new LinkedList<Message>();
		this.messagesSubjects = new LinkedList<Subject>();
		this.connected = false;
		this.connecting = false;
		this.currentSubscriptions = new HashSet<Subject>();
		this.subscriptionInterest = new LinkedList<Subject>();
		messageToBePublished = new LinkedList<Message>();
		subjectOfMessageToBePublished = new LinkedList<Subject>();
		this.status = true;
	}

	public boolean getStatus() {
		return status;
	}
	
	private void act() {		
		actuator.act( messagesSubjects.remove(0), messages.remove(0) );		
	}
	
	public boolean actRequired() {
		return ! messagesSubjects.isEmpty() && ! messages.isEmpty();
	}
	
	public MessageActuator getMessageActuator() {
		return actuator;
	}
	
	// Asynchronous spontaneous event. 
	public void receiveMessage(@Data(name = "subject") Subject subject, 
							   @Data(name = "message") Message message) {
		
		boolean messageAdded = messages.add(message); 
		if ( messageAdded ) {
			
			boolean subjectAdded = messagesSubjects.add(subject);			
			
			if (!subjectAdded) {
				messages.remove( messages.size() - 1 );
			}
										
		};
	}
	
	public boolean requiresConnection() {
		return !connected;
	}
	
	public void connect(@Data(name = "clientId") String clientId) {
		connecting = true;
		this.clientId = clientId;
	}
	
	public void connectAck() {		
		connecting = false;
		connected = true;
	}
	
	@Data(name = "clientId", accessTypePort = AccessType.unallowed, ports = { "connect" })
	public String getClientId() {
		return clientId;
	}
	
	// functions getCurrentMessageBeingPublished and getSubjectOfCurrentMessageBeingPublished 
	// are used for getting data for data transfers. 
	public void publish() {
	}
	
	// Asynchronous spontaneous event. 
	public void addMessageToBePublished(@Data(name="subject") Subject subject, 
										@Data(name="message") Message message) {
		messageToBePublished.add(message);
		subjectOfMessageToBePublished.add(subject);
	}
		
	public boolean isInterestedInPublishing() {
		return (! messageToBePublished.isEmpty() && ! subjectOfMessageToBePublished.isEmpty() );
	}

	@Data(name = "message", accessTypePort = AccessType.allowed, ports = { "publish" })
	public Message getCurrentMessageBeingPublished() {
		return messageToBePublished.get(0);
	}

	@Data(name = "subjectOfMessage", accessTypePort = AccessType.allowed, ports = { "publish" })
	public Subject getSubjectOfCurrentMessageBeingPublished() {
		return subjectOfMessageToBePublished.get(0);
	}

	public void publishAck() {
		messageToBePublished.remove(0);
		subjectOfMessageToBePublished.remove(0);
	}
	
	// Asynchronous spontaneous event. 
	public void addSubscriptionInterest(@Data(name="subject") Subject subject) {
		subscriptionInterest.add(subject);			
	}
	
	public boolean isInterestedInSubscription() {
		
		while (true) {
			
			if ( subscriptionInterest.isEmpty() )
				return false;
			
			if ( currentSubscriptions.contains( subscriptionInterest.get(0) ) ) {
				subscriptionInterest.remove(0);
				continue;
			}
			
			return true;
			
		}
		
	}
	
	// TODO, is data being collected before guards are called? Is there any fixed relationship that we 
	// can rely on? Should we allow such a reliance? Please note that isInterestedInSubscription guard is operating on 
	// subscriptionInterest data structure.
	@Data(name = "subjectOfSubscription", accessTypePort = AccessType.allowed, ports = { "subscribe" })
	public Subject getSubjectOfSubscription() {
		return subscriptionInterest.get(0);
	}
	
	public void subsribe() {
	}
	
	public void subscribeAck() {
		currentSubscriptions.add(subscriptionInterest.remove(0));
	}
	
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	@ExecutableBehaviour
	public BehaviourBuilder initializeBehavior() throws NoSuchMethodException {

		BehaviourBuilder behaviourBuilder = new BehaviourBuilder(this);
		
		behaviourBuilder.setComponentType( this.getClass().getCanonicalName() );
		
		String initialState = "start";
		String connectingState = "connecting";
		String connectedState = "connected";
		String subscribingState = "subscribing";
		String publishingState = "publishing";
		
		behaviourBuilder.setInitialState(initialState);
		
		behaviourBuilder.addState(initialState);
		behaviourBuilder.addState(connectingState);
		behaviourBuilder.addState(subscribingState);
		behaviourBuilder.addState(publishingState);
		
		//PORTS
		
		behaviourBuilder.addPort("connect", PortType.enforceable, this.getClass());
		behaviourBuilder.addPort("connectAck", PortType.enforceable, this.getClass());
		
		behaviourBuilder.addPort("publish", PortType.enforceable, this.getClass());
		behaviourBuilder.addPort("publishAck", PortType.enforceable, this.getClass());
		
		behaviourBuilder.addPort("subscribe", PortType.enforceable, this.getClass());
		behaviourBuilder.addPort("subscribeAck", PortType.enforceable, this.getClass());
		
		behaviourBuilder.addPort("receive", PortType.spontaneous, this.getClass());
		behaviourBuilder.addPort("addMessage", PortType.spontaneous, this.getClass());
		behaviourBuilder.addPort("addSubscription", PortType.spontaneous, this.getClass());
		
		//TRANSITIONS

		behaviourBuilder.addTransitionAndStates("connect", initialState, connectingState, "requiresConnection", 
									   this.getClass().getMethod("connect") );
		behaviourBuilder.addTransitionAndStates("connectAck", connectingState, connectedState, "", 
									   this.getClass().getMethod("connectAck") );
		
		behaviourBuilder.addTransitionAndStates("subscribe", connectedState, subscribingState, "isInterestedInSubscription", 
				   this.getClass().getMethod("subsribe") );
		behaviourBuilder.addTransitionAndStates("subscribeAck", subscribingState, connectedState, "", 
				   this.getClass().getMethod("subsribeAck") );
				
		behaviourBuilder.addTransitionAndStates("publish", connectedState, publishingState, "isInterestedInPublishing", 
				   this.getClass().getMethod("publish") );
		behaviourBuilder.addTransitionAndStates("publishAck", publishingState, connectedState, "", 
				   this.getClass().getMethod("publishAck") );
						
		behaviourBuilder.addTransitionAndStates("receive", connectedState, connectedState, "", 
				   this.getClass().getMethod("receiveMessage") );
		
		behaviourBuilder.addTransitionAndStates("addMessage", connectedState, connectedState, "", 
				   this.getClass().getMethod("addMessageToBePublished") );
		
		behaviourBuilder.addTransitionAndStates("addSubscription", connectedState, connectedState, "", 
				   this.getClass().getMethod("addSubscriptionInterest") );
		

		// internal one.
		behaviourBuilder.addTransitionAndStates("act", connectedState, connectedState, "actRequired", 
				   this.getClass().getMethod("act") );
		
		//GUARDS
		
		behaviourBuilder.addGuard(this.getClass().getMethod("isInterestedInPublishing") );
		behaviourBuilder.addGuard(this.getClass().getMethod("isInterestedInSubscription"));
		behaviourBuilder.addGuard(this.getClass().getMethod("requiresConnection"));
		behaviourBuilder.addGuard(this.getClass().getMethod("actRequired"));
				
		//DATA OUT
				
		behaviourBuilder.addDataOut(this.getClass().getMethod("getSubjectOfSubscription") );
		behaviourBuilder.addDataOut(this.getClass().getMethod("getSubjectOfCurrentMessageBeingPublished") );
		behaviourBuilder.addDataOut(this.getClass().getMethod("getCurrentMessageBeingPublished") );
		behaviourBuilder.addDataOut(this.getClass().getMethod("getClientId") );
		//BUILD
		


		return behaviourBuilder;
	}
	
}
