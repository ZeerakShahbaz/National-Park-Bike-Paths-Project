import java.util.NoSuchElementException;
public class DLStack<T> {

    private DoubleLinkedNode<T> top; // top of stack
    private int numItems; // size of the stack


    /**
     * Constructs an empty stack.
     */
    public DLStack() {
        top = null;
        numItems = 0;
    }

    /**
     * Pushes an element onto the stack.
     *
     * @param element the element to push
     */
    public void push(T element) {
        DoubleLinkedNode node = new DoubleLinkedNode(element);
        if (isEmpty()) {
            top = node;
        } else {
            node.setPrevious(top);
            top.setNext(node);
            top = node;
        }
        numItems++;
    }

    /**
     * Removes the top element from the stack and returns it.
     *
     * @return the top element of the stack
     * @throws EmptyStackException if the stack is empty
     */
    public T pop() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException("Stack is empty");
        }
        T elem = top.getElement();
        top = top.getPrevious();
        numItems--;
        return elem;
    }

    /**
     * Removes the element at the specified position from the stack and returns it.
     *
     * @param k the position of the element to pop
     * @return the popped element
     * @throws InvalidItemException if the position is invalid
     */
    public T pop(int k) throws InvalidItemException {
        DoubleLinkedNode<T> current = top;
        if (k <= 0 || k > numItems) {
            throw new InvalidItemException("Invalid index for stack pop operation");
        }
        for (int i = 1; i < k; i++) {
            current = current.getPrevious();
        }
        DoubleLinkedNode<T> prev = current.getPrevious();
        DoubleLinkedNode<T> next = current.getNext();
        if (prev == null) {
            next.setPrevious(null);
        } else if (k == 1) {
            top = prev;
        } else {
            prev.setNext(next);
            next.setPrevious(prev);
        }
        numItems--;
        return current.getElement();
    }

    /**
     * Returns the top element of the stack without removing it.
     *
     * @return the top element of the stack
     * @throws EmptyStackException if the stack is empty
     */
    public T peek() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException("Stack is empty");
        }
        return top.getElement();
    }

    /**
     * Returns the top node of the stack without removing it.
     *
     * @return the top node of the stack
     */

    public DoubleLinkedNode getTop() {
        return top;
    }

    /**
     * Returns whether the stack is empty.
     *
     * @return true if the stack is empty, false otherwise
     */

    public boolean isEmpty() {
        return numItems == 0;
    }

    /**
     * Returns the size of the stack.
     *
     * @return the size of the stack
     */
    public int size() {
        return numItems;
    }
}
