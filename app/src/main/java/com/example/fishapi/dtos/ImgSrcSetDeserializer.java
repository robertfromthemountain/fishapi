package com.example.fishapi.dtos;

import com.google.gson.*;

import java.lang.reflect.Type;

public class ImgSrcSetDeserializer implements JsonDeserializer<ImgSrcSet> {

    @Override
    public ImgSrcSet deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonObject()) {
            JsonObject jsonObject = json.getAsJsonObject();
            String imgSrc1_5x = jsonObject.has("1.5x") ? jsonObject.get("1.5x").getAsString() : null;
            String imgSrc2x = jsonObject.has("2x") ? jsonObject.get("2x").getAsString() : null;

            return new ImgSrcSet(imgSrc1_5x, imgSrc2x);
        } else {
            return new ImgSrcSet("default_1.5x", "default_2x");
        }
    }
}
