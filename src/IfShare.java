import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

@SuppressWarnings("serial")
public class IfShare extends UnicastRemoteObject implements IIfShare {
	
	private Map<String, Product> products;

	protected IfShare() throws RemoteException {
		super();
		products = new HashMap<String, Product>();
	}

	@Override
	public IProduct getProduct(String id) throws RemoteException {
		Objects.requireNonNull(id);
		IProduct product = new Product();
		if(products.containsKey(id)) {
			product = products.get(id);
		}		
		return product;
	}

	@Override
	public void addProduct(String id, String type, String nameProduct, float price) throws RemoteException {
	
		if (price < 0) {
			throw new IllegalArgumentException("Price can't be negative !");
		}
		
		Product p = new Product(id, type, nameProduct, price);
		products.put(id, p);
		//incrémenter stock
	}

	@Override
	public void delete(String id) throws RemoteException {
		Objects.requireNonNull(id);
	if ( products.containsKey(id)) {
		products.remove(id);
	}
		
	}



	@Override
	public String getStatProduct(String id) throws RemoteException {
		if (products.containsKey(id)) {
			Product p = products.get(id);
			return p.getStat();
		}
		return "Product does not exist!";
	}


	@Override
	public void setStatProduct(String id, String statProduct) throws RemoteException {
		if (products.containsKey(id)) {
			Product p = products.get(id);
		    p.setStat(statProduct);
		}
	}
	
	
	public List<IProduct> lookProductType(String type) throws RemoteException {
		List<IProduct> returnProduct = new ArrayList<IProduct>();
		for(HashMap.Entry<String, Product> e : products.entrySet()) {
				Product p =e.getValue();
				if(p.getType().equals(type)) {
					returnProduct.add(p);
				}
		}
		return returnProduct;
	}

	//HashMap has an inner class called an Entry Class which holds the key and values
	/**
	 * 
	 * @return List<IProduct> allProduct : 
	 * @throws RemoteException
	 */
	@Override
	public List<IProduct> getAllProduct() throws RemoteException {
	List<IProduct> returnProduct = new ArrayList<IProduct>();
	// entryset renvoie des couples clé valeur
	for(HashMap.Entry<String, Product> e : products.entrySet()) {
	// e contient les couples clé valeur des produits
		Product p =e.getValue();
		returnProduct.add(p);
	}
	//
		return returnProduct;
		
	}

	@Override
	public List<IProduct> getAvailableProduct() throws RemoteException {
		List<IProduct> returnProduct = new ArrayList<IProduct>();
		for(Entry<String, Product> e : products.entrySet()) {
				Product p =e.getValue();
				if(p.isAvailable() && p.stock()!=0) {
					returnProduct.add(p);
				
				}
				p.stock();
		}
		return returnProduct;
	}

	@Override
	public void setAvailableProduct(String id, boolean bool) throws RemoteException {
		
		if(products.containsKey(id)) {
			Product product=products.get(id);
			product.setAvailable(bool);
		}
		
	}

	@Override
	public List<IProduct> showTypeProduct(String type) throws RemoteException {
		List<IProduct> returnProduct = new ArrayList<IProduct>();
		for(Entry<String, Product> e : products.entrySet()) {
				Product p =e.getValue();
				if(p.getType().equals(type)) {
					returnProduct.add(p);
				
				}
				
		}
		return returnProduct;
	}


	
	
	
}




