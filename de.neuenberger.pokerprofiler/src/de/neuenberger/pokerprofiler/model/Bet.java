package de.neuenberger.pokerprofiler.model;

public class Bet {
	public static final int TYPE_N_A      = -1;
	public static final int TYPE_FOLD     = 1;
	public static final int TYPE_CHECK    = 2;
	public static final int TYPE_CALL     = 3;
	public static final int TYPE_BET      = 4;
	public static final int TYPE_RAISE    = 5;
	public static final int TYPE_ALL_IN   = 6;
	float amount;
	
	int type;

	
	public Bet(int type) {
		this(type,0.0f);
	}
	
	public Bet(int type, float amnt) {
		this.type=type;
		this.amount=amnt;
	}
	
	
	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
}
