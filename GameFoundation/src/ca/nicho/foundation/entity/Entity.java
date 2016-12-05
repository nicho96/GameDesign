package ca.nicho.foundation.entity;

import java.awt.image.BufferedImage;

import ca.nicho.foundation.Game;
import ca.nicho.foundation.Sprite;

public abstract class Entity {
		
	public int current = 0;
	public Sprite[] sprites;
	public float locX;
	public float locY;
	public int id;
	public boolean isDead = false;
	public boolean detected = false;
	public Entity detector;
	public byte owner = -1;
	public int origHealth;
	public int health;
	public boolean healed;
	public String name = "";
	
	public Entity(float x, float y, int health, Sprite sprite, int id){
		this(x, y, health, new Sprite[1], id);
		sprites[0] = sprite;
	}
	
	public Entity(float x, float y, int health, Sprite[] sprites, int id){
		this.id = id;
		this.sprites = sprites;
		this.locX = x;
		this.locY = y;
		this.origHealth = health;
		this.health = health;
	}
	
	/**
	 * Update the entity. Currently only used by the server
	 * @return true if there is a reason to update the clients with updated information about this entity
	 */
	public int healthTick;
	public int messageTick = 0;
	public boolean tick(){
		if(cooldownTick > 0){
			cooldownTick--;
		}
		
		if(detector == null || detector.isDead){
			detector = null;
			detected = false;
		}
		
		messageTick --;
		if(messageTick < 0){
			messageTick = 0;
		}
		
		boolean ret = false;
		
		if(healthTick == 0 && origHealth > 0){
			ret = this.heal(1);
		}
		healthTick = (healthTick + 1) % 10;
		return ret;
		
		
	}
	
	private int spriteTick = 1;
	public boolean clientTick(){
		if((spriteTick = (spriteTick + 1) % 2) == 0){
			current = (current + 1) % sprites.length;
		}

		if(cooldownTick > 0){
			cooldownTick--;
		}
		
		return false;
	}
	
	
	int cooldownTick = 0;
	public void damage(int amount){
		if(cooldownTick == 0){
			health -= amount;
			if(health <= 0 && origHealth > 0)
				this.isDead = true;
			Game.world.entityDamaged(this);
			cooldownTick = 30;
			if(health < 0){
				sendMessage("Your " + name + " has been destroyed!");
				health = 0;
			}else{
				sendMessage("Your " + name + " is under attack!");
			}
		}
	}
	
	public void sendMessage(String message){
		if(messageTick == 0 && !name.equals("")){
			Game.logger.sendMessage(message, this.owner);
			messageTick = 300;
		}
	}
	
	public void collision(Entity ent){};
	
	public boolean heal(int amount){
		health += amount;
		if(health > this.origHealth / 2 && this.isDead){
			this.isDead = false;
			return true;
		}
		if(health > origHealth){
			health = origHealth;
			return false;
		}
		return true;
	}

}
