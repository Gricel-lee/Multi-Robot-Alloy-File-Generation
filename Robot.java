
public class Robot {
	public Capability[] capabilities;
	public String name;
	
	
	public Robot (String n, Capability[] c) {
		this.name = n;
		this.capabilities = new Capability[c.length];
		//System.arraycopy(this.capabilities, 0, c, 0, c.length);
		System.arraycopy(c, 0, this.capabilities, 0, c.length);
	}
	
	public String getName() {
		   return this.name;
	}
	
	public int getNumCapab() {
		   return this.capabilities.length;
	}

	//System.out.println(this.name);
}