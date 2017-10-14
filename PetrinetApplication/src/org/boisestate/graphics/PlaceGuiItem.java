package org.boisestate.graphics;

import java.awt.Color;
import java.awt.Graphics;


public class PlaceGuiItem implements GuiItemInterface{
	private Color fillColor;
	private Color borderColor;
	private int xPos = -50;
    private int yPos = -50;
    private int width = 30;
    private int height = 40;
    private int nameHeight = 10;
    private String name = "";
    
    public PlaceGuiItem(String name)
    {
    	this.name = name;
    	this.fillColor = Color.GRAY;
    	this.borderColor = Color.BLACK;
    } 
    
    @Override
    public void setX(int xPos){ 
        this.xPos = xPos;
    }

    @Override
    public int getX(){
        return xPos;
    }

    @Override
    public void setY(int yPos){
        this.yPos = yPos;
    }

    @Override
    public int getY(){
        return yPos;
    }

   
    
    @Override
	public void setName(String name) {
		this.name = name;
		
	}
	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public void setBorderColor(Color color) {
		this.borderColor = color;
		
	}

	@Override
	public void setFillColor(Color color) {
		this.fillColor = color;
		
	}
	
	public void setWidth(int w){
        this.width =w;
    } 
	
	public int getWidth(){
        return width;
    } 

	public void setHight(int h){
        this.height =h;
    }
	
	public int getHeight(){
        return height;
    }
	    

   
    public void draw(Graphics g){
        g.setColor(Color.white);
        g.fillOval(xPos, yPos, width, height-nameHeight);
        g.setColor(Color.BLACK);
        g.drawOval(xPos,yPos,width,height-nameHeight); 
        g.drawString(name, xPos+10+2, yPos+height);
    }
}
