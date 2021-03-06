package net.watc4.game.entity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import net.watc4.game.map.Map;

/** Registers all Entity types and spawns required Entities. */
public final class EntityRegistry
{

	/** Sorts Entities by ID. */
	private static HashMap<Integer, Class<? extends Entity>> entities;

	/** Contains description of the Entity's parameters by class. */
	private static HashMap<Class<? extends Entity>, String[]> arguments;

	/** Creates and registers all Entities. */
	public static void createEntities()
	{
		entities = new HashMap<Integer, Class<? extends Entity>>();
		registerEntity(0, EntityLumi.class);
		registerEntity(1, EntityPattou.class);
		registerEntity(2, EntityBattery.class);
		registerEntity(3, EntityCutscene.class);
	}

	/** Registers all the parameters. Note : X and Y are considered as unsigned int for the editor. */
	public static void defineEntities()
	{
		arguments = new HashMap<Class<? extends Entity>, String[]>();
		arguments.put(EntityLumi.class, new String[]
		{ "X", "unsigned int", "Y", "unsigned int" });
		arguments.put(EntityPattou.class, new String[]
		{ "X", "unsigned int", "Y", "unsigned int" });
		arguments.put(EntityBattery.class, new String[]
		{ "X", "unsigned int", "Y", "unsigned int", "Buffer", "unsigned int", "Max Power", "unsigned int" });
		arguments.put(EntityCutscene.class, new String[]
		{ "X", "unsigned int", "Y", "unsigned int", "Tile Width", "unsigned int", "Tile Heigth", "unsigned int", "Cutscene Name", "string" });
	}

	/** Creates the adequate arguments then spawns an Entity.
	 * 
	 * @param map - The Map to spawn the Entity in.
	 * @param id - The ID of the Entity.
	 * @param values - The data from the map file.
	 * @return The spawned Entity. */
	@SuppressWarnings("unchecked")
	public static Entity createEntity(Map map, int id, String[] values)
	{
		Object[] arguments = new Object[values.length];
		arguments[0] = map.game;
		arguments[1] = Float.parseFloat(values[1]) * Map.TILESIZE;
		arguments[2] = Float.parseFloat(values[2]) * Map.TILESIZE;

		Constructor<Entity> constructor = (Constructor<Entity>) entities.get(Integer.parseInt(values[0])).getConstructors()[0];

		for (int i = 3; i < arguments.length; i++)
		{
			if (constructor.getParameters()[i].getType().toString().equals("int")) arguments[i] = Integer.parseInt(values[i]);
			else if (constructor.getParameters()[i].getType().toString().equals("float")) arguments[i] = Float.parseFloat(values[i]);
			else arguments[i] = values[i];
		}

		return spawnEntity(map, id, arguments);
	}

	/** @return The list of Entity types, sorted by ID. (0 -> size-1) */
	public static HashMap<Integer, Class<? extends Entity>> getEntities()
	{
		return entities;
	}

	/** @return The list of Entity definitions sorted by ID. (0 -> size-1) */
	public static HashMap<Class<? extends Entity>, String[]> getDefinitions()
	{
		return arguments;
	}

	/** Registers the target Entity.
	 * 
	 * @param id - The ID of the Entity.
	 * @param entityClass - The Class of the Entity to register. */
	private static void registerEntity(int id, Class<? extends Entity> entityClass)
	{
		if (!entities.containsKey(id)) entities.put(id, entityClass);
	}

	/** Spawns an Entity.
	 * 
	 * @param map - The Map to spawn the Entity in.
	 * @param id - The ID of the Entity to spawn.
	 * @param arguments - The Arguments to spawn the Entity. Always start with GameState, xPos, yPos. See theEntity's constructor for additionnal arguments.
	 * @return The spawned Entity. */
	public static Entity spawnEntity(Map map, int id, Object... arguments)
	{
		if (entities.containsKey(id)) try
		{
			Entity entity = (Entity) entities.get(id).getConstructors()[0].newInstance(arguments);
			map.entityManager.registerEntity(entity);
			return entity;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException e)
		{
			e.printStackTrace();
		}
		return null;
	}

}
