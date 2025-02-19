package backend.datn.controllers;

import backend.datn.dto.ApiResponse;
import backend.datn.dto.response.statistic.*;
import backend.datn.services.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/statistics")
public class StatisticController {

    @Autowired
    private StatisticService statisticService;

    @GetMapping("/daily-revenue")
    public ResponseEntity<ApiResponse> getDailyRevenue() {
        try {
            List<DailyRevenueResponse> data = statisticService.getDailyRevenue();
            ApiResponse response = new ApiResponse("success", "Fetched daily revenue successfully", data);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse("error", "Failed to fetch daily revenue", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/weekly-revenue")
    public ResponseEntity<ApiResponse> getWeeklyRevenue() {
        try {
            List<WeeklyRevenueResponse> data = statisticService.getWeeklyRevenue();
            ApiResponse response = new ApiResponse("success", "Fetched weekly revenue successfully", data);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse("error", "Failed to fetch weekly revenue", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/monthly-revenue")
    public ResponseEntity<ApiResponse> getMonthlyRevenue() {
        try {
            List<MonthlyRevenueResponse> data = statisticService.getMonthlyRevenue();
            ApiResponse response = new ApiResponse("success", "Fetched monthly revenue successfully", data);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse("error", "Failed to fetch monthly revenue", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/yearly-revenue")
    public ResponseEntity<ApiResponse> getYearlyRevenue() {
        try {
            List<YearlyRevenueResponse> data = statisticService.getYearlyRevenue();
            ApiResponse response = new ApiResponse("success", "Fetched yearly revenue successfully", data);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse("error", "Failed to fetch yearly revenue", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/top-5-products")
    public ResponseEntity<ApiResponse> getTopSellingProducts(@RequestParam String startDate, @RequestParam String endDate) {
        try {
            List<ProductDetailDTO> data = statisticService.getTop5BestSellingProductDetailInAPeriodOfTime(startDate, endDate);
            ApiResponse response = new ApiResponse("success", "Fetched top-selling products successfully", data);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse("error", "Failed to fetch top-selling products", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/total-revenue")
    public ResponseEntity<ApiResponse> getTotalRevenue() {
        try {
            BigDecimal data = statisticService.getTotalRevenue();
            ApiResponse response = new ApiResponse("success", "Fetched total revenue successfully", data);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse("error", "Failed to fetch total revenue", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/total-customers")
    public ResponseEntity<ApiResponse> getTotalCustomers() {
        try {
            Integer data = statisticService.getNumberOfCustomers();
            ApiResponse response = new ApiResponse("success", "Fetched total customers successfully", data);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse("error", "Failed to fetch total customers", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/total-invoices")
    public ResponseEntity<ApiResponse> getTotalInvoices() {
        try {
            Integer data = statisticService.getNumberOfInvoices();
            ApiResponse response = new ApiResponse("success", "Fetched total invoices successfully", data);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse("error", "Failed to fetch total invoices", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/total-admins")
    public ResponseEntity<ApiResponse> getTotalAdmins() {
        try {
            Integer data = statisticService.getNumberOfAdmin();
            ApiResponse response = new ApiResponse("success", "Fetched total admins successfully", data);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse("error", "Failed to fetch total admins", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/total-staff")
    public ResponseEntity<ApiResponse> getTotalStaff() {
        try {
            Integer data = statisticService.getNumberOfStaff();
            ApiResponse response = new ApiResponse("success", "Fetched total staff successfully", data);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse("error", "Failed to fetch total staff", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
