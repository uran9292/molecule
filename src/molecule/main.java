package molecule;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.WindowConstants;



public class main  {
	
	public static czastka mol;
	public static ArrayList<czastka>[] tablica = null;
	public static int X,Y,timer,number_of_molecules,czas,curr_frame = 0;
	public static  String tmp;
	public static JFrame jframe = new JFrame( "Program" ); 
	public static JFrame jframe2 = new JFrame( "Info" ); 
	public static int predkosc=30;

	public static float punkt_x, punkt_y;

	
	
	
    public static void main(String[] args) {
		
    	//  włączenie c++
    	try {
			Process proc = new ProcessBuilder("./program").start();
			try {
				proc.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    	// wczytywanie danych wejsciowych
    	try {
			Scanner sc;
			sc = new Scanner(new BufferedReader(new FileReader(new File("data.txt"))));
			Scanner linia = new Scanner(sc.nextLine());
			String tmp;
			tmp=linia.next();
			X=Integer.parseInt(tmp);
			if(X<600) X=600;
			tmp=linia.next();
			Y=Integer.parseInt(tmp);
			tmp=linia.next();
			timer=Integer.parseInt(tmp);
			tmp=linia.next();
			number_of_molecules=Integer.parseInt(tmp);
			sc.close();
			
		    sc = new Scanner(new BufferedReader(new FileReader(new File("source.txt"))));
			
		    
			
		    czas=0;
		    tablica= new ArrayList[timer];
			
		    while(czas<timer-1)
			{
				linia = new Scanner(sc.nextLine()); // wczytujemy ktory to przebieg oraz ile jest czastek
				tmp=linia.next();
				czas=Integer.parseInt(tmp);
				tmp=linia.next();
				number_of_molecules=Integer.parseInt(tmp);

				
				int i=0;
				tablica[czas] = new ArrayList<czastka>();
				 
				while(i<number_of_molecules && sc.hasNextLine()) { 
					czastka mol = new czastka();
			        linia = new Scanner(sc.nextLine());
			        tmp = linia.next(); 
			        mol.x = Float.parseFloat(tmp);
			        tmp = linia.next(); 
			        mol.y = Float.parseFloat(tmp);
			        mol.vx = linia.next(); 
			        mol.vy = linia.next();
			        mol.r = linia.next();
			        tmp=linia.next();
			        if(tmp.equals("-1")) tmp = "Brak";
			        mol.m=tmp;
			        mol.type=linia.next();
			        mol.label=linia.next();
			        mol.collisions="";
			        linia.close();
			        tablica[czas].add(mol);
			        i++;
				    
			    }
				linia.close();
				
				
				if(czas > 0)
				{
					int a,zakres;
					zakres=tablica[czas - 1].size() - 1;
					i=0;
					while(i < number_of_molecules - 1) // zapisywanie poprzednich kolizji do nowego obiektu
					{
						a=0;
						while(!tablica[czas].get(i).label.equals(tablica[czas - 1].get(a).label) && a < zakres) 
						{ a++; }
						tmp=tablica[czas - 1].get(a).collisions;
						if (tablica[czas].get(i).label.equals(tablica[czas - 1].get(a).label)) tablica[czas].get(i).collisions += tmp; 
						i++;
					}
				}
				
				
				Scanner druga_linia = new Scanner(sc.nextLine()); // wczytuje collisions
				int kol1,kol2;
				i=0;
				tmp=druga_linia.next();
				if(!tmp.equals("Brak"))
				{
					int liczba_kolizji=Integer.parseInt(tmp);
					while(liczba_kolizji > 0)
					{
						
						tmp=druga_linia.next();
						kol1=Integer.parseInt(tmp);
						tmp=druga_linia.next();
						kol2=Integer.parseInt(tmp);
						
						tablica[czas].get(kol1).collisions+= czas + " " + tablica[czas].get(kol2).label + " "; 
						tablica[czas].get(kol2).collisions+= czas + " " + tablica[czas].get(kol1).label + " "; 
						
						liczba_kolizji--;
					}
					
					
					
					
				}
				
				druga_linia.close();
				
			}
		    sc.close();
		    
		    
		} catch (Exception e)
		  {
		    System.err.format("Blad");
		    e.printStackTrace();
		  }
		

		// tworzymy okno
		
		Screen.glcanvas.addGLEventListener(new Screen());
		Screen.glcanvas.addMouseListener(new Screen());

        
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.addWindowListener( new WindowAdapter() {
            @Override
			public void windowClosing( WindowEvent windowevent ) {
                System.exit(0);
            }
        });
        

        jframe.add(new Toolbar());
        jframe.pack();
        jframe.getContentPane().add( Screen.glcanvas, BorderLayout.CENTER );
        jframe.setSize(X + 15, Y + 70);
        jframe.setLocation(50, 50);
        jframe.setVisible(true);
		

        jframe2.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        jframe2.addWindowListener(new WindowAdapter() {
            @Override
			public void windowClosing(WindowEvent windowevent) {
                jframe2.dispose();
            }
        });
        
        jframe2.add(new Toolbar_selection());
        jframe2.pack();
        jframe2.setSize(200, Y + 70);
        jframe2.setLocation(X + 65, 50);
        jframe2.setVisible(true);
		

	}
    
    

}



