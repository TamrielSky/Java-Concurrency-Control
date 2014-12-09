
//Rajesh Balasubramanian
//rajeshba@buffalo.edu


public class CDLCoarse<T> extends CDLList<T>{

	private Object lock = new Object();
	public CDLCoarse(T v) 
	{
		super(v);		
	}	

	public Cursor reader(Element from) 
	{
		Cursor c = new Cursor();
		c.elepos = from;
		return c; 
	}

	public class Cursor extends CDLList<T>.Cursor
	{

		// Return the current element.

		public Element current() 
		{
			synchronized(lock)
			{
				return elepos;		    		 
			}
		}
		// Move to the previous element.
		public void previous() 
		{
			synchronized(lock)
			{
				if (elepos == head())
				{
					elepos = elepos.previous.previous;	    			    		
				}
				else
				{		 	    	
					elepos = elepos.previous;
				}
			}

		}
		// Move to the next element
		public void next() 
		{
			synchronized(lock)
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

		}
		// Returns a writer at the current element
		public Writer writer() 
		{		    	 
			return new Writer(this); 		
		}
	}

	public class Writer extends CDLList<T>.Writer {

		public Writer(Cursor curr)
		{
			super(curr);
		}

		// Add before the current element.
		public  boolean insertBefore(T val) 
		{
			Element e  = new Element(val);
			synchronized(lock)
			{

				if(writer.elepos.previous == phantom)
				{
					writer.elepos.previous.next = e;
					e.next = writer.elepos;
					e.previous = writer.elepos.previous;
					writer.elepos.previous = e;
					cdlHead = e;
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


		}
		// Add after the current element.
		public boolean insertAfter(T val) 
		{
			Element e = new Element(val);	

			synchronized(lock)
			{
				writer.elepos.next.previous = e;
				e.next = writer.elepos.next;
				writer.elepos.next = e;
				e.previous = writer.elepos;
				return true;

			}

		}

	}
}




