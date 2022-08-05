package it.almaviva.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.almaviva.entity.Subscription;


public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

	//Utilizziamo questo metodo per trovare una coppia token/Udid, gestita in base all'applicazione chiamante
	Subscription findByTokenAndUdidAndOs(String token, String udid, String os);

	//Utilizziamo questo metodo per trovare una sottoiscrizione specifica
	Subscription findByTokenAndUdidAndSubscriptionId(String token, String udid, long subscriptionId);

	//Utilizzamo questo metodo per cancellare una sottoiscrizione specifica
	@Transactional
	void removeByTokenAndUdidAndSubscriptionId(String token, String udid, long subscriptionId);

}