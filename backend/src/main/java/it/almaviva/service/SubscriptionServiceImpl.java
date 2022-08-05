package it.almaviva.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.almaviva.entity.Subscription;
import it.almaviva.models.Dati;
import it.almaviva.models.Esito;
import it.almaviva.models.TipoEsito;
import it.almaviva.models.TipoLingua;
import it.almaviva.repository.SubscriptionRepository;
import it.almaviva.tools.CheckPass;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    private SubscriptionRepository subRepository;
    
	@SuppressWarnings("unused")
    private Logger log = LoggerFactory.getLogger(getClass());

	//CREATE::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    public Map <Long, Esito> insertSubscripion (Subscription sub, String pass ){
    	
		Map <Long, Esito> ret = new HashMap<>();
    	Esito esito = new Esito();
    	long id;
		

		//Controllo se l'applicazione chiamate ha fornito la password corretta ( 105 )
		if ( CheckPass.checkPass(pass) ){
			
			esito.setCodice(TipoEsito.COD_AUTHORIZATION_FAILED.value());
			ret.put(0L, esito);
			
			return ret;

		}
		
		//Controlliamo se il Json è stato compliato da tutti i campi richiesti  ( 107 )
    	if (isSubJsonEmpty(sub)){
    		
            esito.setCodice(TipoEsito.COD_PARAMETRO_MANCANTE.value());
            ret.put (0L, esito);
            return ret;
            
        }
    	
    	
		//Controlliamo se il Json è stato compilato correttamente ( 102 )
    	if(!isJsonCorrectlyCompiled(sub)) {

            esito.setCodice(TipoEsito.COD_PARAMETRO_NON_VALIDO.value());
            ret.put (0L, esito);
            return ret;

    	}	


		//Controlliamo se l'applicazione chiamante ha già una sottoiscrizione uguale a quella ricevuta e in caso lo segnaliamo
        if (subRepository.findByTokenAndUdidAndOs(sub.getToken(), sub.getUdid(), sub.getOs()) != null) {
        	
            esito.setCodice(TipoEsito.COD_ERRORE_ENTITA_PRESENTE.value());
            ret.put(0L, esito);
            return ret;
            
        }
        
        //Impostiamo l'orario nella quale abbiamo ricevuto il dato
    	sub.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));

		//Salviamo e controlliamo se il salvataggio è andato a buon fine
    	if (subRepository.save(sub) == null) {
    		
    		id = sub.getSubscriptionId();
    		esito.setCodice(TipoEsito.COD_ERRORE_INSERIMENTO.value());
    		
    		ret.put(id, esito);

    	} else {	//Se il salvataggio non è andato a buon fine lo segnaliamo
    		
            id = subRepository.findByTokenAndUdidAndOs(sub.getToken(), sub.getUdid(), sub.getOs()).getSubscriptionId();
            esito.setCodice(TipoEsito.COD_OPERAZIONE_COMPLETATA.value());
            
    		ret.put(id, esito);
 
    	}
    	
    	return ret;
    }
    //:::::::::::::::::::::
    
	//DELETE ::::::::::::::
    @Override
	public Map<Dati, Esito> removeSubscription( Subscription sub, String pass ) {

		Map <Dati, Esito> ret = new HashMap<>();
		Esito esito = new Esito();

		if ( CheckPass.checkPass(pass) ){

			esito.setCodice(TipoEsito.COD_AUTHORIZATION_FAILED.value());
			ret.put(new Dati(), esito );

			return ret;

		}
		
		//Creiamo questa variabile per poter passare dati specifici dentro la subscription
		Dati dati = new Dati ( sub.getToken(), sub.getUdid(), sub.getSubscriptionId() );


		//Controlliamo se il Json è stato compliato correttamente
		if (isUnsubJsonEmpty(sub)) {

			esito.setCodice(TipoEsito.COD_ERRORE_INSERIMENTO.value());
			ret.put( dati, esito );

			return ret;
		}
		

		//Controlliamo se l'applicazione chiamante ha già una sottoiscrizione uguale a quella ricevuta e in caso lo segnaliamo
		if ( subRepository.findByTokenAndUdidAndSubscriptionId(sub.getToken(), sub.getUdid(), sub.getSubscriptionId()) == null ){

			esito.setCodice(TipoEsito.COD_ERRORE_CANCELLAZIONE.value());
			ret.put( dati, esito );

			return ret;
		}


		//Cancellazione
		subRepository.removeByTokenAndUdidAndSubscriptionId( sub.getToken(), sub.getUdid(), sub.getSubscriptionId() );


		//Controlliamo se la cancellazione è andata a buon fine
		if ( subRepository.findByTokenAndUdidAndSubscriptionId( sub.getToken(), sub.getUdid(), sub.getSubscriptionId()) != null ){

			//Se non è andata a buon fine lo segnaliamo
			esito.setCodice(TipoEsito.COD_ERRORE_CANCELLAZIONE.value());
			ret.put( dati, esito );

			return ret;
		}

		esito.setCodice(TipoEsito.COD_OPERAZIONE_COMPLETATA.value());
		ret.put ( dati, esito );

		return ret;

	}
	//::::::::::::::::::::::::::::::::::
    

	//METODI DI CONTROLLO ::::::::::::::

	//Con questo controlliamo se il metodo di inserimento ha ricevuto tutti i dati di cui avesse bisogno ( 107 )
	private boolean isSubJsonEmpty(Subscription sub) {

        
        if (sub.getToken().isBlank() || sub.getToken().equals(""))    		
    		return true;
    	
    	if (sub.getUdid().isBlank() || sub.getUdid().equals(""))
    		return true;
    		
    	if(sub.getVeichleNumber().isBlank() || sub.getVeichleNumber().equals("")) 
    		return true;

        if (sub.getVeichleType().isBlank() || sub.getVeichleType().equals(""))
        	return true;
        
    	if ( sub.getLanguage().isBlank() || sub.getLanguage().equals(""))
			return true;
    	
    	if (sub.getOs().isBlank() || sub.getOs().equals(""))
    		return true;
    	

        return false;

    }


	//Controllo se il Json è stato compilato correttamente	( 102 )
	private boolean isJsonCorrectlyCompiled (Subscription sub) {
		
		String str = TipoLingua.getLingua(sub.getLanguage());

		if(str.equals("-1"))
    		return false;
    	
    	else
    		sub.setLanguage(str);


		//Controlliamo se la data è null; se lo è mettiamo la data corrente
		if (sub.getDataPartenza() == null || sub.getDataPartenza().toString().equals(""))
			sub.setDataPartenza(Date.valueOf(LocalDate.now()));

		//Controlliamo se la Subscription Length è uguale a 0; se così fosse dobbiamo convertirla in uan grandezza che rappresenta tutti
    	if (sub.getSubscriptionLength()<= 0 || sub.getSubscriptionLength() > 7)
    		sub.setSubscriptionLength(7);

//		Controllo lingua v. beta 
//*		if ( TipoLingua.controlloLingua(sub.getLanguage()))
//			return true;

    	//* false
		return true;

	}


	//Con questo controlliamo se il metodo di rimozione ha ricevuto tutti i dati di cui avesse bisogno   ( 107 )
	private boolean isUnsubJsonEmpty ( Subscription sub ) {

		if (sub.getToken().isBlank() || sub.getToken().equals(""))    		
    		return true;
    	
    	if (sub.getUdid().isBlank() || sub.getUdid().equals(""))
    		return true;
		
		if(sub.getSubscriptionId()<= 0 )
    		return true;

		return false;
	}
	//::::::::::::::::::::::
}