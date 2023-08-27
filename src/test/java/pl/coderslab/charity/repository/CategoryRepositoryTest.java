package pl.coderslab.charity.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.coderslab.charity.model.Category;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
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
