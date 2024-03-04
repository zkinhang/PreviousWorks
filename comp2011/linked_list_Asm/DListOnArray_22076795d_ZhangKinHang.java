//Warning: Don't change this line.  If you change the package name, your code will not compile, and you will get zero points.
package comp2011.a1;

import java.util.Arrays;

/*
 * @author Yixin Cao (September 11, 2023)
 *
 * Simulating a doubly linked list with an array.
 * 
 */
public class DListOnArray_22076795d_ZhangKinHang { // Please change!
    private int[] arr;
    private int[] ava; // a list to store all the available nodes
    private int top; // index of the top(the first one to be rewrite) of the list that store the
                     // available nodes
    private static final int SIZE = 18; // it needs to be a multiplier of 3.

    /**
     * VERY IMPORTANT.
     * 
     * I've discussed this question with the following students:
     * 1.
     * 2.
     * 3.
     * ...
     * 
     * I've sought help from the following Internet resources and books:
     * 1. tutorial 5 code templates
     * 2. chatgpt to ask Java questions (I am not familiar with Java)
     * 3.
     * ...
     */
    public DListOnArray_22076795d_ZhangKinHang() {
        arr = new int[SIZE];
        ava = new int[SIZE / 3 - 1];
        arr[0] = 0; // represent the head
        arr[1] = 0; // represent the tail
        for (int i = 2; i < SIZE; i = i + 3)
            arr[i] = i - 3; // initalize the address of last node in each node
        for (int i = 4; i < SIZE; i = i + 3)
            arr[i] = i + 1; // initalize the address of next node in each node
        arr[2] = 0; // the last address of the first node and the next address of the last node
                    // should be null(represent as 0)
        arr[SIZE - 2] = 0;
        top = -1; // indicate the top of the list that store the available nodes
        for (int i = (SIZE - 4); i >= 2; i = i - 3)
            ava[++top] = i;
        arr[SIZE - 1] = ava[top]; // represent the next available address
    }

    /**
     * VERY IMPORTANT.
     * 
     * I've discussed this question with the following students:
     * 1.
     * 2.
     * 3.
     * ...
     * 
     * I've sought help from the following Internet resources and books:
     * 1.
     * 2.
     * 3.
     * ...
     */
    public boolean isEmpty() {
        return top + 1 == ava.length;
    }

    /**
     * VERY IMPORTANT.
     * 
     * I've discussed this question with the following students:
     * 1.
     * 2.
     * 3.
     * ...
     * 
     * I've sought help from the following Internet resources and books:
     * 1.
     * 2.
     * 3.
     * ...
     */
    public boolean isFull() {
        return top == -1;
    }

    public void err() {
        System.out.println("Oops...");
    }

    /**
     * VERY IMPORTANT.
     * 
     * I've discussed this question with the following students:
     * 1.
     * 2.
     * 3.
     * ...
     * 
     * I've sought help from the following Internet resources and books:
     * 1.
     * 2.
     * 3.
     * ...
     */
    public void insertFirst(int x) {
        if (isFull()) { // check whether the list is full
            err();
            return;
        }
        int next = ava[top]; // get the next available address
        if (isEmpty())
            arr[1] = next; // the current tail will change to the address of new node if it is empty
        top--;
        if (top == -1) { // when the list is full, the next available address will be null
            arr[SIZE - 1] = 0;
        } else {
            arr[SIZE - 1] = ava[top]; // change to the next available address
        }
        arr[arr[0]] = next; // the last address of last node will point to the new node
        arr[next] = 0; // the last address of new node will be null(represent as 0)
        arr[next + 1] = x; // write the value
        arr[next + 2] = arr[0]; // the next address will be the current first node
        arr[0] = next; // the head will change to the address of new node
        arr[arr[1] + 2] = 0; // the next address of the new last node will be null also

        // at the same time, the empty node should be linked together as well
        if (top != -1)
            arr[ava[top]] = 0; // the last address of next available node will be null currently(do only when
                               // the list is not full)

    }

    /**
     * VERY IMPORTANT.
     * 
     * I've discussed this question with the following students:
     * 1.
     * 2.
     * 3.
     * ...
     * 
     * I've sought help from the following Internet resources and books:
     * 1.
     * 2.
     * 3.
     * ...
     */
    public void insertLast(int x) {
        if (isFull()) { // check whether the list is full
            err();
            return;
        }
        int next = ava[top]; // get the next available address
        if (isEmpty())
            arr[0] = next; // the current head will change to the address of new node if it is empty
        top--;
        if (top == -1) { // when the list is full
            arr[SIZE - 1] = 0;
        } else {
            arr[SIZE - 1] = ava[top];
        } // change to the next available address
        arr[arr[1] + 2] = next; // the next address of last node will point to the new node
        arr[next] = arr[1]; // the last address of new node will be the tail
        arr[next + 1] = x; // write the value
        arr[next + 2] = 0; // the next address will be null(represent as 0)
        arr[1] = next; // the tail will change to the address of new node

        // at the same time, the empty node should be linked together as well
        if (top != -1)
            arr[ava[top]] = 0; // the last address of next available node will be null currently
    }

    /**
     * VERY IMPORTANT.
     * 
     * I've discussed this question with the following students:
     * 1.
     * 2.
     * 3.
     * ...
     * 
     * I've sought help from the following Internet resources and books:
     * 1.
     * 2.
     * 3.
     * ...
     */
    public int deleteFirst() {
        if (isEmpty()) { // check whether the list is empty
            err();
            return -1;
        }
        int currentHead = arr[0]; // get the current first node
        ava[top + 1] = currentHead; // the next available address will be the current first node(to be deleted)
        top = top + 1;
        arr[SIZE - 1] = ava[top]; // update the next available address
        int secNode = currentHead + 2; // secNode is the address of the second node
        arr[0] = arr[secNode]; // the head will be the second node
        arr[arr[secNode]] = 0; // the last address of second node will be null(as the first node)

        // at the same time, the empty node should be linked together as well
        if (top - 1 != -1) {
            arr[currentHead + 2] = ava[top - 1]; // link the new deleted node to the empty node currently
            arr[ava[top - 1]] = currentHead; // link back
        }

        if (isEmpty())
            arr[1] = 0; // the current tail will be null if it is empty after deletion
        return arr[currentHead]; // return the current first node
    }

    /**
     * VERY IMPORTANT.
     * 
     * I've discussed this question with the following students:
     * 1.
     * 2.
     * 3.
     * ...
     * 
     * I've sought help from the following Internet resources and books:
     * 1.
     * 2.
     * 3.
     * ...
     */
    public int deleteLast() {
        if (isEmpty()) { // check whether the list is empty
            err();
            return -1;
        }
        int currentTail = arr[1]; // get the current last node
        ava[top + 1] = currentTail; // the next available address will be the current last node(to be deleted)
        top++;
        arr[SIZE - 1] = ava[top]; // update the next available address
        int secNode = arr[currentTail]; // secNode is the address of the second last node
        arr[1] = secNode; // the tail will be the second last node
        arr[secNode + 2] = 0; // the next address of second last node will be null(as the first node)

        // at the same time, the empty node should be linked together as well
        if (top - 1 != -1) {
            arr[currentTail] = 0; // the linked empty nodes should start from null as well
            arr[currentTail + 2] = ava[top - 1]; // link the new deleted node to the empty node currently
            arr[ava[top - 1]] = currentTail; // link back
        }
        if (isEmpty())
            arr[0] = 0; // the current tail will be null if it is empty after deletion
        return arr[currentTail]; // return the current first node
    }

    // Optional, this runs in O(n) time.
    public void reverse() {
        if (isEmpty()) { // check whether the list is empty
            err();
            return;
        }
        int loop = ava.length - 1 + top; // find the number of nodes currently in total
        int nextReverse = arr[0]; // to trace the node one by one
        for (int i = 0; i < loop; i++) { // reverse the list by swapping their last address and next address one by one
            int temp = arr[nextReverse];
            arr[nextReverse] = arr[nextReverse + 2];
            arr[nextReverse + 2] = temp;
            nextReverse = arr[nextReverse]; // update the next node to be swapped
        }
        int headTemp = arr[0]; // swap the address of first and last node
        arr[0] = arr[1];
        arr[1] = headTemp;

    }

    /*
     * Optional, but you cannot test without it.
     * // this method should print out the numbers in the list in order
     * // for example, after the demonstration, it should be "75, 85, 38, 49"
     */
    public String toString() { // from tutorial 5 sample code
        if (isEmpty())
            return "The list is empty.";
        StringBuilder sb = new StringBuilder();
        int i = 0;
        sb.append(arr[i]);
        for (i = 1; i < SIZE; i++) {
            sb.append(", ").append(arr[i]);
        }
        return '[' + sb.toString() + ']';
    }

    /*
     * The following is prepared for your reference.
     * You may freely revise it to test your code.
     */
    public static void main(String[] args) {
        DListOnArray_22076795d_ZhangKinHang list = new DListOnArray_22076795d_ZhangKinHang();
        // You may use the following line to print out data (the array),
        // so you can monitor what happens with your operations.
        System.out.println(Arrays.toString(list.arr)); // demostrate the initalization
        System.out.println(Arrays.toString(list.ava));
        System.out.println(list.isEmpty()); // demostrate whether the list is empty or not
        System.out.println(list.isFull());
        System.out.println(list); // demostrate what will be shown if the list is empty
        list.insertFirst(75);
        list.insertFirst(99);
        System.out.println(list); // demostrate the list after two insertFirst()
        list.insertLast(85);
        list.insertLast(38);
        System.out.println(list); // demostrate the list after two insertLast()
        list.deleteFirst();
        System.out.println(list); // demostrate the list after deleteFirst()
        list.insertLast(49);
        System.out.println(list); // demostrate the list after insertLast()
        list.reverse();
        System.out.println(list); // demostrate the list after reverse()
        list.insertLast(99);
        System.out.println(list);
        list.insertFirst(99);
        System.out.println(list); // demostrate the list after it is full
        list.deleteLast();
        list.deleteLast();
        list.deleteLast();
        list.deleteLast();
        list.deleteLast();
        list.deleteLast();
        System.out.println(list); // demostrate the list after it is empty
        list.insertFirst(99);
        list.insertFirst(99);
        System.out.println(list);
        list.insertLast(99);
        System.out.println(list); // demostrate the list executing insert nodes after all the current nodes are
                                  // deleted
    }
}
