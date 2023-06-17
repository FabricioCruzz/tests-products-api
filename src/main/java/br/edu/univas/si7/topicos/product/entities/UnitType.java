package br.edu.univas.si7.topicos.product.entities;

public enum UnitType {
	
	 PIECE(1),
	    LENGTH(2),
	    WEIGHT(3),
	    CAPACITY(4);

	    private int code;

	    private UnitType(int code) {
	        this.code = code;
	    }

	    public int getCode() {
	        return code;
	    }

	    public static UnitType fromCode(int code) {
	        for (UnitType unitType : UnitType.values()) {
	            if (unitType.getCode() == code) {
	                return unitType;
	            }
	        }
	        throw new IllegalArgumentException("Invalid UnitType code: " + code);
	    }

}
