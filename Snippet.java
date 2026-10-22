package com.devforge.model;
import java.time.Instant;
import java.util.UUID;
public record Snippet(String id, String title, String description, String language, String code, String category, String tags, boolean favorite, Instant createdAt, Instant updatedAt) {
 public static Snippet create(String title,String description,String language,String code,String category,String tags){Instant now=Instant.now();return new Snippet(UUID.randomUUID().toString(),title,description,language,code,category,tags,false,now,now);}
}
