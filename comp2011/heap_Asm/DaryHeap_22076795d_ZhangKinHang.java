//Warning: Don't change this line.  If you change the package name, your code will not compile, and you will get zero points.
package comp2011.a2;

/**
 *
 *
 * My student ID is _22076795d__, I'm implementing a 5-aray _max_ heap.
 * 
 * 
 * VERY IMPORTANT.
 * 
 * I've discussed this question with the following students:
 * 1.
 * 2.
 * 3.
 * ...
 * 
 * I've sought help from the following Internet resources and books:
 * 1. chatgpt
 * 2. lecture 9 slides
 * 3.
 * ...
 */
public class DaryHeap_22076795d_ZhangKinHang<T extends Comparable<T>> { // Please change!
    private T[] heapArray;
    private int capacity;
    private int size;

    public DaryHeap_22076795d_ZhangKinHang(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.heapArray = (T[]) new Comparable[capacity];
        /*
         * this part is exactly from chatgpt
         * I am not familar with the type issues in java!
         */
    }

    public void insert(T x) {
        if (size == capacity) {
            System.out.println("the heap is full!"); // overflow warning
            return;
        }

        heapArray[size] = x;
        up(size++);
    }

    // Running time: O(log5 n).
    public T removeRoot() {
        if (size == 0) {
            System.out.println("the heap is empty!"); // underflow warning
            return null;
        }
        T root = heapArray[0]; // store the root to be returned after deletion
        heapArray[0] = heapArray[--size];
        down(0);
        return root;

    }

    public void swap(int a, int b) { // swap the heap element with the input position
        T temp = heapArray[a];
        heapArray[a] = heapArray[b];
        heapArray[b] = temp;
    }

    // Running time: O(log5 n).
    private void up(int c) {
        if (c == 0)
            return; // root already
        int p = (c - 1) / 5; // find the parent in 5-ary heap
        if (heapArray[p].compareTo(heapArray[c]) >= 0)
            return; // if the child is smaller than the parent, it is fine
        swap(c, p); // else swap them
        up(p); // recursive swap until it is fine

    }

    // Running time: O(log5 n).
    private void down(int ind) {
        int lc = 5 * ind + 1; // leftmost child in 5-ary max heap
        if (size < lc)
            return; // if the leftmost child does not exist, means the current node visiting is a
                    // leaf
        int max = lc;
        for (int i = 1; i < 5; i++) { // compare among every exist child (5 at most) to find the largest child
            if ((lc + i) > size)
                break; // if the leftmost child does not exist, end the comparison
            if (heapArray[max].compareTo(heapArray[lc + i]) < 0) { // the left child is smaller than right child
                max = lc + i;
            }
        }
        if (heapArray[ind].compareTo(heapArray[max]) >= 0)
            return; // the parent is larger than the child, the heap is fine
        swap(ind, max); // else swap the largest child and parent
        down(max); // recursive down to check until the parent is larger than the child
    }

    public void printHeap() {
        for (int i = 0; i < size; i++) {
            System.out.println(heapArray[i]);
        }
    }

    /**
     * Merge the given <code>heap</code> with <code>this</code>.
     * The result will be stored in <code>this</code>.
     *
     * VERY IMPORTANT.
     *
     * I've discussed this question with the following students:
     * 1.
     * 2.
     * 3.
     * ...
     *
     * I've sought help from the following Internet resources and books:
     * 1. chatgpt
     * 2.
     * 3.
     * ...
     *
     * Running time: O(m + n).
     * where m is the size of original heap, and n is the size of the another heap
     */

    public void merge(DaryHeap_22076795d_ZhangKinHang<T> heap) {
        // combine two heap together directly
        for (int i = 0; i < heap.size; i++) {
            heapArray[++size] = heap.heapArray[i];
        }
        // start from the last non-leaf node, check if the relation between the parent
        // and their children satisfy the condition of heap
        int start_pointer = (size - 1) / 5;
        // each iteration check one part: one parent and the five children
        // first iteration: with checking if the child exist
        int child = start_pointer * 5 + 1;
        int largest = start_pointer;
        for (int j = 0; j < 5; j++) {
            if ((child + j) > size)
                break;
            if (heapArray[largest].compareTo(heapArray[child + j]) < 0) { // the current largest node is smaller than
                                                                          // the one comparing
                largest = child + j;
            }
        }
        // if the largest node is not the parent, swap them
        if (largest != start_pointer) {
            swap(start_pointer, largest);
        }
        for (int i = start_pointer - 1; i >= 0; i--) { // keep checking until reach the root node
            child = i * 5 + 1;
            largest = i;
            for (int j = 0; j < 5; j++) {
                if (heapArray[largest].compareTo(heapArray[child + j]) < 0) {
                    largest = child + j;
                }
            }
            if (largest != i) {
                swap(i, largest);
            }
        }
    }

    /*
     * Make sure you test your code thoroughly.
     * The more test cases, the better.
     */
    public static void main(String[] args) {
        DaryHeap_22076795d_ZhangKinHang<Integer> heap = new DaryHeap_22076795d_ZhangKinHang<>(114514);
        DaryHeap_22076795d_ZhangKinHang<Integer> bheap = new DaryHeap_22076795d_ZhangKinHang<>(114514);
        heap.insert(5);
        heap.insert(3);
        heap.insert(8);
        heap.insert(1);
        heap.insert(10);
        heap.insert(7);
        heap.insert(4);
        heap.insert(2);
        heap.insert(9);
        heap.insert(6);

        System.out.println("Heap elements after insertions:");
        heap.printHeap();

        System.out.println("Removed root element: " + heap.removeRoot());

        System.out.println("Heap elements after removing root:");
        heap.printHeap();

        bheap.insert(13);
        bheap.insert(2);
        bheap.insert(10);

        heap.merge(bheap);
        System.out.println("Heap elements after merging:");
        heap.printHeap();
    }

}
