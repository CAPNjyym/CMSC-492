import java.applet.Applet;
import java.applet.AudioClip;
import java.util.HashMap;
import java.util.Iterator;
import java.net.URL;
import java.net.MalformedURLException;

/* Class to implement sound, used like a library
 * To play a sound, call playSound(String keypath) and pass the path to the sound file to be played
 * To loop a sound, call loopSound(String keypath) and pass the path to the sound file to be played
 * To stop a sound, call stopSound(String keypath) and pass the path to the sound file to be played
 * *NOTE: if you pass a sound file that has not been played or looped into stopSound, nothing will happen
 */
public class Sound
{
	HashMap<String, AudioClip> sounds;
	URL root;
	
	public Sound()
	{
		try
			{root = new URL("file:/" + System.getProperty("user.dir") + "/");}
		catch (MalformedURLException MUE)
		{
			root = null;
			System.out.println("Error finding root!  Sounds will not play!");
		}
		
		sounds = new HashMap<String, AudioClip>();
	}
	
	public int size()
	{
		return sounds.size();
	}
	
	public void loadSound(String path)
	{
		try
		{
			URL url = new URL(root, path);
			url.getContent(); // tests to see if object exists
			sounds.put(path, Applet.newAudioClip(url));
		}
		catch (Exception E)
		{
			if (root != null)
				System.out.println(path + " not found!");
		}
		
	}
	
	public void playSound(String keyPath)
	{
		if (!sounds.containsKey(keyPath))
			loadSound(keyPath);
		sounds.get(keyPath).play();
	}
	
	public void loopSound(String keyPath)
	{
		if (!sounds.containsKey(keyPath))
			loadSound(keyPath);
		sounds.get(keyPath).loop();
	}
	
	public void stopSound(String keyPath)
	{
		if (sounds.containsKey(keyPath))
			sounds.get(keyPath).stop();
	}
	
	public String toString()
	{
		String list = "";
		Iterator<String> iter = sounds.keySet().iterator();
		
		if (iter.hasNext())
			list += iter.next();
		while(iter.hasNext())
			list += ", " + iter.next();
		
		return list;
	}
}
