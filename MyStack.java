import java.util.*;

public class MyStack<T> {
	private final java.util.List<T> stack;
	private final int maxCards; 

	public MyStack(int maxSize){
		stack = new ArrayList<T>(maxSize); //Specify because the initial capacity (without specifying) is 10
		maxCards = maxSize;
	}

	public void push(T obj){
		if (stack.size() != maxCards){
			stack.add(obj);
		}
	}

	public T pop(){
		int size = stack.size();
		if (size > 0){
			T popped = stack.get(size - 1);
			stack.remove(size - 1);
			return popped;
		}
		return null; //returns null because it can't pop anything since its empty
	}

	public T top(){
		if (stack.size() > 0){
			return stack.get(stack.size() - 1);
		}
		return null;
	}

	public boolean isEmpty(){
		if (stack.size() == 0){
			return true;
		}
		return false;
	}
}