import java.util.Comparator;

public class ZipComparator implements Comparator<Address>{


	public int compare(Address o1, Address o2){
		
		int firstZip = Integer.parseInt(o1.getZip().trim());
		int secondZip = Integer.parseInt(o2.getZip().trim());
		return firstZip-secondZip;
	}
	

}
