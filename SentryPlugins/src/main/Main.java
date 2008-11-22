package main;

import java.io.IOException;

import plugins.irc.IRCPlugin;
import sentry.plugs.IPlugin;

public class Main {

	public static IPlugin[] getPlugins(){
		IPlugin[] plugs = new IPlugin[1];
		plugs[0] = null;
		try{
			plugs[0] = new IRCPlugin();
		}catch(IOException ie){
			ie.printStackTrace(System.err);
		}
		return plugs;
	}
	
	public static void main(String[] args){
		
	}
}
