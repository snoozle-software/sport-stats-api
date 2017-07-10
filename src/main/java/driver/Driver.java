package driver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 
 * @author greg
 *
 */
public class Driver {
	
	private static final String apiBaseURL = "http://sports.snoozle.net/api";
	
	private final static String USER_AGENT = "Snoozle";
	
	/**
	 * 
	 * @param argv
	 * @throws IOException 
	 */
	public static void main(String[] argv) throws IOException
	{
		// MLB APIs
		// Get mlb data league=mlb
		// REST GET Get daily game info including gameid statType=dailygames gamedate=yyyy-MM-dd
		String urlMLBGamedateParameters = "league=mlb&statType=dailygames&gamedate=2017-07-07";
		String mlbMatchupsJson = getJsonAPI(apiBaseURL,urlMLBGamedateParameters);
		System.out.println("Match ups:" + mlbMatchupsJson);
		
		// REST GET Get player stats 
		// statType=montecarlostats
		// need one of the following:
		// gameid={Get from dailygames}
		// teamname AND gamedate
		// (hometeam OR awayteam) AND gamedate
		// acceptible team names
		// {Angels, Astros,	Athletics, Blue Jays, Braves, Brewers, Cardinals, Cubs,	Diamondbacks
		//  Dodgers, Giants, Indians, Mariners, Marlins, Mets, Nationals, Orioles, Padres, Phillies
		//  Pirates, Rangers, Rays, Reds, Red Sox, Rockies, Royals, Tigers, Twins, White Sox, Yankees }
		String urlMcStatsParameters = "league=mlb&statType=montecarlostats&gameid=370707114";
		String mlbStatsJson = getJsonAPI(apiBaseURL,urlMcStatsParameters);
		System.out.println("Game Stats:"+mlbStatsJson);
		
		// NFL APIs
		// Get nfl data league=nfl
		// REST GET Get matchup statType=matchup startDate=yyyy-MM-dd endDate=yyyy-MM-dd
		//                            fileType={json|csvFile|xmlFile}
		String urlNFLMatchupParameters = "league=nfl&fileType=json&statType=matchup&startDate=2016-08-01&endDate=2016-09-13";
		String nflMatchupJson = getJsonAPI(apiBaseURL,urlNFLMatchupParameters);
		System.out.println("NFL Matchups:"+nflMatchupJson);
		
		// REST GET Get NFL game odds statType=latestodds startDate=yyyy-MM-dd endDate=yyyy-MM-dd
		//                            fileType={json|csvFile|xmlFile}		
		String urlNFLOddsParameters = "league=nfl&fileType=json&statType=latestodds&startDate=2016-08-01&endDate=2016-09-13";
		String nflOddsJson = getJsonAPI(apiBaseURL,urlNFLOddsParameters);
		System.out.println("NFL Odds:"+nflOddsJson);
		
		// CFB APIs
		// Get college football data league=cfb
		// REST GET Get matchups statType=matchup startDate=yyyy-MM-dd endDate=yyyy-MM-dd
		//                            fileType={json|csvFile|xmlFile}
		String urlCFBMatchupParameters = "league=cfb&fileType=json&statType=matchup&startDate=2016-09-07&endDate=2016-09-12";
		String cfbMatchupJson = getJsonAPI(apiBaseURL,urlCFBMatchupParameters);
		System.out.println("CFB Matchups:"+cfbMatchupJson);
		
		// REST GET Get matchups statType=matchup startDate=yyyy-MM-dd endDate=yyyy-MM-dd
		//                            fileType={json|csvFile|xmlFile}
		String urlCFBOddsParameters = "league=cfb&fileType=json&statType=latestodds&startDate=2016-09-07&endDate=2016-09-12";
		String cfbOddsJson = getJsonAPI(apiBaseURL,urlCFBOddsParameters);
		System.out.println("CFB Odds:"+cfbOddsJson);
		
	}

	/*
	 * method - getJsonAPI 
	 * input: url of game string, and parameters
	 * output: json string of from game
	 *  
	 * Gets data from the API
	 */
	
	public static String getJsonAPI(String urlString, String postParameters) throws IOException
	{
		String returnString = null;
		
		URL url = new URL(urlString);
		
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(postParameters);
		wr.flush();
		wr.close();
		
		int responseCode = con.getResponseCode();
		if(responseCode == 200)
		{
		
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
	
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			returnString = response.toString();
		} // if(responseCode == 200)
		
		con.disconnect();
		
		return returnString;
	} // public static String getJsonAPI(String urlString, String postParameters) throws IOException


}
