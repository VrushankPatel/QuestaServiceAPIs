package com.questa.blogapi.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.questa.blogapi.exception.QuestaException;
import com.questa.blogapi.model.Answer;
import com.questa.blogapi.model.AnswerFeedback;
import com.questa.blogapi.model.Follower;
import com.questa.blogapi.model.Question;
import com.questa.blogapi.model.QuestionFeedback;
import com.questa.blogapi.service.QuestionService;
import com.questa.blogapi.util.ConstantUtil;

@RestController
public class QuestionController {

	@Autowired
	private QuestionService questionService;

	
	private static final Logger log = LoggerFactory.getLogger(QuestionController.class);

	@RequestMapping(value = ConstantUtil.CREATE_QUESTION_ENDPOINT, method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> createQuestion(@RequestBody Question question) throws QuestaException {
		log.info("Calling "+ ConstantUtil.CREATE_QUESTION_ENDPOINT +" endpoint");
		return questionService.createQuestion(question);
	}
	
	@RequestMapping(value = ConstantUtil.CREATE_ANSWER_ENDPOINT, method = RequestMethod.POST, produces = ConstantUtil.PRODUCE_APP_JSON)
	public ResponseEntity<Object> createAnswer(@RequestBody Answer answer) throws QuestaException {
		log.info("Calling "+ ConstantUtil.CREATE_ANSWER_ENDPOINT +" endpoint");
		
		return questionService.createAnswer(answer);
	}
	
	@RequestMapping(value = ConstantUtil.CREATE_FOLLWER_ENDPOINT, method = RequestMethod.POST, produces = ConstantUtil.PRODUCE_APP_JSON)
	public ResponseEntity<Object> createFollower(@RequestBody Follower follower) throws QuestaException {
		log.info("Calling "+ ConstantUtil.CREATE_FOLLWER_ENDPOINT +" endpoint");
		return questionService.createFollower(follower);
	}
	
	@RequestMapping(value = ConstantUtil.CREATE_QUESTION_FEEDBACK_ENDPOINT, method = RequestMethod.POST, produces = ConstantUtil.PRODUCE_APP_JSON)
	public ResponseEntity<Object> createQuestionFeedback(@RequestBody QuestionFeedback questionFeedback) throws QuestaException {
		log.info("Calling "+ ConstantUtil.CREATE_QUESTION_FEEDBACK_ENDPOINT +" endpoint");
		return questionService.createQuestionFeedback(questionFeedback);
	}
	
	@RequestMapping(value = ConstantUtil.CREATE_ANSWER_FEEDBACK_ENDPOINT, method = RequestMethod.POST, produces = ConstantUtil.PRODUCE_APP_JSON)
	public ResponseEntity<Object> createAnswerFeedback(@RequestBody AnswerFeedback userFeedback) throws QuestaException {
		log.info("Calling "+ ConstantUtil.CREATE_ANSWER_FEEDBACK_ENDPOINT +" endpoint");
		return questionService.createAnswerFeedback(userFeedback);
	}
	
	@RequestMapping(value = ConstantUtil.FIND_ALL_QUESTIONS_ENDPOINT, method = RequestMethod.POST, produces = ConstantUtil.PRODUCE_APP_JSON)
	public List<Question> findAllQuestions(@PathVariable Integer userId) throws QuestaException {
		log.info("Calling "+ ConstantUtil.FIND_ALL_QUESTIONS_ENDPOINT +" endpoint");
		return questionService.findAllQuestions(userId);
	}
	
	@RequestMapping(value = ConstantUtil.FIND_ALL_QUESTIONS_FOR_ADMIN_ENDPOINT, method = RequestMethod.POST, produces = ConstantUtil.PRODUCE_APP_JSON)
	public List<Question> findAllQuestionsForAdmin(@PathVariable Integer userId) throws QuestaException {
		log.info("Calling "+ ConstantUtil.FIND_ALL_QUESTIONS_FOR_ADMIN_ENDPOINT +" endpoint");
		return questionService.findAllQuestionsForAdmin(userId);
	}
	
	@RequestMapping(value = ConstantUtil.FIND_ALL_BY_FOLLOWER_ENDPOINT, method = RequestMethod.POST, produces = ConstantUtil.PRODUCE_APP_JSON)
	public List<Question> findAllByFollower(@PathVariable Integer userId) throws QuestaException {
		log.info("Calling "+ ConstantUtil.FIND_ALL_BY_FOLLOWER_ENDPOINT +" endpoint");
		return questionService.findAllByFollower(userId);
	}
	
	@RequestMapping(value = ConstantUtil.FIND_ALL_BY_ANSWER_ENDPOINT, method = RequestMethod.POST, produces = ConstantUtil.PRODUCE_APP_JSON)
	@ResponseBody
	public List<Question> findAllByAnswer(@PathVariable Integer userId) throws QuestaException {
		log.info("Calling "+ ConstantUtil.FIND_ALL_BY_ANSWER_ENDPOINT +" endpoint");
		return questionService.findAllByAnswer(userId);
	}
	
	@RequestMapping(value = ConstantUtil.FIND_ALL_BY_SUBJECT_TOPIC_ENDPOINT, method = RequestMethod.POST, produces = ConstantUtil.PRODUCE_APP_JSON)
	@ResponseBody
	public List<Question> findAllBySubjectTopic(@RequestBody Question question) throws QuestaException {
		log.info("Calling "+ ConstantUtil.FIND_ALL_BY_SUBJECT_TOPIC_ENDPOINT +" endpoint");
		return questionService.findAllBySubjectTopic(question);
	}
}
