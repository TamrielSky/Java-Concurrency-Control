//Rajesh Balasubramanian
//rajeshba@buffalo.edu

public class CDLList<T> {

	Element cdlHead;
	public Element phantom;

	// Create a CDLList with one value
	public CDLList(T v) 
	{
		cdlHead = new Element(v);
		phantom = new Element();
		cdlHead.previous = cdlHead.next = cdlHead;

		cdlHead.next = phantom;
		phantom.next = cdlHead;
		phantom.previous = cdlHead;
		cdlHead.previous = phantom;

	}
	
	public class Element {
		// return the element’s value
		public T value;
		Element previous;
		Element next;
		

		public Element(T val)
		{
			value = val;

		}

		public Element()
		{

		}
		public T value() 
		{
			return value;

		}
	}
	// Return the head of the list. Never null
	public Element head() 
	{
		return cdlHead;

	}
	// Return a cursor at element from in the list
	public Cursor reader(Element from) 
	{
		Cursor c = new Cursor();
		c.elepos = from;
		return c; 
	}
        
        //Used to point to an element in the list
	public class Cursor {

		Element elepos;
		// Return the current element.

		public Element current() 
		{
			return elepos;	  
		}
		// Move to the previous element.
		public void previous() 
		{
			if (elepos == cdlHead)
			{
				elepos = elepos.previous.previous;	    			    		
			}
			else
			{
				elepos = elepos.previous;
			}

		}
		// Move to the next element
		public void next() 
		{
			if(elepos.next == phantom)
			{
				elepos = elepos.next.next;
			}
			else
			{
				elepos = elepos.next;
			}
		}
		// Returns a writer at the current element
		public Writer writer() 
		{

			return new Writer(this); 		
		}
	}

	
        //Used to insert and delete elements after and before the element where the cursor points 
	public class Writer {

		Cursor writer ;
		public Writer(Cursor curr)
		{
			writer = curr;
		}

		// Add before the current element.
		public boolean insertBefore(T val) 
		{
			Element e  = new Element(val);
			if(writer.elepos.previous == phantom)
			{

				writer.elepos.previous.next = e;
				e.next = writer.elepos;
				e.previous = writer.elepos.previous;
				writer.elepos.previous = e;
			}
			else
			{
				writer.elepos.previous.next = e;
				e.next = writer.elepos;
				e.previous = writer.elepos.previous;
				writer.elepos.previous = e;				
			}

			return true;

		}
		// Add after the current element.
		public boolean insertAfter(T val) 
		{
			Element e = new Element(val);			
			writer.elepos.next.previous = e;
			e.next = writer.elepos.next;
			writer.elepos.next = e;
			e.previous = writer.elepos;
			return true;
		}

	}



}


