package net.watc4.game.states.cutscene;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import net.watc4.game.utils.IRender;
import net.watc4.game.utils.IUpdate;

/** Represents a part of a Cutscene. */
public abstract class CutsceneEvent implements IUpdate, IRender
{
	/** The Cutscene this is a part of. */
	protected CutsceneState cutscene;

	/** Creates a new Cutscene Event.
	 * 
	 * @param cutscene - The parent Cutscene. */
	public CutsceneEvent(CutsceneState cutscene)
	{
		this.cutscene = cutscene;
	}

	/** Called when this Event should start. */
	public void begin()
	{}

	/** @return True if this Event is finished. */
	public abstract boolean isOver();

	/** Called when a key is pressed.
	 * 
	 * @param keyID - The ID of the key.
	 * @see KeyEvent#VK_A */
	public void onKeyPressed(int keyID)
	{}

	@Override
	public void render(Graphics g)
	{}

	@Override
	public void update()
	{}

}
