package com.spring.miniposbackend.repository.sale;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.spring.miniposbackend.model.sale.SaleTemporary;

@Repository
public interface SaleTemporaryRepository extends JpaRepository<SaleTemporary, Long> {

    List<SaleTemporary> findBySeatId(Integer seatId);

    @Query(value = "select s from SaleTemporary s where s.seat.id = ?1 and s.isPrinted = ?2")
    List<SaleTemporary> findBySeatIdWithisPrinted(Integer seatId, boolean isPrinted);

    @Query(value = "select s from SaleTemporary s where s.seat.id = ?1 and s.isPrinted = ?2 and s.cancel = ?3")
    List<SaleTemporary> findBySeatIdWithIsPrintedCancel(Integer seatId, boolean isPrinted, boolean cancel);

    @Query(
            value = "select * from Sales_temp where seat_id=(select seat_id from Sales_temp where user_id=?1 order by value_date desc limit 1)",
            nativeQuery = true)
    List<SaleTemporary> findByUserId(Integer userId);

    @Query(
            value = "select * from Sales_temp where seat_id=(select seat_id from Sales_temp where user_id=?1 and is_printed=?2 and cancel=?3 order by value_date desc limit 1)",
            nativeQuery = true)
    List<SaleTemporary> findByUserIdWithIsPrintedCancel(Integer seatId, boolean isPrinted, boolean cancel);

    @Query(
            value = "select * from Sales_temp where seat_id=(select seat_id from Sales_temp where user_id=?1 and is_printed=?2  order by value_date desc limit 1)",
            nativeQuery = true)
    List<SaleTemporary> findByUserIdWithisPrinted(Integer seatId, boolean isPrinted);

    
    @Modifying
    @Query(value = "delete from Sales_temp where seat_id=?1", nativeQuery = true)
    void deleteBySeatId(Integer seatId);
    
    
    @Modifying
    @Query(value = "delete from Sales_temp where id=?1", nativeQuery = true)
    void deleteBySaleTempId(Long saleTmpId);
    @Modifying
    @Query(value = "update Sales_temp set useredit_id=?1 where seat_id=?2", nativeQuery = true)
    void updateUserEdit(Integer userId,Integer seatId);
}
