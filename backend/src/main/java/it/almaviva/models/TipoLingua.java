package it.almaviva.models;

//import java.util.ArrayList;
//import java.util.List;

public enum TipoLingua {
	ITALIANO("IT"),
    SPAGNOLO("ES"),
    INGLESE("EN"),
    FRANCESE("FR")
;

    private final String value;

    TipoLingua(String v) {value = v;}

    public String value() {return value;}

    public static String getLingua(String lingua) {

    	for(TipoLingua lang : TipoLingua.values())
    		if(lang.value.equalsIgnoreCase(lingua))
    			return lang.value;
    	
    	return "-1";
    }
    
    /*  Controllo lingua v. beta  ( per usare scommentare riga 3 e 4)
    public static boolean controlloLingua ( String lingua ){

        List < String > lingue = new ArrayList<>();

        boolean isItNotHere = false;

        int c = 0;

        
        for ( TipoLingua tipoLingua: TipoLingua.values() ){

            lingue.add(tipoLingua.value());
        }


        for ( String language : lingue ){

            if ( !lingua.equalsIgnoreCase(language) )
                c++;
        }


        if ( c > 0)
            isItNotHere = true;


        return isItNotHere;
    }*/
    
}