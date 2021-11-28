import java.net.Inet4Address;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;

public class app {

	private final static int PORT = 1709;

	public static void main(String[] args) throws RemoteException {
		//IProduct product = new Product();
		IIfShare service = new IfShare();
		
		IIfShare ifshare = new IfShare();
		service.addProduct("vetement", "pull", 10.5f);
		service.addProduct("vetement", "pantalon", 20.5f);
		service.addProduct("vetement", "pull", 10);
		service.addProduct("bureautique", "pc", 1000);
		
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
			System.out.println("///////////////////////////////");
			Scanner scanner = new Scanner(System.in);
			String type = scanner.nextLine();
			scanner = new Scanner(System.in);
			String name = scanner.nextLine();
			scanner = new Scanner(System.in);
			float price = scanner.nextFloat();
			service.addProduct(type, name, price);
		} catch (Exception e) {
			System.out.println("Trouble: " + e);
		}
	}
}
