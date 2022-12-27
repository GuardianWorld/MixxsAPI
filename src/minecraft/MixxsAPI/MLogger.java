package MixxsAPI;

public class MLogger {
	public static void print(String baseClass, String info, ErrorType errorType) {
		String base = "> ["+ baseClass +"]";

		switch (errorType) {
			case NORMAL:
				base = base.concat(": ");
				break;

			case WARNING:
				base = base.concat(" Warning: ");
				break;

			case ERROR:
				base = base.concat(" Error: ");
				break;
		}

		base = base.concat(info);
		
		if(errorType == ErrorType.ERROR) { System.err.println(base);}
		else { System.out.println(base); }
	}

	enum ErrorType {
		NORMAL, WARNING, ERROR
	}
}

