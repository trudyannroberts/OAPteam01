package film;

/**
 * @author Stian
 * @deprecated not enough time for us to implement it properly.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WatchlistManager {
	
	//Hashmap to connect a profile to a list of films watched
	private HashMap<String, List<String>> profileWatchlist;
	
	public WatchlistManager() {
		profileWatchlist = new HashMap<>();
	}
	
	// add a film to a profile's watchlist.
	public void addWatchedMovie(String profileId, String movieTitle) {
		profileWatchlist.putIfAbsent(profileId, new ArrayList<>());
		profileWatchlist.get(profileId).add(movieTitle);
	}

	//Check if a film has been watched by a specific profile
	public boolean hasWatchedMovie(String profileId, String movieTitle) {
		return profileWatchlist.containsKey(profileId) && profileWatchlist.get(profileId).contains(movieTitle);
	}
	
	// return the entire list of movies watched by a profile'
	public List<String> getWatchedMovies(String profileId) {
		return profileWatchlist.getOrDefault(profileId, new ArrayList<>());
	}

}
