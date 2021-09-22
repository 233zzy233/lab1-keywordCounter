import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class LAB2_1 {

  public static void main(String[] args) throws FileNotFoundException {
	
	Scanner scanner = new Scanner(System.in);
	System.out.println("Please enter the path to the file.");
    String fileName = scanner.nextLine();
    System.out.println("Please enter the level.");
    int level=scanner.nextInt();
    long start=System.currentTimeMillis();
    Scanner sc = new Scanner(new File(fileName));

    
    //Save the keyword
    String[] keyword = {"auto", "break", "case", "char", "const", "continue", "default", "do",
            "double", "else", "enum", "extern", "float", "for", "goto", "if", "int",
            "long", "register", "return", "short", "signed", "sizeof", "static", "struct",
            "switch", "typedef", "union", "unsigned", "void", "volatile", "while"
    };
    
    int total = 0;  //Record the total number of keywords
    int quotesFlag = 0;

    String s; //Stores fragments in a row
    String line; //Store a line of strings

    while (sc.hasNextLine()) {
      line = sc.nextLine();
  
      
      //Determine the position of the double-quoted string and delete it
      while (line.indexOf('"')!=-1&&quotesFlag==0) {
    	  int p=line.indexOf('"');
    	  if (line.indexOf('"',p+1)!=-1) {
    	  line = line.substring(0, p)+" "+line.substring(line.indexOf('"',p)+1);
    	  }
    	  else {
    	  quotesFlag=1;
    	  line = line.substring(0,p);
    	  }
      }
      if(quotesFlag==1&&line.indexOf('"')!=-1) {
    	  line =line.substring(line.indexOf('"')+1);
    	  quotesFlag=0;
      }
      else if(quotesFlag==1&&line.indexOf('"')==-1) {
    	  line="";
      }
      
      
      
      
      //Delete all extraneous symbols and keep the keywords and letters
      for (int j = 0; j < line.length(); j++) {
        if (!(line.charAt(j) >= 'a' &&line.charAt(j) <= 'z' ||line.charAt(j) >= 'A' &&line.charAt(j) <= 'Z'||line.charAt(j) == '_')) {
          line = line.substring(0, j) + " " + line.substring(j + 1);
        }
      }
      
      
      //Determine the number of switches and cases
      Scanner sca = new Scanner(line);
      while (sca.hasNext()) {
        s = sca.next();
        
      //Determine the number of keyword
        for (int i = 0; i < 32; i++) {
          if (s.equals(keyword[i])) {
            total++;
          }
        }
      }
    }
    
    
    //Output all results
    System.out.println("total num: " + total);
  }
}