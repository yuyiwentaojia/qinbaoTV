package com.iqinbao.android.songstv.utils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Description : Gson解析工具
 * Author : hhh
 * Date   : 15/12/17
 */
public class GsonUtil {
    private final static String COMPANYFILTER = "|comId|comName|comNumber|comPhone|comEmail|comLatitude|"
            + "comLongitude|comWebsite|comRegisterTime|comAddress|comEmployeeCount|comUrl|comRemark|";

    private final static String EMPLOYEEFILTER = "|empId|empNumber|empName|empSex|empEmail|empJob|empPassword|"
            + "empPhone|empMac|empPhoneType|empRemark|empClientValid|empDeviceSynchro|";

    private final static String DEPARTMENTFILTER = "|departId|departName|departLevel|departRemark|";

    private static Gson gson = null;

    public static Gson getInstance() {
        if (gson == null) {
            GsonBuilder builder = new GsonBuilder();
            builder.setExclusionStrategies(new ExclusionStrategy() {

                public boolean shouldSkipField(FieldAttributes attr) {

                    boolean b = true;
//					if (attr.getDeclaringClass() == Company.class) {
//						b = COMPANYFILTER.contains("|" + attr.getName() + "|");
//					} else if (attr.getDeclaredClass() == Employee.class) {
//						b = EMPLOYEEFILTER.contains("|" + attr.getName() + "|");
//					} else if (attr.getDeclaredClass() == Department.class) {
//						b = DEPARTMENTFILTER.contains("|" + attr.getName()
//								+ "|");
                    //}

                    return !b;
                }

                public boolean shouldSkipClass(Class<?> clazz) {
                    if (clazz == java.util.Set.class) {
                        return true;
                    }

                    return false;
                }

            });

            builder.setDateFormat("yyyy-MM-dd HH:mm:ss");

            builder.registerTypeAdapter(Time.class, new JsonSerializer<Time>() {
                public JsonElement serialize(Time src, Type typeOfSrc,
                                             JsonSerializationContext context) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                            "HH:mm:ss");

                    return new JsonPrimitive(simpleDateFormat.format(src));
                }
            });

            builder.registerTypeAdapter(Time.class,new JsonDeserializer<Time>() {

                public Time deserialize(JsonElement json,
                                        Type typeOfSrc,
                                        JsonDeserializationContext context)
                        throws JsonParseException {
                    // TODO Auto-generated method stub
                    DateFormat format = new SimpleDateFormat(
                            "HH:mm:ss");
                    if (!(json instanceof JsonPrimitive)) {
                        throw new JsonParseException(
                                "The date should be a string value");
                    }

                    try {
                        Date date = format.parse(json.getAsString());
                        return new Time(date.getTime());
                    } catch (ParseException e) {
                        throw new JsonParseException(e);
                    }

                }

            });
            gson = builder.create();
        }

        return gson;
    }
}
