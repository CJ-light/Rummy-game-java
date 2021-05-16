public class Stack extends MyStack<Card>{

	private final static int MaxCards = 52;
	private int size = 0;

	//constructor
	public Stack(){
		super(MaxCards);
	}

	public void addCard(Card card){
		size += 1;
		this.push(card);
	}
	
	public Card removeCard(){
		size -= 1;
		return this.pop();
	}

	public Card peek(){
		return this.top();
	}

	public boolean isEmpty(){
		return this.isEmpty();
	}

	public int getSizeOfStack(){
		return size;
	}
}