package ca.mcgill.ecse321.videogamessystem.controller;

import ca.mcgill.ecse321.videogamessystem.dto.SpecificOrderDto.SpecificOrderRequestDto;
import ca.mcgill.ecse321.videogamessystem.dto.SpecificOrderDto.SpecificOrderResponseDto;
import ca.mcgill.ecse321.videogamessystem.model.SpecificOrder;
import ca.mcgill.ecse321.videogamessystem.service.SpecificOrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class SpecificOrderController {

    @Autowired
    private SpecificOrderService specificOrderService;

    @PostMapping("/orders")
    public ResponseEntity<SpecificOrderResponseDto> createOrder(@Valid @RequestBody SpecificOrderRequestDto orderRequest) {
        SpecificOrder order = specificOrderService.createSpecificOrder(
                orderRequest.getOrderDate(),
                orderRequest.getCardNumber(),
                orderRequest.getCustomerId()
        );
        return ResponseEntity.ok(new SpecificOrderResponseDto(order));
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<SpecificOrderResponseDto> getOrderById(@PathVariable int orderId) {
        SpecificOrder order = specificOrderService.getOrderById(orderId);
        return ResponseEntity.ok(new SpecificOrderResponseDto(order));
    }

    @GetMapping("/orders/customer/{customerId}")
    public ResponseEntity<List<SpecificOrderResponseDto>> getOrdersByCustomer(@PathVariable Long customerId) {
        List<SpecificOrder> orders = specificOrderService.getOrdersByCustomer(customerId);
        List<SpecificOrderResponseDto> responseDtos = orders.stream()
                .map(SpecificOrderResponseDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDtos);
    }

    @PutMapping("/orders/{orderId}/cardNumber")
    public ResponseEntity<SpecificOrderResponseDto> updateCardNumber(
            @PathVariable int orderId,
            @RequestParam int newCardNumber) {
        SpecificOrder updatedOrder = specificOrderService.updateCardNumber(orderId, newCardNumber);
        return ResponseEntity.ok(new SpecificOrderResponseDto(updatedOrder));
    }

    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable int orderId) {
        specificOrderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/orders")
    public ResponseEntity<List<SpecificOrderResponseDto>> getAllOrders() {
        List<SpecificOrder> orders = specificOrderService.getAllOrders();
        List<SpecificOrderResponseDto> responseDtos = orders.stream()
                .map(SpecificOrderResponseDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDtos);
    }
}

