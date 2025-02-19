package backend.datn.mapper;

import backend.datn.dto.response.VoucherRespone;
import backend.datn.entities.Voucher;


public class VoucherMapper {
    public static VoucherRespone toVoucherRespone(Voucher voucher) {
        return VoucherRespone.builder().
                id(voucher.getId()).
                voucherName(voucher.getVoucherName()).
                build();
    }
}
