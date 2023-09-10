package pl.coderslab.charity.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.extension.ExtendWith;
import pl.coderslab.charity.model.Category;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testSaveCategory() {
        Category category = new Category();
        category.setName("Test category");

        Category savedCategory = categoryRepository.save(category);
        assertNotNull(savedCategory.getId());
        assertEquals("Test category", savedCategory.getName());
    }

    @Test
    public void testFindCategoryById() {
        Category category = new Category();
        category.setName("Test category");

        Category savedCategory = categoryRepository.save(category);

        Long categoryId = savedCategory.getId();
        Category foundCategory = categoryRepository.findById(categoryId).orElse(null);
        assertNotNull(foundCategory);
        assertEquals("Test category", foundCategory.getName());
    }

}
