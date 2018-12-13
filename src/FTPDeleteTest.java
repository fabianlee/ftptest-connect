// connects to ftp site via http proxy using CONNECT, changes directory, deletes file
// http://www.sauronsoftware.it/projects/ftp4j/manual.php
import it.sauronsoftware.ftp4j.*;
import it.sauronsoftware.ftp4j.connectors.HTTPTunnelConnector;

public class FTPDeleteTest {
	
	public static void main(String args[]) throws Exception {
		
		if(args.length<8) {
			System.err.println("Usage: ftpHost ftpUser ftpPass proxyHost proxyPort changeDir deleteFile");
			System.err.println("Example: ftp.ubuntu.com myuser myP4ss! squidFTPProxy 3128 /files filetodelete.txt");
			System.err.println(args.length);
			System.exit(1);
		}
		
		int narg=0;
		String ftpHost=args[narg++];
		String ftpUser=args[narg++];
		String ftpPass=args[narg++];
		String proxyHost=args[narg++];
		int proxyPort=Integer.parseInt(args[narg++]);
		String changeDir=args[narg++];
		String deleteFile=args[narg++];
		
		FTPClient client = new FTPClient();
		client.setPassive(true);
		HTTPTunnelConnector connector = new HTTPTunnelConnector(proxyHost,proxyPort);
		client.setConnector(connector);
		
		client.connect(ftpHost);
		client.login(ftpUser, ftpPass);

		System.out.println("initial file listing in root");
		FTPFile[] list = client.list();
		for(FTPFile file : list) {
			System.out.println(file.getName());
		}
		
		System.out.println("changing to dir: " + changeDir);
		client.changeDirectory(changeDir);
		
		System.out.println("deleting file: " + deleteFile);
		client.deleteFile(deleteFile);
		
		// delete first file found in directory
		System.out.println("file listing after deletion");
		list = client.list();
		for(FTPFile file : list) {
			System.out.println(file.getName());
		}
		
		client.disconnect(true);
		System.out.println("done");
		
	}

}