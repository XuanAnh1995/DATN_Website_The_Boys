package backend.datn.controllers;

import backend.datn.dto.ApiResponse;
import backend.datn.dto.request.SleeveCreateRequest;
import backend.datn.dto.request.SleeveUpdateRequest;
import backend.datn.dto.response.SleeveResponse;
import backend.datn.exceptions.EntityAlreadyExistsException;
import backend.datn.exceptions.EntityNotFoundException;
import backend.datn.services.SleeveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sleeve")
public class SleeveController {

    @Autowired
    private SleeveService sleeveService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllSleeve(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        try {
            Page<SleeveResponse> sleeveResponsePage = sleeveService.getAllSleeves(search, page, size, sortBy, sortDir);
            ApiResponse response = new ApiResponse("success", "Get all sleeve successfully", sleeveResponsePage);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse("error", "An error occurred while retrieving the sleeve list", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getSleeveById(@PathVariable int id) {
        try {
            SleeveResponse sleeveResponse = sleeveService.getSleeveById(id);
            ApiResponse response = new ApiResponse("success", "Get sleeve by id successfully", sleeveResponse);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            ApiResponse response = new ApiResponse("error", e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse("error", "An error occurred while retrieving the sleeve", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createSleeve(@RequestBody SleeveCreateRequest sleeveCreateRequest) {
        try {
            SleeveResponse sleeveResponse = sleeveService.createSleeve(sleeveCreateRequest);
            ApiResponse response = new ApiResponse("success", "Create sleeve successfully", sleeveResponse);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (EntityAlreadyExistsException e) {
            ApiResponse response = new ApiResponse("error", e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
        catch (Exception e) {
            ApiResponse response = new ApiResponse("error", "An error occurred while creating the sleeve", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateSleeve(@PathVariable int id, @RequestBody SleeveUpdateRequest sleeveUpdateRequest) {
        try {
            SleeveResponse sleeveResponse = sleeveService.updateSleeve(id, sleeveUpdateRequest);
            ApiResponse response = new ApiResponse("success", "Update sleeve successfully", sleeveResponse);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch(EntityNotFoundException e){
            ApiResponse response = new ApiResponse("error", e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        catch (EntityAlreadyExistsException e) {
            ApiResponse response = new ApiResponse("error", e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse("error", "An error occurred while updating the sleeve", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteSleeve(@PathVariable Integer id){
        try {
            sleeveService.deleteSleeve(id);
            ApiResponse response = new ApiResponse("success", "Delete sleeve successfully", null);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            ApiResponse response = new ApiResponse("error", e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse("error", "An error occurred while deleting the sleeve", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}/toggle-status")
    public ResponseEntity<ApiResponse> toggleStatusSleeve(@PathVariable Integer id){
        try {
            SleeveResponse sleeveResponse = sleeveService.toggleSleeveStatus(id);
            ApiResponse response = new ApiResponse("success", "Toggle status sleeve successfully", sleeveResponse);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            ApiResponse response = new ApiResponse("error", e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse("error", "An error occurred while toggling the status of the sleeve", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
