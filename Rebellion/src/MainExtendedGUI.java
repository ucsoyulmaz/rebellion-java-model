import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.json.JSONException;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class MainExtendedGUI {

	public JFrame frame;
	public double copNum;
	public double agentNum;
	public double visionNum;
	public double glNum;
	public int mjtNum;
	
	// Switch to go or stop the program
	public static boolean stopMain;
	public static boolean isRunning;
	
	public static JButton btnStop = new JButton("Terminate");
	public static JButton btnGo = new JButton("Set and Run");


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainExtendedGUI window = new MainExtendedGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainExtendedGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 363, 350);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTextPane cop = new JTextPane();
		cop.setBackground(Color.LIGHT_GRAY);
		cop.setBounds(296, 11, 53, 16);
		frame.getContentPane().add(cop);
		cop.setText(String.valueOf(4.0));
		
		JSlider copDensity = new JSlider(1,999,40);
		copDensity.setBounds(94, 11, 190, 29);
		frame.getContentPane().add(copDensity);
		copDensity.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				cop.setText(String.valueOf(Double.valueOf(copDensity.getValue())/10));
			}
		});
		
		JTextPane agent = new JTextPane();
		agent.setBackground(Color.LIGHT_GRAY);
		agent.setText("70");
		agent.setBounds(296, 52, 53, 16);
		frame.getContentPane().add(agent);
		
		JSlider agentDensity = new JSlider(1,999,700);
		agentDensity.setBounds(94, 52, 190, 29);
		frame.getContentPane().add(agentDensity);
		agentDensity.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				agent.setText(String.valueOf(Double.valueOf(agentDensity.getValue())/10));
			}
		});
		
		JLabel lblNewLabel = new JLabel("cop-density:");
		lblNewLabel.setBounds(6, 11, 81, 16);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblAgentdensity = new JLabel("agent-density:");
		lblAgentdensity.setBounds(6, 52, 93, 16);
		frame.getContentPane().add(lblAgentdensity);
		
		JTextPane vis = new JTextPane();
		vis.setText("7.0");
		vis.setBackground(Color.LIGHT_GRAY);
		vis.setBounds(296, 93, 53, 16);
		frame.getContentPane().add(vis);
		
		JSlider vision = new JSlider(0,100,70);
		vision.setBounds(94, 93, 190, 29);
		frame.getContentPane().add(vision);
		vision.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				vis.setText(String.valueOf(Double.valueOf(vision.getValue())/10));
			}
		});
		
		JLabel lblNewLabel_1_1 = new JLabel("vision:");
		lblNewLabel_1_1.setBounds(6, 93, 61, 16);
		frame.getContentPane().add(lblNewLabel_1_1);
		
		JLabel lblGlegitimacy = new JLabel("g-legitimacy:");
		lblGlegitimacy.setBounds(6, 134, 93, 16);
		frame.getContentPane().add(lblGlegitimacy);
		
		JTextPane glegi = new JTextPane();
		glegi.setText("0.82");
		glegi.setBackground(Color.LIGHT_GRAY);
		glegi.setBounds(296, 134, 53, 16);
		frame.getContentPane().add(glegi);
		
		JSlider governmentLegitimacy = new JSlider(0,100,82);
		governmentLegitimacy.setBounds(94, 134, 190, 29);
		frame.getContentPane().add(governmentLegitimacy);
		governmentLegitimacy.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				glegi.setText(String.valueOf(Double.valueOf(governmentLegitimacy.getValue())/100));
			}
		});
		
		JLabel lblMaxjailterm = new JLabel("max-jail-term:");
		lblMaxjailterm.setBounds(6, 175, 102, 16);
		frame.getContentPane().add(lblMaxjailterm);
		
		JTextPane mjt = new JTextPane();
		mjt.setText("30");
		mjt.setBackground(Color.LIGHT_GRAY);
		mjt.setBounds(296, 175, 53, 16);
		frame.getContentPane().add(mjt);
		
		JSlider maxJailTerm = new JSlider(0,50,30);
		maxJailTerm.setBounds(94, 175, 190, 29);
		frame.getContentPane().add(maxJailTerm);
		maxJailTerm.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				mjt.setText(String.valueOf(maxJailTerm.getValue()));
			}
		});
	
		
		JButton btnGo = new JButton("Set and Run");
		btnGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				execute();
				
			}
			public void execute() {
				MainExtended.initialCopDensity = Double.valueOf(cop.getText());
				MainExtended.initialCitizenDensity = Double.valueOf(agent.getText());
				MainExtended.vision = Double.valueOf(vis.getText());
				MainExtended.governmentLegitimacy = Double.valueOf(glegi.getText());
				MainExtended.maxJailTerm = Integer.valueOf(mjt.getText());
				stopMain = false;
				btnStop.setEnabled(true);
				btnGo.setEnabled(false);
				MainExtended.stopMainValue = Boolean.valueOf(stopMain);
				
				Thread thread = new Thread() {
					public void run() {
						try {
							MainExtended.run();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				};
				thread.start();
			}
		});
		btnGo.setBounds(6, 255, 114, 29);
		frame.getContentPane().add(btnGo);
		
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopMain = true;
				MainExtended.stopMainValue = Boolean.valueOf(stopMain);
				btnStop.setEnabled(false);
				btnGo.setEnabled(true);
			}
		});
		btnStop.setBounds(230, 255, 125, 29);
		frame.getContentPane().add(btnStop);
		btnStop.setEnabled(false);
		
		JLabel lblToDoMore = new JLabel("Tips: more precisely adjust with'<-'or'->'on keyboard");
		lblToDoMore.setBounds(6, 223, 351, 16);
		frame.getContentPane().add(lblToDoMore);
	}
}
