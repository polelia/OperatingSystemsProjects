
package pagefaultsreplacement_project1;

/**
 * Implements priority queue structure. Is used for lru method.
 * @author nadiia
 */
public class PriorityQueue implements Queueable {   
    
    public Page[] array;
    private int maxSize, front, numElems;  
    
    
    //constructor
    public PriorityQueue(int size) { 
        maxSize = size;
        array = new Page[size];
        front = numElems = 0;
    }
    
    /**
    * Performs linear search, resets currently used page. 
    * @param page
    * @return found
    */
    public boolean linearSearch(Page page) {
        boolean found = false;
        
        for(int i = 0; i < numElems && !found; i++)       //loops until page is found or it reaches the end of array
        {
            if(array[i].getPageNum() == page.getPageNum()) //page found
            {
                array[i].setRecUsed(0);                   //resets age to 0
                //System.out.println("Reset " + array[i].toString() );
                found = true;
            }
        }
        return found;
    }
 
    /**
     * Adds an item to the priority queue.
     *
     * Note:  The isFull method should be called first to prevent errors.
     * @param page is an item to add.
     */
     @Override
    public void insert(Page page) 
    {
        int i;
        if(!isFull())
        {
            if(numElems == 0)                          //empty array
            {
                array[numElems++] = page;
            }
            else
            {
                i = numElems;
                while(i > 0 && (page.getRecUsed()< array[i-1].getRecUsed())) //if new page's age > than previous one
                {
                    array[i] = array[i - 1];                                 //check next
                    i--;
                }
                array[i] = page;                                            //otherwise, insert at rear
                numElems++;
            }
        }
    }
    
    
    /**
     * Determines if the queue is empty.
     * @return True if the queue is empty; otherwise, false.
     */
    @Override
    public boolean isEmpty(){
        return numElems == 0;
    }
    
    /**
     * Determines if the queue is full.
     * @return True if the queue is full; otherwise, false.
     */
    @Override
    public boolean isFull() {
        return numElems == maxSize;
    }
    
    /**
     * Removes an item from the front of the priority queue.
     * 
     * Note:  The isEmpty method should be called first to prevent errors.
     * @return The item that was removed.
     */
    @Override
    public Page remove() {
        Page temp = array[front];
        if (!isEmpty())
        {
            temp = array[front++];
            if (front == maxSize)  //wraparound
            {
                front = 0;
            }
            --numElems;
        }
        return temp;
    }
    
     /**
     * Removes a Page object from the specified position in the queue.
     * 
     * Note:  The isEmpty method should be called first to prevent errors.
     * @param max The position of the least recently used page in the queue
     * @return Page object that was removed.
     */
    @Override
    public Page remove(int max)
    {
        Page removed = array[max];
        if(!isEmpty())
        {
            for(int i = max; i<numElems-1;i++)
        {
            array[i] = array[i+1];
        }
        numElems--; 
        }
        return removed;
    }
    
    /**
     * Searches for the least recently used Page
     * @return maxIndex, position of the least recently used page
     */
    public int findLRU()
    {
        int max = array[front].getRecUsed();
        int maxIndex = front;
        for(int i=front+1; i < numElems; i++)
        {
            if(array[i].getRecUsed() > max)
            {
                max = array[i].getRecUsed();
                maxIndex = i;
            }
        }
        return maxIndex;
    }
    
    /**
     * Increases age of all pages in the frames with each loop
     */
    public void addAge()
    {
        if (!isEmpty())
        {
            for (int j = 0; j < numElems; j++)
        {
            array[j].setRecUsed();
        }
        }
    }
}
    