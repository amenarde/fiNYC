package finra;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;
  
public class Zoom
{
    
}
  
@SuppressWarnings("serial")
class ImagePanel extends JPanel
{
    BufferedImage image;
    double scale;
    boolean pickedCoords;
  
    public ImagePanel() throws IOException
    {
        loadImage();
        scale = 1.0;
        setBackground(Color.black);
        connectToMouse();
        pickedCoords = false;
    }
    
    protected void connectToMouse() {
    	File f = new File("coords.txt");
    	try {
        f.createNewFile();
    	} catch (IOException io) {
    		System.out.println("error making new file in Zoom.java");
    	}
    	this.addMouseListener(new MouseInputAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				try { 
					if (!pickedCoords) {
						pickedCoords = true;
						BufferedWriter coordWriter = new BufferedWriter(new FileWriter(f));
						int x = (int) (e.getX() / scale);
						int y = (int) (e.getY() / scale);
						coordWriter.write(Integer.toString(x));
						coordWriter.newLine();
						coordWriter.write(Integer.toString(y));
						coordWriter.close();
						System.out.println("(" + x + ", " + y + ")");
					} else {
						// do nothing
					}
				} catch (IOException io) {
					System.out.println("error connecting MouseListener in Zoom.java");
				}
				Main.finishedChoosingCoords = true;
			}
		});
    }
  
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                            RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        int w = getWidth();
        int h = getHeight();
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();
        double x = (w - scale * imageWidth)/2;
        double y = (h - scale * imageHeight)/2;
        AffineTransform at = AffineTransform.getTranslateInstance(x,y);
        at.scale(scale, scale);
        g2.drawRenderedImage(image, at);
    }
  
    /**
     * For the scroll pane.
     */
    public Dimension getPreferredSize()
    {
        int w = (int)(scale * image.getWidth());
        int h = (int)(scale * image.getHeight());
        return new Dimension(w, h);
    }
  
    public void setScale(double s)
    {
        scale = s;
        revalidate();      // update the scroll pane
        repaint();
    }
  
    private void loadImage()
    {
        String fileName = "images/NYC_portrait.jpg";
        try
        {
            image = ImageIO.read(new File (fileName));
        }
        catch(MalformedURLException mue)
        {
            System.out.println("URL trouble: " + mue.getMessage());
        }
        catch(IOException ioe)
        {
            System.out.println("read trouble: " + ioe.getMessage());
        }
    }
}
  
class ImageZoom
{
    ImagePanel imagePanel;
  
    public ImageZoom(ImagePanel ip)
    {
        imagePanel = ip;
    }
  
    public JPanel getUIPanel()
    {
    	// default, min, max, incr
        SpinnerNumberModel model = new SpinnerNumberModel(1.0, 0.1, 4.0, 0.15);
        final JSpinner spinner = new JSpinner(model);
        spinner.setPreferredSize(new Dimension(60, spinner.getPreferredSize().height));
        spinner.addChangeListener(new ChangeListener()
        {
            public void stateChanged(ChangeEvent e)
            {
                float scale = ((Double)spinner.getValue()).floatValue();
                imagePanel.setScale(scale);
            }
        });
        JPanel panel = new JPanel();
        JLabel label = new JLabel("scale");
        panel.add(label);
        panel.add(spinner);
        return panel;
    }
}