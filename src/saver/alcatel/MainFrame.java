package saver.alcatel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class MainFrame extends javax.swing.JFrame {

	private Socket echoSocket = null;
	private JPanel jPanel1;
	private JButton jButton1;
	private JTextArea jTextArea1;
	private JButton jButton2;
	private JPanel jPanel2;
	private PrintWriter out = null;
	private BufferedReader in = null;
	private BufferedReader stdIn = null;
	
	public static StationDialog[] threads = null;
	public static int stations_count;

	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MainFrame inst = new MainFrame();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	
	public MainFrame() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
						
			BoxLayout thisLayout = new BoxLayout(getContentPane(), javax.swing.BoxLayout.Y_AXIS);
		getContentPane().setLayout(thisLayout);
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			{
				jPanel1 = new JPanel();
				getContentPane().add(jPanel1);
				{
					jButton2 = new JButton();
					jPanel1.add(jButton2);
					jButton2.setText("Stop");
					jButton2.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							for(int i = 0; i<stations_count; i++)
							{
								threads[i].interrupt();
							}
						}
					});
				}
				{
					jButton1 = new JButton();
					jPanel1.add(jButton1);
					jButton1.setText("Start");
					jButton1.addActionListener(new ActionListener() 
					{
						public void actionPerformed(ActionEvent evt) {
							//System.out.println("jButton1.actionPerformed, event="+evt);
							//TODO add your code for jButton1.actionPerformed
							ConfigWrapper.readProp();
							stations_count = Integer.parseInt(ConfigWrapper.prop.getProperty("stations_count"));
							threads = new StationDialog[stations_count];
							for(int i = 0; i<stations_count; i++)
							{
								threads[i] = new StationDialog();
								threads[i].station_addr = ConfigWrapper.prop.getProperty("station"+(i+1)+"_addr");
								System.out.println("station"+(i+1)+"_addr");
							}
							for(int i = 0; i<stations_count; i++)
							{
								threads[i].start();
							}
							
						}
					});
				}
			}
			{
				jPanel2 = new JPanel();
				BoxLayout jPanel2Layout = new BoxLayout(jPanel2, javax.swing.BoxLayout.X_AXIS);
				jPanel2.setLayout(jPanel2Layout);
				getContentPane().add(jPanel2);
				{
					jTextArea1 = new JTextArea();
					jPanel2.add(jTextArea1);
					jTextArea1.setPreferredSize(new java.awt.Dimension(356, 178));
				}
			}
			pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String stringToHex(String base) {
		 
		     StringBuffer buffer = new StringBuffer();
		     int intValue;
		     for(int x = 0; x < base.length(); x++)
		         {
		         int cursor = 0;
		         intValue = base.charAt(x);
		         /*String binaryChar = new String(Integer.toBinaryString(base.charAt(x)));
		         for(int i = 0; i < binaryChar.length(); i++)
		             {
		             if(binaryChar.charAt(i) == '1')
		              {
		                 cursor += 1;
		              }
		         }
		         if((cursor % 2) > 0)
		             {
		             intValue += 128;
		         }*/
		         buffer.append(Integer.toHexString(intValue) + " ");
		     }
		     return buffer.toString();
	}

}
