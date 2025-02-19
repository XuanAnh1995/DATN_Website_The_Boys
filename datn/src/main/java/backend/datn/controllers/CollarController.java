package backend.datn.controllers;

import backend.datn.dto.ApiResponse;
import backend.datn.dto.request.CollarRequest;
import backend.datn.dto.response.CollarResponse;
import backend.datn.exceptions.EntityAlreadyExistsException;
import backend.datn.exceptions.EntityNotFoundException;
import backend.datn.services.CollarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/collars")
public class CollarController {
    @Autowired
    private CollarService collarService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCollarById(@PathVariable Integer id) {
        try {
            CollarResponse collarResponse = collarService.getCollarById(id);
            ApiResponse response = new ApiResponse("success", "Collar retrieved successfully", collarResponse);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            ApiResponse response = new ApiResponse("error", e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse("error", "An error occurred while retrieving the collar", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping
    public ResponseEntity<ApiResponse> createCollar(@RequestBody CollarRequest collarRequest) {
        try {
            CollarResponse collarResponse = collarService.createCollar(collarRequest);
            ApiResponse response = new ApiResponse("success", "Collar created successfully", collarResponse);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (EntityAlreadyExistsException e) {
            ApiResponse response = new ApiResponse("error", e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse("error", "An error occurred while creating the collar", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllCollars(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        try {
            Page<CollarResponse> collars = collarService.getAllCollars(search, page, size, sortBy, sortDir);
            ApiResponse response = new ApiResponse("success", "Collars retrieved successfully", collars);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse("error", "An error occurred while retrieving the collars", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateCollar(@PathVariable Integer id, @RequestBody CollarRequest collarRequest) {
        try {
            CollarResponse collarResponse = collarService.updateCollar(id, collarRequest);
            ApiResponse response = new ApiResponse("success", "Collar updated successfully", collarResponse);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            ApiResponse response = new ApiResponse("error", e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (EntityAlreadyExistsException e) {
            ApiResponse response = new ApiResponse("error", e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse("error", "An error occurred while updating the collar", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCollar(@PathVariable Integer id) {
        try {
            collarService.deleteCollar(id);
            ApiResponse response = new ApiResponse("success", "Collar deleted successfully", null);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            ApiResponse response = new ApiResponse("error", e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse("error", "An error occurred while deleting the collar", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
