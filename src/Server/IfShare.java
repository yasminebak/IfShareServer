package Server;

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

import Common.IEmploye;
import Common.IIfShare;
import Common.IProduct;

@SuppressWarnings("serial")
public class IfShare extends UnicastRemoteObject implements IIfShare {

	private Map<String, IProduct> products;
	private Map<IProduct, LinkedList<IEmploye>> fifo;

	protected IfShare() throws RemoteException {
		super();
		products = new HashMap<String, IProduct>();
		fifo = new HashMap<IProduct, LinkedList<IEmploye>>();
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

		// generate ID: 2 premier lettre type + 2 premiere lettre nameProduct + chiffre
		if (type.length() < 2 && nameProduct.length() < 2) {
			throw new IllegalArgumentException("Product type or name can't be null or short !");
		}

		do {
			int randomNum = ThreadLocalRandom.current().nextInt(1, 5000 + 1);
			id = type.substring(0, 2) + nameProduct.substring(0, 2) + randomNum;
		} while (getAllProduct().contains(id));

		Product p = new Product(id, type, nameProduct, price);
		
		// set the value of isSold as the one of the same category (type + name)
		for (Entry<String, IProduct> p1 : products.entrySet()) {
			IProduct product = p1.getValue();
			if (product.getName().equals(nameProduct) && product.getType().equals(type)) {
				p.setIsSold(product.getIsSold());
			}
		}
		products.put(id, p);
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
		for (HashMap.Entry<String, IProduct> e : products.entrySet()) {
			IProduct p = e.getValue();
			if (p.getType().equals(type)) {
				returnProduct.add(p);
			}
		}
		return returnProduct;
	}

	@Override
	public List<IProduct> lookProductTypeName(String type, String nameProduct) throws RemoteException {
		List<IProduct> returnProduct = new ArrayList<IProduct>();
		for (Entry<String, IProduct> e : products.entrySet()) {
			IProduct p = e.getValue();
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
		for (HashMap.Entry<String, IProduct> e : products.entrySet()) {
			// e contient les couples clé valeur des produits
			IProduct p = e.getValue();
			returnProduct.add(p);
		}
		return returnProduct;
	}

	@Override
	public List<IProduct> getAvailableProduct() throws RemoteException {
		List<IProduct> returnProduct = new ArrayList<IProduct>();
		for (Entry<String, IProduct> e : products.entrySet()) {
			IProduct p = e.getValue();
			if (p.isAvailable()) {
				returnProduct.add(p);
			}
		}
		return returnProduct;
	}

	@Override
	public String buyProduct(String id, IEmploye employee) throws RemoteException {
		
		if (id == null) {
			return null;
		}
		
		String response = "";
		
		if (products.containsKey(id)) {
			IProduct p = products.get(id);
			if (p.isAvailable()) {
				p.setIsSold(true);
				p.setAvailable(false);
				return id;			
			} 
		} else {
			response = "This product doesn't exist";
		}
		
		for(Entry<String, IProduct> e : products.entrySet()) {
			IProduct p =e.getValue();
			if(p.getId().equals(id)) {
				if (fifo.containsKey(p)) {
					if (!fifo.get(p).contains(employee)) {
						fifo.get(p).add(employee);
						return "Sorry, this product is not available, you will be notified when it will be available again!";
					} else {
						return "You have an order in progress !";
					}
				} else {
					LinkedList<IEmploye> employeeList = new LinkedList<>();
					employeeList.add(employee);
					fifo.put(p, employeeList);
					return "Sorry, this product is not available, you will be notified when it will be available again!";
				}
			}
		}
		
		return response;	
	}

	@Override
	public void setFifo() throws RemoteException {
		fifo = new HashMap<IProduct, LinkedList<IEmploye>>();
	}

	@Override
	public IProduct sellProduct(String id, float note, String state, float price) throws RemoteException {
		IProduct prod = null;
		boolean isSold = false;
		for(Entry<String, IProduct> e : products.entrySet()) {
			IProduct p = e.getValue();
			if(p.getId().equals(id)) {
				prod = p;
				p.setNote(note);
				p.setState(state);
				p.setPrice(price);
				p.setAvailable(true);
				isSold = true;
			}
		}
		
		if (!fifo.isEmpty() && isSold) {
			for (Entry<IProduct, LinkedList<IEmploye>> e1 : fifo.entrySet()) {
				IProduct product = e1.getKey();
				LinkedList<IEmploye> listEmp = e1.getValue();
				if (!listEmp.isEmpty()) {
					if (product.getId().equals(id)) {
						LinkedList<IEmploye> value = fifo.get(product);
						IEmploye employee = value.pollFirst();
						prod.setAvailable(false);
						employee.notifyEmployee(product);
					}
				}
			}
		}
		return prod;
	}
}
