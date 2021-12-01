package Server;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import Common.IProduct;

public class Product extends UnicastRemoteObject implements IProduct{
	
	protected Product() throws RemoteException {
		
	}

	private String id;
	private String type;
	private String nameProduct;
	private float price;
	private String stateProduct="";
	private float noteProduct = -1;
	private boolean available;
	private boolean isSold;
	
	public Product(String id, String type, String nameProduct, float price) throws RemoteException {
		super();
		this.id = id;
		this.type = type;
		this.nameProduct = nameProduct;
		this.price = price;
		this.available = true;
		this.isSold = false;
		
		if (price < 0) {
			throw new IllegalArgumentException("Price can't be negative !");
		}
	}
	
	public Product(Product p, String stateProduct, float noteProduct) throws RemoteException {
		super();
		this.id = p.getId();
		this.type = p.getType();
		this.nameProduct = p.getName();
		this.price = p.getPrice();
		this.available = p.isAvailable();
		this.stateProduct = stateProduct;
		this.noteProduct = noteProduct;
		
		if (price < 0) {
			throw new IllegalArgumentException("Price can't be negative !");
		}
		if (noteProduct < 0) {
			throw new IllegalArgumentException("note can't be negative !");
		}
	}
	
	@Override
	public String getId() throws RemoteException {
		return id;
	}

	@Override
	public String getType() throws RemoteException {
		return type;
	}
	
	@Override
	public void setType(String type) throws RemoteException {
		this.type=type;
	}
	
	@Override
	public String getName() throws RemoteException {
		return nameProduct;
	}

	@Override
	public float getPrice() throws RemoteException {
		return price;
	}

	@Override
	public void setPrice(float price) throws RemoteException {
		this.price=price;
	} 


	@Override
	public float getNote() throws RemoteException {
		return noteProduct;
	}

	@Override
	public void setNote(float noteProduct) throws RemoteException {
		 this.noteProduct = noteProduct;
	}

	@Override
	public String getState() throws RemoteException {
		return stateProduct;
	}

	@Override
	public void setState(String stateProduct) throws RemoteException {
		 this.stateProduct = stateProduct;
	}
	
	@Override
	public boolean getIsSold() throws RemoteException {
		return this.isSold;
	}

	@Override
	public void setIsSold(boolean isSold) throws RemoteException {
		this.isSold = isSold;
	}


	@Override
	public String toString() {
		return "Produit [Id : " + id + ", Type : " + type + ", Product name : " + nameProduct + ", Price : " + price + ", Availibility : " + available + ", isSold : " + isSold + "]";
	}

	@Override
	public boolean isAvailable() throws RemoteException {
		return available;
	}

	/*
	@Override
	public int stock() throws RemoteException {
		if(stock !=0) {
			stock=stock-1;
		}else {
			available=false;
		}
		return stock;
	}
*/
	@Override
	public void setAvailable(boolean bool) throws RemoteException {
		available=bool;
	}

	
}
