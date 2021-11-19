import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Product extends UnicastRemoteObject implements IProduct{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7822263390327070559L;

	protected Product() throws RemoteException {
		
	}

	private String id;
	private String type;
	private String nameProduct;
	private float price;
	private String statProduct=" ";
	private float noteProduct;
	private boolean available;
	private int stock;
	
	public Product(String id, String type, String nameProduct, float price) throws RemoteException {
		super();
		this.id = id;
		this.type = type;
		this.nameProduct = nameProduct;
		this.price = price;
		this.available = false;
		
		
		
		
		if (price < 0) {
			throw new IllegalArgumentException("Price can't be negative !");
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
		// TODO Auto-generated method stub
		this.price=price;
	} 


	@Override
	public float getNote() throws RemoteException {
		return noteProduct;
	}

	@Override
	public void setNote(float noteProduct) throws RemoteException {
		 this.noteProduct = (this.noteProduct + noteProduct) / 2;
	}

	@Override
	public String getStat() throws RemoteException {
		return statProduct;
	}

	@Override
	public void setStat(String statProduct) throws RemoteException {
		 this.statProduct = this.statProduct + "\n\n" + statProduct;
	}



	@Override
	public String toString() {
		return "Produit [Id : " + id + ", Type : " + type + ", Name of the Product : " + nameProduct + ", Price : " + price + "]";
	}

	@Override
	public boolean isAvailable() throws RemoteException {
		return available;
	}

	@Override
	public int stock() throws RemoteException {
		// TODO Auto-generated method stub
		if(stock !=0) {
			stock=stock-1;
		}else {
			available=false;
		}
		return stock;
	}

	@Override
	public void setAvailable(boolean bool) throws RemoteException {
		available=bool;
	}

	

	

	
}
