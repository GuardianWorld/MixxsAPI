package MixxsAPI;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class Auxiliary_Calls {

	public Properties inputFormatException;
	
	public Auxiliary_Calls(Properties loadedProperty) {
		inputFormatException = loadedProperty;
	}
	
    public int TryGettingValues(String valueProperty, int valueDef){
        int value;
        try{
        	value = Integer.valueOf(inputFormatException.getProperty(valueProperty)).intValue();
        	if(value < 0) {	throw new Exception(); }
        }
        catch(Exception numberFormatException5){
        	MLogger.print("Item API", "Invalid value for: " + valueProperty + ". Using default value ["+valueDef+"].", MLogger.ErrorType.ERROR);
            return valueDef;
        }    
        
        return value;
    }
    
    public int TryGettingValuesNoError(String valueProperty, int valueDef) {
    	int value;
        try {
        	value = Integer.valueOf(inputFormatException.getProperty(valueProperty)).intValue();
        	if(value < 0) throw new Exception();
        } catch(Exception numberFormatException5) {
            return valueDef;
        }    
        
        return value;
    }

    public boolean TryGettingValuesNoError(String valueProperty) {
    	String aux = inputFormatException.getProperty(valueProperty, "NO").toUpperCase();
    	return (aux.equals("YES") || aux.equals("TRUE"));
    }
    
    public float TryGettingValues(String valueProperty, float valueDef){
        float value;
        try {
        	value = Float.valueOf(inputFormatException.getProperty(valueProperty)).floatValue();
        	if(value < 0) throw new Exception();
        } catch(Exception numberFormatException5) {
        	MLogger.print("Item API", "Invalid value for: " + valueProperty + ". Using default value ["+valueDef+"].", MLogger.ErrorType.ERROR);
            return valueDef;
        }    
        
        return value;
    }
    
    public String TryGettingTexture(String textureProperty) {
    	String image = inputFormatException.getProperty(textureProperty, null);
    	if(image == null) {
    		MLogger.print("Item API", "No texture for: " + textureProperty + ". Using default texture.", MLogger.ErrorType.ERROR);
    		return null;
    	}
    	if(getClass().getResourceAsStream(image) != null) {
    		return image;
    	}
    	MLogger.print("Item API", "Invalid texture for: " + textureProperty + ". Using default texture", MLogger.ErrorType.ERROR);
		return null;
    }
    
    
    public String TryGettingTextureRedux(String texture, String baseNameProperty) {
    	if(getClass().getResourceAsStream(texture) != null) { 
    		return texture; 
    	}  	
    	MLogger.print("Item API", "Invalid texture for: " + texture + ". Using default texture", MLogger.ErrorType.ERROR);
		return null;
    }
    
	
}
