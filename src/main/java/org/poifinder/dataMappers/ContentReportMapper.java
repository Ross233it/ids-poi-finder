package org.poifinder.dataMappers;

public class ContentReportMapper {
    private String content_type;
    private String email;
    private String motivation;
    private String name;
    private String surname;


    public ContentReportMapper(String content_type,
                               String email,
                               String motivation,
                               String name,
                               String surname) {
        this.content_type = content_type;
        this.email = email;
        this.motivation = motivation;
        this.name = name;
        this.surname = surname;
    }

    public String getContent_type() { return content_type; }

    public String getEmail() { return email; }

    public String getMotivation() { return motivation; }

    public String getName() { return name; }

    public String getSurname() { return surname; }
}
