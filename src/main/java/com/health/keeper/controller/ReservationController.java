package com.health.keeper.controller;

import com.health.keeper.entity.ReservationEntity;
import com.health.keeper.service.ReservationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/reservations")
@Controller
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

//    @PostMapping("/book")
//    public boolean bookLesson(@RequestParam Long lessonId, @RequestParam Long userId, @RequestParam LocalDate reservationDate) {
//        return reservationService.bookLesson(lessonId, userId, reservationDate);
//    }

    @GetMapping("/user/{userId}")
    public List<ReservationEntity> getReservationsByUserId(@PathVariable Long userId) {
        return reservationService.getReservationsByUserId(userId);
    }

    @Transactional
    @GetMapping("/{id}")
    public String reservation(@PathVariable("id") Long id){
        System.out.println("id = " + id);
        List<ReservationEntity> result =
        reservationService.getReservationsByUserId(id);
        System.out.println("result : " + result);
        //result : [com.health.keeper.entity.ReservationEntity@1833b721]
        return "lessonReservation";
    }
}
