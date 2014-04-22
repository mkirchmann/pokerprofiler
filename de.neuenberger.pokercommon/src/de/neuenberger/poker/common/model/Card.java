package de.neuenberger.poker.common.model;

public class Card {

	public Card(final int rank, final int color) {
		this.rank = rank;
		this.color = color;
	}

	public int getColor() {
		return color;
	}

	public int getRank() {
		return rank;
	}

	public String toString() {
		return toString(rank, color);
	}

	public static String toString(final int rank, final int color) {
		return rankName[rank] + colorName[color];
	}

	public static String toString(final int rank) {
		return rankName[rank];
	}

	public int getRandom() {
		return random;
	}

	public void setRandom(final int random) {
		this.random = random;
	}

	public static int getRankFromCharacter(final char c) {
		switch (("" + c).toUpperCase().charAt(0)) {
		case 65: // 'A'
			return 14;

		case 75: // 'K'
			return 13;

		case 81: // 'Q'
			return 12;

		case 74: // 'J'
			return 11;

		case 84: // 'T'
			return 10;

		case 57: // '9'
			return 9;

		case 56: // '8'
			return 8;

		case 55: // '7'
			return 7;

		case 54: // '6'
			return 6;

		case 53: // '5'
			return 5;

		case 52: // '4'
			return 4;

		case 51: // '3'
			return 3;

		case 50: // '2'
			return 2;
		}
		return -1;
	}

	public static int getColorFromCharacter(final char c) {
		switch (("" + c).toLowerCase().charAt(0)) {
		case 115: // 's'
			return 2;

		case 99: // 'c'
			return 3;

		case 104: // 'h'
			return 1;

		case 100: // 'd'
			return 0;
		}
		return -1;
	}

	public static final int ACE = 14;
	public static final int KING = 13;
	public static final int QUEEN = 12;
	public static final int JACK = 11;
	public static final int TEN = 10;
	public static final int NINE = 9;
	public static final int EIGHT = 8;
	public static final int SEVEN = 7;
	public static final int SIX = 6;
	public static final int FIVE = 5;
	public static final int FOUR = 4;
	public static final int THREE = 3;
	public static final int TWO = 2;
	public static final int CLUB = 3;
	public static final int SPADE = 2;
	public static final int HEART = 1;
	public static final int DIAMOND = 0;
	int rank;
	int color;
	int random;
	static final String rankName[] = { "0", "Ace", "2", "3", "4", "5", "6",
			"7", "8", "9", "10", "J", "Q", "K", "A" };
	static final String colorName[] = { "d", "h", "s", "c" };

}