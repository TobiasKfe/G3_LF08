package de.szut.lf8_starter.project;

import de.szut.lf8_starter.exceptionHandling.ResourceNotFoundException;
import de.szut.lf8_starter.project.dto.ProjectCreateDto;
import de.szut.lf8_starter.project.dto.ProjectGetDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.hibernate.annotations.Array;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "project")
@PreAuthorize("hasAnyAuthority('user')")
public class ProjectController {

    private final ProjectService service;
    private final ProjectMapper projectMapper;

    public ProjectController(ProjectService service, ProjectMapper projectMapper) {
        this.service = service;
        this.projectMapper = projectMapper;
    }

    @Operation(summary = "creates a new project with its id and project information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "created project",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProjectGetDto.class))}),
            @ApiResponse(responseCode = "400", description = "invalid JSON posted",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content)})
    @PostMapping
    public ProjectGetDto create(@RequestBody @Valid ProjectCreateDto projectCreateDto) {
        ProjectEntity projectEntity = this.projectMapper.mapCreateDtoToEntity(projectCreateDto);
        projectEntity = this.service.create(projectEntity);
        return this.projectMapper.mapToGetDto(projectEntity);
    }

    @Operation(summary = "get all projects")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of Projects",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ProjectGetDto.class)))}),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "invalid JSON posted",
                    content = @Content)})
    @GetMapping
    public List<ProjectGetDto> getAll() {
        List<ProjectGetDto> projectGetDtos = new ArrayList<>();

        List<ProjectEntity> projectEntities = this.service.getAll();
        projectEntities.forEach(x -> projectGetDtos.add(this.projectMapper.mapToGetDto(x)));

        return projectGetDtos;
    }

    @Operation(summary = "deletes a Project by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "delete successful"),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "resource not found",
                    content = @Content)})
    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteProjectById(@PathVariable long id) {
        ProjectEntity entity = this.service.readById(id);
        if (entity == null) {
            throw new ResourceNotFoundException("ProjectEntity not found on id = " + id);
        } else {
            this.service.delete(entity);
        }
    }


    @Operation(summary = "finds a project by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "delivers project by id",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProjectGetDto.class))}),
            @ApiResponse(responseCode = "400", description = "invalid JSON posted",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "resource not found",
                    content = @Content)})
    @GetMapping("/{id}")
    public ProjectEntity getProjectById(@PathVariable Long id) {
        ProjectEntity entity = this.service.readById(id);
        if(entity == null){
            throw new ResourceNotFoundException("ProjectEntity not found on id = " + id);
        } else {
            return entity;
        }
    }

    @Operation(summary = "updates a project")
    @ApiResponses(value =
    @ApiResponse(responseCode = "201",
            description = "created project",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProjectGetDto.class)
            )}
    )
    )
    @PutMapping
    public ProjectGetDto update(@Valid @RequestBody ProjectGetDto projectGetDto){
        ProjectEntity newProject = this.projectMapper.mapGetDtoToEntity(projectGetDto);
        newProject = this.service.update(newProject);
        return this.projectMapper.mapToGetDto(newProject);
    }

}
