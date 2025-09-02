package com.dau.cafeteria_portal.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
@Schema(
        name = "Announcement",
        description = "Represents an announcement in the cafeteria portal. It may contain a title, description, and an optional attachment."
)
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(
            description = "Unique identifier for the announcement",
            example = "101"
    )
    private Long announcementId;
    @Schema(
            description = "URL of the attachment (if any) associated with the announcement",
            example = "https://cafeteria.com/files/menu_update.pdf"
    )
    private String attachmentUrl;
    @Schema(
            description = "Title of the announcement",
            example = "New Menu Update"
    )
    private String title;
    @Schema(
            description = "Detailed description of the announcement",
            example = "The cafeteria will be introducing a new set of healthy meal options starting next week."
    )
    private String description;

}
