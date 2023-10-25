abstract class List 
{
    abstract void add(int data);
    abstract public void addToEnd(int data);
    abstract public Node deleteAtStart();
    abstract public Node deleteAtEnd();
    abstract public Node deleteAtIndex(int indexToDelete);
    abstract public Node search(int dataRequired);
    abstract public Node searchAtIndex(int indexToSearch);
    abstract public void print();
    abstract public String toString();
    abstract public void reverseList();
    abstract public LinkedList clone();
}

class LinkedList extends List 
{
    Node headNode;
    Node tailNode;

    int totalNodes = 0;

    public void add(int data) 
    {
        Node nodeToAdd = new Node(data);

        if (this.headNode == null) 
        {
            this.headNode = nodeToAdd;
            this.tailNode = nodeToAdd;
        } 
        else 
        {
            nodeToAdd.next = this.headNode;
            this.headNode.prev = nodeToAdd; // Update the previous reference
            this.headNode = nodeToAdd;
        }
        totalNodes++;
    }

    public void addToEnd(int data) 
    {
        Node nodeToAdd = new Node(data);

        if (this.headNode == null) 
        {
            this.headNode = nodeToAdd;
            this.tailNode = nodeToAdd;
        } 
        else 
        {
            tailNode.next = nodeToAdd;
            nodeToAdd.prev = tailNode; // Update the previous reference
            tailNode = nodeToAdd;
        }
        totalNodes++;
    }

    public Node deleteAtStart() 
    {
        Node nodeToDelete = headNode;

        if (headNode != null) 
        {
            if (headNode.next != null) 
            {
                headNode = headNode.next;
                headNode.prev = null; // Update the previous reference of the new head
                // totalNodes--;
            } 
            else 
            {
                headNode = null;
                tailNode = null;
                // totalNodes--;
            }
            totalNodes--;
        } 
        else 
        {
            System.out.println("List is empty");
        }
        
        return nodeToDelete;
    }

    public Node deleteAtEnd() 
    {
        Node nodeToDelete = tailNode;

        if (tailNode != null) 
        {
            if (tailNode.prev != null) 
            {
                tailNode = tailNode.prev;
                tailNode.next = null; // Update the next reference of the new tail
                // totalNodes--;
            } 
            else 
            {
                headNode = null;
                tailNode = null;
                // totalNodes--;
            }
            totalNodes--;
        } 
        else 
        {
            System.out.println("List is empty");
        }
        
        return nodeToDelete;
    }


    public Node deleteAtIndex(int indexToDelete)  //index starting from 1 ... not 0   :)
    {
        int indexCounter = 1;
        Node nodeToDelete = headNode;

        if (indexToDelete == 1) 
        {
            nodeToDelete = this.deleteAtStart();
        }
        else if (indexToDelete == totalNodes) 
        {
            nodeToDelete = deleteAtEnd();
        } 
        else if (headNode != null) 
        {
            while (indexCounter != indexToDelete) 
            {
                nodeToDelete = nodeToDelete.next;
                indexCounter++;
            }
            Node prevNode = nodeToDelete.prev;
            Node nextNode = nodeToDelete.next;

                                //just making sure that prev is not null ... these two(this one and next one) if-else are not
                                //necessary because we already checked for first and last indexex
                                //
                                //or we can use these and delete/remove/ignore the if statements used at start for checking of 1st and last indexes
                                //i think...
            if (prevNode != null)
            {
                prevNode.next = nextNode;
            } 
            else 
            {
                headNode = nextNode;
            }

            if (nextNode != null) 
            {
                nextNode.prev = prevNode;
            } 
            else 
            {
                tailNode = prevNode;
            }
            totalNodes--;
        } 
        else 
        {
            System.out.println("List is empty");
        }
        
        return nodeToDelete;
    }


    public Node search(int dataRequired) 
    {
        // Node returnNode = null;
        Node currentNode = headNode;

        while (currentNode != null) 
        {
            if (currentNode.data == dataRequired) 
            {
                return currentNode; // exits the function
            }
            currentNode = currentNode.next;
        }

        return currentNode;
        // return returnNode;

    }


    public Node searchAtIndex(int indexToSearch) 
    {
        Node returnNode = null;
        Node currentNode = headNode;

        if(indexToSearch <= totalNodes)
        {
            int indexCounter = 1;

            while (currentNode != null) 
            {
                if (indexCounter == indexToSearch) 
                {
                    return currentNode; // exits the function
                }
                currentNode = currentNode.next;
                indexCounter++;
            }
        }

        return returnNode;
    }


    public void print() 
    {
        String toPrint = "";
        Node currentNode = this.headNode;

        if (headNode != null) 
        {
            while (currentNode != null) 
            {
                toPrint += currentNode.data;
                if (currentNode.next != null) 
                {
                    toPrint += "->";
                }
                currentNode = currentNode.next;
            }
        } 
        else 
        {
            toPrint = "The List is empty";
        }

        System.out.println(toPrint);
    }

    public String toString() 
    {
        String toReturn = "[";
        Node currentNode = this.headNode;

        if (headNode != null) 
        {
            while (currentNode != null) 
            {
                toReturn += currentNode.data;
                if (currentNode.next != null) 
                {
                    toReturn += ", ";
                }
                currentNode = currentNode.next;
            }
        }

        toReturn += "]";

        return toReturn;
    }

    public void reverseList() 
    {
        Node temp = null;
        Node currentNode = headNode;

        while (currentNode != null) 
        {
            temp = currentNode.prev;
            currentNode.prev = currentNode.next;
            currentNode.next = temp;
            currentNode = currentNode.prev;
        }

        if (temp != null) 
        {
            headNode = temp.prev;
        }
    }

    public LinkedList clone() 
    {
        LinkedList clonedList = new LinkedList();

        if (this.headNode != null) 
        {
            Node currentNode = this.headNode;

            while (currentNode != null) 
            {
                clonedList.addToEnd(currentNode.data); // Create new nodes in the cloned list
                currentNode = currentNode.next;
            }
        }
        return clonedList;
    }

    public static LinkedList mergeList(LinkedList firstList, LinkedList secondList) 
    {
        LinkedList cloneOfFirstList = firstList.clone();
        LinkedList cloneOfSecondList = secondList.clone();
        LinkedList mergedList = new LinkedList();

        mergedList.headNode = cloneOfFirstList.headNode;
        mergedList.tailNode = cloneOfFirstList.tailNode;
        mergedList.tailNode.next = cloneOfSecondList.headNode;
        
        cloneOfSecondList.headNode.prev = mergedList.tailNode; // Update the previous reference of the second list's head
        mergedList.tailNode = cloneOfSecondList.tailNode;
        mergedList.totalNodes = firstList.totalNodes + secondList.totalNodes;

        return mergedList;
    }
}

class Node 
{
    int data;
    Node next;
    Node prev; // Add a previous reference

    Node() 
    {
        this.data = 0;
        this.next = null;
        this.prev = null;
    }

    Node(int data) 
    {
        this.data = data;
        this.next = null;
        this.prev = null;
    }
}

public class Main
{
    public static void main(String[] args)
    {
        LinkedList myList = new LinkedList();
        LinkedList myList2 = new LinkedList();

        myList.addToEnd(5);
        myList.addToEnd(10);
        myList.addToEnd(15);
        myList.addToEnd(20);
        myList.addToEnd(25);
        myList.addToEnd(30);


        myList2.add(40);
        myList2.add(50);
        myList2.add(60);
        myList2.add(70);
        myList2.add(80);
        myList2.add(90);


        myList.print();
        myList2.print();

        LinkedList merged = LinkedList.mergeList(myList, myList2);
        merged.print();

        merged.reverseList();
        merged.print();
    }
}
