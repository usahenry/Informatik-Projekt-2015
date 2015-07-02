package de.duererInfoProject.fruitNinja;

import java.awt.Graphics2D;

public class Fruit extends Item {
	
	public static final int ItemTypeID = 1;
	
	public Fruit(int x, Game game, Universe universe) {
		super(x, game, universe);
	}
	
	public int getItemTypeID() {
		return ItemTypeID;
	}
}
