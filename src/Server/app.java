package Server;
import java.net.Inet4Address;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

import Common.IIfShare;
import Common.IProduct;

public class app {

	private final static int PORT = 1709;

	public static void main(String[] args) throws RemoteException {
		//IProduct product = new Product();
		IIfShare service = new IfShare();
		service.addProduct("vetement", "pull", 10.5f);
		service.addProduct("vetement", "pantalon", 20.5f);
		service.addProduct("vetement", "pull", 10);
		service.addProduct("vetement", "t-shirt", 5f);
		service.addProduct("vetement", "manteau", 30);
		
		service.addProduct("bureautique", "pc", 1000);
		service.addProduct("bureautique", "souris", 11);
		
		service.addProduct("chaussure", "bottes", 25);
		service.addProduct("chaussure", "basket", 15);
		
		List<IProduct> produits = service.getAllProduct();
		for(IProduct p : produits) {
			System.out.println(p);
		}
		
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
			r.rebind("//" + ip + "/IFShareService", service);
			System.out.println("Server is Up and Running");
		} catch (Exception e) {
			System.out.println("Trouble: " + e);
		}
	}
}
