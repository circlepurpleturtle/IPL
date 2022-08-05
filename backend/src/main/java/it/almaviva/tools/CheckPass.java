package it.almaviva.tools;

public class CheckPass {

    private static final String PASS = "pass";

    static public boolean checkPass ( String password ) {

        if ( !PASS.equals(password) )
			return true;
        
        return false;
        
    }    
}