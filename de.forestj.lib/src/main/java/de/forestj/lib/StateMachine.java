package de.forestj.lib;

/**
 * 
 * Collection of classes to realize a finite state automation system or state machine.
 *
 */
public class StateMachine {
	
	/* Constant */
	
	public static final String EXIT = "EXIT";
	
	/* Fields */
	
	private boolean b_ready;
	private java.util.List<String> a_states;
	private java.util.List<String> a_returnCodes;
	private java.util.List<Transition> a_transitions;
	private java.util.List<StateMethodContainer> a_stateMethods;
	
	/* Properties */
	
	/* Methods */
	
	/**
	 * Creates a state machine object which is not ready for executing - declaring which states and return codes are valid
     * 
     * @param p_a_states							States of state machine; only capital characters; 'EXIT' is not allowed and will be added automatically at the end of the constructor
     * @param p_a_returnCodes						Return codes of state machine; only capital characters; 'EXIT' is not allowed and will be added automatically at the end of the constructor
     * @throws IllegalArgumentException				States or Return codes parameter are empty
     * @see de.forestj.lib.StateMachine.State
     * @see de.forestj.lib.StateMachine.ReturnCode
     */
	public StateMachine(java.util.List<String> p_a_states, java.util.List<String> p_a_returnCodes) {
		if ( (p_a_states == null) || (p_a_states.size() < 1) ) {
			throw new IllegalArgumentException("States parameter is empty");
		}
		
		if ( (p_a_returnCodes == null) || (p_a_returnCodes.size() < 1) ) {
			throw new IllegalArgumentException("Return codes parameter is empty");
		}
		
		this.b_ready = false;
		this.a_states = new java.util.ArrayList<String>();
		this.a_returnCodes = new java.util.ArrayList<String>();
		this.a_transitions = new java.util.ArrayList<Transition>();
		this.a_stateMethods = new java.util.ArrayList<StateMethodContainer>();
		
		/* filter out 'EXIT' state */
    	for (String s_state : p_a_states) {
    		if (s_state != StateMachine.EXIT) {
    			this.a_states.add(s_state.toUpperCase());
    		}
		}
    	
    	/* filter out 'EXIT' return code */
    	for (String s_returnCode : p_a_returnCodes) {
    		if (s_returnCode != StateMachine.EXIT) {
    			this.a_returnCodes.add(s_returnCode.toUpperCase());
    		}
		}
    	
    	/* add 'EXIT' state and return code at the end */
    	this.a_states.add(StateMachine.EXIT);
    	this.a_returnCodes.add(StateMachine.EXIT);
	}
	
	/**
	 * Add a transition to state machine configuration, with at least one transition state machine is ready for execution
	 * 
	 * @param p_s_fromState		Entering state which leads to this transition; only capital characters
	 * @param p_s_returnCode	Return code at the end of the current state; only capital characters
	 * @param p_s_toState		Next state after combination of entering state and return code; only capital characters
	 * @throws IllegalArgumentException		Invalid state or return code parameter
	 * @see de.forestj.lib.StateMachine.State
	 * @see de.forestj.lib.StateMachine.ReturnCode
	 */
	public void addTransition(String p_s_fromState, String p_s_returnCode, String p_s_toState) throws IllegalArgumentException {
		p_s_fromState = p_s_fromState.toUpperCase();
		p_s_returnCode = p_s_returnCode.toUpperCase();
		p_s_toState = p_s_toState.toUpperCase();
    	
		if (!this.a_states.contains(p_s_fromState)) {
    		throw new IllegalArgumentException("Invalid state '" + p_s_fromState + "', valid states are [" + de.forestj.lib.Helper.joinList(this.a_states, ',') + "]");
    	}
    	
    	if (!this.a_returnCodes.contains(p_s_returnCode)) {
    		throw new IllegalArgumentException("Invalid return code '" + p_s_returnCode + "', valid states are [" + de.forestj.lib.Helper.joinList(this.a_returnCodes, ',') + "]");
    	}
    	
    	if (!this.a_states.contains(p_s_toState)) {
    		throw new IllegalArgumentException("Invalid state '" + p_s_toState + "', valid states are [" + de.forestj.lib.Helper.joinList(this.a_states, ',') + "]");
    	}
    	
    	this.a_transitions.add(new Transition(p_s_fromState, p_s_returnCode, p_s_toState));
    	
    	this.b_ready = true;
    }
	
	/**
	 * Execute the current state method depending of state parameter.
	 * optional parameters possible.
	 * 
	 * @param p_s_state		information about the current state of the state machine; only capital characters
	 * @param p_a_param		optional parameters as dynamic object list
	 * @return ReturnCode	return code of a state transition
	 * @throws Exception	any exception from execution of state method
	 * @throws IllegalStateException	State machine is not ready, transition configuration must be loaded first
	 * @see de.forestj.lib.StateMachine.State
	 * @see de.forestj.lib.StateMachine.ReturnCode
	 */
	public String executeStateMethod(String p_s_state, java.util.List<Object> p_a_param) throws Exception {
		if (!this.b_ready) {
			throw new IllegalStateException("State machine is not ready. Please load a transition configuration first");
		}
		
		/* look for state method in dynamic list */
		for (StateMethodContainer o_stateMethodContainer : this.a_stateMethods) {
			/* state method state must be equal to state parameter */
			if (o_stateMethodContainer.getState().contentEquals(p_s_state.toUpperCase())) {
				/* execute state method shell with optional parameters */
				return o_stateMethodContainer.getStateMethodInterface().MethodShell(p_a_param);
			}
		}
		
		/* return exit state if no state method was found */
		return StateMachine.EXIT;
	}
	
	/**
	 * Adding a state method to dynamic list of state machine
	 * 
	 * @param o_stateMethodContainer	state method container
	 * @throws IllegalStateException	State machine is not ready, transition configuration must be loaded first
	 * @see de.forestj.lib.StateMachine.StateMethodContainer
	 */
	public void addStateMethod(StateMethodContainer o_stateMethodContainer) throws IllegalStateException {
		if (!this.b_ready) {
			throw new IllegalStateException("State machine is not ready. Please load a transition configuration first");
		}
		
		boolean b_found = false;
		
		/* look if state of state method is in state dynamic list */
		for (String s_state : this.a_states) {
			if (o_stateMethodContainer.getState().contentEquals(s_state)) {
				b_found = true;
			}
		}
		
		/* invalid state found */
		if (!b_found) {
			throw new IllegalStateException("Invalid state '" + o_stateMethodContainer.getState() + "', valid states are [" + de.forestj.lib.Helper.joinList(this.a_states, ',') + "]");
		}
		
		/* add method container to dynamic list */
		this.a_stateMethods.add(o_stateMethodContainer);
	}
	
	/**
	 * Lookup transitions to get the next state of state machine based on current state and return code of it's state method
	 * 
	 * @param p_s_currentState		current state of state machine
	 * @param p_s_returnCode		return code of state method
	 * @return State
	 * @throws IllegalStateException	State machine is not ready, transition configuration must be loaded first - or transition configuration is incomplete
	 * @see de.forestj.lib.StateMachine.State
	 * @see de.forestj.lib.StateMachine.ReturnCode
	 */
	public String lookupTransitions(String p_s_currentState, String p_s_returnCode) throws IllegalStateException {
		if (!this.b_ready) {
			throw new IllegalStateException("State machine is not ready. Please load a transition configuration first");
		}
		
		String s_state = null;
		
		/* iterate each transition of state machine */
		for (Transition o_transition : this.a_transitions) {
			boolean currentStateMatches = o_transition.getFromState().equals(p_s_currentState.toUpperCase());
			boolean conditionsMatch = o_transition.getReturnCode().equals(p_s_returnCode.toUpperCase());

			/* state and return code conditions are matching */
			if (currentStateMatches && conditionsMatch) {
				s_state = o_transition.getToState();
				break;
			}
		}
		
		/* if state is null our transition configuration is incomplete */
		if (s_state == null) {
			throw new IllegalStateException("There was no transition found for current state[" + p_s_currentState.toUpperCase() + "] and return code[" + p_s_returnCode.toUpperCase() + "].");
		}
		
		/* return next state */
		return s_state;
	}
		
	/* Internal Classes */
	
	/**
	 * 
	 * Interface declaration to define the shell of a state method which must be used to fill the state machine
	 *
	 */
	public interface StateMethodInterface {
		/**
		 * Empty method shell for a state method container
		 * 
		 * @param p_a_param		optional dynamic list of objects as parameter
		 * @return				Return code after state method was executed
		 * @throws Exception	Any kind of exception which can happen during the state
		 * @see de.forestj.lib.StateMachine.ReturnCode
		 */
		String MethodShell(java.util.List<Object> p_a_param) throws Exception;
	}
	
	/**
	 * 
	 * Internal state machine class to encapsulate a state method as a container; can only be set on creation
	 *
	 */
	public class StateMethodContainer {

		/* Fields */
		
		private String s_state;
		private StateMethodInterface o_smi;
		
		/* Properties */
		
		public String getState() {
			return this.s_state;
		}
		
		public StateMethodInterface getStateMethodInterface() {
			return this.o_smi;
		}
		
		/* Methods */
		
		/**
		 * Constructor
		 * 
		 * @param p_s_state		State of state method as string value
		 * @param p_o_smi		Method shell of state method
		 */
		public StateMethodContainer(String p_s_state, StateMethodInterface p_o_smi) {
			this.s_state = p_s_state.toUpperCase();
			this.o_smi = p_o_smi;
		}
	}
	
	/**
	 * 
	 * Internal state machine class to encapsulate a transition; can only be set on creation
	 *
	 */
	private class Transition {

		/* Fields */
		
		private String s_fromState;
		private String s_returnCode;
		private String s_toState;
		
		/* Properties */
		
		public String getFromState() {
			return this.s_fromState;
		}
		
		public String getReturnCode() {
			return this.s_returnCode;
		}
		
		public String getToState() {
			return this.s_toState;
		}
		
		/* Methods */
		
		/**
		 * Constructor
		 *  
		 * @param p_s_fromState		Entering state which leads to this transition
		 * @param p_s_returnCode	Return code at the end of the current state
		 * @param p_s_toState		Next state after combination of entering state and return code
		 * @see de.forestj.lib.StateMachine.State
		 * @see de.forestj.lib.StateMachine.ReturnCode
		 */
		public Transition(String p_s_fromState, String p_s_returnCode, String p_s_toState) {
			this.s_fromState = p_s_fromState;
			this.s_returnCode = p_s_returnCode;
			this.s_toState = p_s_toState;
		}
	}
}
