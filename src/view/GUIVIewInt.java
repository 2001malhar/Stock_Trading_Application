package view;

import java.util.List;

/**
 * Interface representing methods for displaying output in a graphical user interface (GUI).
 */
public interface GUIVIewInt {

  /**
   * Displays a single output message in the GUI.
   *
   * @param output The message to be displayed.
   */
  void displayOutput(String output);

  /**
   * Displays a list of output messages in the GUI.
   *
   * @param messages The list of messages to be displayed.
   */
  void displayOutputList(List<String> messages);
}
