package it.almaviva.service;

import java.util.Map;

import it.almaviva.entity.Subscription;
import it.almaviva.models.Dati;
import it.almaviva.models.Esito;

public interface SubscriptionService{

    //CREATE
    Map <Long, Esito> insertSubscripion (Subscription sub, String pass);
    
    //DELETE
    Map <Dati, Esito> removeSubscription( Subscription sub, String pass);
    
}
