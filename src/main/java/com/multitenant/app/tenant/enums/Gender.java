package com.multitenant.app.tenant.enums;

/**
 * Gender
 *
 * Enum representing supported
 * gender types in the system.
 *
 * Used to map API string values
 * to compact numeric values
 * stored in the database.
 */
public enum Gender {

	/* Male gender */
	MALE((byte) 0),

	/* Female gender */
	FEMALE((byte) 1),

	/* Other / unspecified gender */
	OTHER((byte) 2);

	/* Database stored value */
	private final byte value;

	Gender(byte value) {
		this.value = value;
	}

	/**
	 * Returns database numeric value.
	 */
	public byte getValue() {
		return value;
	}

	/**
	 * Converts API input string
	 * into database numeric value.
	 *
	 * Example:
	 * male -> 0
	 * female -> 1
	 * other -> 2
	 */
	public static Byte fromString(String input) {
		if (input == null)
			return null;
		return switch (input.toUpperCase()) {
			case "MALE" -> MALE.value;
			case "FEMALE" -> FEMALE.value;
			case "OTHER" -> OTHER.value;
			default -> null;
		};
	}

	/**
	 * Converts database value
	 * into Gender enum.
	 */
	public static Gender fromValue(Byte value) {
		if (value == null)
			return null;
		for (Gender gender : values()) {
			if (gender.value == value)
				return gender;
		}
		throw new IllegalArgumentException("Invalid gender value: " + value);
	}

}