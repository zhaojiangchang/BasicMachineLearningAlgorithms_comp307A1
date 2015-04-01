
public class Main {

	public static void main(String[] args) {
		int choice = 0;
		if (args.length > 0) {
		    try {
		    	choice = Integer.parseInt(args[0]);
		    	if(choice == 1 || choice == 2 || choice ==3){
		    		if(choice==1)
		    			part1.Main.main(null);
		    		if(choice==2)
		    			part2.Main.main(null);
		    		if(choice ==3)
		    			part3.Main.main(null);
		    	}else{
		    	}
		    } catch (NumberFormatException e) {
		        System.err.println("Argument" + args[0] + " must be an integer.");
		        System.exit(1);
		    }
		}
	}

}
