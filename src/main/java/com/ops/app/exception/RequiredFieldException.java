package com.ops.app.exception;


public class RequiredFieldException extends Exception{

	private static final long serialVersionUID = 5355394586667727786L;

	private String fieldName;

	private String localisedErrorMessage;

	public RequiredFieldException(String fieldName, String localisedErrorMessage) {
		this.fieldName = fieldName;
		this.localisedErrorMessage = localisedErrorMessage;
	}

	public RequiredFieldException(String fieldName) {
		this.fieldName = fieldName;
	}
	@Override
	public String toString() {
		String formatedName = Character.toUpperCase(fieldName.charAt(0))	 + fieldName.substring(1);
		return  formatedName + " " + (localisedErrorMessage != null ? localisedErrorMessage : " cannot be null") ;
	}

}