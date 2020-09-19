package com.questa.blogapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.questa.blogapi.exception.QuestaException;
import com.questa.blogapi.model.Answer;
import com.questa.blogapi.model.UserFeedback;
import com.questa.blogapi.model.Follower;
import com.questa.blogapi.model.QuestaResponse;
import com.questa.blogapi.model.Question;
import com.questa.blogapi.repository.UserFeedbackRepository;
import com.questa.blogapi.repository.AnswerRepository;
import com.questa.blogapi.repository.FollowerRepository;
import com.questa.blogapi.repository.QuestionRepository;
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
	
	
	private static final Logger log = LoggerFactory.getLogger(QuestionService.class);

	public ResponseEntity<Object> createQuestion(Question question) throws QuestaException {
		log.info(question.toString());
		questionRepository.save(question);
		return new ResponseEntity<>(new QuestaResponse(ConstantUtil.QUESTION_CREATED_MESSAGE,ConstantUtil.SUCCESS_CODE,true), HttpStatus.OK);
	}
	
	public ResponseEntity<Object> createAnswer(Answer answer) throws QuestaException {
		log.info(answer.toString());
		answerRepository.save(answer);
		return new ResponseEntity<>(new QuestaResponse(ConstantUtil.ANSWER_CREATED_MESSAGE,ConstantUtil.SUCCESS_CODE,true), HttpStatus.OK);
	}
	
	public ResponseEntity<Object> createFollower(Follower follower) throws QuestaException {
		log.info(follower.toString());
		followerRepository.save(follower);
		return new ResponseEntity<>(new QuestaResponse(ConstantUtil.FOLLOWER_CREATED_MESSAGE,ConstantUtil.SUCCESS_CODE,true), HttpStatus.OK);
	}
	

	public ResponseEntity<Object> createUserFeedback(UserFeedback userFeedback) {
		log.info(userFeedback.toString());
		userFeedbackRepository.save(userFeedback);
		return new ResponseEntity<>(new QuestaResponse(ConstantUtil.USER_FEEDBACK_CREATED_MESSAGE,ConstantUtil.SUCCESS_CODE,true), HttpStatus.OK);
	}
	
	public List<Question> findAllQuestions(Integer userId) {
		Iterable<Question> questionIterable = questionRepository.findAll();
		List<Question> questionList = StreamSupport.stream(questionIterable.spliterator(), false)
				    .collect(Collectors.toList());
		return fetchAnswersAndFeedbacks(questionList, userId);
	}

	public List<Question> findAllByFollower(Integer userId) {
		Iterable<Follower> followerIterable = followerRepository.findByUserId(userId);
		List<Follower> followerList = StreamSupport.stream(followerIterable.spliterator(), false)
				    .collect(Collectors.toList());
		List<Question> questionList = new ArrayList<>();
		followerList.forEach(follwer -> questionList.add(findAllQuestionsByLoginUser(follwer.getQuestionId(),userId)));
		return fetchAnswersAndFeedbacks(questionList, userId);
	}

	public List<Question> findAllByAnswer(Integer userId) {
		Iterable<Answer> answerIterable = answerRepository.findByUserId(userId);
		List<Answer> answerList = StreamSupport.stream(answerIterable.spliterator(), false)
				    .collect(Collectors.toList());
		List<Question> questionList = new ArrayList<>();
		answerList.forEach(answer -> questionList.add(findAllQuestionsByLoginUser(answer.getQuestionId(), userId)));
		return fetchAnswersAndFeedbacks(questionList, userId);
	}
	
	public List<Question> findAllBySubjectTopic(Question question) {
		List<Question> questionList = questionRepository.findBySubjectIgnoreCaseContainingAndTopicIgnoreCaseContaining(question.getSubject(), question.getTopic());
		return fetchAnswersAndFeedbacks(questionList, question.getUserId());
	}
	
	private Question findAllQuestionsByLoginUser(Integer QuestionId, Integer userId) {
		Question question = questionRepository.findById(QuestionId).get();
		return fetchAnswersAndFeedbacksByQuestion(question, userId);
	}
	
	private List<Question> fetchAnswersAndFeedbacks(List<Question> questionList, Integer userId) {
		questionList.stream().forEach(que -> fetchAnswersAndFeedbacksByQuestion(que, userId));
		return questionList;
	}
	
	private Question fetchAnswersAndFeedbacksByQuestion(Question question, Integer userId) {
		question.setAnswerList(getAnswerListByQuestionId(question.getQuestionId(),userId));
		question.setNoOfAnswers(answerRepository.countByQuestionId(question.getQuestionId()));
		question.setNoOfFollowers(followerRepository.countByQuestionId(question.getQuestionId()));
		question.setNoOfLikes(followerRepository.countByQuestionIdAndLiked(question.getQuestionId(),true));
		followerRepository.findByQuestionIdAndUserId(question.getQuestionId(),userId).ifPresent(follower -> question.setFollowerByCurrentUser(follower));
		return question;
	}
	private List<Answer> getAnswerListByQuestionId(Integer questionId, Integer userId) throws QuestaException {
		Iterable<Answer> answerIterable = answerRepository.findByQuestionId(questionId);
		List<Answer> answerList = StreamSupport.stream(answerIterable.spliterator(), false)
				    .collect(Collectors.toList());
		answerList.stream().forEach(ans -> fetchAnswerDetails(ans, userId));
		return answerList;
	}
	
	private Answer fetchAnswerDetails(Answer answer, Integer userId) {
		answer.setNoOfDislikes(userFeedbackRepository.countByAnswerIdAndUnliked(answer.getAnswerId(), true));
		answer.setNoOfLikes(userFeedbackRepository.countByAnswerIdAndLiked(answer.getAnswerId(), true));
		userFeedbackRepository.findByAnswerIdAndUserId(answer.getAnswerId(), userId).ifPresent(feedback -> answer.setAnswerFeedbackByCurrentUser(feedback));
		return answer;
	}
}
