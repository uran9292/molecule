package molecule;

/*
Kod odpowiada za wyœwietlanie zawartoœci ma³ego okna które wyœwietla informacje
 o zaznaczonej cz¹steczce.
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;

public class Toolbar_selection extends JPanel
{
/**
* 
*/
private static final long serialVersionUID = -193140145158540755L;
public static JLabel text;
public static JLabel text_x;
public static JLabel text_y;
public static JLabel text_vx;
public static JLabel text_vy;
public static JLabel text_promien;
public static JLabel text_masa;
public static JLabel text_typ;
public static JLabel text_etykieta;
public static JLabel text_kolizje;


public Toolbar_selection() {
super(new BorderLayout());

// tworze toolbar
JToolBar toolBar2 = new JToolBar("");
dodaj_tekst(toolBar2);
setPreferredSize(new Dimension(main.X, 60));
add(toolBar2, BorderLayout.PAGE_START);
}

protected static void dodaj_tekst(JToolBar toolBar2) {

// pola tekstowe


JPanel label_selekcja = new JPanel(new GridLayout(0,1));

czastka c = main.tablica[main.curr_frame].get(0);;
String tmp;

tmp= " Info about molecule:";
text = new JLabel(tmp);
tmp= " X:  " + c.x;
text_x = new JLabel(tmp);
tmp= " Y:  " + c.y;
text_y = new JLabel(tmp);
tmp= " Vx:  " + c.vx;
text_vx = new JLabel(tmp);
tmp= " Vy:  " + c.vy;
text_vy = new JLabel(tmp);
tmp= " Radius:  " + c.r;
text_promien = new JLabel(tmp);
tmp= " Mass:  " + c.m;
text_masa = new JLabel(tmp);
tmp= " Type:  " + c.type;
text_typ = new JLabel(tmp);
tmp= " Label:  " + c.label;
text_etykieta = new JLabel(tmp);
tmp= " Collisions:  ";
text_kolizje = new JLabel(tmp);

// tworzymy dla pola tekstowego scroller
JScrollPane  scroller = new JScrollPane(text_kolizje, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

scroller.setPreferredSize(new Dimension(700, 400));


JPanel panel = new JPanel(new BorderLayout());
panel.add(label_selekcja, BorderLayout.PAGE_START);
panel.add(scroller,BorderLayout.PAGE_END);

label_selekcja.add(text);
label_selekcja.add(text_x);
label_selekcja.add(text_y);
label_selekcja.add(text_vx);
label_selekcja.add(text_vy);
label_selekcja.add(text_promien);
label_selekcja.add(text_masa);
label_selekcja.add(text_typ);
label_selekcja.add(text_etykieta);
toolBar2.add(panel);


}

// uaktualnia tekst po kliknieciu lub nastepnym frame
public static void uaktualnij_tekst(czastka wyswietl)
{
String tmp;
tmp= " X:  " + wyswietl.x;
Toolbar_selection.text_x.setText(tmp);
tmp= " Y:  " + wyswietl.y;
Toolbar_selection.text_y.setText(tmp);
tmp= " Vx:  " + wyswietl.vx;
Toolbar_selection.text_vx.setText(tmp);
tmp= " Vy:  " + wyswietl.vy;
Toolbar_selection.text_vy.setText(tmp);
tmp= " Radious:  " + wyswietl.r;
Toolbar_selection.text_promien.setText(tmp);
tmp= " Mass:  " + wyswietl.m;
Toolbar_selection.text_masa.setText(tmp);
tmp= " Type:  " + wyswietl.type;
Toolbar_selection.text_typ.setText(tmp);
tmp= " Label:  " + wyswietl.label;
Toolbar_selection.text_etykieta.setText(tmp);
tmp= " Collisions:  " + wyswietl.collisions +  " ";
Toolbar_selection.text_kolizje.setText(tmp);
}


}


