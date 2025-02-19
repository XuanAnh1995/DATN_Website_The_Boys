package backend.datn.controllers;

import backend.datn.dto.ApiResponse;
import backend.datn.dto.request.CategoryRequest;
import backend.datn.dto.response.CategoryResponse;
import backend.datn.exceptions.EntityAlreadyExistsException;
import backend.datn.exceptions.EntityNotFoundException;
import backend.datn.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ApiResponse> createCategory(@RequestBody CategoryRequest categoryRequest) {
        try {
            CategoryResponse categoryResponse = categoryService.createCategory(categoryRequest);
            ApiResponse response = new ApiResponse("success", "Category created successfully", categoryResponse);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (EntityAlreadyExistsException e) {
            ApiResponse response = new ApiResponse("error", e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse("error", "An error occurred while creating the category", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Integer id, @RequestBody CategoryRequest categoryRequest) {
        try {
            CategoryResponse categoryResponse = categoryService.updateCategory(id, categoryRequest);
            ApiResponse response = new ApiResponse("success", "Category updated successfully", categoryResponse);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            ApiResponse response = new ApiResponse("error", e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (EntityAlreadyExistsException e) {
            ApiResponse response = new ApiResponse("error", e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse("error", "An error occurred while updating the category", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer id) {
        try {
            categoryService.deleteCategory(id);
            ApiResponse response = new ApiResponse("success", "Category deleted successfully", null);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            ApiResponse response = new ApiResponse("error", e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse("error", "An error occurred while deleting the category", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Integer id) {
        try {
            CategoryResponse categoryResponse = categoryService.getCategoryById(id);
            ApiResponse response = new ApiResponse("success", "Category retrieved successfully", categoryResponse);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            ApiResponse response = new ApiResponse("error", e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse("error", "An error occurred while retrieving the category", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllCategories(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        try {
            Page<CategoryResponse> categories = categoryService.getAllCategories(search, page, size, sortBy, sortDir);
            ApiResponse response = new ApiResponse("success", "Categories retrieved successfully", categories);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse("error", "An error occurred while retrieving the categories", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}