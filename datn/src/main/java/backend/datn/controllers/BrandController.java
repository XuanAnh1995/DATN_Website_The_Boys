package backend.datn.controllers;

import backend.datn.dto.ApiResponse;
import backend.datn.dto.request.BrandCreateRequest;
import backend.datn.dto.request.BrandUpdateRequest;
import backend.datn.dto.response.BrandResponse;
import backend.datn.exceptions.EntityAlreadyExistsException;
import backend.datn.exceptions.EntityNotFoundException;
import backend.datn.services.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllBrand(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        try {
            Page<BrandResponse> brandPage = brandService.getAllBrand(search, page, size, sortBy, sortDir);
            ApiResponse response = new ApiResponse("success", "Get all brand successfully", brandPage);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse("error", "An error occurred while retrieving the brand list", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getBrandById(@PathVariable int id) {
        try {
            BrandResponse brandResponse = brandService.getBrandById(id);
            ApiResponse response = new ApiResponse("success", "Get brand by id successfully", brandResponse);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            ApiResponse response = new ApiResponse("error", e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse("error", "An error occurred while retrieving the brand", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createBrand(@RequestBody BrandCreateRequest brandCreateRequest) {
        try {
            BrandResponse brandResponse = brandService.createBrand(brandCreateRequest);
            ApiResponse response = new ApiResponse("success", "Create brand successfully", brandResponse);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (EntityAlreadyExistsException e) {
            ApiResponse response = new ApiResponse("error", e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
        catch (Exception e) {
            ApiResponse response = new ApiResponse("error", "An error occurred while creating the brand", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateBrand(@PathVariable int id, @RequestBody BrandUpdateRequest brandUpdateRequest) {
        try {
            BrandResponse brandResponse = brandService.updateBrand(id, brandUpdateRequest);
            ApiResponse response = new ApiResponse("success", "Update brand successfully", brandResponse);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch(EntityNotFoundException e){
            ApiResponse response = new ApiResponse("error", e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        catch (EntityAlreadyExistsException e) {
            ApiResponse response = new ApiResponse("error", e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse("error", "An error occurred while updating the brand", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteBrand(@PathVariable Integer id){
        try {
            brandService.deleteBrand(id);
            ApiResponse response = new ApiResponse("success", "Delete brand successfully", null);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            ApiResponse response = new ApiResponse("error", e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse("error", "An error occurred while deleting the brand", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}/toggle-status")
    public ResponseEntity<ApiResponse> toggleStatusBrand(@PathVariable Integer id){
        try {
            BrandResponse brandResponse = brandService.toggleStatusBrand(id);
            ApiResponse response = new ApiResponse("success", "Toggle status brand successfully", brandResponse);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            ApiResponse response = new ApiResponse("error", e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse("error", "An error occurred while toggling the status of the brand", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
