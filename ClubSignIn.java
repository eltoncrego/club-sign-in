import java.util.Date;
import java.util.Scanner;
import java.util.HashMap;
import java.text.SimpleDateFormat;
import java.io.*;

public class ClubSignIn{
	
	private static class Brother{

		private String name;
		private Date timeIn, timeOut;

		Brother(String name, Date timeIn){
			this.name = name;
			this.timeIn = timeIn;
		}

		public void setLogoutTime(Date logoutTime){
			timeOut = logoutTime;
		}

		public boolean isLoggedOut(){
			if(timeOut == null){
				return false;
			} return true;
		}

		public String getBrotherName(){
			return this.name;
		}

		public Date getLogInTime(){
			return this.timeIn;
		}

		public Date getLogOutTime(){
			return this.timeOut;
		}

	}

	public static void main(String[] args){

		Scanner user = new Scanner(System.in);
		System.out.print("Please enter the name of this event: ");
		String eventName = user.nextLine();

		HashMap<String, Brother> attendanceMap = new HashMap<String, Brother>();
		while(true){
			System.out.println("Please enter your name to login or logout\n(or type finalize to end event and print a report)\n");
			String enteredText = user.nextLine();
			if(enteredText.compareTo("finalize") != 0){
				// check if the name is logged in. If so log out
				if(attendanceMap.containsKey(enteredText) && attendanceMap.get(enteredText).isLoggedOut()){
					Date loginTime = new Date();
					String nameOrRelogIn = enteredText + " came back and";
					Brother loggedInBrother = new Brother(nameOrRelogIn, loginTime);
					attendanceMap.put(nameOrRelogIn, loggedInBrother);
					System.out.println("\nThank you, " + enteredText + ", you are logged IN again\n\n-----------");
				} else if (attendanceMap.containsKey(enteredText) && !attendanceMap.get(enteredText).isLoggedOut()){
					Date logoutTime = new Date();
					Brother loggingOutBrother = attendanceMap.get(enteredText);
					loggingOutBrother.setLogoutTime(logoutTime);
					attendanceMap.put(enteredText, loggingOutBrother);
					System.out.println("\nThank you, " + enteredText + ", you are now logged OUT\n\n-----------");
				} else {
					Date loginTime = new Date();
					Brother loggedInBrother = new Brother(enteredText, loginTime);
					attendanceMap.put(enteredText, loggedInBrother);
					System.out.println("\nThank you, " + enteredText + ", you are logged IN\n\n-----------");
				}
			} else {

				try{
				    PrintWriter writer = new PrintWriter(eventName+ ".txt", "UTF-8");
				    writer.println("Here is your attendance report for: " + eventName);
				    writer.println("--------------------------");
				    for(String name : attendanceMap.keySet()){
						Brother brotherToReport = attendanceMap.get(name);
						if(!brotherToReport.isLoggedOut()){
							Date logOutTime = new Date();
							brotherToReport.setLogoutTime(logOutTime);
						}
						String outTimeText, inTimeText;
						SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
						outTimeText = sdf.format(brotherToReport.getLogOutTime());
						inTimeText = sdf.format(brotherToReport.getLogInTime());

						writer.println(brotherToReport.getBrotherName() + " was here from " + 
								inTimeText + " to " + outTimeText);
					}
				    writer.close();
				} catch (IOException e) {
				   // do something
				}

				System.out.println("Output written to: " + eventName);
				// for(String name : attendanceMap.keySet()){
				// 	Brother brotherToReport = attendanceMap.get(name);
				// 	if(!brotherToReport.isLoggedOut()){
				// 		Date logOutTime = new Date();
				// 		brotherToReport.setLogoutTime(logOutTime);
				// 	}
				// 	String outTimeText, inTimeText;
				// 	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
				// 	outTimeText = sdf.format(brotherToReport.getLogOutTime());
				// 	inTimeText = sdf.format(brotherToReport.getLogInTime());

				// 	System.out.println("\n" + brotherToReport.getBrotherName() + " was here from " + 
				// 			inTimeText + " to " + outTimeText);
				// }
				break;
			}
		}
		System.out.println("\nThank you for using club-sign-in!");
		System.out.println("Copyright 2017 Elton Christopher Rego.");

	}


}