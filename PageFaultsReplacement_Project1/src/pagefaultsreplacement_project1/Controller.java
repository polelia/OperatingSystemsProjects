package pagefaultsreplacement_project1;


import java.util.Random;

/**
 * Creates collection of page objects
 * Executes management system algorithms
 * Displays statistics
 */
public class Controller {
    
    private Queue fifoQueue;         //run those 2 100 times for each 2 - 5 frames
    private PriorityQueue lruQueue;
    
    private Page[] pagesArray = new Page [50];     //contains page objects created using random number generator, comment out during the test
    
    //********Test_1 book's example Test_1**********************
    //comment out for the test, comment out previous array declaration
    //where A = 1, B = 2, C = 3, D = 4    to test methods and be able to compare results with the ones from book
    // A B A C A B D B A C D (PAGE 72 IN THE BOOK)
    // 1 2 1 3 1 2 4 2 1 3 4
    
    //    public int[] testArray = {1,2,1,3,1,2,4,2,1,3,4};
    //    private Page[] pagesArray = new Page[11];
   
    //******************************************************
   
    private double numOfPages; // the divisor
    private int fifoFaults = 0;
    private int lruFaults = 0;
    private int fifoHits = 0;
    private int lruHits = 0;
    private double fifoFailureRate;
    private double lruFailureRate;
    Random generator = new Random(5);
    
    private int numFrames = 2;        // <-- change number of frames here
    
    /**
     * Main processing method, controls program 
     */
    public void execute() 
    {
        //populates array with newly created page objects, make it as a comment if you do book example test
        for (int i = 0; i < pagesArray.length; i++)
        {
            pagesArray[i] = new Page(getPages()); //passes randomly generated number into Page constructor
            // System.out.print(pagesArray[i].toString());
        }     
       
        //********Test_1 book's example Test_1**********************
        // please comment in previous method before doing this test
//         for (int i = 0; i < pagesArray.length; i++)
//         {
//           pagesArray[i] = new Page(testArray[i]); 
//           System.out.print(pagesArray[i].toString());
//         }  
        //*******************************************************
        
        
        fifoQueue = new Queue(numFrames);            
        lruQueue = new PriorityQueue (numFrames);
        fifoPageFaults(pagesArray);
        lruPageFaults(pagesArray);
        
        numOfPages = pagesArray.length;
        System.out.println("Fifo Page Hits: " + fifoHits);
        System.out.println("Fifo Page Faults: " + fifoFaults);  
        fifoFailureRate = (fifoFaults / numOfPages) *100;
        System.out.println("Avg Failure Rate: " + fifoFailureRate +"%"); 
        System.out.println();
        
        System.out.println("LRU Page Hits: " + lruHits);
        System.out.println("LRU Page Faults: " + lruFaults);  
        lruFailureRate = (lruFaults / numOfPages) * 100;
        System.out.println("Avg Failure Rate: " + lruFailureRate +"%");   
    }
      
 /**
  * Mimics FIFO page replacement algorithm
 * @param pages pagesArray of Page objects that is waiting to be loaded in the memory
 */
    public void fifoPageFaults(Page[] pages) 
    {
        for (Page page : pages) 
        {                                           
            if (!fifoQueue.isFull())               //not full
            { 
                if (!fifoQueue.linearSearch(page)) //the page isn't loaded
                {
                    fifoQueue.insert(page);         //load page
                    fifoFaults++;                   //page fault
                } 
                else                                //not full, page is already loaded
                {
                    fifoHits++;
                }
            } 
            else                                    //frame is full
            {   
                if (!fifoQueue.linearSearch(page)) //the page isn't loaded
                {
                   fifoFaults++;                   
                   fifoQueue.remove();
                   fifoQueue.insert(page);
                } 
                else                               //full, but the page is already loaded
                {
                    fifoHits++;
                } 
            }                  
        }
    }
    
    
 /**
 * Mimics LRU page replacement algorithm
 * @param pages pagesArray of Page objects that is waiting to be loaded in the memory
 */
    public void lruPageFaults(Page[] pages) {
        
        for (int i = 0; i< pages.length; i++) 
        {
            lruQueue.addAge();                     //increments age of all pages
            
            if (!lruQueue.isFull())                //not full
            { 
                
                if (!lruQueue.linearSearch(pages[i])) //the page isn't loaded
                {
                    lruQueue.insert(pages[i]);         //load page
                    lruFaults++;                       //page fault
                } 
                else                                  //not full, page is already loaded
                {
                    lruHits++;
                }
            } 
            else                                      //frame is full
            {   
                if (!lruQueue.linearSearch(pages[i])) //the page isn't loaded
                {
                   lruFaults++;
                   lruQueue.remove(lruQueue.findLRU()); //removes least recently used element
                   lruQueue.insert(pages[i]);           //inserts the page into appropriate location
                } 
                else                                    //full, but the page is already loaded
                {
                   lruHits++;
                } 
            }                  
        }
    }
    
    
  /**
  * Generates random numbers
  * @return random integer
  */
    private int getPages() 
    {  
        int num = generator.nextInt(6);  
        return num;
    }              
}
