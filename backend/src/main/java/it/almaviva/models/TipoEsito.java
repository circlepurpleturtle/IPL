package it.almaviva.models;

/*
 * Enumeratore che ci permette di gestire le varie casistiche delle operazioni
 */
public enum TipoEsito {
	COD_OPERAZIONE_COMPLETATA("100"),
    COD_ERRORE_INSERIMENTO("101"),
    COD_PARAMETRO_NON_VALIDO("102"),
//  COD_ERRORE_GENERICO("103"),
//  COD_BAD_REQUEST("104"),
    COD_AUTHORIZATION_FAILED("105"),
//  COD_ERRORE_MODIFICA("106"),
    COD_PARAMETRO_MANCANTE("107"),
	COD_ERRORE_ENTITA_PRESENTE("108"),
    COD_ERRORE_CANCELLAZIONE("109"),
//  COD_RICHIESTA_NON_VALIDA("200"),
//  COD_VEICOLO_NON_IN_VIAGGIO("201"),
//  COD_VEICOLO_ARRIVATO("202"),
//  COD_VEICOLO_NON_VALIDO("203"),
//  COD_VEICOLO_ESCLUSO_DALLA_SPERIMENTAZIONE("204"),
//  COD_RIPROVA_PIU_TARDI("205"),
;

    
    private final String value;

    TipoEsito(String v) {value = v;}

    public String value() {return value;}
    
}