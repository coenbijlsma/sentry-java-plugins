package plugins.irc;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import sentry.plugs.IPlugin;
import sentry.plugs.IPluginCommand;

public class IRCPlugin implements IPlugin {

	private static final String _propertiesFile = "core.irc.properties";
	
	/*
	 * The name of the plug-in
	 */
	private String _name;
	
	/*
	 * The commands available in this plug-in
	 */
	private HashMap<String, IPluginCommand> _commands;
	
	/*
	 * The configuration of this plug-in
	 */
	private Properties _properties;
	
	public IRCPlugin() throws InvalidPropertiesFormatException, IOException {
		_properties = new Properties();
		_properties.loadFromXML(new FileInputStream(_propertiesFile));
		_name = _properties.getProperty("plugin.name");
		_commands = new HashMap<String, IPluginCommand>();
		_initCommands();
	}
	
	private void _initCommands(){
		Connect connect = new Connect(this, _properties);
		_commands.put(connect.getName(), connect);
	}
	
	private IPluginCommand _getCommandByName(String name){
		return _commands.get(name);
	}
	
	protected Properties getProperties(){
		return _properties;
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
