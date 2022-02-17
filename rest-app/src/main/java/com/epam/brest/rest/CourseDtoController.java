package com.epam.brest.rest;

import com.epam.brest.dao.CourseDaoJDBCImpl;
import com.epam.brest.model.Course;
import com.epam.brest.model.dto.CourseDto;
import com.epam.brest.service.CourseDtoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@Tag(name="Course DTO")
public class CourseDtoController {
    private static final Logger logger = LogManager.getLogger(CourseDaoJDBCImpl.class);

    private final CourseDtoService courseDtoService;

    public CourseDtoController(CourseDtoService courseDtoService) {
        this.courseDtoService = courseDtoService;
    }



    @GetMapping(value = "/course-dtos")
    @Operation(summary = "Find all and count student", responses = {
            @ApiResponse(description = "Find all and count student success", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Course.class)))
    })
    public final Collection<CourseDto> courseDtos() {

        logger.debug("courseDtos()");
        return courseDtoService.findAllWithCountStudent();
    }
}
