package org.neutrinocms.core.conf;

import java.io.IOException;

import org.apache.commons.lang.StringEscapeUtils;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.NonTypedScalarSerializerBase;

public class SanitizedStringSerializer extends NonTypedScalarSerializerBase<String> {

    public SanitizedStringSerializer() { 
        super(String.class); 
    }

    @Override
    public void serialize(String value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonGenerationException {
        jgen.writeRawValue("\"" + StringEscapeUtils.escapeHtml(value) + "\"");
    }
}