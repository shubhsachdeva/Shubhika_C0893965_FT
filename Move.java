import java.util.ArrayList;
import java.util.List;

class Box {
    protected List<Object> contents;
    protected int maxCapacity;
    protected int boxNumber;

    public Box(int maxCapacity, int boxNumber) 
	{
        this.maxCapacity = maxCapacity;
        this.boxNumber = boxNumber;
        contents = new ArrayList<>();
    }

    public void addItem(Object itemOrBox) 
	{
        if (contents.size() < maxCapacity) {
            contents.add(itemOrBox);
        } else {
            System.out.println("Box is full. Cannot add more items.");
        }
    }

    public void displayContents() 
	{
        System.out.println("Box " + boxNumber + " contents:");
        for (Object itemOrBox : contents) {
            if (itemOrBox instanceof SingleObject) {
                System.out.println(((SingleObject) itemOrBox).getName());
            } else if (itemOrBox instanceof Box) {
                ((Box) itemOrBox).displayContents();
            }
        }
    }

    public int findBoxNumber(String itemName) 
	{
        for (int i = 0; i < contents.size(); i++) {
            Object itemOrBox = contents.get(i);
            if (itemOrBox instanceof SingleObject && ((SingleObject) itemOrBox).getName().equals(itemName)) {
                return boxNumber;
            } else if (itemOrBox instanceof Box) {
                int result = ((Box) itemOrBox).findBoxNumber(itemName);
                if (result > 0) {
                    return result;
                }
            }
        }
        return -1;
    }
}

class SingleObject 
{
    private String name;

    public SingleObject(String name) 
	{
        this.name = name;
    }

    public String getName() 
	{
        return name;
    }
}

class Cardboard extends Box 
{
    public Cardboard(int maxCapacity, int boxNumber) 
	{
        super(maxCapacity, boxNumber);
    }
}


class Move 
{
	private List<Box> boxes;

    public Move(int numMainBoxes) 
	{
        boxes = new ArrayList<>();
        for (int i = 0; i < numMainBoxes; i++) {
            boxes.add(new Box(0, i + 1));
        }
    }

    public void addBox(Box box) 
	{
        if (boxes.size() < 2) 
		{
            boxes.get(boxes.size() - 1).addItem(box);
        } else {
            System.out.println("Cannot add more main boxes.");
        }
    }

    public void print() 
	{
        System.out.println("Move contents:");
        for (Box box : boxes) {
            box.displayContents();
        }
    }

    public int find(String itemName) 
	{
        for (Box box : boxes) {
            int result = box.findBoxNumber(itemName);
            if (result > 0) {
                return result;
            }
        }
        return -1;
    }


	public static void main(String[] args) 
	{
		// We create a move that will hold 2 main boxes
		Move move = new Move(2);

		/*
		 * We create and then fill 3 boxes
		 * Arguments of the constructor of Box:
		 * argument 1: number of items (simple items/objects or box) that the box can hold
		 * argument 2: box number
		 */

		// box 1 contains scissors
		Box box1 = new Box(1, 1);
		box1.addItem(new SingleObject("scissors"));

		// box 2 contains one book
		Box box2 = new Box(1, 2);
		box2.addItem(new SingleObject("book"));

		// box 3 contains one compass
		// and one box containing one scarf
		Box box3 = new Box(2, 3);
		box3.addItem(new SingleObject("compass"));
		Box box4 = new Box(1, 4);
		box4.addItem(new SingleObject("scarf"));
		box3.addItem(box4);

		// We add the three boxes to the first box of move - see below
		Box box5 = new Box(3, 5);
		box5.addItem(box1);
		box5.addItem(box2);
		box5.addItem(box3);

		// We add one box containing 3 objects to move
		Box box6 = new Box(3, 6);
		box6.addItem(new SingleObject("pencils"));
		box6.addItem(new SingleObject("pens"));
		box6.addItem(new SingleObject("rubber"));

		// We add the two most external boxes to the move
		move.addBox(box5);
		move.addBox(box6);

		// We print all the contents of the move
		move.print();

		// We print the number of the outermost cardboard containing the item "scarf"
		System.out.println("The sarf is in the cardboard number " + move.find("scarf"));
	}
}