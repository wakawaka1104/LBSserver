package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import asset.Order;
import asset.Property;
import asset.SlaveList;
import tcpIp.SocketClient;
import tcpIp.SocketServer;

public class MainWindow extends JFrame {

	private static final int SERVER_PORT = 11111;

	private static Property myProp = new Property();
	private static int port;
	private SocketClient sc;

	private JPanel contentPane;
	private JLabel stateLabel = new JLabel("none");
	private final JButton funcTestButton = new JButton("func test");
	private final Action funcBtnAction = new FuncBtnSwingAction();
	private final Action SendBtnAction = new SendSwingAction();


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					SlaveList.loadList();
					System.out.println(SlaveList.getInstance().toString());
					String addr = "localhost";
					SocketServer ts = new SocketServer(addr, 11111);
					Thread serverThread = new Thread(ts);
					serverThread.start();

					MainWindow frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		stateLabel.setBounds(5, 5, 333, 231);


		contentPane.add(stateLabel);
		funcTestButton.setAction(funcBtnAction);
		funcTestButton.setBounds(320, 5, 109, 41);
		contentPane.add(funcTestButton);

		JButton btnSend = new JButton("send");
		btnSend.setAction(SendBtnAction);
		btnSend.setBounds(331, 110, 91, 21);
		contentPane.add(btnSend);
	}

	private class FuncBtnSwingAction extends AbstractAction {
		public FuncBtnSwingAction() {
			putValue(NAME, "FuncBtnSwingAction");
			putValue(SHORT_DESCRIPTION, "func test action");
		}
		public void actionPerformed(ActionEvent e) {

//			System.out.println(Property.getInstance().getLocation().toString());

			try {

				sc = new SocketClient("localhost", 22222);
				Thread clientThread = new Thread(sc);
				clientThread.start();

				Thread.sleep(500);

			} catch (Exception e1) {
				System.err.println("aaa");
				e1.printStackTrace();
			}

		}
	}
	private class SendSwingAction extends AbstractAction {
		public SendSwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
			sc.asyncSend(new Order("file send"),(byte)0);
		}
	}
}
