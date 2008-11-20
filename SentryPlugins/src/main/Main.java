package main;

import plugins.irc.IRCPlugin;
import sentry.plugs.IPlugin;

public class Main {

	public static IPlugin[] getPlugins(){
		IPlugin[] plugs = new IPlugin[1];
		plugs[0] = new IRCPlugin();
		return plugs;
	}
	
	public static void main(String[] args){
		
	}
}
