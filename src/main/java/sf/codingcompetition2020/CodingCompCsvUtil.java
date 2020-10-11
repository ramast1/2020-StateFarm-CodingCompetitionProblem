package sf.codingcompetition2020;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import sf.codingcompetition2020.structures.Agent;
import sf.codingcompetition2020.structures.Claim;
import sf.codingcompetition2020.structures.Customer;
import sf.codingcompetition2020.structures.Vendor;

public class CodingCompCsvUtil {
	
	/* #1 
	 * readCsvFile() -- Read in a CSV File and return a list of entries in that file.
	 * @param filePath -- Path to file being read in.
	 * @param classType -- Class of entries being read in.
	 * @return -- List of entries being returned.
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> readCsvFile(String filePath, Class<T> classType) {
		//this is a test
		try {
			File file = new File(filePath);
        	FileReader fr = new FileReader(file);
        	BufferedReader br = new BufferedReader(fr);
        	
        	if(classType==Agent.class) {
        		List<Agent> agents = new ArrayList<Agent>();
        		String vars = br.readLine();
        		String line="";
        		String[] arr = new String[5];
        		
        		while((line = br.readLine())!=null) {
        			arr=line.split(",");
        			Agent p = new Agent();
        			p.setAgentId(Integer.parseInt(arr[0]));
        			p.setArea(arr[1]);
        			p.setLanguage(arr[2]);
        			p.setFirstName(arr[3]);
        			p.setLastName(arr[4]);
        			agents.add(p);
        		}
        		return (List<T>)agents;
        	}
        	else if(classType==Claim.class) {
        		List<Claim> claims = new ArrayList<Claim>();
        		String vars = br.readLine();
        		String line="";
        		String[] arr = new String[4];
        		
        		while((line = br.readLine())!=null) {
        			arr=line.split(",");
        			Claim c = new Claim();
        			c.setClaimId(Integer.parseInt(arr[0]));
        			c.setCustomerId(Integer.parseInt(arr[1]));
        			c.setClosed(Boolean.parseBoolean(arr[2]));
        			c.setMonthsOpen(Integer.parseInt(arr[3]));
        			claims.add(c);
        		}
        		return (List<T>)claims;
        	}
        	else if(classType==Customer.class) {
        		List<Customer> customers = new ArrayList<Customer>();
        		String vars = br.readLine();
        		String line="";
        		String[] arr = new String[15];
        		
        		while((line = br.readLine())!=null) {
        			arr=line.split(",");
        			Customer c = new Customer();
        			c.setCustomerId(Integer.parseInt(arr[0]));
        			c.setFirstName(arr[1]);
        			c.setLastName(arr[2]);
        			c.setAge(Integer.parseInt(arr[3]));
        			c.setArea(arr[4]);
        			c.setAgentId(Integer.parseInt(arr[5]));
        			c.setAgentRating((short) Integer.parseInt(arr[6]));
        			c.setPrimaryLanguage(arr[7]);
        			        			
        			//String dependent = arr[8];
        			
        			//c.setDependents();
        			
        			
        			//c.setHomePolicy(Boolean.parseBoolean(arr[9]));
        			//c.setAutoPolicy(Boolean.parseBoolean(arr[10]));
        			//c.setRentersPolicy(Boolean.parseBoolean(arr[11]));
        			//c.setTotalMonthlyPremium(arr[12]);
        			//c.setYearsOfService((short) Integer.parseInt(arr[13]));
        			//c.setVehiclesInsured(Integer.parseInt(arr[14]));
        			customers.add(c);
        		}
        		return (List<T>)customers;
        	}
        	else {
        		List<Vendor> vendors = new ArrayList<Vendor>();
        		String vars = br.readLine();
        		String line="";
        		String[] arr = new String[4];
        		
        		while((line = br.readLine())!=null) {
        			arr=line.split(",");
        			Vendor v = new Vendor();
        			v.setVendorId(Integer.parseInt(arr[0]));
        			v.setArea(arr[1]);
        			v.setVendorRating(Integer.parseInt(arr[2]));
        			v.setInScope(Boolean.parseBoolean(arr[3]));
        			vendors.add(v);
        		}
        		return (List<T>)vendors;
        	}     	
        	       	
		}
		catch(IOException e) {
            System.out.println(e);
            
         }
		return null;
	}

	
	/* #2
	 * getAgentCountInArea() -- Return the number of agents in a given area.
	 * @param filePath -- Path to file being read in.
	 * @param area -- The area from which the agents should be counted.
	 * @return -- The number of agents in a given area
	 */
	public int getAgentCountInArea(String filePath,String area) {
		List<Agent> agents = readCsvFile(filePath,Agent.class);
        int numInArea = 0;
        for(Agent a : agents) {
             if(a.getArea().equals(area)) numInArea++;
        }
        return numInArea;
	}

	
	/* #3
	 * getAgentsInAreaThatSpeakLanguage() -- Return a list of agents from a given area, that speak a certain language.
	 * @param filePath -- Path to file being read in.
	 * @param area -- The area from which the agents should be counted.
	 * @param language -- The language spoken by the agent(s).
	 * @return -- The number of agents in a given area
	 */
	public List<Agent> getAgentsInAreaThatSpeakLanguage(String filePath, String area, String language) {
		List<Agent> agents = readCsvFile(filePath,Agent.class);
        List<Agent> agentsInParam = new ArrayList<Agent>();
        for(Agent a : agents) {
            if(a.getArea().equals(area) & a.getLanguage().equals(language)) agentsInParam.add(a);
        }
        return agentsInParam;
	}
	
	
	/* #4
	 * countCustomersFromAreaThatUseAgent() -- Return the number of individuals from an area that use a certain agent.
	 * @param filePath -- Path to file being read in.
	 * @param customerArea -- The area from which the customers should be counted.
	 * @param agentFirstName -- First name of agent.
	 * @param agentLastName -- Last name of agent.
	 * @return -- The number of customers that use a certain agent in a given area.
	 */
	public short countCustomersFromAreaThatUseAgent(Map<String,String> csvFilePaths, String customerArea, String agentFirstName, String agentLastName) {
		List<Agent> agents = readCsvFile(csvFilePaths.get("agentList"),Agent.class);
        List<Customer> customers = readCsvFile(csvFilePaths.get("customerList"),Customer.class);
        int agentId = -1;
        short numCustomers = 0;
        for(Agent a : agents) {
            if(a.getFirstName().equals(agentFirstName) & a.getLastName().equals(agentLastName)) {
                 agentId = a.getAgentId();
            }
        }
        for(Customer c : customers) {
            if(c.getArea().equals(customerArea) & c.getAgentId()==agentId) numCustomers++;
        }
        return numCustomers;
	}

	
	/* #5
	 * getCustomersRetainedForYearsByPlcyCostAsc() -- Return a list of customers retained for a given number of years, in ascending order of their policy cost.
	 * @param filePath -- Path to file being read in.
	 * @param yearsOfServeice -- Number of years the person has been a customer.
	 * @return -- List of customers retained for a given number of years, in ascending order of policy cost.
	 */
	public List<Customer> getCustomersRetainedForYearsByPlcyCostAsc(String customerFilePath, short yearsOfService) {
		return null;
	}

	
	/* #6
	 * getLeadsForInsurance() -- Return a list of individuals who’ve made an inquiry for a policy but have not signed up.
	 * *HINT* -- Look for customers that currently have no policies with the insurance company.
	 * @param filePath -- Path to file being read in.
	 * @return -- List of customers who’ve made an inquiry for a policy but have not signed up.
	 */
	public List<Customer> getLeadsForInsurance(String filePath) {
		return null;
	}


	/* #7
	 * getVendorsWithGivenRatingThatAreInScope() -- Return a list of vendors within an area and include options to narrow it down by: 
			a.	Vendor rating
			b.	Whether that vendor is in scope of the insurance (if inScope == false, return all vendors in OR out of scope, if inScope == true, return ONLY vendors in scope)
	 * @param filePath -- Path to file being read in.
	 * @param area -- Area of the vendor.
	 * @param inScope -- Whether or not the vendor is in scope of the insurance.
	 * @param vendorRating -- The rating of the vendor.
	 * @return -- List of vendors within a given area, filtered by scope and vendor rating.
	 */
	public List<Vendor> getVendorsWithGivenRatingThatAreInScope(String filePath, String area, boolean inScope, int vendorRating) {
		return null;
	}


	/* #8
	 * getUndisclosedDrivers() -- Return a list of customers between the age of 40 and 50 years (inclusive), who have:
			a.	More than X cars
			b.	less than or equal to X number of dependents.
	 * @param filePath -- Path to file being read in.
	 * @param vehiclesInsured -- The number of vehicles insured.
	 * @param dependents -- The number of dependents on the insurance policy.
	 * @return -- List of customers filtered by age, number of vehicles insured and the number of dependents.
	 */
	public List<Customer> getUndisclosedDrivers(String filePath, int vehiclesInsured, int dependents) {
		return null;
	}	


	/* #9
	 * getAgentIdGivenRank() -- Return the agent with the given rank based on average customer satisfaction rating. 
	 * *HINT* -- Rating is calculated by taking all the agent rating by customers (1-5 scale) and dividing by the total number 
	 * of reviews for the agent.
	 * @param filePath -- Path to file being read in.
	 * @param agentRank -- The rank of the agent being requested.
	 * @return -- Agent ID of agent with the given rank.
	 */
	public int getAgentIdGivenRank(String filePath, int agentRank) {
			return 0;
	}	

	
	/* #10
	 * getCustomersWithClaims() -- Return a list of customers who’ve filed a claim within the last <numberOfMonths> (inclusive). 
	 * @param filePath -- Path to file being read in.
	 * @param monthsOpen -- Number of months a policy has been open.
	 * @return -- List of customers who’ve filed a claim within the last <numberOfMonths>.
	 */
	public List<Customer> getCustomersWithClaims(Map<String,String> csvFilePaths, short monthsOpen) {
		return null;
	}	

}
