package backend.datn.controllers;

import backend.datn.dto.ApiResponse;
import backend.datn.dto.request.ColorCreateRequest;
import backend.datn.dto.request.ColorUpdateRequest;
import backend.datn.dto.response.ColorResponse;
import backend.datn.dto.response.SizeResponse;
import backend.datn.exceptions.EntityAlreadyExistsException;
import backend.datn.exceptions.EntityNotFoundException;
import backend.datn.services.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/colors")
public class ColorController {

    @Autowired
    private ColorService colorService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getColorById(@PathVariable Integer id) {
        try {
            ColorResponse colorResponse = colorService.getColorById(id);
            ApiResponse response = new ApiResponse("success", "Color retrieved successfully", colorResponse);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            ApiResponse response = new ApiResponse("error", e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse("error", "An error occurred while retrieving the Color", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping
    public ResponseEntity<ApiResponse> createColor(@RequestBody ColorCreateRequest colorCreateRequest) {
        try {
            ColorResponse colorResponse = colorService.createColor(colorCreateRequest);
            ApiResponse response = new ApiResponse("success", "Color created successfully", colorResponse);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (EntityAlreadyExistsException e) {
            ApiResponse response = new ApiResponse("error", e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse("error", "An error occurred while creating the color", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public ResponseEntity<ApiResponse> getAllColors(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        try {
            Page<ColorResponse> colors = colorService.getAllColors(search, page, size, sortBy, sortDir);
            ApiResponse response = new ApiResponse("success", "Colors retrieved successfully",colors);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse("error", "An error occurred while retrieving the colors", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateColor(@PathVariable Integer id, @RequestBody ColorUpdateRequest colorUpdateRequest) {
        try {
            ColorResponse colorResponse = colorService.updateColor(id, colorUpdateRequest);
            ApiResponse response = new ApiResponse("success", "Color updated successfully", colorResponse);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            ApiResponse response = new ApiResponse("error", e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (EntityAlreadyExistsException e) {
            ApiResponse response = new ApiResponse("error", e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse("error", "An error occurred while updating the color", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteColor(@PathVariable Integer id) {
        try {
            colorService.deleteColor(id);
            ApiResponse response = new ApiResponse("success", "Color deleted successfully", null);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            ApiResponse response = new ApiResponse("error", e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse("error", "An error occurred while deleting the color", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}/toggle-status")
    public ResponseEntity<ApiResponse> toggleStatusColor(@PathVariable Integer id){
        try {
            ColorResponse colorResponse = colorService.toggleColorStatus(id);
            ApiResponse response = new ApiResponse("success", "Toggle status color successfully", colorResponse);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            ApiResponse response = new ApiResponse("error", e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse("error", "An error occurred while toggling the status of the color", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
