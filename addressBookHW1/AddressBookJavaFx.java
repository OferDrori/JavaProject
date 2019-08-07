import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.ListIterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

//Ofer Drori 313264541
public class AddressBookJavaFx extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		AddressBookPane pane = new AddressBookPane();
		Scene scene = new Scene(pane);
		scene.getStylesheets().add("styles.css");
		primaryStage.setTitle("AddressBook");
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setAlwaysOnTop(true);
	}
}//// ---------------------------------------------------------------------

class AddressBookPane extends GridPane {
	private RandomAccessFile raf;
	// Text fields
	private TextField jtfName = new TextField();
	private TextField jtfStreet = new TextField();
	private TextField jtfCity = new TextField();
	private TextField jtfState = new TextField();
	private TextField jtfZip = new TextField();
	// Buttons
	private AddButton jbtAdd;
	private FirstButton jbtFirst;
	private NextButton jbtNext;
	private PreviousButton jbtPrevious;
	private LastButton jbtLast;
	private SortOneButton jbtsortOne;
	private SortTwoButton jbtSortTwo;
	private IterButton IterButton;
	public EventHandler<ActionEvent> ae = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent arg0) {
			((Command) arg0.getSource()).Execute();
		}
	};

	public AddressBookPane() { // Open or create a random access file
		try {
			raf = new RandomAccessFile("address.dat", "rw");
		} catch (IOException ex) {
			System.out.print("Error: " + ex);
			System.exit(0);
		}
		jtfState.setAlignment(Pos.CENTER_LEFT);
		jtfState.setPrefWidth(25);
		jtfZip.setPrefWidth(60);
		jbtAdd = new AddButton(this, raf);
		jbtFirst = new FirstButton(this, raf);
		jbtNext = new NextButton(this, raf);
		jbtPrevious = new PreviousButton(this, raf);
		jbtLast = new LastButton(this, raf);
		jbtsortOne = new SortOneButton(this, raf);
		jbtSortTwo = new SortTwoButton(this, raf);
		;
		IterButton = new IterButton(this, raf);
		;
		Label state = new Label("State");
		Label zp = new Label("Zip");
		Label name = new Label("Name");
		Label street = new Label("Street");
		Label city = new Label("City");
		// Panel p1 for holding labels Name, Street, and City
		GridPane p1 = new GridPane();
		p1.add(name, 0, 0);
		p1.add(street, 0, 1);
		p1.add(city, 0, 2);
		p1.setAlignment(Pos.CENTER_LEFT);
		p1.setVgap(8);
		p1.setPadding(new Insets(0, 2, 0, 2));
		GridPane.setVgrow(name, Priority.ALWAYS);
		GridPane.setVgrow(street, Priority.ALWAYS);
		GridPane.setVgrow(city, Priority.ALWAYS);
		// City Row
		GridPane adP = new GridPane();
		adP.add(jtfCity, 0, 0);
		adP.add(state, 1, 0);
		adP.add(jtfState, 2, 0);
		adP.add(zp, 3, 0);
		adP.add(jtfZip, 4, 0);
		adP.setAlignment(Pos.CENTER_LEFT);
		GridPane.setHgrow(jtfCity, Priority.ALWAYS);
		GridPane.setVgrow(jtfCity, Priority.ALWAYS);
		GridPane.setVgrow(jtfState, Priority.ALWAYS);
		GridPane.setVgrow(jtfZip, Priority.ALWAYS);
		GridPane.setVgrow(state, Priority.ALWAYS);
		GridPane.setVgrow(zp, Priority.ALWAYS);
		// Panel p4 for holding jtfName, jtfStreet, and p3
		GridPane p4 = new GridPane();
		p4.add(jtfName, 0, 0);
		p4.add(jtfStreet, 0, 1);
		p4.add(adP, 0, 2);
		p4.setVgap(1);
		GridPane.setHgrow(jtfName, Priority.ALWAYS);
		GridPane.setHgrow(jtfStreet, Priority.ALWAYS);
		GridPane.setHgrow(adP, Priority.ALWAYS);
		GridPane.setVgrow(jtfName, Priority.ALWAYS);
		GridPane.setVgrow(jtfStreet, Priority.ALWAYS);
		GridPane.setVgrow(adP, Priority.ALWAYS);
		// Place p1 and p4 into jpAddress
		GridPane jpAddress = new GridPane();
		jpAddress.add(p1, 0, 0);
		jpAddress.add(p4, 1, 0);
		GridPane.setHgrow(p1, Priority.NEVER);
		GridPane.setHgrow(p4, Priority.ALWAYS);
		GridPane.setVgrow(p1, Priority.ALWAYS);
		GridPane.setVgrow(p4, Priority.ALWAYS);
		// Set the panel with line border
		jpAddress.setStyle("-fx-border-color: grey;" + " -fx-border-width: 1;" + " -fx-border-style: solid outside ;");
		// Add buttons to a panel
		FlowPane jpButton = new FlowPane();
		jpButton.setHgap(5);
		jpButton.getChildren().addAll(jbtAdd, jbtFirst, jbtNext, jbtPrevious, jbtLast, jbtsortOne, jbtSortTwo,
				IterButton);
		jpButton.setAlignment(Pos.CENTER);
		GridPane.setVgrow(jpButton, Priority.NEVER);
		GridPane.setVgrow(jpAddress, Priority.ALWAYS);
		GridPane.setHgrow(jpButton, Priority.ALWAYS);
		GridPane.setHgrow(jpAddress, Priority.ALWAYS);
		// Add jpAddress and jpButton to the stage
		this.setVgap(5);
		this.add(jpAddress, 0, 0);
		this.add(jpButton, 0, 1);
		jbtAdd.setOnAction(ae);
		jbtFirst.setOnAction(ae);
		jbtNext.setOnAction(ae);
		jbtPrevious.setOnAction(ae);
		jbtLast.setOnAction(ae);
		jbtsortOne.setOnAction(ae);
		jbtSortTwo.setOnAction(ae);
		IterButton.setOnAction(ae);
		jbtFirst.Execute();
	}

	public void actionHandled(ActionEvent e) {
		((Command) e.getSource()).Execute();
	}

	public void SetName(String text) {
		jtfName.setText(text);
	}

	public void SetStreet(String text) {
		jtfStreet.setText(text);
	}

	public void SetCity(String text) {
		jtfCity.setText(text);
	}

	public void SetState(String text) {
		jtfState.setText(text);
	}

	public void SetZip(String text) {
		jtfZip.setText(text);
	}

	public String GetName() {
		return jtfName.getText();
	}

	public String GetStreet() {
		return jtfStreet.getText();
	}

	public String GetCity() {
		return jtfCity.getText();
	}

	public String GetState() {
		return jtfState.getText();
	}

	public String GetZip() {
		return jtfZip.getText();
	}
}

interface Command {
	public void Execute();
}

class CommandButton extends Button implements Command {
	public final static int NAME_SIZE = 32;
	public final static int STREET_SIZE = 32;
	public final static int CITY_SIZE = 20;
	public final static int STATE_SIZE = 10;
	public final static int ZIP_SIZE = 5;
	public final static int RECORD_SIZE = (NAME_SIZE + STREET_SIZE + CITY_SIZE + STATE_SIZE + ZIP_SIZE);
	protected AddressBookPane p;
	protected RandomAccessFile raf;

	public CommandButton(AddressBookPane pane, RandomAccessFile r) {
		super();
		p = pane;
		raf = r;
	}

	public Address makeaddressfromfile(long pos) throws IOException {
		readAddress(pos);
		return new Address(p.GetName(), p.GetStreet(), p.GetCity(), p.GetState(), p.GetZip());

	}

	public void sort(Comparator<Address> com) throws IOException {
		long minPos = 0;
		long startPos = 0;
		long nextPos = 0;
		while (startPos < raf.length()) {
			minPos = startPos;
			Address current = makeaddressfromfile(startPos);
			Address smallestAddressForNow = current;
			nextPos = startPos + RECORD_SIZE * 2;
			while (nextPos < raf.length()) {
				Address comparedAddress = makeaddressfromfile(nextPos);
				if (com.compare(smallestAddressForNow, comparedAddress) > 0) {
					smallestAddressForNow = comparedAddress;
					minPos = nextPos;
				}
				nextPos = nextPos + RECORD_SIZE * 2;
			}
			if (minPos != startPos) {
				swapAddressInFile(raf, startPos, current, minPos, smallestAddressForNow);
			}
			startPos = startPos + RECORD_SIZE * 2;
			raf.seek(startPos);
		}

	}

	public void swapAddressInFile(RandomAccessFile raf, long startPos, Address current, long nextPos,
			Address comparedAddress) throws IOException {
		raf.seek(startPos);
		writeAAddressToFile(raf, comparedAddress);
		raf.seek(nextPos);
		writeAAddressToFile(raf, current);

	}

	private void writeAAddressToFile(RandomAccessFile raf, Address current) throws IOException {
		FixedLengthStringIO.writeFixedLengthString(current.getName(), NAME_SIZE, raf);
		FixedLengthStringIO.writeFixedLengthString(current.getStreet(), STREET_SIZE, raf);
		FixedLengthStringIO.writeFixedLengthString(current.getCity(), CITY_SIZE, raf);
		FixedLengthStringIO.writeFixedLengthString(current.getState(), STATE_SIZE, raf);
		FixedLengthStringIO.writeFixedLengthString(current.getZip(), ZIP_SIZE, raf);
	}

	public void writeAAddressToFile(Address address, int location) {
		try {
			raf.seek(location);
			FixedLengthStringIO.writeFixedLengthString(address.getName(), NAME_SIZE, raf);
			FixedLengthStringIO.writeFixedLengthString(address.getStreet(), STREET_SIZE, raf);
			FixedLengthStringIO.writeFixedLengthString(address.getCity(), CITY_SIZE, raf);
			FixedLengthStringIO.writeFixedLengthString(address.getState(), STATE_SIZE, raf);
			FixedLengthStringIO.writeFixedLengthString(address.getZip(), ZIP_SIZE, raf);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void Execute() {
	}

	/** Write a record at the end of the file */
	public void writeAddress() {
		try {
			raf.seek(raf.length());
			FixedLengthStringIO.writeFixedLengthString(p.GetName(), NAME_SIZE, raf);
			FixedLengthStringIO.writeFixedLengthString(p.GetStreet(), STREET_SIZE, raf);
			FixedLengthStringIO.writeFixedLengthString(p.GetCity(), CITY_SIZE, raf);
			FixedLengthStringIO.writeFixedLengthString(p.GetState(), STATE_SIZE, raf);
			FixedLengthStringIO.writeFixedLengthString(p.GetZip(), ZIP_SIZE, raf);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/** Read a record at the specified position */
	public void readAddress(long position) throws IOException {
		raf.seek(position);
		String name = FixedLengthStringIO.readFixedLengthString(NAME_SIZE, raf);
		String street = FixedLengthStringIO.readFixedLengthString(STREET_SIZE, raf);
		String city = FixedLengthStringIO.readFixedLengthString(CITY_SIZE, raf);
		String state = FixedLengthStringIO.readFixedLengthString(STATE_SIZE, raf);
		String zip = FixedLengthStringIO.readFixedLengthString(ZIP_SIZE, raf);
		p.SetName(name);
		p.SetStreet(street);
		p.SetCity(city);
		p.SetState(state);
		p.SetZip(zip);
	}
}

class AddButton extends CommandButton {
	public AddButton(AddressBookPane pane, RandomAccessFile r) {
		super(pane, r);
		this.setText("Add");
	}

	@Override
	public void Execute() {
		writeAddress();
	}
}

class SortOneButton extends CommandButton {
	public SortOneButton(AddressBookPane pane, RandomAccessFile r) {
		super(pane, r);
		this.setText("sort1");
	}

	@Override
	public void Execute() {
		// call sort function with Namecomparator

		try {
			sort(new NameComparator());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class SortTwoButton extends CommandButton {
	public SortTwoButton(AddressBookPane pane, RandomAccessFile r) {
		super(pane, r);
		this.setText("sort2");
	}

	@Override
	public void Execute() {
		// call sort function with Zipcomparator
		try {
			sort(new ZipComparator());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

// -------------------------------------------------------------------------------
class IterButton extends CommandButton {
	private Map<Address, Address> iterMap = new LinkedHashMap<>();
	private MyListIterator lit = new MyListIterator();
	private boolean hasClicked = false;

	public class MyListIterator implements ListIterator<Address> {
		private int cur = 0; // Indicates element to be retrieved
		private int last = -1; // Indicates element to be removed

		public MyListIterator(int index) {
			cur = index;
		}

		public MyListIterator() {
			cur = 0;
		}

		@Override
		public void add(Address addAddress) {
			ArrayList<Address> addressList = new ArrayList<Address>();
			int tempLocation = cur;
			while (hasNext())
				addressList.add(next());
			cur = tempLocation;
			writeAAddressToFile(addAddress, cur * 2 * RECORD_SIZE);
			for (Address address : addressList) {
				cur++;
				writeAAddressToFile(address, cur * 2 * RECORD_SIZE);
			}
			cur = tempLocation;
			last = -1;

		}

		@Override
		public boolean hasNext() {
			try {
				return (cur * RECORD_SIZE * 2 < raf.length());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;

		}

		@Override
		public boolean hasPrevious() {
			return (cur > 0);
		}

		@Override
		public Address next() {
			Address temp = null;
			if (!hasNext())
				throw new NoSuchElementException();
			else {
				try {
					temp = makeaddressfromfile((cur) * RECORD_SIZE * 2);
					last = cur;
					cur++;

					return temp;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return temp;
			}
		}

		@Override
		public int nextIndex() {
			return cur;
		}

		@Override
		public Address previous() {
			if (!hasPrevious()) {
				throw new NoSuchElementException();
			} else {
				cur--;

				Address temp;
				try {
					temp = makeaddressfromfile((cur) * RECORD_SIZE * 2);
					last = cur;
					return temp;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return null;

		}

		protected void clear() throws IOException {
			while (hasNext()) {
				raf.seek(0);
				cur = 0;
				next();
				remove();
			}
			cur = 0;
			last = 0;
			raf.setLength(0);
		}

		@Override
		public int previousIndex() {
			return cur - 1;
		}

		@Override
		public void remove() {
			ArrayList<Address> addressList = new ArrayList<Address>();
			int tempLocation = cur;
			if (last == -1)
				throw new IllegalStateException();
			else {
				while (hasNext())
					addressList.add(next());
				cur = tempLocation - 1;
				for (Address address : addressList) {
					writeAAddressToFile(address, cur * 2 * RECORD_SIZE);
					cur++;
				}
				cur = tempLocation;
				last = -1;
				cur--;
				try {
					raf.setLength(raf.length() - 2 * RECORD_SIZE);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		@Override
		public void set(Address e) {
			if (last == -1)
				throw new IllegalStateException();
			else {
				writeAAddressToFile(e, cur * 2 * RECORD_SIZE);
				cur++;
			}
		}
	}

	public IterButton(AddressBookPane pane, RandomAccessFile r) {
		super(pane, r);
		this.setText("iter");
	}

	@Override
	public void Execute() {
		if (!hasClicked) {
			updateMap();
		} else {
			try {
				mapToTreeSet(lit, iterMap);
				hasClicked = false;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	protected void mapToTreeSet(MyListIterator lit, Map<Address, Address> iterMap) throws IOException {
		updateMap();// for option that you click on iter and after you change the file
		Set<Address> treeSet = new TreeSet<>(new StreetComparator());
		treeSet.addAll(iterMap.values());
		lit.clear();
		for (Address address : treeSet)
			lit.add(address);
	}

	public void updateMap() {
		while (lit.hasNext()) {
			Address temp = lit.next();
			lit.remove();
			iterMap.put(temp, temp);// the map is mapped by address object
		}
		for (Address addr : iterMap.values()) {
			lit.add(addr);
			lit.next();
		}
		hasClicked = true;

	}
}

// -----------------------------------------------------------------------------------
class NextButton extends CommandButton {
	public NextButton(AddressBookPane pane, RandomAccessFile r) {
		super(pane, r);
		this.setText("Next");
	}

	@Override
	public void Execute() {
		try {
			long currentPosition = raf.getFilePointer();
			if (currentPosition < raf.length())
				readAddress(currentPosition);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}

class PreviousButton extends CommandButton {
	public PreviousButton(AddressBookPane pane, RandomAccessFile r) {
		super(pane, r);
		this.setText("Previous");
	}

	@Override
	public void Execute() {
		try {
			long currentPosition = raf.getFilePointer();
			if (currentPosition - 2 * 2 * RECORD_SIZE >= 0)
				readAddress(currentPosition - 2 * 2 * RECORD_SIZE);
			else
				;
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}

class LastButton extends CommandButton {
	public LastButton(AddressBookPane pane, RandomAccessFile r) {
		super(pane, r);
		this.setText("Last");
	}

	@Override
	public void Execute() {
		try {
			long lastPosition = raf.length();
			if (lastPosition > 0)
				readAddress(lastPosition - 2 * RECORD_SIZE);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}

class FirstButton extends CommandButton {
	public FirstButton(AddressBookPane pane, RandomAccessFile r) {
		super(pane, r);
		this.setText("First");
	}

	@Override
	public void Execute() {
		try {
			if (raf.length() > 0)
				readAddress(0);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
