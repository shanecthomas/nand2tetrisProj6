import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

public class Assembler {
  private File assemblyCode;
  private BufferedWriter machineCode;
  private Code code = new Code();
  private SymbolTable symbolTable = new SymbolTable();

  //constructor - intialize assembler
  public Assembler (File src, File dest) throws IOException {
    assemblyCode = src;
    machineCode = new BufferedWriter(new FileWriter(dest));
  }

  public void parse() throws IOException{
    parseSymbol();
    parseWrite();
  }

  //first run of parser - grabs symbols & writes them to symbolTable
  public void parseSymbol() throws IOException {
    Parser parser = new Parser(assemblyCode);
    while (parser.hasMoreLines()) {
      parser.advance();
      
      InstructionType instructionType = parser.instructionType();
      
      if (instructionType.equals(InstructionType.L_INSTRUCTION)) {
        String symbol = parser.symbolL();
        int address = symbolTable.getProgramAddress();
        symbolTable.addEntry(symbol, address);
      }
      else
        symbolTable.incrementProgram();
    }
  }

  //second run of parser - writes binary code
  public void parseWrite() throws IOException {
    Parser parser = new Parser(assemblyCode);
    while (parser.hasMoreLines()) {
      parser.advance();

      InstructionType instructionType = parser.instructionType();
      String instruction = null;

      if (instructionType.equals(InstructionType.A_INSTRUCTION)) {
        String symbol = parser.symbolA();
        char c = symbol.charAt(0);
        String address = null;

        if (!Character.isDigit(c)) {
          
          if (!symbolTable.contains(symbol)) {
            int intAddress = symbolTable.getDataAddress();
            symbolTable.addEntry(symbol, intAddress);
            symbolTable.incrementData();
          }
          
          address = Integer.toString(symbolTable.getAddress(symbol));
        }
        else
          address = symbol;

        instruction = "0" + code.convertBinary(address);
      }

      else if (instructionType.equals(InstructionType.C_INSTRUCTION)) {
        StringWriter string = new StringWriter();
        string.append("111");
        String dest = parser.dest();
        string.append(code.dest(dest));
        String comp = parser.comp();
        string.append(code.comp(comp));
        String jump = parser.jump();
        string.append(code.jump(jump));
        instruction = string.toString();
      }

      if (!instructionType.equals(InstructionType.L_INSTRUCTION)) {
        machineCode.write(instruction);
        machineCode.newLine();
      }
    }
    machineCode.flush();
    machineCode.close();
  }
  
}