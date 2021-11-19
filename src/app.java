import java.net.Inet4Address;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class app {

	private final static int PORT = 1708;

	public static void main(String[] args) throws RemoteException {
		IProduct product = new Product();
		try {
			String ip = Inet4Address.getLocalHost().getHostAddress();
			if (ip == null || ip == "") {
				ip = "localhost";
			}
			System.out.println("################################");
			System.out.println("Try to start IFshare Server ...");
			System.out.println("Ip adress : " + ip);
			System.out.println("Listen port : " + PORT);
			System.out.println("################################");
			Registry r = LocateRegistry.createRegistry(PORT);
			System.setProperty("java.rmi.server.hostname", ip);
			r.rebind("//" + ip + "/IFShareService", product);
			System.out.println("Server is Up and Running");
		} catch (Exception e) {
			System.out.println("Trouble: " + e);
		}
	}
}
