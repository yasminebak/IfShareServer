import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class IfShare extends UnicastRemoteObject implements IIfShare {
	
	private HashMap<String, Product> products;

	protected IfShare() throws RemoteException {
		super();
		products = new HashMap<String, Product>();
	}

	
	public IProduct getProduct(String id) throws RemoteException {
		if(products.containsKey(id)) {
			products.get(id);
		}
		return null;
	}

	
	public void addProduct(String id, String type, String nameProduct, float price) throws RemoteException {
	
		if (price < 0) {
			throw new IllegalArgumentException("Price can't be negative !");
		}
		
		Product p = new Product(id, type, nameProduct, price);
		products.put(id, p);
		
	}

	
	public void delete(String id) throws RemoteException {
	if ( products.containsKey(id)) {
		products.remove(id);
	}
		
	}

	
	public void setBuyable(String id, boolean b) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	
	public float getTypeProduct(String id) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public void setTypeProduct(String id, float type) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getStatProduct(String matricule) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setStatProduct(String id, String noteProduct) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	
	public List<IProduct> lookProductType(String type) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	//HashMap has an inner class called an Entry Class which holds the key and values
	/**
	 * 
	 * @return List<IProduct> allProduct : 
	 * @throws RemoteException
	 */
	public List<IProduct> getAllProduct() throws RemoteException {
	List<IProduct> allProduct = new ArrayList<IProduct>();
	// entryset renvoie des couples clé valeur
	for(HashMap.Entry<String, Product> e : products.entrySet()) {
	// e contient les couples clé valeur des produits
		Product p =e.getValue();
		allProduct.add(p);
	}
	//
		return allProduct;
		
	}


	public List<IProduct> getBuyableProduct() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
