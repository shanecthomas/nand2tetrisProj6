import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

public class Main {
  public static void main (String[] args) {
    System.out.println("Enter a file to convert: ");
    Scanner keyboard = new Scanner(System.in);
    String input = keyboard.nextLine();
    File srcFile = new File(input);

    if (!srcFile.exists()) {
      System.out.println("File doesn't exist. Exiting program.");
      System.exit(0);
    }

    String fileFullName = srcFile.getName();
    int index = fileFullName.lastIndexOf(".");
    String fileName = fileFullName.substring(0, index);
    String outFileName = fileName + ".hack";
    File destFile = new File(outFileName);

    try {
      if (destFile.exists()) {
        destFile.delete();
      }
      destFile.createNewFile();
      
      Assembler assembler = new Assembler(srcFile, destFile);
      assembler.parse();
      System.out.println(destFile + " has been created!");
    }

    catch (IOException e) {
      System.out.println("An unknown error occurred.");
      System.exit(0);
    }
  }
}