package de.szut.lf8_starter.project;

import de.szut.lf8_starter.employee.EmployeeProjectEntity;
import de.szut.lf8_starter.employee.EmployeeProjectService;
import de.szut.lf8_starter.employee.EmployeeService;
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
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "project")
@PreAuthorize("hasAnyAuthority('user')")
public class ProjectController {

    private final ProjectService service;
    private final EmployeeService employeeService;
    private final EmployeeProjectService employeeProjectService;
    private final ProjectMapper projectMapper;

    public ProjectController(ProjectService service, ProjectMapper projectMapper, EmployeeService employeeService, EmployeeProjectService employeeProjectService) {
        this.service = service;
        this.projectMapper = projectMapper;
        this.employeeService = employeeService;
        this.employeeProjectService = employeeProjectService;
    }

    @Operation(summary = "creates a new project with its id and project information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "created project",
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
    public ProjectGetDto getProjectById(@PathVariable Long id) {
        ProjectEntity entity = this.service.readById(id);
        if (entity == null) {
            throw new ResourceNotFoundException("ProjectEntity not found on id = " + id);
        } else {
            ProjectGetDto projectGetDto = this.projectMapper.mapToGetDto(entity);

            return projectGetDto;
        }
    }

    @Operation(summary = "deletes a Project by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "delete successful"),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "resource not found",
                    content = @Content)})
    @DeleteMapping("/{projectId}/employee/{employeeId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void RemoveEmployeeFromProject(@PathVariable("projectId") long projectId,
                                          @PathVariable("employeeId") long employeeId){
        EmployeeProjectEntity entity = employeeProjectService.getEmployeeProjectByEmployeeIdAndProjectId(employeeId, projectId);
        if (entity == null) {
            throw new ResourceNotFoundException("Ressource not found");
        } else {
            this.employeeProjectService.delete(entity);
        }
    }
}

