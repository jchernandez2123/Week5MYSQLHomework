package application;

public class Display {

	private static Display instance = new Display();
	
	

	private Display() { }

    public static Display getInstance( ) {
		return instance;
      
        }

      
        public static void displayInstructions(String instructions) {
          
            System.out.print("--> " + instructions + " <--\n");
            
        }

        public static void displayOption(String option, int optionNumber) {
            
            System.out.println("\t" + optionNumber + ") " + option);
            
        }

        public static void displayResponse(String response) {
            
            System.out.println(response);
           
        }

        public static void displayAlert(String alert) {
            
            System.out.println("!!! " + alert + " !!!");
            
        }

      
      

        private static StringBuilder build(String string, int length) {
            StringBuilder stringBuilder = new StringBuilder(length);
            for (int i = 0; i < length; i++) {
                stringBuilder.append(string);
            }
            return stringBuilder;
        }

        

    
   

}