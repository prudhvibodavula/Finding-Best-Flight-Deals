package com.asu.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.RDFNode;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asu.model.DealsFilter;

@RestController
@RequestMapping("/v1")
public class MainController {

	static String defaultIndeedNameSpace = "http://www.semanticweb.org/SER531_group4_del2#";
	static String fusekiEndpoint = "http://localhost:3030/airlinesdeals/query"; //"http://ec2-54-183-179-124.us-west-1.compute.amazonaws.com:3030/airlinesdeals/query";
				

	@CrossOrigin(origins = "*")
	@PostMapping("/dealsfilter")
	public List<DealsFilter> getJobs(@RequestBody DealsFilter dealsFilter) {
		List<DealsFilter> result = new ArrayList<>();
		result = flightOntology(result, dealsFilter);		
		return result;
	}

	public List<DealsFilter> flightOntology(List<DealsFilter> deals, DealsFilter dealsFilter) {
		StringBuffer queryStr = new StringBuffer();
		List<String> ids = new ArrayList<>();

		String idValue = "";	
		
		queryStr.append("PREFIX dbi" + ": <" + defaultIndeedNameSpace + "> ");
		queryStr.append("PREFIX rdfs" + ": <" + "http://www.w3.org/2000/01/rdf-schema#" + "> ");
		queryStr.append("PREFIX rdf" + ": <" + "http://www.w3.org/1999/02/22-rdf-syntax-ns#" + "> ");
		queryStr.append("PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>");		
				
		if (dealsFilter.getNoOfTickets() != "") {
			queryStr.append("SELECT ?id WHERE { ?id dbi:has_d_iataCode dbi:" + dealsFilter.getSource() + ". ?id dbi:has_a_iataCode dbi:" + dealsFilter.getDestination() + ". ?id dbi:has_numberOfBookableSeats dbi:" + dealsFilter.getNoOfTickets() + ". } LIMIT 25");
		} 
		else {
			queryStr.append("SELECT ?id WHERE { ?id dbi:has_d_iataCode dbi:" + dealsFilter.getSource() + ". ?id dbi:has_a_iataCode dbi:" + dealsFilter.getDestination() + ". } LIMIT 25");
		}	   
			
		Query query = QueryFactory.create(queryStr.toString());

		QueryExecution qexec = QueryExecutionFactory.sparqlService(fusekiEndpoint, query);

		try {
			ResultSet response = qexec.execSelect();			
			while (response.hasNext()) {
				QuerySolution soln = response.nextSolution();
				RDFNode id = soln.get("?id");

				idValue = id.toString().substring(id.toString().lastIndexOf("#") + 1);
				ids.add(idValue);

			}

		} finally {
			qexec.close();
		}
		

		for (int i = 0; i < ids.size(); i++) {
			StringBuffer queryStr2 = new StringBuffer();
			QueryExecution qexec2;

			queryStr2.append("PREFIX dbi" + ": <" + defaultIndeedNameSpace + "> ");
			queryStr2.append("PREFIX rdfs" + ": <" + "http://www.w3.org/2000/01/rdf-schema#" + "> ");
			queryStr2.append("PREFIX rdf" + ": <" + "http://www.w3.org/1999/02/22-rdf-syntax-ns#" + "> ");
			queryStr2.append("PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>");
			queryStr2.append("SELECT ?src ?dst ?airlines ?duration ?cost ?journeyDt   WHERE { OPTIONAL {dbi:"+ids.get(i)+" dbi:has_d_iataCode ?src. dbi:"+ids.get(i)+" dbi:has_a_iataCode ?dst. dbi:"+ids.get(i)+" dbi:has_grandTotal ?cost. dbi:"+ids.get(i)+" dbi:has_flight_name ?airlines. dbi:"+ids.get(i)+" dbi:has_duration ?duration. dbi:"+ids.get(i)+" dbi:has_d_at ?journeyDt. }}");

			Query query2 = QueryFactory.create(queryStr2.toString());

			qexec2 = QueryExecutionFactory.sparqlService(fusekiEndpoint, query2);

			ResultSet response = qexec2.execSelect();
			
			while (response.hasNext()) {
				QuerySolution soln = response.nextSolution();
				RDFNode source = soln.get("?src");
				RDFNode destination = soln.get("?dst");
				RDFNode price = soln.get("?cost");
				RDFNode durationTime = soln.get("?duration");				
				RDFNode airlinesName = soln.get("?airlines");
				RDFNode dateOFJourney = soln.get("journeyDt");
				
				

				String from = source.toString().substring(source.toString().lastIndexOf("#") + 1);
				String to = destination.toString().substring(source.toString().lastIndexOf("#") + 1);
				String cost = price.toString().substring(price.toString().lastIndexOf("#") + 1);
				String name = airlinesName.toString().substring(price.toString().lastIndexOf("#") + 1);				
				String time = durationTime.toString().substring(price.toString().lastIndexOf("#") + 1);
				String travelDt = dateOFJourney.toString().substring(price.toString().lastIndexOf("#") + 1);
				String dateofTravel = travelDt.substring(0, travelDt.indexOf("T"));
				
				deals.add(new DealsFilter(from, dateofTravel, "noOfTickets", to, cost, name, time, "Success"));
						

			}
			qexec2.close();

		}

		return deals;
	}

}
