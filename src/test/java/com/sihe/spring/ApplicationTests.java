package com.sihe.spring;

import com.sihe.spring.boundedContext.question.Question;
import com.sihe.spring.boundedContext.question.QuestionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ApplicationTests {
	@Autowired
	private QuestionRepository questionRepository;

	@Test
	@DisplayName("데이터 저장하기")
	void contextLoads() {
		//Question q1 = new Question();
		//q1.setSubject("자존감/마음건강");
		//q1.setContent("요즘 아무 의욕이 없어요 나만 그런가요?");
		//q1.setCreateDate(LocalDateTime.now());
		//questionRepository.save(q1);  // 첫번째 질문 저장 save insert query

		//Question q2 = new Question();
		//q2.setSubject("대인관계 고민");
		//q2.setContent("친구랑 사이가 멀어졌는데, 제가 먼저 연락해야되나요?");
		//q2.setCreateDate(LocalDateTime.now());
		//questionRepository.save(q2);  // 두번째 질문 저장

		//Question q3 = new Question();
		//q3.setSubject("연애/이성 문제");
		//q3.setContent("일상속에서 자꾸 생각나는건 좋아하는건가요?");
		//q3.setCreateDate(LocalDateTime.now());
		//questionRepository.save(q3);  // 세번째 질문 저장

		Question q4 = new Question();
		q4.setSubject("일상 속 선택 고민");
		q4.setContent("취업을 할지,대학원에 갈지 고민이에요");
		q4.setCreateDate(LocalDateTime.now());
		questionRepository.save(q4);


	}

	@Test
	@DisplayName("findAll")
	void t002() {
		List<Question> all = questionRepository.findAll();
		assertEquals(4, all.size());

		Question q = all.get(0);
		assertEquals("자존감/마음건강", q.getSubject());
	}

	@Test
	@DisplayName("findAll")
	void t003() {
		Optional<Question> oq = questionRepository.findById(1);
		if (oq.isPresent()) {
			Question q = oq.get();
			assertEquals("자존감/마음건강", q.getSubject());
		}
	}
	@Test
	@DisplayName("findBySubject")
	void t004(){
		Question q = questionRepository.findBySubject("자존감/마음건강");
		assertEquals(1, q.getId());
	}
	@Test
	@DisplayName("findBySubjectAndContent")
	void t005(){
		Question q = this.questionRepository.findBySubjectAndContent(
				"자존감/마음건강", "요즘 아무 의욕이 없어요 나만 그런가요?");
		assertEquals(1, q.getId());
	}

	@Test
	@DisplayName("findBySubjectAndContent")
	void t006(){
		List<Question> qList = questionRepository.findBySubjectLike("자존%");
		Question q = qList.get(0);
		assertEquals("자존감/마음건강", q.getSubject());
	}
	//데이터 수정
	@Test
	@DisplayName("데이터 수정하기")
	void t007(){
		Optional<Question> oq = questionRepository.findById(1);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		q.setSubject("자존감/마음건강");
		questionRepository.save(q);
	}

	//데이터 삭제
	@Test
	@DisplayName("데이터 삭제하기")
	void t008(){
		//select count(*) from question;
		assertEquals(4, questionRepository.count());
		Optional<Question> oq = questionRepository.findById(1);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		questionRepository.delete(q);
		assertEquals(3, questionRepository.count());
	}
}
