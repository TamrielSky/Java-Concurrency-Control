
//Rajesh Balasubramanian
//rajeshba@buffalo.edu

public class CDLListFine<T> extends CDLList<T>{


	public CDLListFine(T v) 
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
			synchronized(elepos)
			{
				return elepos;

			}

		}
		// Move to the previous element.
		public void previous() 
		{
			synchronized(elepos)
			{
				if(elepos.previous == phantom)
				{
					elepos = phantom.previous;
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
			synchronized(elepos)
			{
				if(elepos.next == phantom)
				{
					elepos = phantom.next;
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

			while(true)
			{

				Element temp = writer.elepos.previous;

				synchronized(temp)
				{

					synchronized(writer.elepos)
					{
						

						if(temp == writer.elepos.previous)
						{
							if(temp == phantom)
							{
								writer.elepos.previous.next = e;
								e.next = writer.elepos;
								e.previous = writer.elepos.previous;
								writer.elepos.previous = e;
								cdlHead = e;
								break;
							}
							else
							{
								writer.elepos.previous.next = e;
								e.next = writer.elepos;
								e.previous = writer.elepos.previous;
								writer.elepos.previous = e;
								break;

							}
						}


					}
				}
			}

			return true;
		}


		// Add after the current element.
		public boolean insertAfter(T val) 
		{
			Element e = new Element(val);	

			synchronized(writer.elepos)
			{
				synchronized(writer.elepos.next)
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
}
