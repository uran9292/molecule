package molecule;

// odpowiada za wyswietlanie symulacji

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.fixedfunc.GLMatrixFunc;
import javax.media.opengl.glu.GLU;

public class Screen implements GLEventListener,  MouseListener, MouseMotionListener {
	
	public static GLProfile glprofile = GLProfile.getDefault();
    public static GLCapabilities glcapabilities = new GLCapabilities( glprofile );
    public final static GLCanvas glcanvas = new GLCanvas( glcapabilities );
    public static GLU glu = new GLU();
    public static czastka wyswietl;
	public static czastka czastka_wybrana;
	public static boolean czy_wybrana=false;
	public static int obecna_index=0;
	public static boolean curr_destroyed=true;
	
	// odswieza okno
	public static void render(  GL2 gl2, int width, int height, int mode) {
        
    	if(main.curr_frame>=main.timer) main.curr_frame=0;
    	
    	if(czy_wybrana) zmien_wartosc();
    	else wyswietl = main.tablica[main.curr_frame].get(0);
    	
    	Toolbar_selection.uaktualnij_tekst(wyswietl);
    	
    	gl2.glClear( GL.GL_COLOR_BUFFER_BIT );
        gl2.glLoadIdentity();
        gl2.glBegin (GL.GL_POINTS);
        gl2.glColor3f( 155, 255, 155 );

        int i=0;
    	
    	while(i<main.tablica[main.curr_frame].size() && main.tablica[main.curr_frame]!=null)
    		{
    			main.mol=main.tablica[main.curr_frame].get(i);
    			gl2.glVertex2f (main.mol.x,main.mol.y);
    			i++;
    		}	
    	
    	if(!curr_destroyed)
    	{
    	gl2.glColor3f( 255, 0, 0 );
    	gl2.glVertex2f (wyswietl.x,wyswietl.y);
    	gl2.glVertex2f (wyswietl.x+1,wyswietl.y);
    	gl2.glVertex2f (wyswietl.x-1,wyswietl.y);
    	gl2.glVertex2f (wyswietl.x,wyswietl.y+1);
    	gl2.glVertex2f (wyswietl.x,wyswietl.y-1);
    	}
    	gl2.glEnd ();
    	
    }

	@Override
	public void display(GLAutoDrawable glautodrawable) {

	    Screen.render(glautodrawable.getGL().getGL2(), glautodrawable.getSurfaceWidth(), glautodrawable.getSurfaceHeight(),GL2.GL_RENDER);
	}

	@Override
	public void dispose(GLAutoDrawable glautodrawable) {
		
		
	}

	@Override
	public void init(GLAutoDrawable glautodrawable) {

		Screen.render(glautodrawable.getGL().getGL2(), glautodrawable.getSurfaceWidth(), glautodrawable.getSurfaceHeight(),GL2.GL_RENDER);
	}

	@Override
	public void reshape(GLAutoDrawable glautodrawable, int x, int y, int width, int height) {
		
		GL2 gl2 = glautodrawable.getGL().getGL2();
		gl2.glViewport(0, 0, width, height);
		gl2.glMatrixMode( GLMatrixFunc.GL_PROJECTION );
        gl2.glLoadIdentity();
        glu.gluOrtho2D(0.0f, width, 0.0f, height);
        gl2.glMatrixMode( GLMatrixFunc.GL_MODELVIEW );
        gl2.glLoadIdentity();
	}
    
	// zmienia wartosc wyswietlanej czastki
	public static void zmien_wartosc()
	{
		int i=0;
		int zakres=main.tablica[main.curr_frame].size()-1;
		while(!czastka_wybrana.label.equals(main.tablica[main.curr_frame].get(i).label) && i < zakres)
		{
			i++;
		}
		if (!czastka_wybrana.label.equals(main.tablica[main.curr_frame].get(i).label))
		{
			curr_destroyed = true;
			wyswietl.vx = "Destroyed";
			wyswietl.vy = "Destroyed";
			wyswietl.m = "Destroyed";
			wyswietl.r = "Destroyed";
			wyswietl.type = "Destroyed";
		}
		else 
			{
			wyswietl=main.tablica[main.curr_frame].get(i);
			obecna_index=i;
			curr_destroyed=false;
			}
	}
	
	// obsluguje selekcje po nacisnieciu myszki
    @Override
	public void mousePressed(MouseEvent e)
    {
      main.punkt_x = e.getX();
      main.punkt_y = main.Y - e.getY() + 30;
      czy_wybrana = true;
      
      double[] odleglosc;
      odleglosc = new double[main.tablica[main.curr_frame].size()];
      int i = 0, index = 0;
      czastka odl;
      while(i < main.tablica[main.curr_frame].size())
      {
    	  odl = main.tablica[main.curr_frame].get(i);
    	  odleglosc[i]=Math.sqrt(((odl.x-main.punkt_x)*(odl.x-main.punkt_x))+((odl.y-main.punkt_y)*(odl.y-main.punkt_y)));
    	  i++;
      }
      i = 1;
      while(i < main.tablica[main.curr_frame].size())
      {
    	  if(odleglosc[i] < odleglosc[index]) index = i;
    	  i++;
      }
      odleglosc=null;
      czastka_wybrana=main.tablica[main.curr_frame].get(index);
      Toolbar_selection.uaktualnij_tekst(czastka_wybrana);
      glcanvas.repaint();
    }
    
    
    
    
    @Override
	public void mouseEntered(MouseEvent e) {}
    @Override
	public void mouseExited(MouseEvent e) {}
    @Override
	public void mouseReleased(MouseEvent e) {}
    @Override
	public void mouseClicked(MouseEvent e) {}
    @Override
	public void mouseDragged(MouseEvent e) {}
    @Override
	public void mouseMoved(MouseEvent e) {}
	
	

}