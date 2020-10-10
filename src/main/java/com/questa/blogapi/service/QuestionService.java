package com.questa.blogapi.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.questa.blogapi.exception.QuestaException;
import com.questa.blogapi.model.Answer;
import com.questa.blogapi.model.AnswerFeedback;
import com.questa.blogapi.model.Follower;
import com.questa.blogapi.model.QuestaResponse;
import com.questa.blogapi.model.Question;
import com.questa.blogapi.model.QuestionFeedback;
import com.questa.blogapi.model.User;
import com.questa.blogapi.repository.AnswerFeedbackRepository;
import com.questa.blogapi.repository.AnswerRepository;
import com.questa.blogapi.repository.FollowerRepository;
import com.questa.blogapi.repository.QuestionFeedbackRepository;
import com.questa.blogapi.repository.QuestionRepository;
import com.questa.blogapi.repository.UserRepository;
import com.questa.blogapi.util.ConstantUtil;

@Service("questionService")
public class QuestionService {

	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private AnswerRepository answerRepository;
	
	@Autowired
	private FollowerRepository followerRepository;
	
	@Autowired
	private AnswerFeedbackRepository answerFeedbackRepository;
	
	@Autowired
	private QuestionFeedbackRepository questionFeedbackRepository;
		
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private NotificationService notificationService;
	
	@Value("${spring.mail.username}")
	private String fromEmail;
	
	private static final Logger log = LoggerFactory.getLogger(QuestionService.class);

	public ResponseEntity<Object> createQuestion(Question question) throws QuestaException {
		log.info(question.toString());
		questionRepository.save(question);
		Follower follower = new Follower();
		follower.setQuestionId(question.getQuestionId());
		follower.setUserId(question.getUserId());
		follower.setFollowed(true);
		followerRepository.save(follower);
		return new ResponseEntity<>(new QuestaResponse(ConstantUtil.QUESTION_CREATED_MESSAGE,ConstantUtil.SUCCESS_CODE,true), HttpStatus.OK);
	}
	
	public ResponseEntity<Object> createAnswer(Answer answer) throws QuestaException {
		log.info("Saving answer details :: " + answer.toString());
		answerRepository.findByQuestionIdAndUserId(answer.getQuestionId(), answer.getUserId()).ifPresent(ans -> answer.setAnswerId(ans.getAnswerId()));
		answerRepository.save(answer);
		List<String> emailIds = new ArrayList<>();
		log.info("Sending notification mail to the questioned user...");
		followerRepository.findByQuestionId(answer.getQuestionId()).forEach(follower -> { //get question details for that
			userRepository.findByUserId(follower.getUserId()).ifPresent(user -> {
				emailIds.add(user.getEmail());
			});
		});
		if(!emailIds.isEmpty()) {
			String text = "<p>Hi There!</p><p>Your question has been commented by "+userRepository.findByUserId(answer.getUserId()).orElse(new User()).getNickName()+" as below:</p>"
					+ "<p>Question: "+questionRepository.findByQuestionId(answer.getQuestionId()).orElse(new Question()).getQuestionDesc()+"</p>"
					+ "<p>Answer: "+answer.getAnswerDesc()+"</p>"
					+ "<p>Login <a href=\""+ServletUriComponentsBuilder.fromCurrentContextPath().toUriString()+"/Signin\">Here</a> to reply on the comment.</p>"
					+ "<p>For any queries/concerns, please reach out to us <a href=\"mailto:"+fromEmail+",\">"+fromEmail+",</a></p><p>Thanks,</p><p>Questa Support</p>";
			notificationService.sendBccNotification(emailIds.stream().toArray(String[]::new), "Profile updated in Questa", text);
		}
		
		return new ResponseEntity<>(new QuestaResponse(ConstantUtil.ANSWER_CREATED_MESSAGE,ConstantUtil.SUCCESS_CODE,true), HttpStatus.OK);
	}
	
	public ResponseEntity<Object> deleteAnswer(Integer answerId) throws QuestaException {
		answerRepository.findByAnswerId(answerId).ifPresent(ans -> {
			answerFeedbackRepository.findByAnswerIdAndUserId(ans.getAnswerId(), ans.getUserId()).ifPresent(ansfdback -> {
				log.info("deleteing answer feedback details :: " + ansfdback.toString());
				answerFeedbackRepository.delete(ansfdback);
			});
			log.info("deleteing answer details :: " + ans.toString());
			answerRepository.delete(ans);
		});		
		return new ResponseEntity<>(new QuestaResponse(ConstantUtil.ANSWER_DELETED_MESSAGE,ConstantUtil.SUCCESS_CODE,true), HttpStatus.OK);
	}
	
	public ResponseEntity<Object> createFollower(Follower follower) throws QuestaException {
		log.info(follower.toString());
		followerRepository.findByQuestionIdAndUserId(follower.getQuestionId(), follower.getUserId()).ifPresent(flwer -> follower.setFolllowerId(flwer.getFolllowerId()));
		if(follower.getFollowed()) {
			followerRepository.save(follower);
			return new ResponseEntity<>(new QuestaResponse(ConstantUtil.FOLLOWER_CREATED_MESSAGE,ConstantUtil.SUCCESS_CODE,true), HttpStatus.OK);
		}else {
			followerRepository.delete(follower);
			return new ResponseEntity<>(new QuestaResponse(ConstantUtil.FOLLOWER_DELETED_MESSAGE,ConstantUtil.SUCCESS_CODE,true), HttpStatus.OK);
		}
	}
	
	public ResponseEntity<Object> createQuestionFeedback(QuestionFeedback questionFeedback) {
		log.info(questionFeedback.toString());
		questionFeedbackRepository.findByQuestionIdAndUserId(questionFeedback.getQuestionId(), questionFeedback.getUserId())
					.ifPresent(qback -> questionFeedback.setFeedbackId(qback.getFeedbackId()));
		questionFeedbackRepository.save(questionFeedback);
		return new ResponseEntity<>(new QuestaResponse(ConstantUtil.USER_FEEDBACK_CREATED_MESSAGE,ConstantUtil.SUCCESS_CODE,true), HttpStatus.OK);
	}
	
	public ResponseEntity<Object> createAnswerFeedback(AnswerFeedback answerFeedback) {
		log.info(answerFeedback.toString());
		answerFeedbackRepository.findByAnswerIdAndUserId(answerFeedback.getAnswerId(), answerFeedback.getUserId())
					.ifPresent(fback -> answerFeedback.setFeedbackId(fback.getFeedbackId()));
		answerFeedbackRepository.save(answerFeedback);
		return new ResponseEntity<>(new QuestaResponse(ConstantUtil.USER_FEEDBACK_CREATED_MESSAGE,ConstantUtil.SUCCESS_CODE,true), HttpStatus.OK);
	}
	
	public List<Question> findAllQuestions(Integer userId) {
		List<Question> questionList = questionRepository.findByOrderByCreateDateDesc();
		return fetchAnswersAndFeedbacks(questionList, userId);
	}
	
	public List<Question> findAllQuestionsForAdmin(Integer userId) {
		List<Question> questionList = new ArrayList<>();
		List<Integer> questionIdList = new ArrayList<>();
		answerFeedbackRepository.findByUserIdAndReportDescNotNull(userId).ifPresent(ansFdBk ->{
			answerRepository.findByAnswerId(ansFdBk.getAnswerId()).ifPresent(ans ->{
				questionIdList.add(ans.getQuestionId());
			});
		});
		questionRepository.findDistinctByQuestionIdIn(questionIdList).forEach(que -> {
			questionList.add(que);
		});
		return fetchAnswersAndFeedbacks(questionList, userId);
	}

	public List<Question> findAllQuestionsByUser(Integer userId) {
		List<Question> questionList = questionRepository.findByUserIdOrderByCreateDateDesc(userId);
		return fetchAnswersAndFeedbacks(questionList, userId);
	}

	public List<Question> findAllByFollower(Integer userId) {
		List<Question> questionList = new ArrayList<>();
		followerRepository.findByUserId(userId).forEach(follwer -> questionList.add(findAllQuestionsByLoginUser(follwer.getQuestionId(),userId)));
		return fetchAnswersAndFeedbacks(questionList, userId);
	}

	public List<Question> findAllByAnswer(Integer userId) {
		List<Question> questionList = new ArrayList<>();
		answerRepository.findByUserIdOrderByCreateDateDesc(userId).forEach(answer -> questionList.add(findAllQuestionsByLoginUser(answer.getQuestionId(), userId)));
		return fetchAnswersAndFeedbacks(questionList, userId);
	}
	
	public List<Question> findAllBySubjectTopic(Question question) {
		List<Question> questionList = fetchAnswersAndFeedbacks(questionRepository.findBySubjectAndTopicIgnoreCaseContainingOrderByCreateDateDesc(question.getSubject(), question.getTopic()), question.getUserId());
		List<Question> questionWithAnsList = new ArrayList<>();
		questionList.forEach(que -> {
			if(que.getNoOfAnswers()>0) questionWithAnsList.add(que);
		});
		return questionWithAnsList;
	}
	
	private Question findAllQuestionsByLoginUser(Integer QuestionId, Integer userId) {
		Question question = questionRepository.findById(QuestionId).get();
		return fetchAnswersAndFeedbacksByQuestion(question, userId);
	}
	
	private List<Question> fetchAnswersAndFeedbacks(List<Question> questionList, Integer userId) {
		questionList.stream().forEach(que -> {
										userRepository.findByUserId(que.getUserId()).ifPresent(usr -> que.setNickName(usr.getNickName()));
										fetchAnswersAndFeedbacksByQuestion(que, userId);
									});
		return questionList;
	}
	
	private Question fetchAnswersAndFeedbacksByQuestion(Question question, Integer userId) {
		List<Answer> answerList = answerRepository.findByQuestionIdOrderByCreateDateDesc(question.getQuestionId());
		answerList.stream().forEach(ans -> fetchAnswerDetails(ans, userId));
		question.setAnswerList(answerList);
		questionFeedbackRepository.findByQuestionIdAndUserId(question.getQuestionId(), userId).ifPresent(quefb -> question.setQuestionFeedbackByCurrentUser(quefb));
		question.setNoOfLikes(questionFeedbackRepository.countByQuestionIdAndLiked(question.getQuestionId(), true));
		question.setNoOfDislikes(questionFeedbackRepository.countByQuestionIdAndLiked(question.getQuestionId(), true));
		question.setNoOfAnswers(answerRepository.countByQuestionId(question.getQuestionId()));
		question.setNoOfFollowers(followerRepository.countByQuestionId(question.getQuestionId()));
		followerRepository.findByQuestionIdAndUserId(question.getQuestionId(),userId).ifPresent(follower -> question.setFollowerByCurrentUser(follower));
		return question;
	}
	
	private Answer fetchAnswerDetails(Answer answer, Integer userId) {
		userRepository.findByUserId(answer.getUserId()).ifPresent(usr -> answer.setNickName(usr.getNickName()));
		answer.setNoOfDislikes(answerFeedbackRepository.countByAnswerIdAndUnliked(answer.getAnswerId(), true));
		answer.setNoOfLikes(answerFeedbackRepository.countByAnswerIdAndLiked(answer.getAnswerId(), true));
		answerFeedbackRepository.findByAnswerIdAndUserId(answer.getAnswerId(),userId).ifPresent(feedback -> answer.setAnswerFeedbackByCurrentUser(feedback));
		return answer;
	}
}
