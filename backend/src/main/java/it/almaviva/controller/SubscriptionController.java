package it.almaviva.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.almaviva.entity.Subscription;
import it.almaviva.models.Dati;
import it.almaviva.models.Esito;
import it.almaviva.service.SubscriptionService;

@RestController
@CrossOrigin
@RequestMapping ("/subscription-service")
public class SubscriptionController {

    @Autowired
    SubscriptionService subService;
    
    //CREATE:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    @PostMapping("/subscribe")
    public Map <Long, Esito> subscribe ( @RequestBody Subscription sub, @RequestHeader ("authorization") String pass ){

    	return subService.insertSubscripion(sub, pass);
    }
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    

    //DELETE;::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    @DeleteMapping("/unsubscribe")
    public Map < Dati, Esito > unsubscribe ( @RequestBody Subscription sub, @RequestHeader ("authorization") String pass ){

        return subService.removeSubscription( sub, pass );
    }
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
}
