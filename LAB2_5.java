import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class LAB2_5 {

  public static void main(String[] args) throws FileNotFoundException {
	
	Scanner scanner = new Scanner(System.in);
	System.out.println("Please enter the path to the file.");
    String fileName = scanner.nextLine();
    System.out.println("Please enter the level.");
    int level=scanner.nextInt();
    long start=System.currentTimeMillis();
    Scanner sc = new Scanner(new File(fileName));
    //C:\Users\86158\Desktop\gjz.cpp
    
    //Save the keyword
    String[] keyword = {"auto", "break", "case", "char", "const", "continue", "default", "do",
            "double", "else", "enum", "extern", "float", "for", "goto", "if", "int",
            "long", "register", "return", "short", "signed", "sizeof", "static", "struct",
            "switch", "typedef", "union", "unsigned", "void", "volatile", "while"
    };
    
    int total = 0;  //Record the total number of keywords
    int quotes = 0; //Record the position of quotes
    int count = 0;  //Count the number of cases in each switch  
    int switchNum = 0; //Record the total number of switch
    int quotesFlag = 0;
    int flag = 0;   //Used to determine the number of times the switch has been encountered
    int annoFlag=0; //Used to determine if there are multi-line comments that need to be deleted
    int if_elseNum = 0; //Record the total number of if else
    int if_elseif_elseNum = 0; //Record the total number of if elseif else

    String s; //Stores fragments in a row
    String line; //Store a line of strings
    String caseNum = ""; //Records the number of cases in each switch structure

    Stack<Integer> stack = new Stack<Integer>();
    
    while (sc.hasNextLine()) {
      line = sc.nextLine();
  
      //Determine the position of the comment and delete the comment
      if (line.indexOf("//")!=-1) {
    	  line = line.substring(0, line.indexOf("//"));
      }
      if (line.indexOf("#")!=-1) {
    	  line = line.substring(0, line.indexOf("#"));
      }
      
      if (line.indexOf("/*")!=-1) {
    	  if (line.indexOf("*/")!=-1) {
    	  line = line.substring(0, line.indexOf("/*"))+" "+line.substring(line.indexOf("*/")+1);
    	  }
    	  else {
    	  annoFlag=1;
    	  line = line.substring(0, line.indexOf("/*"));
    	  }
      }
      if(annoFlag==1&&line.indexOf("*/")!=-1) {
    	  line =line.substring(line.indexOf("*/")+1);
    	  annoFlag=0;
      }
      else if(annoFlag==1&&line.indexOf("*/")==-1) {
    	  line="";
      }
      
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
      
      //Count the number of if else and if elseif else structures using the stack
      if (line.contains("else if")) {
        stack.push(2);
      } else if (line.contains("if")) {
        stack.push(1);
      } else if (line.contains("else")) { 
        if (stack.peek() == 1) {
          if_elseNum++;
        } else {
          if_elseif_elseNum++;
        }
        while (!stack.empty() && stack.pop() != 1);
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
        if (s.equals("switch")) {
          switchNum++;
          if (flag != 0) {
            caseNum = caseNum + count + " ";
            count = 0;
          }
          flag++;
        }
        if (s.equals("case")) {
          count++;
        }
        
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
    if (level>=2) {
    System.out.println("switch num: " + switchNum);
    if (flag == 1) {
      System.out.println("case num: " + count);
    } else {
      System.out.println("case num: " + caseNum + count);
    }
    }
    if(level>=3) {
    System.out.println("if-else num: " + if_elseNum);
    }
    if(level>=4) {
    System.out.println("if-elseif-else num: " + if_elseif_elseNum);
    }
    long end=System.currentTimeMillis();
    System.out.println("program run time��"+(end-start)+"ms");
  }
}