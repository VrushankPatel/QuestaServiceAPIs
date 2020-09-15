package com.questa.blogapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.questa.blogapi.exception.QuestaException;
import com.questa.blogapi.model.Answer;
import com.questa.blogapi.model.Follower;
import com.questa.blogapi.model.QuestaResponse;
import com.questa.blogapi.model.Question;
import com.questa.blogapi.model.UserFeedback;
import com.questa.blogapi.repository.AnswerRepository;
import com.questa.blogapi.repository.FollowerRepository;
import com.questa.blogapi.repository.QuestionRepository;
import com.questa.blogapi.repository.UserFeedbackRepository;
import com.questa.blogapi.util.ConstantUtil;

@Service("questionService")
public class QuestionService {

	@Autowired
	QuestionRepository questionRepository;
	
	@Autowired
	AnswerRepository answerRepository;
	
	@Autowired
	FollowerRepository followerRepository;
	
	@Autowired
	UserFeedbackRepository userFeedbackRepository;
	
	public ResponseEntity<Object> createQuestion(Question question) throws QuestaException {
		System.out.println(question.toString());
		questionRepository.save(question);
		return new ResponseEntity<>(new QuestaResponse(ConstantUtil.QUESTION_CREATED_MESSAGE,ConstantUtil.SUCCESS_CODE,true), HttpStatus.OK);
	}
	
	public ResponseEntity<Object> createAnswer(Answer answer) throws QuestaException {
		System.out.println(answer.toString());
		answerRepository.save(answer);
		return new ResponseEntity<>(new QuestaResponse(ConstantUtil.ANSWER_CREATED_MESSAGE,ConstantUtil.SUCCESS_CODE,true), HttpStatus.OK);
	}
	
	public ResponseEntity<Object> createFollower(Follower follower) throws QuestaException {
		System.out.println(follower.toString());
		followerRepository.save(follower);
		return new ResponseEntity<>(new QuestaResponse(ConstantUtil.FOLLOWER_CREATED_MESSAGE,ConstantUtil.SUCCESS_CODE,true), HttpStatus.OK);
	}
	

	public ResponseEntity<Object> createUserFeedback(UserFeedback userFeedback) {
		System.out.println(userFeedback.toString());
		userFeedbackRepository.save(userFeedback);
		return new ResponseEntity<>(new QuestaResponse(ConstantUtil.USER_FEEDBACK_CREATED_MESSAGE,ConstantUtil.SUCCESS_CODE,true), HttpStatus.OK);
	}
	
	public List<Question> getQuestionListByUserId(Integer userId) throws QuestaException {
		Iterable<Question> questionIterable = questionRepository.findByUserId(userId);
		System.out.println(questionIterable);
		List<Question> questionList = StreamSupport.stream(questionIterable.spliterator(), false)
				    .collect(Collectors.toList());
		questionList.stream().forEach(que -> que.setAnswerList(getAnswerListByQuestionId(que.getQuestionId())));
		questionList.stream().forEach(que -> que.setFollowerList(getFollowerListByQuestionId(que.getQuestionId())));
		return questionList;
	}
	
	private List<Answer> getAnswerListByQuestionId(Integer questionId) throws QuestaException {
		Iterable<Answer> answerIterable = answerRepository.findByQuestionId(questionId);
		System.out.println(answerIterable);
		List<Answer> answerList = StreamSupport.stream(answerIterable.spliterator(), false)
				    .collect(Collectors.toList());
		answerList.stream().forEach(ans -> ans.setUserFeedbackList(getUserFeedbackListByAnswerId(ans.getAnswerId())));
		return answerList;
	}
	
	private List<Follower> getFollowerListByQuestionId(Integer questionId) throws QuestaException {
		Iterable<Follower> followerIterable = followerRepository.findByQuestionId(questionId);
		System.out.println(followerIterable);
		List<Follower> followerList = StreamSupport.stream(followerIterable.spliterator(), false)
				    .collect(Collectors.toList());
		return followerList;
	}
	
	private List<UserFeedback> getUserFeedbackListByAnswerId(Integer answerId) throws QuestaException {
		Iterable<UserFeedback> followerIterable = userFeedbackRepository.findByAnswerId(answerId);
		System.out.println(followerIterable);
		List<UserFeedback> followerList = StreamSupport.stream(followerIterable.spliterator(), false)
				    .collect(Collectors.toList());
		return followerList;
	}

	public List<Question> findAllQuestions(Integer userId) {
		Iterable<Question> questionIterable = userId==null? questionRepository.findAll():questionRepository.findByUserId(userId);
		List<Question> questionList = StreamSupport.stream(questionIterable.spliterator(), false)
				    .collect(Collectors.toList());
		questionList.stream().forEach(que -> que.setAnswerList(getAnswerListByQuestionId(que.getQuestionId())));
		questionList.stream().forEach(que -> que.setFollowerList(getFollowerListByQuestionId(que.getQuestionId())));
		return questionList;
	}

	public List<Question> findAllByFollower(Integer userId) {
		Iterable<Follower> followerIterable = followerRepository.findByUserId(userId);
		System.out.println(followerIterable);
		List<Follower> followerList = StreamSupport.stream(followerIterable.spliterator(), false)
				    .collect(Collectors.toList());
		List<Question> questionList = new ArrayList<>();
		followerList.forEach(follwer -> questionList.add(findAllQuestionsByQuestionId(follwer.getQuestionId())));
		return questionList;
	}

	public List<Question> findAllByAnswer(Integer userId) {
		Iterable<Answer> answerIterable = answerRepository.findByUserId(userId);
		System.out.println(answerIterable + "  " + userId);
		List<Answer> answerList = StreamSupport.stream(answerIterable.spliterator(), false)
				    .collect(Collectors.toList());
		List<Question> questionList = new ArrayList<>();
		answerList.forEach(answer -> questionList.add(findAllQuestionsByQuestionId(answer.getQuestionId())));
		return questionList;
	}
	
	private Question findAllQuestionsByQuestionId(Integer QuestionId) {
		Question question = questionRepository.findById(QuestionId).get();
		question.setAnswerList(getAnswerListByQuestionId(question.getQuestionId()));
		question.setFollowerList(getFollowerListByQuestionId(question.getQuestionId()));
		return question;
	}
}
