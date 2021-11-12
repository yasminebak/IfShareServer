import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IIfShare extends Remote {
	public IProduct getProduct(String id) throws RemoteException;
	public void addProduct(String id, String type, String nameProduct, float price)throws RemoteException;
	public void delete(String id) throws RemoteException;
	public void setBuyable(String id, boolean b) throws RemoteException;
	public float getTypeProduct(String id) throws RemoteException;
	public void setTypeProduct(String id, float type) throws RemoteException;
	public String getStatProduct(String matricule) throws RemoteException;
	public void setStatProduct(String id, String noteProduct) throws RemoteException;
	
	
}
