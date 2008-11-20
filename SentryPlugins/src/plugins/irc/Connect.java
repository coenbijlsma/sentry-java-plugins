package plugins.irc;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

import sentry.plugs.IPlugin;
import sentry.plugs.IPluginCommand;

public class Connect implements IPluginCommand {

	/* Configuration for the plugin */
	private String _name = "connect";
	private String _hookPoint = "post_startup";
	
	private IPlugin _plugin;
	private String _host;
	private int _port;
	
	private String _pass;
	private String _nick;
	private String _user;
	
	private Socket _socket;
	
	public Connect(IPlugin plugin, String host, int port, String pass, String nick, String user){
		_plugin = plugin;
		_host = host;
		_port = port;
		_pass = pass;
		_nick = nick;
		_user = user;
		_socket = null;
	}
		
	@Override
	public void execute() {
		System.out.println("Executing command " + _name);
		
		if(_socket == null){
			try{
				_socket = new Socket();
				_socket.setReuseAddress(true);
				_socket.connect(new InetSocketAddress(_host, _port));
			}catch(SocketException se){
				se.printStackTrace(System.err);
			}catch(IOException ie){
				ie.printStackTrace(System.err);
			}
		}else{
			System.out.println("Already connected");
		}
	}

	@Override
	public String getHookPoint() {
		return _hookPoint;
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
	public Object getResult() {
		return _socket;
	}

	@Override
	public IPlugin getPlugin() {
		return _plugin;
	}

}
