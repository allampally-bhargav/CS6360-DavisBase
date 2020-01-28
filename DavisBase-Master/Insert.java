
public class Insert {
	 public static void executeInsertString(String insertString) {
			System.out.println("INSERT METHOD");
			System.out.println("Executing the string:\"" + insertString + "\"");
			
			String[] tokens=insertString.split(" ");
			String table = tokens[3];
			String[] temp = insertString.split("values");
			String temporary=temp[1].trim();
			String[] insert_vals = temporary.substring(1, temporary.length()-1).split(",");
			for(int i = 0; i < insert_vals.length; i++)
				insert_vals[i] = insert_vals[i].trim();
			if(!DavisBase.IfTableAvailable(table)){
				System.out.println("Table "+table+" does not exist.");
			}
			else
			{
				CreateTable.insertInto(table, insert_vals);
			}

		}
	    
}
