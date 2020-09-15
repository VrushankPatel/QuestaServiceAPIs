package com.questa.blogapi.controller;

import java.util.List;

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
import com.questa.blogapi.model.Follower;
import com.questa.blogapi.model.Question;
import com.questa.blogapi.model.UserFeedback;
import com.questa.blogapi.service.QuestionService;
import com.questa.blogapi.util.ConstantUtil;

@RestController
public class QuestionController {

	@Autowired
	private QuestionService questionService;

	@RequestMapping(value = ConstantUtil.CREATE_QUESTION_ENDPOINT, method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> createQuestion(@RequestBody Question question) throws QuestaException {
		return questionService.createQuestion(question);
	}
	
	@RequestMapping(value = ConstantUtil.CREATE_ANSWER_ENDPOINT, method = RequestMethod.POST, produces = ConstantUtil.PRODUCE_APP_JSON)
	public ResponseEntity<Object> createAnswer(@RequestBody Answer answer) throws QuestaException {
		return questionService.createAnswer(answer);
	}
	
	@RequestMapping(value = ConstantUtil.CREATE_FOLLWER_ENDPOINT, method = RequestMethod.POST, produces = ConstantUtil.PRODUCE_APP_JSON)
	public ResponseEntity<Object> createFollower(@RequestBody Follower follower) throws QuestaException {
		return questionService.createFollower(follower);
	}
	
	@RequestMapping(value = ConstantUtil.CREATE_FEEDBACK_ENDPOINT, method = RequestMethod.POST, produces = ConstantUtil.PRODUCE_APP_JSON)
	public ResponseEntity<Object> createUserFeedback(@RequestBody UserFeedback userFeedback) throws QuestaException {
		return questionService.createUserFeedback(userFeedback);
	}
	
	@RequestMapping(value = ConstantUtil.FIND_ALL_QUESTIONS_ENDPOINT, method = RequestMethod.POST, produces = ConstantUtil.PRODUCE_APP_JSON)
	public List<Question> findAllQuestions() throws QuestaException {
		return questionService.findAllQuestions(null);
	}
	
	@RequestMapping(value = ConstantUtil.FIND_ALL_BY_FOLLOWER_ENDPOINT, method = RequestMethod.POST, produces = ConstantUtil.PRODUCE_APP_JSON)
	public List<Question> findAllByFollower(@PathVariable Integer userId) throws QuestaException {
		return questionService.findAllByFollower(userId);
	}
	
	@RequestMapping(value = ConstantUtil.FIND_ALL_BY_ANSWER_ENDPOINT, method = RequestMethod.POST, produces = ConstantUtil.PRODUCE_APP_JSON)
	@ResponseBody
	public List<Question> findAllByAnswer(@PathVariable Integer userId) throws QuestaException {
		return questionService.findAllByAnswer(userId);
	}
}
