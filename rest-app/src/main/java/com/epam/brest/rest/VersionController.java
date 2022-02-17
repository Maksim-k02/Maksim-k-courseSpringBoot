package com.epam.brest.rest;


import com.epam.brest.model.Course;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name="Version")
public class VersionController {
    private final static String VERSION="1.0.0";

    @GetMapping("/version")
    @Operation(summary = "Return version", responses = {
            @ApiResponse(description = "Return version success", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Course.class)))
    })
    public String version(){
        return VERSION;
    }
}
