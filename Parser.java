import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Parser {
  private BufferedReader file;
  private String currentLine = null;
  private String nextLine;

  //constructor - opens input file / stream & gets ready to parse it 
  public Parser (File f) throws IOException {
    file = new BufferedReader(new FileReader(f));
    nextLine = this.getNextLine();
  }

  //grabs next line, skips white space/comments
  //assumes hasMoreLines == true
  public String getNextLine() throws IOException {
    String line;

    do {
      line = file.readLine();
      if (line == null)
        return null;
    }
      while (line.trim().isEmpty() || line.trim().startsWith("//"));

    int comment = line.indexOf("//");
    if (comment != -1)
      line = line.substring(0, comment - 1);
    
    return line;
  }

  //is file empty?
  public boolean hasMoreLines() {
    return (nextLine != null);
  }

  //read next line
  public void advance() throws IOException {
    currentLine = nextLine;
    nextLine = this.getNextLine();
  }

  //Returns instruction type
  //@xxx == A_INSTRUCTION
  //(xxx) == L_INSTRUCTION
  //dest=comp;jump == C_INSTRUCTION
  public InstructionType instructionType() {
    String line = currentLine.trim();

    if (line.startsWith("(") && line.endsWith(")"))
      return InstructionType.L_INSTRUCTION;
    else if (line.startsWith("@"))
      return InstructionType.A_INSTRUCTION;
    else
      return InstructionType.C_INSTRUCTION;
  }

  //returns symbol or decimal xxx (A_INSTRUCTION)
  public String symbolA() {
    String line = currentLine.trim();
    return line.substring(1);
  }

  //returns symbol xxx (L_INSTRUCTION)
  public String symbolL() {
    String line = currentLine.trim();
    return line.substring(1, currentLine.length() - 1);
  }

  //returns dest - 8 possibilites (C_INSTRUCTION)
  public String dest() {
    String line = currentLine.trim();
    int equals = line.indexOf("=");
    if (equals == -1)
      return null;
    else
      return line.substring(0, equals);
  }

  //returns comp - 28 possibilities (C_INSTRUCTION)
  public String comp() {
    String line = currentLine.trim();
    int equals = line.indexOf("=");
    int jump = line.indexOf(";");
    if (equals == -1 && jump == -1)
      return line;
    else if (equals != -1 && jump == -1)
      return line.substring(equals + 1);
    else if (equals == -1 && jump != -1)
      return line.substring(0, jump);
    else
      return line.substring(equals + 1, jump);
  }

  //returns jump - 8 possibilities (C_INSTRUCTION)
  public String jump() {
    String line = currentLine.trim();
    int jump = line.indexOf(";");
    if (jump == -1)
      return null;
    else
      return line.substring(jump + 1);
  }
}
