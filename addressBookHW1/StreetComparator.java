import java.util.Comparator;

public class StreetComparator implements Comparator<Address> {

	@Override
	public int compare(Address o1, Address o2) {
		int comp;
		comp = o2.getStreet().compareTo(o1.getStreet());
		if (comp == 0)
			return 1;
		return comp;
	}
}
