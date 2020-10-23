package be.nabu.libs.artifacts.api;

import java.util.List;

public interface ExceptionDescription {
	
	public enum ExceptionType {
		// an exception that follows from a design flaw, this can generally only be fixed by fixing the code and redeploying
		DESIGN,
		// an (unintentional) technical runtime exception that is thrown by the system
		// this is a catch all for any exception that is not further specified, it could be an external system, a transient problem, a fundamental design issue (that is not flagged as such) etc
		TECHNICAL,
		// an intentional exception thrown as part of the business requirements
		BUSINESS
	}
	
	// codes are not guaranteed unique, they can happen in different circumstances with different messages/descriptions
	// if you add an id, we can make sure we take every exception only once
	public String getId();
	public String getCode();
	public String getMessage();
	public String getDescription();
	// a context explaining where it occurs (if relevant)
	public List<String> getContext();
	public boolean isTransient();
	public ExceptionType getType();
}
