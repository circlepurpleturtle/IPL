package it.almaviva.entity;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class Subscription {	

	/*            JSON

   		{
    		"os": "Windows",
    		"dataPartenza": "2018-12-10",
    		"token": "MyToken",
    		"nextStop": "",
   			 "veichleNumber": 11,
    		"subscriptionLength": 0,
    		"language": "IT",
    		"udid": "MyUdid",
    		"veichleType": "bus"
   		}
   
 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column ( name = "subscription_id" )
	long subscriptionId;
	
	@Column(length = 50, nullable = false )
	String token;
	
	@Column(length = 50, nullable = false)
	String udid;
	
	@Column(name = "veichle_number", nullable = false) 
	String veichleNumber;

	@Column(name = "veichle_type", nullable = false)
	String veichleType;
	
	//LocalDateTime
	@Column(name = "data_partenza")
	Date dataPartenza;
	
	@Column(name = "next_stop")
	String nextStop;
	
	@Column(name = "subscription_lenght")
	int subscriptionLength;
	
	@Column(name = "operative_system", nullable = false)
	String os;
	
	@Column(length = 2, nullable = false)
	String language;
	
	@Column(nullable = false)
	@JsonIgnore
	Timestamp timestamp;
	
}