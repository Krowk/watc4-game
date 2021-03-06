package net.watc4.game.map;

import net.watc4.game.display.Animation;

/** A part of the Map. */
public class Tile
{
	/** Its Identifier. Allows better memory usage. */
	public final int id;
	/** True if this Tile is opaque ; i.e. it stops the light. */
	public final boolean isOpaque;
	/** True if this Tile is solid ; i.e. it stops entities. */
	public final boolean isSolid;
	/** Its sprite. */
	public final Animation sprite;

	/** Creates a new Tile.
	 * 
	 * @param id - Its identifier.
	 * @param sprite - Its Sprite. */
	public Tile(int id, Animation sprite, boolean solid, boolean opaque)
	{
		this.id = id;
		this.sprite = sprite;
		this.isSolid = solid;
		this.isOpaque = opaque;
		TileRegistry.registerTile(this);
	}

}
