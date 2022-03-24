package me.study.datajpa.repository;

import me.study.datajpa.entity.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    /**
     * @Description [새로운 Entity인지 판별하는 법?]
     **/
    @Test
    public void save(){
        Item item = new Item("A");
        itemRepository.save(item);
    }
}