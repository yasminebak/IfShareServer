import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings("serial")
public class IfShare extends UnicastRemoteObject implements IIfShare {

	private Map<String, Product> products;
	private Map<IProduct, LinkedList<Integer>> fifo;

	protected IfShare() throws RemoteException {
		super();
		products = new HashMap<String, Product>();
		fifo = new HashMap<IProduct, LinkedList<Integer>>();
	}

	@Override
	public IProduct getProduct(String id) throws RemoteException {
		Objects.requireNonNull(id);
		IProduct product = new Product();
		if (products.containsKey(id)) {
			product = products.get(id);
		}
		return product;
	}

	@Override
	public void addProduct(String type, String nameProduct, float price) throws RemoteException {

		String id = null;

		if (price < 0) {
			throw new IllegalArgumentException("Price can't be negative !");
		}

		// generate ID
		// 2 premier lettre type + 2 premiere lettre nameProduct + chiffre
		if (type.length() < 2 && nameProduct.length() < 2) {
			throw new IllegalArgumentException("Product type or name can't be null or short !");
		}

		do {
			int randomNum = ThreadLocalRandom.current().nextInt(1, 5000 + 1);
			id = type.substring(0, 2) + nameProduct.substring(0, 2) + randomNum;
		} while (getAllProduct().contains(id));

		Product p = new Product(id, type, nameProduct, price, true);
		products.put(id, p);

		// verify la liste d'attente pour le produit (la list fifo)
		// si exist ==> notify (1st) employee + enlever de la liste
		// sinon on fait rien
	}

	@Override
	public void delete(String id) throws RemoteException {
		Objects.requireNonNull(id);
		if (products.containsKey(id)) {
			products.remove(id);
		}

	}

	@Override
	public List<IProduct> lookProductType(String type) throws RemoteException {
		List<IProduct> returnProduct = new ArrayList<IProduct>();
		for (HashMap.Entry<String, Product> e : products.entrySet()) {
			Product p = e.getValue();
			if (p.getType().equals(type)) {
				returnProduct.add(p);
			}
		}
		return returnProduct;
	}

	@Override
	public List<IProduct> lookProductTypeName(String type, String nameProduct) throws RemoteException {
		List<IProduct> returnProduct = new ArrayList<IProduct>();
		for (Entry<String, Product> e : products.entrySet()) {
			Product p = e.getValue();
			if (p.getType().equals(type) && p.getName().equals(nameProduct)) {
				returnProduct.add(p);
			}
		}
		return returnProduct;
	}

	// HashMap has an inner class called an Entry Class which holds the key and
	// values
	/**
	 * 
	 * @return List<IProduct> allProduct :
	 * @throws RemoteException
	 */
	@Override
	public List<IProduct> getAllProduct() throws RemoteException {
		List<IProduct> returnProduct = new ArrayList<>();
		// entryset renvoie des couples clé valeur
		for (HashMap.Entry<String, Product> e : products.entrySet()) {
			// e contient les couples clé valeur des produits
			Product p = e.getValue();
			returnProduct.add(p);
		}
		return returnProduct;
	}

	@Override
	public List<IProduct> getAvailableProduct() throws RemoteException {
		List<IProduct> returnProduct = new ArrayList<IProduct>();
		for (Entry<String, Product> e : products.entrySet()) {
			Product p = e.getValue();
			if (p.isAvailable()) {
				returnProduct.add(p);
			}
		}
		return returnProduct;
	}

	@Override
	public String buyProduct(String type, String nameProduct, int id) throws RemoteException {
		
		if (type == null || nameProduct == null) {
			return null;
		}
		
		String response = "";
		
		for(Entry<String, Product> e : products.entrySet()) {
			Product p =e.getValue();
			if(p.getType().equals(type) && p.getName().equals(nameProduct)) {
				if (p.isAvailable()) {
					p.setAvailable(false);
					return p.getId();			
				} else {
					if (fifo.containsKey(p)) {
						if (!fifo.get(p).contains(id)) {
							fifo.get(p).add(id);
							response = "Sorry, this product is not available, you will be notified when it will be available again!";
						} else {
							response = "You have an order in progress !";
						}
					} else {
						LinkedList<Integer> idList = new LinkedList<>();
						idList.add(id);
						fifo.put(p, idList);
						response = "Sorry, this product is not available, you will be notified when it will be available again!";
					}
				}
			} else {
				response = "This product doesn't exist";
			}
		}
		return response;	
	}

	// TODO
	// cancelProduct()
	// si un employé notifié decide qu'il veut plus acheter le produit
	// on prend le prochain dans la liste fifo et on le notifie
	
	
}
