package de.neuenberger.pokerprofiler.model;

public class PlayerShowDownSummary {
	int numberOfHighCard;
	int numberOfFullHouse;
	int numberOfPairs;
	int numberOfTwoPair;
	int numberOfTriplet;
	int numberOfQuads;
	int numberOfFlush;
	int numberOfStraight;
	int numberOfStraightFlush;
	
	public PlayerShowDownSummary() {
		
	}
	
	public int getNumberOfFlush() {
		return numberOfFlush;
	}
	public void setNumberOfFlush(int numberOfFlush) {
		this.numberOfFlush = numberOfFlush;
	}
	public int getNumberOfFullHouse() {
		return numberOfFullHouse;
	}
	public void setNumberOfFullHouse(int numberOfFullHouse) {
		this.numberOfFullHouse = numberOfFullHouse;
	}
	public int getNumberOfHighCard() {
		return numberOfHighCard;
	}
	public void setNumberOfHighCard(int numberOfHighCard) {
		this.numberOfHighCard = numberOfHighCard;
	}
	public int getNumberOfPairs() {
		return numberOfPairs;
	}
	public void setNumberOfPairs(int numberOfPairs) {
		this.numberOfPairs = numberOfPairs;
	}
	public int getNumberOfQuads() {
		return numberOfQuads;
	}
	public void setNumberOfQuads(int numberOfQuads) {
		this.numberOfQuads = numberOfQuads;
	}
	public int getNumberOfStraight() {
		return numberOfStraight;
	}
	public void setNumberOfStraight(int numberOfStraight) {
		this.numberOfStraight = numberOfStraight;
	}
	public int getNumberOfStraightFlush() {
		return numberOfStraightFlush;
	}
	public void setNumberOfStraightFlush(int numberOfStraightFlush) {
		this.numberOfStraightFlush = numberOfStraightFlush;
	}
	public int getNumberOfTriplet() {
		return numberOfTriplet;
	}
	public void setNumberOfTriplet(int numberOfTriplet) {
		this.numberOfTriplet = numberOfTriplet;
	}
	public int getNumberOfTwoPair() {
		return numberOfTwoPair;
	}
	public void setNumberOfTwoPair(int numberOfTwoPair) {
		this.numberOfTwoPair = numberOfTwoPair;
	}
	
	public int getSumOfAll() {
		return this.numberOfHighCard
				+this.numberOfPairs
				+this.numberOfTwoPair
				+this.numberOfTriplet
				+this.numberOfStraight
				+this.numberOfFlush
				+this.numberOfFullHouse
				+this.numberOfQuads
				+this.numberOfStraightFlush;
	}
}
