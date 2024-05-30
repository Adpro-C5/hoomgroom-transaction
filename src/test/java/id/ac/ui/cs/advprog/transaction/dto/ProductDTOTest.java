package id.ac.ui.cs.advprog.transaction.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ProductDTOTest {

    ProductDTO productDTO;

    @BeforeEach
    void setUp() {
        productDTO = new ProductDTO();
        productDTO.setId(UUID.fromString("7af4edd5-97b8-49cc-8b75-4d4a9d0029ef"));
        productDTO.setProductName("meja batu");
        productDTO.setCategories(null);
        productDTO.setDescription("asli dari jaman megalitikum");
        productDTO.setImagePath("dummy_path");
        productDTO.setPrice(575000.0);
        productDTO.setDiscountedPrice(null);
        productDTO.setSales(0);
    }

    @Test
    void testGetId() {
        assertEquals(UUID.fromString("7af4edd5-97b8-49cc-8b75-4d4a9d0029ef"), this.productDTO.getId());
    }

    @Test
    void testGetName() {
        assertEquals("meja batu", this.productDTO.getProductName());
    }

    @Test
    void testGetCategories() {
        assertEquals(null, this.productDTO.getCategories());
    }

    @Test
    void testGetDescription() {
        assertEquals("asli dari jaman megalitikum", this.productDTO.getDescription());
    }

    @Test
    void testGetImagePath() {
        assertEquals("dummy_path", this.productDTO.getImagePath());
    }

    @Test
    void testGetPrice() {
        assertEquals(575000.0, this.productDTO.getPrice());
    }

    @Test
    void testGetDiscountedPrice() {
        assertEquals(null, this.productDTO.getDiscountedPrice());
    }

    @Test
    void testGetSales() {
        assertEquals(0, this.productDTO.getSales());
    }
}