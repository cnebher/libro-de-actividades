package tmtimer;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

import com.darwinsys.swingui.UtilGUI;

/**
 * TMTimer - simple speech timer for Toastmasters or similar
 * events where speeches are time-limited.
 * TODO:
 *  fiddle gui builder to obviate fixGUI().
 * @author  Ian F. Darwin, http://www.darwinsys.com/
 * @version $Id: TMTimer.java,v 1.13 2004/09/11 13:55:59 ian Exp $
 */
public class TMTimer extends JFrame implements Runnable {

  /** Constructor - Initializes the Form */
  public TMTimer() {
    initComponents ();
    finishGUI();
    gotoState(READY);
    pack();
    UtilGUI.centre(this);
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the FormEditor.
   */
  private void initComponents () {//GEN-BEGIN:initComponents
    addWindowListener (new java.awt.event.WindowAdapter () {
        public void windowClosing (java.awt.event.WindowEvent evt) {
          exitForm (evt);
        }
      }
    );
    getContentPane ().setLayout (new java.awt.BorderLayout ());

    jMenuBar1 = new javax.swing.JMenuBar ();
      fileMenu = new javax.swing.JMenu ();
      fileMenu.setText ("File");
        exitMenuItem = new javax.swing.JMenuItem ();
        exitMenuItem.setText ("Exit");
        exitMenuItem.addActionListener (new java.awt.event.ActionListener () {
            public void actionPerformed (java.awt.event.ActionEvent evt) {
              jMenuItem1ActionPerformed (evt);
            }
          }
        );
        fileMenu.add(exitMenuItem);

      jMenuBar1.add(fileMenu);


    setJMenuBar(jMenuBar1);
    buttonsPanel = new javax.swing.JPanel ();
    buttonsPanel.setLayout (new java.awt.FlowLayout ());

      startButton = new javax.swing.JButton ();
      startButton.setText ("Start");
      startButton.addActionListener (new java.awt.event.ActionListener () {
          public void actionPerformed (java.awt.event.ActionEvent evt) {
            jButton1ActionPerformed (evt);
          }
        }
      );
      buttonsPanel.add (startButton);

      stopButton = new javax.swing.JButton ();
      stopButton.setText ("Stop");
      stopButton.addActionListener (new java.awt.event.ActionListener () {
          public void actionPerformed (java.awt.event.ActionEvent evt) {
            stopButtonActionPerformed (evt);
          }
        }
      );
      buttonsPanel.add (stopButton);

      resetButton = new javax.swing.JButton ();
      resetButton.setText ("Reset");
      resetButton.addActionListener (new java.awt.event.ActionListener () {
          public void actionPerformed (java.awt.event.ActionEvent evt) {
            resetButtonActionPerformed (evt);
          }
        }
      );
      buttonsPanel.add (resetButton);

    getContentPane ().add (buttonsPanel, "South");

    timesPanel = new javax.swing.JPanel ();
    timesPanel.setLayout (new java.awt.BorderLayout ());

      presetsPanel = new javax.swing.JPanel ();
      presetsPanel.setLayout (new java.awt.FlowLayout ());

        presetsLabel = new javax.swing.JLabel ();
        presetsLabel.setText ("Presets:");
        presetsPanel.add (presetsLabel);

        presetsList = new javax.swing.JList ();
        presetsList.setMinimumSize (new java.awt.Dimension(100, 100));
        presetsPanel.add (presetsList);

      timesPanel.add (presetsPanel, "West");

      fieldsPanel = new javax.swing.JPanel ();
      fieldsPanel.setLayout (new java.awt.GridLayout (3, 2));

        greenLabel = new javax.swing.JLabel ();
        greenLabel.setBackground (java.awt.Color.green);
        greenLabel.setText ("Green");
        greenLabel.setHorizontalAlignment (javax.swing.SwingConstants.RIGHT);
        fieldsPanel.add (greenLabel);

        greenTF = new javax.swing.JTextField ();
        greenTF.setText ("0:00");
        fieldsPanel.add (greenTF);

        yellowLabel = new javax.swing.JLabel ();
        yellowLabel.setBackground (java.awt.Color.yellow);
        yellowLabel.setText ("Yellow");
        fieldsPanel.add (yellowLabel);

        yellowTF = new javax.swing.JTextField ();
        yellowTF.setText ("0:00");
        fieldsPanel.add (yellowTF);

        redLabel = new javax.swing.JLabel ();
        redLabel.setBackground (java.awt.Color.red);
        redLabel.setText ("Red");
        fieldsPanel.add (redLabel);

        redTF = new javax.swing.JTextField ();
        redTF.setText ("0:00");
        fieldsPanel.add (redTF);

      timesPanel.add (fieldsPanel, BorderLayout.CENTER);

      runningTimesPanel = new javax.swing.JPanel ();
      runningTimesPanel.setLayout (new javax.swing.BoxLayout (runningTimesPanel, 1));

        curTime = new javax.swing.JLabel ();
        curTime.setHorizontalTextPosition (javax.swing.SwingConstants.CENTER);
        curTime.setText ("0:00");
        curTime.setHorizontalAlignment (javax.swing.SwingConstants.CENTER);
        curTime.setFont (new java.awt.Font ("Dialog", 3, 36));
        runningTimesPanel.add (curTime);

        maxTime = new javax.swing.JLabel ();
        maxTime.setHorizontalTextPosition (javax.swing.SwingConstants.CENTER);
        maxTime.setText ("0:00");
        maxTime.setHorizontalAlignment (javax.swing.SwingConstants.CENTER);
        maxTime.setFont (new java.awt.Font ("Dialog", 3, 36));
        runningTimesPanel.add (maxTime);

      timesPanel.add (runningTimesPanel, BorderLayout.EAST);

    getContentPane ().add (timesPanel, BorderLayout.NORTH);

    bigFlag = new javax.swing.JLabel ();
    bigFlag.setPreferredSize (new java.awt.Dimension(500, 300));
    bigFlag.setOpaque (true);
    bigFlag.setHorizontalAlignment (javax.swing.SwingConstants.CENTER);
    getContentPane().add(bigFlag, BorderLayout.CENTER);

  }//GEN-END:initComponents

  /** The action handler for the Start button (doStart) */
  private void jButton1ActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    // Add your handling code here:
  if (TMTimerUtil.mmssToInt(greenTF.getText()) <= 0 ||
      TMTimerUtil.mmssToInt(yellowTF.getText()) <= 0 ||
      TMTimerUtil.mmssToInt(redTF.getText()) <= 0) {
      JOptionPane.showMessageDialog(this,
        "All time values must be non-zero", "Error",
        JOptionPane.ERROR_MESSAGE);
      return;
    }
    maxTime.setText(redTF.getText());
    gotoState(RUNNING);
    new Thread(this).start();
  }//GEN-LAST:event_jButton1ActionPerformed

  /** The action handler for the Stop button (doStop) */
  private void stopButtonActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopButtonActionPerformed
    // Add your handling code here:
  gotoState(STOPPED);
  }//GEN-LAST:event_stopButtonActionPerformed

  /** The action handler for the Reset button (doReset) */
  private void resetButtonActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetButtonActionPerformed
    // Add your handling code here:
    gotoState(READY);
  }//GEN-LAST:event_resetButtonActionPerformed

  /** Action handler for the File->Exit menu item. */
  private void jMenuItem1ActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
    // Add your handling code here:
    System.exit(0);
  }//GEN-LAST:event_jMenuItem1ActionPerformed

  /** Exit the Application */
  private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
    System.exit(0);
  }//GEN-LAST:event_exitForm


// Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JPanel buttonsPanel;
  private javax.swing.JPanel timesPanel;
  private javax.swing.JLabel bigFlag;
  private javax.swing.JButton startButton;
  private javax.swing.JButton stopButton;
  private javax.swing.JButton resetButton;
  private javax.swing.JPanel presetsPanel;
  private javax.swing.JPanel fieldsPanel;
  private javax.swing.JPanel runningTimesPanel;
  private javax.swing.JLabel presetsLabel;
  private javax.swing.JList presetsList;
  private javax.swing.JLabel greenLabel;
  private javax.swing.JTextField greenTF;
  private javax.swing.JLabel yellowLabel;
  private javax.swing.JTextField yellowTF;
  private javax.swing.JLabel redLabel;
  private javax.swing.JTextField redTF;
  private javax.swing.JLabel curTime;
  private javax.swing.JLabel maxTime;
  private javax.swing.JMenuBar jMenuBar1;
  private javax.swing.JMenu fileMenu;
  private javax.swing.JMenuItem exitMenuItem;
// End of variables declaration//GEN-END:variables

  protected final int READY  = 0;
  protected final int RUNNING  = 1;
  protected final int GREEN  = 2;
  protected final int YELLOW  = 3;
  protected final int RED     = 4;
  protected final int STOPPED  = 5;

  // These values are arbitrarily chosen not to intersect with the above.
  protected final int C_NONE  = 100;
  protected final int C_GREEN = 101;
  protected final int C_YELLOW = 102;
  protected final int C_RED  = 103;

  /** A halt-the-timer flag */
  protected boolean done;
  /** Iterator that maps from foo to bar. */
   protected HashMap presetsMap;
  /** Map to make array processing of times easier */
  protected JTextField[] times = new JTextField[3];
  /** The three numbers for the current event */
  int[] data = new int[3];

  /** Attend to a few details that the GUI doesn't seem to drive.
   * Called at end of Constructor.
   */
   protected void finishGUI() {
  // double widths[] = { .33, .66 };
  // fieldsPanel.setLayout(new EntryLayout(widths));
  yellowLabel.setHorizontalAlignment (javax.swing.SwingConstants.RIGHT);
  redLabel.setHorizontalAlignment (javax.swing.SwingConstants.RIGHT);
    times[0] = greenTF;
    times[1] = yellowTF;
    times[2] = redTF;
    presetsList.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
    gotoState(READY);
        String key = (String)presetsList.getSelectedValue();
    data = (int[]) presetsMap.get(key);
        for (int i=0; i<data.length; i++)
          times[i].setText(TMTimerUtil.intToMmss(data[i]));
      }
    });
  }

  public void showTime(String newTime) {
        curTime.setText(newTime);
  }

  /* The start button creates a Thread to run this. */
  public void run() {
  // start where we left off! doReset sets it to zero
    int time = TMTimerUtil.mmssToInt(curTime.getText());
  if (time == 0)
    gotoState(RUNNING);
    done = false;
  data[0] = TMTimerUtil.mmssToInt(greenTF.getText());
  data[1] = TMTimerUtil.mmssToInt(yellowTF.getText());
  data[2] = TMTimerUtil.mmssToInt(redTF.getText());
    while (!done) {
      try {
    showTime(TMTimerUtil.intToMmss(time));
    /* Adjust color display. Tests in reverse order to avoid flashing. */
    if (time == data[2])
      gotoState(RED);
    else
      if (time == data[1])
        gotoState(YELLOW);
      else
        if (time == data[0])
          gotoState(GREEN);
    Thread.sleep(1000);
    time++;
      } catch (InterruptedException e) {
        // nothing
      }
    }
  }

  /** Set the color on the screen and/or LED's. */
  protected void setColor(int c) {
    switch (c) {
    case C_NONE:
      bigFlag.setBackground(Color.white);
      break;
    case C_GREEN:
      bigFlag.setBackground(Color.green);
      break;
    case C_YELLOW:
      bigFlag.setBackground(Color.yellow);
      break;
    case C_RED:
      bigFlag.setBackground(Color.red);
      break;
    default:
      throw new IllegalStateException("setColor(" + c + ") invalid");
  }
  }

  /** Set the program into the given state. */
  protected void gotoState(int st) {
    switch(st) {
    case READY:
      curTime.setText(TMTimerUtil.intToMmss(0));
      maxTime.setText(redTF.getText());
      setColor(C_NONE);
      /* FALLTHROUGH INTENTIONAL */
    case STOPPED:
      done = true;
      stopButton.setEnabled(false);
      startButton.setEnabled(true);
      break;
    case RUNNING:
      startButton.setEnabled(false);
      stopButton.setEnabled(true);
      break;
    case GREEN:
      setColor(C_GREEN);
      break;
    case YELLOW:
      setColor(C_YELLOW);
      break;
    case RED:
      setColor(C_RED);
      break;
      default:
      throw new IllegalStateException("gotoState(" + st + ") invalid");
    }
  }

  /** Fill in the fields in the Presets list. */
  protected void populateList(Properties p) {
    String[] labels = new String[p.size()];
    Enumeration e = p.keys();
    int i = 0;
    while (e.hasMoreElements())
      labels[i++] = (String)e.nextElement();
    presetsList.setListData(labels);
  }

  /** Main program to create the whole thing. */
  public static void main(java.lang.String[] args) {
  TMTimer t = new TMTimer();
  Properties p = new Properties();
  TMTimerUtil.getProperties(p, "TMTimer");
  t.presetsMap = TMTimerUtil.parseProps(p);
  t.populateList(p);
  t.setVisible(true);
  }
}
