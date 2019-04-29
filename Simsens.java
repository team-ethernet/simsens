import java.io.IOException;
import java.lang.ProcessBuilder;

public class Simsens {
	public static void main(String[] args) throws IOException, InterruptedException {
		
		String helpmsg = "\nUsage: \njava -jar Simsens.jar <Broker IP> <Topic> <Num. of messages per node> <Message delay (ms)> <Num. of nodes>\nExecute the program without arguments or with -h only to view this text\n\nPassing arguments incorrectly will most likely mess something up\nDO IT CORRECTLY";
		
		if(args.length == 0) {
			System.out.println(helpmsg);
		} else if(args[0].equals("-h")) {
			System.out.println(helpmsg);
		} else if(args.length==5) {
			/*
			 * 0 = Broker IP
			 * 1 = Topic
			 * 3 = Num of msg
			 * 4 = Msg delay
			 * 5 = Num of different sim. nodes
			 */
			
			//Handling args
			final String brokerIp = args[0];
			final String topic = args[1];
			final int numOfMsg = Integer.parseInt(args[2]);
			final int msgDelay = Integer.parseInt(args[3]);
			final int numOfNodes = Integer.parseInt(args[4]);
			
			
			//Threads
			for(int n = 0; n < numOfNodes; n++) {
				final int threadNum = n;
				Thread thread = new Thread(){
				    public void run(){
				    	try {
				    		Thread.sleep((threadNum*1000)/numOfNodes);
				    	} catch(Exception e) {
				    		System.out.println(e);
				    	}
				      System.out.println("Thread Running: " + threadNum);
				      int db = 0;
				      String nodeId = "FAKE3d000001";
						nodeId += String.format("%04d", threadNum*10);
						try {
							multiMsgLoop(brokerIp, topic, nodeId, numOfMsg, msgDelay, db);
						} catch(Exception e) {
							System.out.println(e);
						}
				    }
				  };
				  thread.start();
			}
		}
	}
	
	public static int dB() {
		return((int)Math.floor(Math.random()*Math.random()*36+50));
	}
	
	public static void multiMsgLoop(String brokerIp, String topic, String nodeId, int numOfMsg, int msgDelay, int db) throws IOException, InterruptedException {
		//Sending messages		        
			for(int i = 0; i < numOfMsg; i++) {
				if(true) {
					db = dB();
				}
				sendmsg(brokerIp, topic, nodeId, db+"");
				Thread.sleep(msgDelay);
			}
	}
	
	//Message sending function
	public static void sendmsg(String brokerIp, String topic, String nodeId, String db) throws IOException {
		ProcessBuilder prcBldr = new ProcessBuilder();
		prcBldr.command("./mosquittotester.bat", brokerIp, topic, nodeId, db);
		prcBldr.start();
		System.out.println("Msg sent: " + "{\"node_id\":\"" + nodeId + "\",\"db\":" + db + "}");
	}
}