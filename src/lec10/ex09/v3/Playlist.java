package lec10.ex09.v3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Playlist {

	private Song[] playlist_songs;

	private Playlist(Song[] songs) {
		playlist_songs = songs;
	}

	public static Playlist readFromFile(String filename)
			throws PlaylistFormatException, FileNotFoundException {
		// Catch filename is null error early rather than letting it
		// happen in File constructor below.
		//
		// if (filename == null) {
		//	throw new IllegalArgumentException("Playlist filename is null");
		// }
		
		ArrayList<Song> songs = new ArrayList<Song>();

		Scanner scanner = null;
		scanner = new Scanner(new File(filename));
		
		try {
			while (scanner.hasNext()) {
				String song_name = scanner.nextLine();
				String artist_name = scanner.nextLine();
				double length = scanner.nextDouble();
				int rating = scanner.nextInt();
				
				// Need this next call to skip rest of line
				scanner.nextLine();

				Song next_song = new Song(song_name, artist_name, length,
						rating);
				songs.add(next_song);
				
			}
		} catch (NoSuchElementException e) {

			throw new PlaylistFormatException("Error in playlist after "
					+ songs.size() + " songs");

		} catch (NegativeSongLengthException e) {

			throw new PlaylistFormatException("Song " + (songs.size()+1) + 
					" in playlist has illegal length: " + e.getIllegalLength());
			
		} catch (RatingOutOfRangeException e) {
			
			throw new PlaylistFormatException("Song " + (songs.size()+1) + 
					" in playlist has out of range rating: " + e.getOutOfRangeRating());
		}	
		finally {
			scanner.close();
		}

		return new Playlist(songs.toArray(new Song[0]));
	}

	public void print() {
		for (Song s : playlist_songs) {
			System.out.println(s.toString());
		}
	}
}
