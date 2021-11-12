import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Product extends UnicastRemoteObject implements IProduct{
	
	protected Product() throws RemoteException {
		
	}

	private String id;
	private String type;
	private String nameProduct;
	private float price;
	private String statProduct=" ";
	private float noteProduct;
	private boolean buyable;
	
	public Product(String id, String type, String nameProduct, float price) throws RemoteException {
		super();
		this.id = id;
		this.type = type;
		this.nameProduct = nameProduct;
		this.price = price;
		this.buyable = false;
		
		
		if (price < 0) {
			throw new IllegalArgumentException("Price can't be negative !");
		}
	}
	
	public String getId() throws RemoteException {
		return id;
	}

	
	public String getType() throws RemoteException {
		return type;
	}

	
	public String getName() throws RemoteException {
		return nameProduct;
	}

	@Override
	public float getPrice() throws RemoteException {
		return price;
	}

	
	public float getNote() throws RemoteException {
		return noteProduct;
	}

	
	public void setNote(float noteProduct) throws RemoteException {
		 this.noteProduct = (this.noteProduct + noteProduct) / 2;
	}

	
	public String getStat() throws RemoteException {
		return statProduct;
	}

	
	public void setStat(String statProduct) throws RemoteException {
		 this.statProduct = this.statProduct + "\n\n" + statProduct;
	}


	public boolean isByable() throws RemoteException {
		return buyable;
	}

	
	public void setBuyable(boolean b) throws RemoteException {
		buyable=b;
	}

	@Override
	public String toString() {
		return "Produit [Id : " + id + ", Type : " + type + ", Name of the Product : " + nameProduct + ", Price : " + price + "]";
	} 

	
}
