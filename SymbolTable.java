import java.util.Hashtable;

public class SymbolTable {
  private Hashtable<String,Integer> symbolMap;
  private int dataAddress = 16;
  private int programAddress = 0;

  //constructor - initialize table with symbols
  public SymbolTable() {
    symbolMap = new Hashtable<String, Integer>();
    symbolMap.put("R0", Integer.valueOf(0));
    symbolMap.put("R1", Integer.valueOf(1));
    symbolMap.put("R2", Integer.valueOf(2));
    symbolMap.put("R3", Integer.valueOf(3));
    symbolMap.put("R4", Integer.valueOf(4));
    symbolMap.put("R5", Integer.valueOf(5));
    symbolMap.put("R6", Integer.valueOf(6));
    symbolMap.put("R7", Integer.valueOf(7));
    symbolMap.put("R8", Integer.valueOf(8));
    symbolMap.put("R9", Integer.valueOf(9));
    symbolMap.put("R10", Integer.valueOf(10));
    symbolMap.put("R11", Integer.valueOf(11));
    symbolMap.put("R12", Integer.valueOf(12));
    symbolMap.put("R13", Integer.valueOf(13));
    symbolMap.put("R14", Integer.valueOf(14));
    symbolMap.put("R15", Integer.valueOf(15));
    symbolMap.put("SP", Integer.valueOf(0));
    symbolMap.put("LCL", Integer.valueOf(1));
    symbolMap.put("ARG", Integer.valueOf(2));
    symbolMap.put("THIS", Integer.valueOf(3));
    symbolMap.put("THAT", Integer.valueOf(4));
    symbolMap.put("SCREEN", Integer.valueOf(16384));
    symbolMap.put("KBD", Integer.valueOf(24576));
  }

  //adds entry to table
  public void addEntry (String s, int i) {
    symbolMap.put(s,Integer.valueOf(i));
  }

  //does table contain entry?
  public boolean contains (String s) {
    return this.symbolMap.containsKey(s);
  }

  //gets value from key in table
  public int getAddress (String s) {
    return this.symbolMap.get(s);
  }

  //increment data address
  public void incrementData() {
    dataAddress++;
  }

  //increment program address
  public void incrementProgram() {
    programAddress++;
  }

  //return data address
  public int getDataAddress() {
    return dataAddress;
  }

  //return program address
  public int getProgramAddress() {
    return programAddress;
  }
}