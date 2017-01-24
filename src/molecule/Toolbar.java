package molecule;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.Timer;
import javax.swing.WindowConstants;


public class Toolbar extends JPanel
implements PropertyChangeListener {
/**
* 
*/
private static final long serialVersionUID = 1L;
public JLabel text_label=new JLabel(" Frame: ");
public static JFormattedTextField text_wartosc;
public NumberFormat text_format;
public Action button_poprzednia_akcja = new poprzednia_akcja("Previous");
public Action button_nastepna_akcja = new nastepna_akcja("Next");
public Action button_start_akcja = new start_akcja("Start");
public Action button_stop_akcja = new stop_akcja("Stop");
public Action button_szybciej_akcja = new szybciej_akcja("Faster");
public Action button_wolniej_akcja = new wolniej_akcja("Slower");
public Action button_selekcja_akcja = new selekcja_akcja("Info");
public Timer timer = new Timer(main.predkosc, new MyTimerActionListener());


public Toolbar() {
super(new BorderLayout());


// tworze toolbar
JToolBar toolBar = new JToolBar("Tools");
dodaj_przyciski(toolBar);
setPreferredSize(new Dimension(main.X + 15, 30));
add(toolBar, BorderLayout.PAGE_START);

}

protected void dodaj_przyciski(JToolBar toolBar) {

// przyciski
JButton button_poprzedni = new JButton(button_poprzednia_akcja);
JButton button_nastepny = new JButton(button_nastepna_akcja);
JButton button_start = new JButton(button_start_akcja);
JButton button_stop = new JButton(button_stop_akcja);
JButton button_szybciej = new JButton(button_szybciej_akcja);
JButton button_wolniej = new JButton(button_wolniej_akcja);
JButton button_selekcja = new JButton(button_selekcja_akcja);

/*button_poprzedni.setBackground(Color.BLACK);
button_poprzedni.setForeground(Color.white);*/

text_wartosc = new JFormattedTextField(text_format);
text_wartosc.setValue(main.curr_frame + 1);
text_wartosc.setColumns(4);
text_wartosc.addPropertyChangeListener("value", this);
text_label.setLabelFor(text_wartosc);
JPanel labelPane = new JPanel(new GridLayout(0,1));
labelPane.setMaximumSize(new Dimension(60, 30));
labelPane.add(text_label);
JPanel fieldPane = new JPanel(new GridLayout(0,1));
fieldPane.setMaximumSize(new Dimension(45, 30));
fieldPane.add(text_wartosc);
JPanel labelPane2 = new JPanel(new GridLayout(0,1));
labelPane2.setMaximumSize(new Dimension(55, 30));
String tmp = " / " + main.timer;
JLabel text_label2 = new JLabel(tmp);
labelPane2.add(text_label2);

toolBar.add(button_poprzedni);
toolBar.add(button_nastepny);
toolBar.add(labelPane);
toolBar.add(fieldPane);
toolBar.add(labelPane2);
toolBar.add(button_start);
toolBar.add(button_stop);
toolBar.add(button_szybciej);
toolBar.add(button_wolniej);
toolBar.add(button_selekcja);
}





// zmiana wartosci pola tekstowego
@Override
public void propertyChange(PropertyChangeEvent e) {
Object source = e.getSource();
if (source == text_wartosc) {
	
main.curr_frame = ((Number)text_wartosc.getValue()).intValue() - 1; 
Screen.glcanvas.repaint();}
}



// zmiana wartosci pola tekstowego
public static void zmien_text_wartosc(ActionEvent e) {

text_wartosc.setValue(main.curr_frame + 1);
}




// obsluga przyciskow

public class poprzednia_akcja extends AbstractAction {
/**
* 
*/
private static final long serialVersionUID = 1L;
public poprzednia_akcja(String text) { super(text); }
@Override
public void actionPerformed(ActionEvent e) {
main.curr_frame--;
if(main.curr_frame < 0) main.curr_frame = main.timer - 1;
zmien_text_wartosc(e);
Screen.glcanvas.repaint();
}
}

public class nastepna_akcja extends AbstractAction {
/**
* 
*/
private static final long serialVersionUID = 1L;
public nastepna_akcja(String text) { super(text); }
@Override
public void actionPerformed(ActionEvent e) {
main.curr_frame++;
if(main.curr_frame >= main.timer) main.curr_frame = 0;
zmien_text_wartosc(e);
Screen.glcanvas.repaint();
}
}

public class start_akcja extends AbstractAction {
/**
* 
*/
private static final long serialVersionUID = 1L;
public start_akcja(String text) { super(text); }
@Override
public void actionPerformed(ActionEvent e) {
timer.start();
}
}

public class stop_akcja extends AbstractAction {
/**
* 
*/
private static final long serialVersionUID = 1L;
public stop_akcja(String text) { super(text); }
@Override
public void actionPerformed(ActionEvent e) {
timer.stop();
}
}

public class szybciej_akcja extends AbstractAction {
/**
* 
*/
private static final long serialVersionUID = 1L;
public szybciej_akcja(String text) { super(text); }
@Override
public void actionPerformed(ActionEvent e) {
if (main.predkosc > 10) main.predkosc = main.predkosc - 5;
timer.stop();
timer = new Timer(main.predkosc, new MyTimerActionListener());
timer.start();

}
}

public class wolniej_akcja extends AbstractAction {
/**
* 
*/
private static final long serialVersionUID = 1L;
public wolniej_akcja(String text) { super(text); }
@Override
public void actionPerformed(ActionEvent e) {
main.predkosc = main.predkosc + 5;
timer.stop();
timer = new Timer(main.predkosc, new MyTimerActionListener());
timer.start();
}
}

// odtwarzanie zamknietego okna
public class selekcja_akcja extends AbstractAction {
/**
* 
*/
private static final long serialVersionUID = 1L;
public selekcja_akcja(String text) { super(text); }
@Override
public void actionPerformed(ActionEvent e) {

main.jframe2.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
main.jframe2.addWindowListener( new WindowAdapter() {
@Override
public void windowClosing( WindowEvent windowevent ) {
main.jframe2.dispose();
}
});

main.jframe2.add(new Toolbar_selection());
main.jframe2.pack();
main.jframe2.setSize(200, 225);
main.jframe2.setLocation(main.X + 65, 50);
main.jframe2.setVisible( true );


}
}




}

// odliczanie dla symulacji

class MyTimerActionListener implements ActionListener {
@Override
public void actionPerformed(ActionEvent e) {
Toolbar.zmien_text_wartosc(e);
main.curr_frame++;
if(main.curr_frame>=main.timer) main.curr_frame=0;
Screen.glcanvas.repaint();

}
}

