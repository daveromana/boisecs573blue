package org.boisestate.graphics;

import java.awt.Button;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.List;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.boisestate.petrinet.Petrinet;
import org.boisestate.petrinet.Place;

class DrawingPanel extends JPanel {
	

		
	TransitionGuiItem transition = new TransitionGuiItem();

	public int placeCount = 0;
	int transitionCount = 0;
	Object selectedItem;
	
   Petrinet petrinet;

   
	public DrawingPanel(final Petrinet petrinet) {
		this.petrinet = petrinet;
        setBorder(BorderFactory.createLineBorder(Color.black));
        addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e){
            	if(e.getButton() == MouseEvent.BUTTON1) {
            		if(MainPanel.currentState == MainPanel.currentState.PLACE) {
            			System.out.println("mouse event occured...."+petrinet.placeVector.size());
            			Place place = new Place();
                		drawPlace(place, e.getX(),e.getY());                		
                	}else if (MainPanel.currentState == MainPanel.currentState.TRANSITION){
                		drawTransition(e.getX(),e.getY());
                	}
                	else if (MainPanel.currentState == MainPanel.currentState.COPY){
                		Object obj = petrinet.selectedPlace(e.getX(), e.getY());
                		selectedItem = obj;
                	}else if (MainPanel.currentState == MainPanel.currentState.PASTE){
                		if(selectedItem!=null && selectedItem instanceof Place) {
                			Place place = (Place) selectedItem;
                			drawPlace(place, e.getX(), e.getY());
                		}
                	}else if (MainPanel.currentState == MainPanel.currentState.DELETE){
                		Object obj = petrinet.selectedPlace(e.getX(), e.getY());
                		selectedItem = obj;
                		if(selectedItem!=null && selectedItem instanceof Place) {
                			System.out.println("delete called");
                			Place place = (Place) selectedItem;
                			petrinet.deletePlace(place);
                			repaint();
                		}
                	}
            	}
            	if(e.getButton() == MouseEvent.NOBUTTON)
            		System.out.println("button 0");
            	if(e.getButton() == MouseEvent.BUTTON2)
            		System.out.println("button 2");
            	if(e.getButton() == MouseEvent.BUTTON3) {
            		Object obj = petrinet.selectedPlace(e.getX(), e.getY());
            		selectedItem = obj;
            		if(selectedItem!=null && selectedItem instanceof Place) {

            			Place place = (Place) selectedItem;
            			
            			JTextField name = new JTextField();
            			JTextField numberOfTokens = new JTextField();
            			name.setText(place.getName());
            			numberOfTokens.setText(Integer.toString(place.getTokenNumbers()));
            			Object[] message = {
            			    "Name:", name,
            			    "NumberOfTokens:", numberOfTokens
            			};

            			int option = JOptionPane.showConfirmDialog(null, message, "Input", JOptionPane.OK_CANCEL_OPTION);
            			if (option == JOptionPane.OK_OPTION) {
            			    if(name.getText()!=null){
            			    	for(int i=0; i< petrinet.placeVector.size(); i++) {
            			    		Place place1 = petrinet.placeVector.get(i);
            			    		if(place1!=place && place1.getName().equals(name.getText())) {
            			    			final JPanel panel = new JPanel();
            			    		    JOptionPane.showMessageDialog(panel, "Duplicate name error.", "Error", JOptionPane.ERROR_MESSAGE);
            			    			break;
            			    		}else {
            			    			if(i==petrinet.placeVector.size()-1){
                        			    	place.setName(name.getText());
                        			    	if(numberOfTokens.getText()!=null){
                            			    	place.setTokenNumbers(Integer.parseInt(numberOfTokens.getText()));
                            			    }
                            			    repaint(place.getX(), place.getY(), 
                                            		place.getRadius()+1, 
                                            		place.getHeight()+10+2);
            			    			}else continue;
            			    		}
            			    	}
            			    }
            			    
            			} else {
            			    System.out.println("Not Changed.");
            			}
            			
            		}
            		
            	}
            		
            	
                
            }
            
            
        });
        
       
        

//        addMouseMotionListener(new MouseAdapter(){
//            public void mouseDragged(MouseEvent e){
//            	if(MainPanel.currentState == MainPanel.currentState.PLACE) {
//            		drawPlace(e.getX(),e.getY());
//            	}else if (MainPanel.currentState == MainPanel.currentState.TRANSITION){
//            		drawTransition(e.getX(),e.getY());
//            	}
//            }
//        });

    }
   
	private void existingPlaceNameCheck() {
		placeCount++;
		String name = "P" + placeCount;
		for(Place place: petrinet.placeVector) {
			if(place.getName().equals(name)) {
				existingPlaceNameCheck();
			}
		}
		
	}
    private void drawPlace(Place place, int x, int y){
    	existingPlaceNameCheck();
    	place.setX(x);
    	place.setY(y);
    	place.setName("P"+placeCount);
        repaint(place.getX(), place.getY(), 
        		place.getRadius()+1, 
        		place.getHeight()+10+2);
       
        MainPanel.placeCoordinator.add(x + "," + y);
		System.out.println("Current placeCoordinator array list is:"+MainPanel.placeCoordinator);
		
		petrinet.addPlace(place);
		

    }
    private void drawTransition(int x, int y){
    	transitionCount ++;
    	transition.setX(x);
    	transition.setY(y);
    	transition.setName("T" + transitionCount);
        // Repaint the square at the new location.
        repaint(transition.getX(), transition.getY(), 
        		transition.getWidth()+1, 
        		transition.getHeight()+10+2);
        
        MainPanel.transitionCoordinator.add(x + "," + y);
		System.out.println("Current transitionCoordinator array list is:"+MainPanel.transitionCoordinator);
	
    }

    public Dimension getPreferredSize() {
    	 Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

         int height = screenSize.height * 3 / 4;
         int width = screenSize.width * 5/6;

        return new Dimension(width,height);
    }
    public void paintAgain(){
    	repaint();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g); 
        System.out.println("place count :"+petrinet.placeVector.size());
        for(PlaceGuiItem place : petrinet.placeVector){
        	place.draw(g);
        	System.out.println(place.getX() + "  "+ place.getY());
        }
        	
        transition.draw(g);
    }  
}