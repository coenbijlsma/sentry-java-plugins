package plugins.irc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import sentry.plugs.IPlugin;
import sentry.plugs.IPluginCommand;

public class IRCPlugin implements IPlugin {

	private String _name;
	private HashMap<String, IPluginCommand> _commands;
	
	public IRCPlugin(){
		_name = "core.irc";
		_commands = new HashMap<String, IPluginCommand>();
		_initCommands();
	}
	
	private void _initCommands(){
		// XXX replace values by values read from config file
		Connect connect = new Connect(this, "localhost", 6667, "foo", "sentry", "sentry");
		_commands.put(connect.getName(), connect);
	}
	
	private IPluginCommand _getCommandByName(String name){
		return _commands.get(name);
	}
	
	@Override
	public String getName() {
		return _name;
	}

	@Override
	public String[] getDependencies() {
		return null;
	}

	@Override
	public Collection<IPluginCommand> getCommands() {
		return _commands.values();
	}

	@Override
	public Collection<IPluginCommand> getCommandsByHookPoint(String hook) {
		ArrayList<IPluginCommand> retval = new ArrayList<IPluginCommand>();
		
		for(IPluginCommand cmd : getCommands()){
			if(cmd.getHookPoint().equalsIgnoreCase(hook)){
				retval.add(cmd);
			}
		}
		
		return retval;
	}
	
}
