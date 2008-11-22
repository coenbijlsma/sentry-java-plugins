package plugins.irc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Properties;

import sentry.plugs.IPlugin;
import sentry.plugs.IPluginCommand;

public class Connect implements IPluginCommand {

	/* Configuration for the plug-in */
	private String _name = "connect";
	private String _hookPoint;

	private IPlugin _plugin;

	private String _host;
	private int _port;
	private String _pass;
	private String _nick;
	private String _user;
	private String _channel;

	private Socket _socket;
	private BufferedWriter _bufferedWriter;
	private BufferedReader _bufferedReader;

	public Connect(IPlugin plugin, Properties properties) {
		_plugin = plugin;
		_hookPoint = properties.getProperty(_name + ".hook_point");
		_host = properties.getProperty("connect.host");
		_port = Integer.parseInt(properties.getProperty("connect.port"));
		_pass = properties.getProperty("connect.pass");
		_nick = properties.getProperty("connect.nick");
		_user = properties.getProperty("connect.user");
		_channel = properties.getProperty("connect.channel");
		_socket = null;
	}

	/**
	 * Tries to connect to the provided irc server, and registers there if the
	 * connection is successful. It also creates a <code>BufferedReader</code>
	 * and a <code>BufferedWriter</code> to enable reading and writing from and
	 * to the <code>Socket</code>.
	 */
	@Override
	public void execute() {
		System.out.println("Executing command " + _name);

		if (_socket == null) {
			try {
				_socket = new Socket();
				_socket.setReuseAddress(true);
				_socket.connect(new InetSocketAddress(_host, _port));
				_bufferedWriter = new BufferedWriter(new OutputStreamWriter(
						_socket.getOutputStream()));
				_bufferedReader = new BufferedReader(new InputStreamReader(
						_socket.getInputStream()));

				_bufferedWriter.write("PASS " + _pass + "\r\n");
				_bufferedWriter.write("NICK " + _nick + "\r\n");
				_bufferedWriter.write("USER " + _user + " foo bar :" + _user
						+ "\r\n");
				_bufferedWriter.flush();
				_bufferedWriter.write("JOIN " + _channel + "\r\n");
				_bufferedWriter.flush();
			} catch (SocketException se) {
				se.printStackTrace(System.err);
			} catch (IOException ie) {
				ie.printStackTrace(System.err);
			}
		} else {
			System.out.println("Already connected");
		}
	}

	/**
	 * 
	 * @return BufferedReader The <code>BufferedReader</code> of the
	 *         <code>Socket</code>.
	 */
	protected BufferedReader getBufferedReader() {
		return _bufferedReader;
	}

	/**
	 * 
	 * @return BufferedWriter The <code>BufferedWriter</code> of the
	 *         <code>Socket</code>.
	 */
	protected BufferedWriter getBufferedWriter() {
		return _bufferedWriter;
	}

	/**
	 * @return String The point this command hooks on to.
	 */
	@Override
	public String getHookPoint() {
		return _hookPoint;
	}

	/**
	 * @return String The name of this command.
	 */
	@Override
	public String getName() {
		return _name;
	}

	/**
	 * @return String[] A list with <strong>internal</strong> commands this
	 *         command depends on, I.E. those commands have to be carried out
	 *         before this command can be executed.
	 */
	@Override
	public String[] getDependencies() {
		return null;
	}

	/**
	 * @return IPlugin The <code>IPlugin</code> this command belongs to.
	 */
	@Override
	public IPlugin getPlugin() {
		return _plugin;
	}

}
