package me.study.datajpa.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @FileName Item.java
 * @Author ccs
 * @Version 1.0.0
 * @Date 2022-03-24
 * @Description [새로운 Entity인지 판별하는 법?] Entity
 **/
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item
        /**
         * @Description [새로운 Entity인지 판별하는 법?] 식별자가 보통 쓰는 @Id @GeneratedValue Long id이 아니고 @Id String id 일 경우(식별자를 강제로 주는 경우) Persistable 상속
         **/
        extends BaseEntity
        implements Persistable<String>{
    @Id
    private String id;

    public Item(String id){
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    /**
     * @Description [새로운 Entity인지 판별하는 법?] 영속성 신규인지 판별하는 메소드를 오버리아드 하여 사용! 보통 @CreatedDate 값을 추가로 대입하여 null 체크로 함
     **/
    @Override
    public boolean isNew() {
        return super.getCreatedDate() == null;
    }
}
