import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.ArrayList;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors
import java.util.HashMap;

/**
 * This program creates a list of all people you have recieved
 * an email from, given an inbox in the .mbox format.
 * Any domain that contains a term from excluded.txt will not be
 * in the final result.
 */
public class ExportSenders {
    
  public static void main(String[] args) {
    int sendersFound = 0;
    HashMap<String, Integer> senders = new HashMap<String, Integer>();
    ArrayList<String> excludeList = new ArrayList<String>();
    String prevSender = "";
    
    // get a list of all excluded keywords from exclude.txt
    try {
      File excludedFile = new File("exclude.txt");
      Scanner excludedScanner = new Scanner(excludedFile);
      while(excludedScanner.hasNextLine()) {
          excludeList.add(excludedScanner.nextLine().toLowerCase());
      }
      excludedScanner.close();
    } catch (FileNotFoundException e) {
        System.out.println("An error occured.");
        e.printStackTrace();
    }
    
    //Create list of all email senders
    try {
      File sendersFile = new File("mail.mbox");
      Scanner sendersScanner = new Scanner(sendersFile);
      while (sendersScanner.hasNextLine()) {
        String data = sendersScanner.nextLine().toLowerCase();
        if(data.length() >= 5 && data.substring(0,5).equals("from:") || prevSender.equals("next")) {
            if(data.length() <= 5) {
               continue;
            }
            String temp = formatName(data.substring(6), excludeList);
            if(temp == "next") {
                temp = formatName(sendersScanner.nextLine().toLowerCase(), excludeList);
            }
            if(temp == "excluded") continue;
            if(senders.get(temp) != null) {
                senders.put(temp, senders.get(temp) + 1);
            } else {
                senders.put(temp, 1);
                System.out.println("Unique senders found:" + ++sendersFound);
            }
            prevSender = temp;
        }
      }
      sendersScanner.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
    
    // Export final list
    try {
      FileWriter myWriter = new FileWriter("export.txt");
      System.out.println("All senders found! Exporting now...");
      for(String s : senders.keySet()) {
          myWriter.write(s + ", seen " +  senders.get(s) + " time(s)." + "\n");
      }
      myWriter.close();
      System.out.println("Successfully exported all senders.");
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
  
  //formats the item, removing everything but the email
  private static String formatName(String name, ArrayList<String> excluded) {
      // check for excluded words
      for(String s : excluded) 
          if(name.indexOf(s) != -1) return "excluded";
      // check if this line contains an email address
      if(name.indexOf("@") == -1 || name.indexOf(">") == -1) return "next";
      // reduce string to just domain in email address
      String shortName = name.substring(name.indexOf("@") + 1, name.indexOf(">"));
      // trim to just first level of domain with regex (hello.test.com --> test.com)
      while(shortName.matches(".+\\..+\\..+")) {
          shortName = shortName.substring(shortName.indexOf(".") + 1);
      }
      return shortName;
    }
}
