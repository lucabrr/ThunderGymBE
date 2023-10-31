package com.ThunderGym;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ThunderGym.security.payload.RegisterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.ThunderGym.classes.Cliente;
import com.ThunderGym.classes.Fattura;
import com.ThunderGym.service.ClienteService;
import com.ThunderGym.service.FatturaService;

@Component
public class TestRunner  implements CommandLineRunner{
@Autowired ClienteService cs;
@Autowired FatturaService fs;

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		for(int i = 0; i < 18; i++) {
		
			Cliente c = cs.creaClienteJF("y");
			//System.out.println(c.toString());
			cs.salvaCliente(c);	
			Fattura f = fs.creaFattura(c);
			fs.salvaFattura(f);
		}
		for(int i = 0; i < 22; i++) {
			
			Cliente c = cs.creaClienteJF("x");
			//System.out.println(c.toString());
			cs.salvaCliente(c);	
			Fattura f = fs.creaFatturaMenoMese(c);
			fs.salvaFattura(f);
		}
		
		
		System.out.println("mese corrente: " +fs.importoMeseCorrente());
		System.out.println("mese scorso: " + fs.importoMeseScorso());
		
		// creazione account admin su database
		RestTemplate rs = new RestTemplate();
		String url = "http://localhost:8080/api/auth/register";
		//oggetto da inviare
		Set<String> roles = new HashSet<String>();
		roles.add("ADMIN");
		RegisterDto admin = new RegisterDto("admin","admin","test@email.it","admin",roles);
		//configurazione JSON (gli dico alla chiamata che il body è un json)
		HttpHeaders configurazione = new HttpHeaders();
		configurazione.setContentType(MediaType.APPLICATION_JSON);
		//creazione chiamata
		HttpEntity<RegisterDto>chiamata = new HttpEntity<>(admin,configurazione);
		//esecuzione chiamata
		ResponseEntity<String> res = rs.postForEntity(url, chiamata, String.class);
		
		System.out.println(res.getBody());
		
		
		
		
		
		
		
	}

}
