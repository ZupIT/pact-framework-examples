package br.com.vinirib.pact.consumer.client.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import lombok.experimental.SuperBuilder;
import springfox.documentation.spring.web.json.Json;

import java.lang.reflect.Type;

@SuperBuilder
public class SpringfoxJsontoGsonAdapter implements JsonSerializer<Json> {

    @Override
    public JsonElement serialize(Json json, Type type, JsonSerializationContext jsonSerializationContext) {
        final JsonParser jsonParser = new JsonParser();
        return jsonParser.parse(json.value());
    }
}
