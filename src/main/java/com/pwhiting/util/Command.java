package com.pwhiting.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;

public class Command {

	private final String command;
	private List<String> args = new ArrayList<String>();
	private boolean isFinalized;
	
	public Command() {
		command = "";
	}
	
	public Command(Object obj) {
		this(String.valueOf(obj));
	}
	
	public Command(String command) {
		this.command = command;
	}
	
	public Command(String command, String[] args) {
		this(command);
		for (String arg : args) {
			this.args.add(arg);
		}
	}
	
	/**
	 * Takes a value and automatically prepends '-',
	 * then adds to total arguments.
	 * 
	 * @param flag
	 * @return this
	 */
	public Command addFlag(String flag) {
		if (!isFinalized) args.add('-' + flag);
		return this;
	}
	
	/**
	 * Adds {@code arg} to the arguments list.
	 * 
	 * @param arg
	 * @return this
	 */
	public Command addArgument(String arg) {
		if (!isFinalized) args.add(arg);
		return this;
	}
	
	/**
	 * Adds and argument of the form "--name=value".
	 * 
	 * @param name
	 * @param value
	 * @return this
	 */
	public Command addParameter(String name, String value) {
		if (!isFinalized) args.add("--" + name + '=' + value);
		return this;
	}
	
	/**
	 * Makes this immutable so no more args can be stored.
	 * Extra ones can still be used with {@link Command#execute(String[])}
	 * 
	 * @return this
	 */
	public Command setFinal() {
		isFinalized = true;
		return this;
	}
	
	/**
	 * Executes the command with the current arguments.
	 * 
	 * @return the output
	 * @throws IOException
	 */
	public String execute() throws IOException {
		String[] args = this.args.toArray(new String[this.args.size()]);
		return CommandLineUtils.executeCommand(command, args);
	}
	
	public String execute(Object...args) throws IOException {
		String[] args2 = new String[args.length];
		for (int i = 0; i < args.length; ++i) {
			args2[i] = String.valueOf(args[i]); 
		}
		return execute(args2);
	}
	
	/**
	 * Runs with given args in addition to stored args. Given args are not stored.
	 * 
	 * @param args
	 * @return this
	 * @throws IOException
	 */
	public String execute(String...args) throws IOException {
		List<String> temp = new ArrayList<String>(this.args);
		temp.addAll(Arrays.asList(args));
		return CommandLineUtils.executeCommand(command, temp.toArray(new String[temp.size()]));
	}
	
	
	public static class CommandChain implements Iterable<Command> {
		
		private final List<Command> commands;
		
		public CommandChain(Command...commands) {
			this.commands = Lists.newArrayList(commands);
		}
		
		public void addCommand(Command command) {
			commands.add(command);
		}
		
		public String executeChain() throws IOException {
			StringBuilder value = new StringBuilder();
			for (Command command : this) {
				System.out.println("Executing command: " + command.command + " with args: " + command.args);
				String output = command.execute();
				System.out.println(output);
				value.append(output);
			}
			return value.toString();
		}

		@Override
		public Iterator<Command> iterator() {
			return commands.iterator();
		}
		
	}
	
}
