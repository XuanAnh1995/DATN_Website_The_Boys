package backend.datn.controllers;

import backend.datn.dto.ApiResponse;
import backend.datn.dto.request.MaterialCreateRequest;
import backend.datn.dto.request.MaterialUpdateRequest;
import backend.datn.dto.response.MaterialResponse;
import backend.datn.exceptions.EntityAlreadyExistsException;
import backend.datn.exceptions.EntityNotFoundException;
import backend.datn.services.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/material")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllMaterial(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        try {
            Page<MaterialResponse> materialResponsePage = materialService.getAllMaterials(search, page, size, sortBy, sortDir);
            ApiResponse response = new ApiResponse("success", "Material retrieved successfully", materialResponsePage);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch(Exception e){
            ApiResponse response = new ApiResponse("error", "An error occurred while retrieving the material", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getMaterialById(@PathVariable Integer id){
        try{
            MaterialResponse materialResponse = materialService.getMaterialById(id);
            ApiResponse response = new ApiResponse("success", "Material retrieved successfully", materialResponse);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (EntityNotFoundException e){
            ApiResponse response = new ApiResponse("error", e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e){
            ApiResponse response = new ApiResponse("error", "An error occurred while retrieving the material", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createMaterial(@RequestBody MaterialCreateRequest materialCreateRequest){
        try {
            MaterialResponse materialResponse = materialService.createMaterial(materialCreateRequest);
            ApiResponse response = new ApiResponse("success", "Create material successfully", materialResponse);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (EntityAlreadyExistsException e) {
            ApiResponse response = new ApiResponse("error", e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
        catch (Exception e) {
            ApiResponse response = new ApiResponse("error", "An error occurred while creating the material", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateMaterial(@PathVariable int id, @RequestBody MaterialUpdateRequest materialUpdateRequest) {
        try {
            MaterialResponse materialResponse = materialService.updateMaterial(id, materialUpdateRequest);
            ApiResponse response = new ApiResponse("success", "Update material successfully", materialResponse);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch(EntityNotFoundException e){
            ApiResponse response = new ApiResponse("error", e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        catch (EntityAlreadyExistsException e) {
            ApiResponse response = new ApiResponse("error", e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse("error", "An error occurred while updating the material", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteMaterial(@PathVariable Integer id){
        try {
            materialService.deleteMaterial(id);
            ApiResponse response = new ApiResponse("success", "Delete material successfully", null);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            ApiResponse response = new ApiResponse("error", e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse("error", "An error occurred while deleting the material", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}/toggle-status")
    public ResponseEntity<ApiResponse> toggleStatusMaterial(@PathVariable Integer id){
        try {
            MaterialResponse materialResponse = materialService.toggleMaterialStatus(id);
            ApiResponse response = new ApiResponse("success", "Toggle status brand successfully", materialResponse);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            ApiResponse response = new ApiResponse("error", e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse("error", "An error occurred while toggling the status of the material", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
