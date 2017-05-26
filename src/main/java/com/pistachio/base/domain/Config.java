package com.pistachio.base.domain;

public class Config extends DynaModel
{
    public static int IS_SYSTEM_VALUE = 1;
    public static int IS_NOT_SYSTEM_VALUE = 0;

    public int getId()
    {
        return getInt("id");
    }

    public void setId(int id)
    {
        set("id", id);
    }

    public String getName()
    {
        return getString("name");
    }

    public void setName(String name)
    {
        set("name", name);
    }

    public String getCaption()
    {
        return getString("caption");
    }

    public void setCaption(String caption)
    {
        set("caption", caption);
    }

    public String getValue()
    {
        return getString("cur_value");
    }

    public void setValue(String value)
    {
        set("cur_value", value);
    }

    public String getDescription()
    {
        return getString("description");
    }

    public void setDescription(String description)
    {
        set("description", description);
    }

    public int getIsSystem()
    {
        return getInt("is_system");
    }

    public void setIsSystem(int isSystem)
    {
        set("is_system", isSystem);
    }
}
