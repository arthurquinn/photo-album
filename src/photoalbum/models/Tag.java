package photoalbum.models;

/**
 * Describes the tags that are associated with a given Photo object
 * @author Stephen Eisen
 * @author Arthur Quintanilla
 */

import java.io.Serializable;

public class Tag implements Serializable
{
	private static final long serialVersionUID = -1051996609054126747L;

	private String tagType;
	
	private String tagValue;
	
	public Tag(String type, String value)
	{
		this.tagType = type;
		this.tagValue = value;
	}
	
	public void setType(String type)
	{
		this.tagType = type;
	}
	
	public void setValue(String value)
	{
		this.tagValue = value;
	}
	
	public String getType()
	{
		return this.tagType;
	}
	
	public String getValue()
	{
		return this.tagValue;
	}
}
