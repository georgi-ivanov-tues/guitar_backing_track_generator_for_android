import java.applet.*;
import javax.swing.*;
import java.io.*;
import java.net.*;

public class Sound extends JApplet
{
    public class Sound1 // Holds one audio file
    {
  private AudioClip song; // Sound player
  private URL songPath; // Sound path
  Sound1(String filename)
  {
     try
     {
    songPath = new URL(getCodeBase(),filename); // Get the Sound URL
    song = Applet.newAudioClip(songPath); // Load the Sound
     }
     catch(Exception e){}} // Satisfy the catch
  public void playSound()
  {
     song.loop(); // Play
  }
  public void stopSound()
  {
     song.stop(); // Stop
  }
  public void playSoundOnce()
  {
     song.play(); // Play only once
  }
    }
    public void init()
    {
  Sound1 testsong = new Sound1("adios.wav");
  testsong.playSound();
    }
}
