package backend.datn.controllers;



import backend.datn.dto.ApiResponse;
import backend.datn.dto.request.VoucherCreateRequest;
import backend.datn.dto.request.VoucherUpdateRequest;
import backend.datn.dto.response.VoucherResponse;
import backend.datn.exceptions.EntityAlreadyExistsException;
import backend.datn.exceptions.EntityNotFoundException;
import backend.datn.services.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vouchers")
public class VoucherController {
    @Autowired private VoucherService voucherService;


    @GetMapping
    public ResponseEntity<?> getALlVoucher(@RequestParam(required = false) String search,
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10")int size,
                                                       @RequestParam(defaultValue = "id") String id,
                                                       @RequestParam(defaultValue = "asc")String sort){
        try {
            Page<VoucherResponse> voucherResponses = voucherService.getAllVoucher(search, page, size, id, sort);
            return ResponseEntity.ok(voucherResponses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi lấy danh sách voucher: " + e.getMessage());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getVoucherById(@PathVariable Integer id) {
       return ResponseEntity.ok(voucherService.getVoucherById(id));
    }
    @PostMapping
    public ResponseEntity<?> createVoucher(@RequestBody VoucherCreateRequest voucherRequest) {
        try {
            return ResponseEntity.ok(voucherService.createVoucher(voucherRequest));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi tạo voucher: " + e.getMessage());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateVoucher(@PathVariable Integer id, @RequestBody VoucherUpdateRequest updateRequestvoucherRequest) {
        try {
            VoucherResponse Response = voucherService.updateVoucher(updateRequestvoucherRequest,id);
            ApiResponse response = new ApiResponse("success", "Update  successfully", updateRequestvoucherRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch(EntityNotFoundException e){
            ApiResponse response = new ApiResponse("error", e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        catch (EntityAlreadyExistsException e) {
            ApiResponse response = new ApiResponse("error", e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse("error", "An error", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteVoucher(@PathVariable Integer id) {
        try {
            voucherService.deleteVoucher(id);
            return  ResponseEntity.ok(new ApiResponse("success", "Xóa voucher thành công", null));
        } catch (EntityNotFoundException e) {
            ApiResponse response = new ApiResponse("error", e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse("error", "Khong the xoa voucher", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
        @PutMapping("/{id}/toggle-status")
        public ResponseEntity<ApiResponse> toggleStatusVoucher(@PathVariable Integer id) {
            try {
                VoucherResponse voucherResponse = voucherService.toggleStatusVoucher(id);
                ApiResponse response = new ApiResponse("success", "Thành Công Thay Đổi ", voucherResponse);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (EntityNotFoundException e) {
                ApiResponse response = new ApiResponse("error", e.getMessage(), null);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            } catch (Exception e) {
                ApiResponse response = new ApiResponse("error", "Lỗi khi thay đổi trạng thái", null);
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }


