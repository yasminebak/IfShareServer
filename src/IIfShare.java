import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IIfShare extends Remote {
	public IProduct getProduct(String id) throws RemoteException;

	public List<IProduct> getAllProduct() throws RemoteException;

	public void addProduct(String id, String type, String nameProduct, float price) throws RemoteException;

	public void delete(String id) throws RemoteException;

	//getAvailableproduct c'est les produit disponibles achetable
	public List<IProduct> getAvailableProduct() throws RemoteException;
	
	public void setAvailableProduct(String id, boolean bool) throws RemoteException;

	public String getStatProduct(String id) throws RemoteException;

	public void setStatProduct(String id, String statProduct) throws RemoteException;
	
	public List<IProduct> showTypeProduct(String type) throws RemoteException;

}