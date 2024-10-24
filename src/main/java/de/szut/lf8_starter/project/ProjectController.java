package de.szut.lf8_starter.project;

import de.szut.lf8_starter.employee.EmployeeProjectEntity;
import de.szut.lf8_starter.employee.EmployeeProjectService;
import de.szut.lf8_starter.employee.EmployeeProjectsResponseDto;
import de.szut.lf8_starter.employee.EmployeeService;
import de.szut.lf8_starter.employee.*;
import de.szut.lf8_starter.exceptionHandling.ResourceNotFoundException;
import de.szut.lf8_starter.project.dto.ProjectCreateDto;
import de.szut.lf8_starter.project.dto.ProjectDetailsGetDto;
import de.szut.lf8_starter.project.dto.ProjectGetDto;
import de.szut.lf8_starter.qualification.QualificationDto;
import de.szut.lf8_starter.qualification.QualificationService;
import de.szut.lf8_starter.qualification.QualificationValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/project")
@PreAuthorize("hasAnyAuthority('user')")
public class ProjectController {

    private final ProjectService service;
    private final EmployeeService employeeService;
    private final EmployeeProjectService employeeProjectService;
    private final ProjectMapper projectMapper;
    private final QualificationService qualificationService;
    private final QualificationValidator qualificationValidator = new QualificationValidator();
    private final EmployeeValidator employeeValidator;

    public ProjectController(ProjectService service,
                             ProjectMapper projectMapper,
                             QualificationService qualificationService,
                             EmployeeService employeeService,
                             EmployeeProjectService employeeProjectService,
                             EmployeeValidator employeeValidator)
    {
        this.service = service;
        this.projectMapper = projectMapper;
        this.employeeService = employeeService;
        this.employeeProjectService = employeeProjectService;
        this.qualificationService = qualificationService;
        this.employeeValidator = employeeValidator;
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

    @Operation(summary = "finds a project by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "assigns an employee with a qualification to an project",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = QualificationDto.class))})
    })
    @PostMapping("/{projectId}/{qualificationId}/{employeeId}")
    public void assignAnEmployeeToAProjectWithSpecificQualification(@PathVariable long pId,@PathVariable long qId,@PathVariable long eId) {

    }

    @Operation(summary = "finds projects by employeeId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "delivers projects by employeeId",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ProjectGetDto.class)))}),
            @ApiResponse(responseCode = "204", description = "no content available for the given employee id",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "invalid JSON posted",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "resource not found",
                    content = @Content)})
    @GetMapping("/employee/{employeeId}/projects")
    public List<ProjectDetailsGetDto> getProjectsByEmployeeId(@PathVariable Long employeeId) {
        List<EmployeeProjectEntity> entities = employeeProjectService.getAllProjectsByEmployeeId(employeeId);

        if (entities == null || entities.isEmpty()) {
           throw new ResourceNotFoundException("Resource not found");
        }

        // Mappen auf ProjectDetailsGetDto
        List<ProjectDetailsGetDto> projectDtos = new ArrayList<>();
        for (EmployeeProjectEntity entity : entities) {
            ProjectDetailsGetDto dto = projectMapper.mapToDetailsDto(entity);
            projectDtos.add(dto);
        }

        return projectDtos;
    }

    @GetMapping("/testSkillset/{id}")
    public boolean testSkillset(@PathVariable long id, @RequestHeader("Authorization") String authorizationHeader){
        return employeeValidator.isSkillsetValid(id, authorizationHeader);}


@GetMapping("/test/{id}")
public boolean test(@PathVariable long id, @RequestHeader("Authorization") String authorizationHeader){
        return employeeValidator.doesEmployeeExist(id, authorizationHeader);}
}


