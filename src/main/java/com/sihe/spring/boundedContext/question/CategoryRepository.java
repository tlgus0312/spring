package com.sihe.spring.boundedContext.question;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// @Repository 어노테이션은 선택 사항이지만 명시해 두면 좋습니다.
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    // 필요한 커스텀 메서드가 있으면 여기에 선언
}
