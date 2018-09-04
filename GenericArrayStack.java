public class GenericArrayStack<E> implements Stack<E> {
   
   // ADD YOUR INSTANCE VARIABLES HERE

    private E[] elems;
    private int top;


    @SuppressWarnings("unchecked")
   // Constructor
    public GenericArrayStack( int capacity ) {
    // ADD YOU CODE HEREs

        elems = (E[]) new Object[capacity];
        top = 0;
    }


    // Returns true if this ArrayStack is empty
    public boolean isEmpty() {
    // ADD YOU CODE HERE

        return top == 0;
    }


    public void push( E elem ) {
    // ADD YOU CODE HERE

        elems[top] = elem;
        top++;
    }


    public E pop() {  
    // ADD YOU CODE HERE

        top--;
        E tmp = elems[top];
        elems[top] = null;
        return tmp;
    }


    public E peek() {   
    // ADD YOU CODE HERE

        return elems[top - 1];
    }
}
