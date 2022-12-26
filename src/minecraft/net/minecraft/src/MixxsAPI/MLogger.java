package net.minecraft.src.MixxsAPI;

public class MLogger {

	public static final int NORMAL = 0;
	public static final int WARNING = 1;
	public static final int ERROR = 2;
	
	public static void print(String baseClass, String info, int errorType) {
		String base = "> ["+ baseClass +"]";
		if(errorType == NORMAL) 	  { base = base.concat(": "); 		   }
		else if(errorType == WARNING) { base = base.concat(" Warning: "); }
		else if(errorType == ERROR)   {	base = base.concat(" Error: ");   }
		
		base = base.concat(info);
		
		if(errorType == ERROR) { System.err.println(base);}
		else { System.out.println(base); }
	}

}

