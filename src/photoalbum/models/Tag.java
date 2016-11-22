package photoalbum.models;

import java.io.Serializable;

/**
 * Describes the tags that are associated with a given Photo object
 * @author Stephen Eisen
 * @author Arthur Quintanilla
 */

public class Tag implements Serializable
{
	/**
	 * The serial UID associated with album objects
	 */
	private static final long serialVersionUID = -1051996609054126747L;

	/**
	 * The tag type associated with this tag object
	 */
	private String tagType;
	
	/**
	 * The tag value associated with this tag object
	 */
	private String tagValue;
	
	/**
	 * Constructs a Tag object
	 * @param type The type to be associated with this tag object
	 * @param value The value to be associated with this tag object
	 */
	public Tag(String type, String value)
	{
		this.tagType = type;
		this.tagValue = value;
	}
	
	/**
	 * Sets the tag type for this tag object
	 * @param type The new desired type for this tag
	 */
	public void setType(String type)
	{
		this.tagType = type;
	}
	
	/**
	 * Sets the tag value for this tag object
	 * @param value The new desired value for this tag
	 */
	public void setValue(String value)
	{
		this.tagValue = value;
	}
	
	/**
	 * Gets the current tag type for this object
	 * @return The current type for this tag
	 */
	public String getType()
	{
		return this.tagType;
	}
	
	/**
	 * Gets the current tag value for this object
	 * @return The current value for this tag
	 */
	public String getValue()
	{
		return this.tagValue;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString()
	{
		return String.format("%s: %s", this.tagType, this.tagValue);
	}
}
