package com.devforge.service;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
public final class JsonService {
 private final ObjectMapper mapper=new ObjectMapper();
 public String format(String input,int indent)throws Exception{JsonNode node=parse(input);ObjectWriter writer=mapper.writer(new DefaultPrettyPrinterWithIndent(indent));return writer.writeValueAsString(node);}
 public String minify(String input)throws Exception{return mapper.writeValueAsString(parse(input));}
 public void validate(String input)throws Exception{parse(input);}
 private JsonNode parse(String input)throws Exception{if(input==null||input.isBlank())throw new IllegalArgumentException("Input is empty.");JsonNode node=mapper.reader().with(DeserializationFeature.FAIL_ON_TRAILING_TOKENS).readTree(input);if(node==null)throw new IllegalArgumentException("Input is empty.");return node;}
 private static final class DefaultPrettyPrinterWithIndent extends DefaultPrettyPrinter {private final String spaces;DefaultPrettyPrinterWithIndent(int n){spaces=" ".repeat(Math.max(0,Math.min(8,n)));indentArraysWith(new DefaultIndenter(spaces,"\n"));indentObjectsWith(new DefaultIndenter(spaces,"\n"));}private DefaultPrettyPrinterWithIndent(DefaultPrettyPrinterWithIndent src){super(src);spaces=src.spaces;}@Override public DefaultPrettyPrinter createInstance(){return new DefaultPrettyPrinterWithIndent(this);}}
}
