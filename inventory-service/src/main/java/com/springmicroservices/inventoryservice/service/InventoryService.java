package com.springmicroservices.inventoryservice.service;

import com.springmicroservices.inventoryservice.dto.InventoryResponse;
import com.springmicroservices.inventoryservice.model.Inventory;
import com.springmicroservices.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    @SneakyThrows // not for production
    public List<InventoryResponse> isInStock(List<String> skuCode) {
        log.info("Wait Started");
        log.info("Wait Ended");

        return inventoryRepository.findBySkuCodeIn(skuCode).stream()
                .map(inventory ->
                    InventoryResponse.builder()
                        .skuCode(inventory.getSkuCode())
                        .isInStock(inventory.getQuantity() > 0)
                        .build()
            ).toList();
    }
}
